package com.esa.test.services.server.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 
 * @author esalazar
 *
 */
@ResponseStatus(value = HttpStatus.OK)
public class ResourceAlreadyExistsException extends Exception{

	private static final long serialVersionUID = 1L;

	public ResourceAlreadyExistsException(String message){
    	super(message);
    }
}
