package com.pactera.fallback;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.route.ZuulFallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class ServerFallback implements ZuulFallbackProvider {

	@Autowired
	private ObjectMapper objectMapper;

	HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

	@Override
	public String getRoute() {
		return "*";
	}

	@Override
	public ClientHttpResponse fallbackResponse() {
		return new ClientHttpResponse() {

			@Override
			public InputStream getBody() throws IOException {
				HashMap<String, Object> hashMap = new HashMap<String, Object>();
				hashMap.put("state", httpStatus.value());
				hashMap.put("message", "请求异常");
				String writeValueAsString = objectMapper.writeValueAsString(hashMap);
				return new ByteArrayInputStream(writeValueAsString.getBytes("UTF-8"));
			}

			@Override
			public HttpHeaders getHeaders() {
				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
				return headers;
			}

			@Override
			public int getRawStatusCode() throws IOException {
				return httpStatus.value();
			}

			@Override
			public HttpStatus getStatusCode() throws IOException {
				return httpStatus;
			}

			@Override
			public String getStatusText() throws IOException {
				return httpStatus.name();
			}

			@Override
			public void close() {

			}

		};

	}

}
