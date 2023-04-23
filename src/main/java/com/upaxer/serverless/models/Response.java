package com.upaxer.serverless.models;

import lombok.Data;

@Data
public class Response {
	private int code;
	
	private String message;
	
	private String error;


}