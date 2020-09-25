package com.saulo.desafio.services.exceptions;

public class ResourceDataConflit extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ResourceDataConflit(Object data) {
		super("Data conflit. data: " + data);
	}
	
}
