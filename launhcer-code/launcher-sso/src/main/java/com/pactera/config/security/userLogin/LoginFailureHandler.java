package com.pactera.config.security.userLogin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.pactera.config.exception.status.ErrorStatus;
import com.pactera.result.ResultData;
import com.pactera.utlis.JsonUtils;

@Component
public class LoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {
	
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		response.setHeader("Access-Control-Allow-Origin", "*");		
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		response.setContentType("application/json;charset=UTF-8");
		if(exception instanceof BadCredentialsException){
			response.getWriter().write(JsonUtils.ObjectToJson(new ResultData(ErrorStatus.PASSWORD_ERROR)));
		}else if(exception instanceof InternalAuthenticationServiceException){
			response.getWriter().write(JsonUtils.ObjectToJson(new ResultData(ErrorStatus.NOT_USER)));
		}else{
			response.getWriter().write(JsonUtils.ObjectToJson(new ResultData(ErrorStatus.AUTHENTICATION_FAILED)));
		}
	}
}
