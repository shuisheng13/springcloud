package com.pactera.config.security.imageFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.google.common.collect.Lists;

@Component
public class UserLoginValidateFilter extends OncePerRequestFilter {
	private Environment env;
	private TokenStore tokenStore;
	private ValueOperations<String, Object> valueOperations;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String method = request.getRequestURI();
		if (method != null && method.equalsIgnoreCase("/user/login")) {
			String parameter = request.getParameter("username");
			String headerUsername = env.getProperty("spring.token.header.username");
			List<OAuth2AccessToken> list = Lists.newArrayList();
			Collection<OAuth2AccessToken> findTokensByClientIdAndUserName = tokenStore
					.findTokensByClientIdAndUserName(headerUsername, parameter);
			findTokensByClientIdAndUserName.forEach(token -> list.add(token));
			for (OAuth2AccessToken oAuth2AccessToken : findTokensByClientIdAndUserName) {
				String value = oAuth2AccessToken.getValue();
				valueOperations.set(value, value);
				tokenStore.removeAccessToken(oAuth2AccessToken);
				tokenStore.removeRefreshToken(oAuth2AccessToken.getRefreshToken());
			}
		}
		filterChain.doFilter(request, response);
	}

	public TokenStore getTokenStore() {
		return tokenStore;
	}

	public void setTokenStore(TokenStore tokenStore) {
		this.tokenStore = tokenStore;
	}

	public Environment getEnv() {
		return env;
	}

	public void setEnv(Environment env) {
		this.env = env;
	}

	public ValueOperations<String, Object> getValueOperations() {
		return valueOperations;
	}

	public void setValueOperations(ValueOperations<String, Object> valueOperations) {
		this.valueOperations = valueOperations;
	}
	

}
