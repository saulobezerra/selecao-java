package com.saulo.desafio.services.exceptions;

public class ResourceNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public ResourceNotFoundException(Object info) {
		super("Resource not found:  " + info);
	}

}
