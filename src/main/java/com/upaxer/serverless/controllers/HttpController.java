package com.upaxer.serverless.controllers;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.upaxer.serverless.idaoService.IService;
import com.upaxer.serverless.models.DataRequest;
import com.upaxer.serverless.models.Helpers;
import com.upaxer.serverless.models.Response;



@CrossOrigin(origins = "*", methods = {RequestMethod.POST})
@RestController
@RequestMapping("/api")
public class HttpController {
	private static final Logger logging = LoggerFactory.getLogger(HttpController.class);
    @Autowired
	private IService service;
	
	@RequestMapping(path = "/encode", method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> encode(@RequestBody  String request) throws Exception {
		Map<String, Object> dataResponse = new HashMap<>();
		Response response = new Response();
			try {
					
			        String json = service.request(request);
			        dataResponse.put("data", json);
			        response.setCode(200);
					response.setMessage("The client has been successfully!");
			} catch (Exception e) {
				logging.error(e.getMessage());
				response.setCode(500);
				response.setMessage("");
				response.setError(e.getMessage().toString());
			}
	       
	        return   Helpers.ResponseClass(response.getCode(), dataResponse, response.getMessage(), response.getError());
	}

	@RequestMapping(path = "/decode", method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> decode(@RequestBody  DataRequest requestJson) throws Exception {
		Map<String, Object> dataResponse = new HashMap<>();
		Response response = new Response();
			try {
				DataRequest json = service.response(requestJson);
			        dataResponse.put("data", json.getData());
			        response.setCode(200);
					response.setMessage("The client has been successfully!");
			} catch (Exception e) {
				logging.error(e.getMessage());
				response.setCode(500);
				response.setMessage("");
				response.setError(e.getMessage().toString());
			}
	       
	        return   Helpers.ResponseClass(response.getCode(), dataResponse, response.getMessage(), response.getError());
	}
}
