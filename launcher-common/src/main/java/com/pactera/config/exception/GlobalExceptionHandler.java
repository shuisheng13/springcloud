package com.pactera.config.exception;

import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.pactera.result.ResultData;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<ResultData> defaultErrorHandler(HttpServletRequest request,HttpServletResponse response,Exception exception)  {
		exception.printStackTrace();
		if(exception instanceof DataStoreException){
			DataStoreException httpException=(DataStoreException)exception;
			return ResponseEntity.status(httpException.getHttpStatus()).body(new ResultData(httpException.getStatus(),httpException.getMessage()));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResultData(HttpStatus.BAD_REQUEST.value(),"请求异常"));
	}
}
