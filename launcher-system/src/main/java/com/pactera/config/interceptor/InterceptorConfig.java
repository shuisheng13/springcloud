//package com.pactera.config.interceptor;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
//import com.pactera.config.interceptor.LogsInterceptor;
//
//
//@Configuration
//public class InterceptorConfig extends WebMvcConfigurerAdapter{
//
//	@Override
//	public void addInterceptors(InterceptorRegistry registry){
//		//日志的拦截器
//		registry.addInterceptor(new LogsInterceptor()).addPathPatterns("/**");
//		super.addInterceptors(registry);
//	}
//
//}
