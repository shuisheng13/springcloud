package com.pactera.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationManager;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationProcessingFilter;
import org.springframework.security.oauth2.provider.error.DefaultWebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.error.OAuth2AuthenticationEntryPoint;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

import com.pactera.constant.ConstantUtlis;
import com.pactera.utlis.HStringUtlis;

/**
 * @description: 刷新token
 * @author:woqu
 * @since:2018年5月24日 下午7:21:52
 */
@Configuration
@EnableResourceServer
public class TokenClientConfig extends ResourceServerConfigurerAdapter {
	@Override
	public void configure(HttpSecurity http) throws Exception {

		http
				// 自定义认证失败后返回的信息
				.addFilterBefore(authenticationProcessingFilter(), AbstractPreAuthenticatedProcessingFilter.class)
				// 设置请求拦截
				.authorizeRequests()
				// 设置不拦截的请求
				.antMatchers(HttpMethod.OPTIONS).permitAll()
				// 设置拦截所有的请求
				// .anyRequest().authenticated()
				// 关闭跨站的问题
				.and().csrf().disable();
	}

	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		resources.tokenStore(redisTokenStore());
	}

	@Autowired
	private RedisConnectionFactory redisConnectionFactory;
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	@Autowired
	private ValueOperations<String, Object> valueOperations;

	@Bean
	public TokenStore redisTokenStore() {
		return new RedisTokenStore(redisConnectionFactory);
	}

	public OAuth2AuthenticationProcessingFilter authenticationProcessingFilter() {
		OAuth2AuthenticationProcessingFilter oauth2AuthenticationProcessingFilter = new OAuth2AuthenticationProcessingFilter();
		OAuth2AuthenticationEntryPoint oAuth2AuthenticationEntryPoint = new OAuth2AuthenticationEntryPoint();
		oAuth2AuthenticationEntryPoint.setExceptionTranslator(webResponseExceptionTranslator());
		oauth2AuthenticationProcessingFilter.setAuthenticationEntryPoint(oAuth2AuthenticationEntryPoint);
		OAuth2AuthenticationManager oauth2AuthenticationManager = new OAuth2AuthenticationManager();
		DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
		defaultTokenServices.setTokenStore(redisTokenStore());
		oauth2AuthenticationManager.setTokenServices(defaultTokenServices);
		oauth2AuthenticationProcessingFilter.setAuthenticationManager(oauth2AuthenticationManager);
		return oauth2AuthenticationProcessingFilter;
	}

	@Bean
	public WebResponseExceptionTranslator webResponseExceptionTranslator() {
		return new DefaultWebResponseExceptionTranslator() {
			@Override
			public ResponseEntity<OAuth2Exception> translate(Exception exception) throws Exception {
				OAuth2Exception oAuth2Exception = new OAuth2Exception("认证失败");
				if (exception instanceof InsufficientAuthenticationException) {
					InsufficientAuthenticationException insufficientAuthenticationException = (InsufficientAuthenticationException) exception;
					String message = insufficientAuthenticationException.getMessage();
					if (HStringUtlis.isNotBlank(message) && message.length() > 0) {
						String[] split = message.split(":");
						if (split != null && split.length == ConstantUtlis.DOWN_SHELF) {
							String tokenValue = split[1].trim();
							String token = String.valueOf(valueOperations.get(tokenValue));
							if (HStringUtlis.isNotBlank(token) && HStringUtlis.isNotBlank(tokenValue)
									&& tokenValue.equalsIgnoreCase(token)) {
								redisTemplate.delete(token);
								System.out.println("用户已在其他地方登陆");
								oAuth2Exception = new OAuth2Exception("用户已在其他地方登陆");
							}
						}
					}
				}
				HttpHeaders headers = new HttpHeaders();
				headers.set("Access-Control-Allow-Origin", "*");
				return new ResponseEntity<OAuth2Exception>(oAuth2Exception, headers, HttpStatus.UNAUTHORIZED);
			}
		};
	}

}
