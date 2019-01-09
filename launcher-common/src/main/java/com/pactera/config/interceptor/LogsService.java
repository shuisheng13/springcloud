package com.pactera.config.interceptor;

import com.pactera.config.httpclien.RestTemplateClient;
import com.pactera.config.spring.SpringUtil;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Map;
@Component
public class LogsService{
	
	@Async
	public void saveUserLogs(Map<String, String> hashMap){
		RestTemplateClient restTemplateClient = SpringUtil.getBean(RestTemplateClient.class);
		//restTemplateClient.exchangeObject("http://logs/addStoreBrowseLogs", HttpMethod.POST, null, hashMap);
	}
}
