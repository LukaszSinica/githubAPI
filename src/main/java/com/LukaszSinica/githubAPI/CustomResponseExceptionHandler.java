package com.LukaszSinica.githubAPI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.handler.ResponseStatusExceptionHandler;

@ControllerAdvice
public class CustomResponseExceptionHandler extends ResponseStatusExceptionHandler{

	@ExceptionHandler(Exception.class)
	public final ResponseEntity<Object> handleAllExceptions(ResponseStatusException ex, WebRequest request) {
		Error error = new Error(ex.getStatusCode().value(), ex.getReason());

		return new ResponseEntity<>(error, ex.getStatusCode());
	}

}
