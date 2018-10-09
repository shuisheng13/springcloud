package com.pactera.config.security.userLogin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

@Configuration
@EnableAuthorizationServer
public class TokenServerConfig extends AuthorizationServerConfigurerAdapter {
	@Autowired
	private Environment env;
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients
		// 内村存储
		.inMemory()
		// clientId 配置文件里面的配置就失效
		.withClient(env.getProperty("spring.token.header.username"))
		// clientSecret 配置文件里面的配置就失效
		.secret(env.getProperty("spring.token.header.password"))
		// 支持的认证方式
		.authorizedGrantTypes("authorization_code", "client_credentials",
				"refresh_token","password", "implicit")
		// token有效时间
		.accessTokenValiditySeconds(Integer.parseInt(env.getProperty("spring.token.time.accessToken")))
		// refreshToken的有效时间
		.refreshTokenValiditySeconds(Integer.parseInt(env.getProperty("spring.token.time.refreshToken")))
		.scopes("all");
	}
	
	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		security.tokenKeyAccess("isAuthenticated()");
	}
	
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints.tokenStore(redisTokenStore());
	}
	
	@Autowired
	private RedisConnectionFactory redisConnectionFactory;

	@Bean
	public TokenStore redisTokenStore() {
		return new RedisTokenStore(redisConnectionFactory);
	}

}
