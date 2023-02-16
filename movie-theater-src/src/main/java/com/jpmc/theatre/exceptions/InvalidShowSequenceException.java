package com.jpmc.theatre.exceptions;

public class InvalidShowSequenceException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public InvalidShowSequenceException(String errorMessage, Throwable err) {
		super(errorMessage, err);
	}
}
