package com.lattice.exception;

import java.util.HashMap;
import java.util.Map;

import org.apache.catalina.connector.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.lattice.payload.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponse> resourceNotFoundException(ResourceNotFoundException ex){
		String message = ex.getMessage();
		ApiResponse apiResponse = new ApiResponse(message, false);
		
		return new ResponseEntity<com.lattice.payload.ApiResponse>(apiResponse,HttpStatus.NOT_FOUND);
	}
	
	
	//Handle the MethodArgumentNotValidException It's only trigger when the payload class instance have null, not mim size , blank etc
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String,String>> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException mex){
		
		Map<String,String> resp = new HashMap<>();
		
		mex.getBindingResult().getAllErrors().forEach((error)->{
			String filedName =  ((FieldError) error).getField();
			String message = error.getDefaultMessage();
			
			resp.put(filedName,message);
		});
		
		return new ResponseEntity<Map<String,String>>(resp,HttpStatus.BAD_REQUEST);
	}
	
	//Handle the Exception when we want to delete the doctor entity and patient entity
	//and we don't provide any id of doctor and patient entity into the url then this fuction handle the excepition
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<ApiResponse> httpRequestMethodNotSupportedExceptionHandler( ){
		return new ResponseEntity<ApiResponse>(new ApiResponse("Doctor not found",false),HttpStatus.BAD_REQUEST);
	}
	
	
	//whenever the IllegalArgumentException occure it trigger 
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ApiResponse> httpRequestMethodNotSupportedExceptionHandler(IllegalArgumentException ex ){
		String message = ex.getMessage();
		ApiResponse apiResponse = new ApiResponse(message, false);
			
		return new ResponseEntity<com.lattice.payload.ApiResponse>(apiResponse,HttpStatus.NOT_FOUND);
	}
	
	
	
	
	
}
