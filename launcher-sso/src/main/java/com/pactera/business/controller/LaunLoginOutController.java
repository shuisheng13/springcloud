package com.pactera.business.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pactera.constant.ConstantUtlis;
import com.pactera.utlis.HStringUtlis;

@RestController
public class LaunLoginOutController {

	@Autowired
	private TokenStore tokenStore;

	@PostMapping("/token/logout")
	public ResponseEntity<String> tokenLogout(HttpServletRequest request) {
		String header = request.getHeader("Authorization");
		if (HStringUtlis.isNoneBlank(header) && header.length() > 0) {
			String tokenValue = header.split(" ")[1];
			if (HStringUtlis.isNoneBlank(tokenValue) && tokenValue.length() > 0) {
				OAuth2AccessToken readAccessToken = tokenStore.readAccessToken(tokenValue);
				if (readAccessToken != null) {
					tokenStore.removeAccessToken(readAccessToken);
					return ResponseEntity.ok(ConstantUtlis.SUCCESS_SATE);
				}
			}
		}
		return ResponseEntity.ok(ConstantUtlis.FAILURE_SATE);
	}
}
