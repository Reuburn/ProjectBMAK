package com.example.projectbmak;

public class FallbackException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public FallbackException(Exception e){
		super(e);
	}
}