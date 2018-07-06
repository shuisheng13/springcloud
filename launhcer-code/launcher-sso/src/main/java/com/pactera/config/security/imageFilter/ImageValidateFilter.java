package com.pactera.config.security.imageFilter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class ImageValidateFilter extends OncePerRequestFilter{
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		//校验验证码
		if(StringUtils.equals("/user/login",request.getRequestURI()) && request.getMethod().equalsIgnoreCase("post")){
			String parameter = request.getParameter("imageCode");
			System.out.println("验证码："+parameter);
			//判断，如果不满足就抛出异常
			filterChain.doFilter(request, response);
		}else{
			//执行下面的过滤器
			filterChain.doFilter(request, response);
		}
	}

}
