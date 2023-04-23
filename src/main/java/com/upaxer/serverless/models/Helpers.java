package com.upaxer.serverless.models;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class Helpers {

	public static ResponseEntity<Map<String, Object>> ResponseClass(int codeError, Map<String, Object> object,
			String msgSucces, String msgError) {
		Map<String, Object> response = new HashMap<>();
		response.put("statusCode", codeError);
		response.put("Data", object);
		response.put("Message", msgSucces);
		response.put("Error", msgError);
		return new ResponseEntity<Map<String, Object>>(response, codeError == 500 ? HttpStatus.INTERNAL_SERVER_ERROR : codeError == 201 ? HttpStatus.CREATED 
				: codeError == 200 ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}
}
