package com.pactera.config.exception;

import com.pactera.config.exception.status.ErrorStatus;
import com.pactera.result.ResultData;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(value = NullPointerException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<ResultData> NullPointerHandler(Exception exception)  {
        exception.printStackTrace();
	    return new ResponseEntity<>(new ResultData(exception.getMessage(), ErrorStatus.PARAMETER_ERROR),HttpStatus.BAD_REQUEST);
	}

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
