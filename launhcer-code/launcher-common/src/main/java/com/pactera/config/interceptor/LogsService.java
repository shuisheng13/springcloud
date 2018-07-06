package com.pactera.config.interceptor;

import java.util.Map;

import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.pactera.config.httpclien.RestTemplateClient;
import com.pactera.config.spring.SpringUtil;
@Component
public class LogsService{
	
	@Async
	public void saveUserLogs(Map<String, String> hashMap){
		RestTemplateClient restTemplateClient = SpringUtil.getBean(RestTemplateClient.class);
		restTemplateClient.exchangeObject("http://logs/addStoreBrowseLogs", HttpMethod.POST, null, hashMap);
	}
}
