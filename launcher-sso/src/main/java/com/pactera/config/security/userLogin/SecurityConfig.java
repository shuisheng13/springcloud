package com.pactera.config.security.userLogin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.pactera.config.security.imageFilter.ImageValidateFilter;
import com.pactera.config.security.imageFilter.UserLoginValidateFilter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private Environment env;
	@Autowired
	private TokenStore tokenStore;
	@Autowired
	private LoginSuccessHandler loginSuccessHandler;
	@Autowired
	private LoginFailureHandler loginFailureHandler;
	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	private ValueOperations<String, Object> valueOperations;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//验证码过滤器
		ImageValidateFilter imageValidateFilter = new ImageValidateFilter();
		//用户登录限制的过滤器
		UserLoginValidateFilter userLoginValidateFilter = new UserLoginValidateFilter();
		userLoginValidateFilter.setValueOperations(valueOperations);
		userLoginValidateFilter.setTokenStore(tokenStore);
		userLoginValidateFilter.setEnv(env);
		http
		// 在登陆之前添加验证码过滤器
		.addFilterBefore(imageValidateFilter, UsernamePasswordAuthenticationFilter.class)
		// 在登陆之前添加用户登录限制的过滤器
		.addFilterBefore(userLoginValidateFilter, UsernamePasswordAuthenticationFilter.class)
		// 设置表单登陆
		.formLogin()
		// 登陆的方法
		.loginProcessingUrl("/user/login")
		// 登陆成功之后需要处理的方法
		.successHandler(loginSuccessHandler)
		// 登陆失败之后需要处理的方法
		.failureHandler(loginFailureHandler)
		//登陆的实现方法
		.and().userDetailsService(userDetailsService)
		// 设置请求拦截
		.authorizeRequests()
		// 放行退出的方法
		.antMatchers("/token/logout").permitAll()
		.antMatchers(HttpMethod.OPTIONS).permitAll()
		// 设置拦截所有的请求
		.anyRequest().authenticated()
		// 关闭跨站的问题
		.and().csrf().disable();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}
}
