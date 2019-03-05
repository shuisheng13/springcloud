package com.pactera.config.exception;

import com.pactera.config.exception.status.ErrorStatus;
import com.pactera.result.ResultData;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class GlobalExceptionHandler {

    Log logger = LogFactory.getLog(GlobalExceptionHandler.class);

	@ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<ResultData> HttpRequestMethodNotSupportedHandler(Exception exception)  {
		logger.error(exception.getMessage(),exception);
		return new ResponseEntity<>(new ResultData(exception.getMessage(), ErrorStatus.HTTP_REQUEST_METHOD_ERROR),HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = IORuntimeException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseEntity<ResultData> IOExceptionHandler(Exception exception)  {
		logger.error(exception.getMessage(),exception);
		return new ResponseEntity<>(new ResultData(exception.getMessage(), ErrorStatus.IO_ERROR),HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(value = ConstraintViolationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<ResultData> ConstraintViolationExceptionHandler(ConstraintViolationException exception)  {
		logger.error(exception.getMessage(),exception);
		return new ResponseEntity<>(new ResultData(exception.getMessage(), ErrorStatus.PARAMETER_ERROR),HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<ResultData> defaultErrorHandler(HttpServletRequest request,HttpServletResponse response,Exception exception)  {
		exception.printStackTrace();
        logger.error(exception.getMessage(),exception);
		if(exception instanceof DataStoreException){
			DataStoreException httpException=(DataStoreException)exception;
			return ResponseEntity.status(httpException.getHttpStatus()).body(new ResultData(httpException.getStatus(),httpException.getMessage()));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResultData(HttpStatus.BAD_REQUEST.value(),"请求异常"));
	}
}
