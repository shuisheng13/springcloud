//package com.pactera.config.security;
//
//import javax.servlet.http.HttpServletRequest;
//
//import org.apache.commons.lang.StringUtils;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Pointcut;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.context.request.RequestAttributes;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//
//@Aspect
//@Configuration
//public class RefreshToken {
//
//	@Autowired
//	private RefreshTokenService refreshTokenService;
//
//	@Pointcut("execution(* com.pactera.business.controller.*.*(..))")
//	public void excudeService() {
//	}
//
//	@Around("excudeService()")
//	public Object doAround(ProceedingJoinPoint point) throws Throwable {
//		Object result = point.proceed();
//		RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
//		ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) requestAttributes;
//		HttpServletRequest request = servletRequestAttributes.getRequest();
//		String header = request.getHeader("Authorization");
//		if(StringUtils.isBlank(header) || header.equalsIgnoreCase("null")){
//			String methodType = request.getMethod();
//			if (methodType.equalsIgnoreCase("GET"))return result;
//		}
//		if(StringUtils.isNotBlank(header) && !header.equalsIgnoreCase("null")){
//			refreshTokenService.refreshTokenTime(header);
//		}
//		return result;
//	}
//}
