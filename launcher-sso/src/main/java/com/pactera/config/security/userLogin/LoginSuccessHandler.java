package com.pactera.config.security.userLogin;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.TokenRequest;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.pactera.business.service.LaunUserService;
import com.pactera.config.security.DataUser;
import com.pactera.domain.LaunUser;
import com.pactera.utlis.IpUtlis;
import com.pactera.utlis.JsonUtils;

@Component
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

	@Autowired
	private Environment env;
	@Autowired
	private LaunUserService launUserService;
	@Autowired
	private ClientDetailsService clientDetailsService;
	@Autowired
	private AuthorizationServerTokenServices authorizationServerTokenServices;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		response.setHeader("Access-Control-Allow-Origin", "*");		
		String headerUsername = env.getProperty("spring.token.header.username");
		String headerPassword = env.getProperty("spring.token.header.password");
		byte[] encode = Base64.encode(String.valueOf(headerUsername+":"+headerPassword).getBytes());
		String header = "Basic "+new String(encode);
		if (header == null || !header.startsWith("Basic ")) {
			response.getWriter().write(JsonUtils.ObjectToJson(authentication));
			return;
		}
		String[] tokens = extractAndDecodeHeader(header, request);
		assert tokens.length == 2;
		String clientId = tokens[0];
		String clientSecret = tokens[1];
		
		ClientDetails loadClientByClientId = clientDetailsService.loadClientByClientId(clientId);
		if(loadClientByClientId==null){
			response.getWriter().write(JsonUtils.ObjectToJson(authentication));
			return;
		}
		if(!StringUtils.equals(clientSecret, loadClientByClientId.getClientSecret())){
			response.getWriter().write(JsonUtils.ObjectToJson(authentication));
			return;
		}
		@SuppressWarnings("unchecked")
		TokenRequest tokenRequest = new TokenRequest(MapUtils.EMPTY_MAP, clientId, loadClientByClientId.getScope(), "custom");
		OAuth2Request createOAuth2Request = tokenRequest.createOAuth2Request(loadClientByClientId);
		OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(createOAuth2Request, authentication);
		OAuth2AccessToken createAccessToken = authorizationServerTokenServices.createAccessToken(oAuth2Authentication);
		HashMap<String, Object> hashMap = new HashMap<String,Object>();
		hashMap.put("access_token", createAccessToken.getValue());
		hashMap.put("token_type", createAccessToken.getTokenType());
		hashMap.put("refresh_token", createAccessToken.getRefreshToken().getValue());
		hashMap.put("expires_in", createAccessToken.getExpiresIn());
		hashMap.put("user", getUser(authentication));
		LaunUser jsonToClass = JsonUtils.jsonToClass(JsonUtils.ObjectToJson(getUser(authentication)), LaunUser.class);
		launUserService.saveUserLogs(jsonToClass, IpUtlis.getIpAddr(request));
		System.out.println(JsonUtils.ObjectToJson(hashMap));
		response.setStatus(200);
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().write(JsonUtils.ObjectToJson(hashMap));
	}
	private String[] extractAndDecodeHeader(String header, HttpServletRequest request)
			throws IOException {

		byte[] base64Token = header.substring(6).getBytes("UTF-8");
		byte[] decoded;
		try {
			decoded = Base64.decode(base64Token);
		}
		catch (IllegalArgumentException e) {
			throw new BadCredentialsException(
					"Failed to decode basic authentication token");
		}

		String token = new String(decoded,"UTF-8");

		int delim = token.indexOf(":");

		if (delim == -1) {
			throw new BadCredentialsException("Invalid basic authentication token");
		}
		return new String[] { token.substring(0, delim), token.substring(delim + 1) };
	}
	
	public Object getUser(Authentication authentication){
		Object principal = authentication.getPrincipal();
		DataUser user=(DataUser) principal;
		return user.getObject();
	}
}
