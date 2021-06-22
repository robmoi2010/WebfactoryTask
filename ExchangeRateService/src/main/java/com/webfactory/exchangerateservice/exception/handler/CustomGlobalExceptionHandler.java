package com.webfactory.exchangerateservice.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.webfactory.exchangerateservice.exception.ErrorResponse;
import com.webfactory.exchangerateservice.exception.ExchangeRateNotFoundException;
import com.webfactory.exchangerateservice.exception.GeneralException;

@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {
	@ExceptionHandler(ExchangeRateNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleExchangeRateNotFoundException(Exception ex, WebRequest request) {
		String message = ex.getMessage();
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setCode("CODE_1");
		errorResponse.setStatus(404);
		errorResponse.setMessage(message);
		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);

	}

	@ExceptionHandler(GeneralException.class)
	public ResponseEntity<ErrorResponse> handleGeneralException(Exception ex, WebRequest request) {
		String message = ex.getMessage();
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setCode("CODE_2");
		errorResponse.setStatus(500);
		errorResponse.setMessage(message);
		return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);

	}
}
