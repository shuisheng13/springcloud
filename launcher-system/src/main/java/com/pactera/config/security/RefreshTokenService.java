package com.pactera.config.security;

import java.util.Collections;
import java.util.Date;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Component;

@Component
public class RefreshTokenService {
	@Autowired
	private TokenStore tokenStore;
	@Autowired
	private Environment env;
	
	@Async
	public String refreshTokenTime(String header){
		String[] split = header.split(" ");
		header = split[1];
		TokenEnhancer accessTokenEnhancer = new TokenEnhancerChain();
		OAuth2AccessToken accessTokens = tokenStore.readAccessToken(header);
		OAuth2Authentication readAuthentication = tokenStore.readAuthentication(accessTokens);
		OAuth2AccessToken accessToken = createAccessToken(accessTokenEnhancer, readAuthentication,
				accessTokens.getRefreshToken(), accessTokens.getValue());
		tokenStore.storeAccessToken(accessToken, readAuthentication);
		tokenStore.storeRefreshToken(accessTokens.getRefreshToken(), readAuthentication);
		return accessTokens.getValue();
	}

	private OAuth2AccessToken createAccessToken(TokenEnhancer accessTokenEnhancer, OAuth2Authentication authentication,
			OAuth2RefreshToken refreshToken, String tokenValue) {
		DefaultOAuth2AccessToken token = new DefaultOAuth2AccessToken(tokenValue);
		token.setExpiration(new Date(System.currentTimeMillis() + (Integer.parseInt(env.getProperty("spring.token.time.accessToken")) * 1000L)));
		token.setRefreshToken(refreshToken);
		token.setScope(authentication.getOAuth2Request().getScope());
		return accessTokenEnhancer != null ? accessTokenEnhancer.enhance(token, authentication) : token;
	}
}

class TokenEnhancerChain implements TokenEnhancer {
	private List<TokenEnhancer> delegates = Collections.emptyList();

	public void setTokenEnhancers(List<TokenEnhancer> delegates) {
		this.delegates = delegates;
	}

	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		OAuth2AccessToken result = accessToken;
		for (TokenEnhancer enhancer : delegates) {
			result = enhancer.enhance(result, authentication);
		}
		return result;
	}

}
