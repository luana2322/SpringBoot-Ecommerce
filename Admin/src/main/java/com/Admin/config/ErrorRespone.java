package com.Admin.config;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ErrorRespone {
	private HttpStatus statusCode;
    private String errorMessage;
    private Object body;
    
    public ErrorRespone(HttpStatus statusCode,String errorMessage) {
    	this(statusCode,errorMessage,errorMessage);
    }
    
    public int getStatusCodeValue() {
    	return statusCode.value();
    }
}
