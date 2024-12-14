package com.coforge.training.examportal.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/*
* Author      : Satyam.3.Singh
* Date        : 12:32:30 pm
* Time        : 12:32:30 pm
* Project     : user-service
*/

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ResourceNotFoundException(String message) {
		super(message);
	}

	
	
}
