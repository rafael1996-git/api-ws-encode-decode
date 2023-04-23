package com.upaxer.serverless.idaoService;

import com.upaxer.serverless.models.DataRequest;

public interface IService {
	public String request(String request)throws Exception;
	public DataRequest response(DataRequest request)throws Exception;

}
