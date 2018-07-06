package com.pactera.config.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import com.pactera.config.interceptor.LogsInterceptor;

/**
 * @description: 日志
 * @author:woqu
 * @since:2018年5月24日 下午7:21:09
 */
@Configuration
public class InterceptorConfig extends WebMvcConfigurerAdapter {

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// 日志的拦截器
		registry.addInterceptor(new LogsInterceptor()).addPathPatterns("/**");
		super.addInterceptors(registry);
	}

}
