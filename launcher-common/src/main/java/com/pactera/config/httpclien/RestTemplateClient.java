package com.pactera.config.httpclien;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class RestTemplateClient {

	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private static ObjectMapper mapper = new ObjectMapper();
	
	public <T> ResponseEntity<T> exchangeEntity(String url,HttpMethod method,Class<T> clazz,Map<String,String> map){
		HttpHeaders headers = new HttpHeaders();
		//  请勿轻易改变此提交方式，大部分的情况下，提交方式都是表单提交
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		//  封装参数，千万不要替换为Map与HashMap，否则参数无法传递
		MultiValueMap<String, Object> params= new LinkedMultiValueMap<String, Object>();
		//  也支持中文
		if(map!=null && map.size()>0){
			for (Map.Entry<String, String> entry : map.entrySet()) {  
				params.add(entry.getKey(), entry.getValue());
			}
		}
		HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<MultiValueMap<String, Object>>(params, headers);
		//  执行HTTP请求
		return restTemplate.exchange(url,method, requestEntity,clazz);
	}
	
	public <T> T exchange(String url,HttpMethod method,Class<T> clazz,Map<String,String> map){
		ResponseEntity<T> response = exchangeEntity(url,method,clazz,map);
		int value = response.getStatusCode().value();
		if(value==200){return response.getBody();}return null;
	}
	
	@SuppressWarnings("unchecked")
	public <T> ResponseEntity<T> exchangeObject(String url,HttpMethod method,Class<T> clazz,Object object){
		String objectToJson=null;
		Map<String, String> hashMap = new HashMap<String,String>();
		try {
			objectToJson = mapper.writeValueAsString(object);
			Map<String, Object> jsonToMap = mapper.readValue(objectToJson, Map.class);
			for (Map.Entry<String, Object> entry : jsonToMap.entrySet()) {  
			    hashMap.put(entry.getKey(), String.valueOf(entry.getValue()));
			}  
		} catch (Exception e) {
			e.printStackTrace();
		}
		return exchangeEntity(url,method,clazz,hashMap);
	}
}
