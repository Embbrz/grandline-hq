package com.worldgovernment.grandline_hq.Exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(PirateNotFoundException.class)
	public ResponseEntity<Object> handlePirateNotFound(PirateNotFoundException ex){
		
		Map<String, Object> body = new HashMap<>();
		
		body.put("timestamp", LocalDateTime.now());
		
		body.put("status", HttpStatus.NOT_FOUND.value());
		body.put("error", "Not Found");
		
		body.put("message", ex.getMessage());
		
		return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(BountyAlreadyClaimedException.class)
	public ResponseEntity<Object> handleBountyAlreadyClaimed(BountyAlreadyClaimedException bac){
		
		Map<String, Object> body = new HashMap<>();
		
		body.put("timestamp", LocalDateTime.now());
		
		body.put("status", HttpStatus.CONFLICT.value());
		body.put("error", "Conflict");
		
		body.put("message", bac.getMessage());
		
		return new ResponseEntity<>(body, HttpStatus.CONFLICT);
	}
	
	
	@ExceptionHandler(InvalidAmountException.class)
	public ResponseEntity<Object> handleInvalidAmount(InvalidAmountException ex){
		Map<String, Object> body = new HashMap<>();
		
		body.put("timestamp", LocalDateTime.now());
		
		body.put("status", HttpStatus.BAD_REQUEST.value());
		
		body.put("error", "Invalid Argument!");
		
		body.put("message", ex.getMessage());
		
		return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
		
	}

}
