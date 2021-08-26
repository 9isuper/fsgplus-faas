package com.fsgplus;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;
import com.fsgplus.serverless.faas.FsgplusFaasApplication;
import com.qcloud.services.scf.runtime.events.APIGatewayProxyRequestEvent;
import com.qcloud.services.scf.runtime.events.APIGatewayProxyRequestEvent.ProxyRequestContext;
import com.qcloud.services.scf.runtime.events.APIGatewayProxyRequestEvent.RequestIdentity;
import com.qcloud.services.scf.runtime.events.APIGatewayProxyResponseEvent;


public class TencentFaas {

	private static boolean cold_launch = true;
	
	public static void main(String[] args) {
		APIGatewayProxyRequestEvent req = new APIGatewayProxyRequestEvent();
		req.setBody("{\"test\":\"body\"}");
		Map<String,String> headerParameter= new HashMap<String,String>();
		headerParameter.put("Refer", "10.0.2.14");
		req.setHeaderParameters(headerParameter);
		Map<String,String> headers= new HashMap<String,String>();
		headers.put("Accept", "text/html,application/xml,application/json");
		headers.put("Accept-Language", "en-US,en,cn");
		headers.put("Host", "service-3ei3tii4-251000691.ap-guangzhou.apigateway.myqloud.com");
		headers.put("User-Agent", "User Agent String");
		req.setHeaders(headers);
		req.setHttpMethod("GET");
		
		Map<String,String> parameters= new HashMap<String,String>();
		parameters.put("path", "value");
		req.setPathParameters(parameters);
		
		Map<String,String> queryString= new HashMap<String,String>();
		queryString.put("unicode", "123321");
		queryString.put("secret", "432424");
		req.setQueryString(queryString);
		
		Map<String,String> queryStringParameters= new HashMap<String,String>();
		queryStringParameters.put("foo", "bar");
		req.setQueryStringParameters(queryStringParameters);

		ProxyRequestContext proxyRequestContext = new ProxyRequestContext();
		proxyRequestContext.setHttpMethod("POST");
		RequestIdentity requestIdentity = new RequestIdentity();
		requestIdentity.setSecretId("abdcdxxxxxxxsdfs");
		proxyRequestContext.setIdentity(requestIdentity);
		proxyRequestContext.setPath("/test/{path}");
		proxyRequestContext.setRequestId("c6af9ac6-7b61-11e6-9a41-93e8deadbeef");
		proxyRequestContext.setServiceId("service-f94sy04v");
		proxyRequestContext.setSourceIp("10.0.2.14");
		proxyRequestContext.setStage("release");
		req.setRequestContext(proxyRequestContext);
		req.setPath("/fsgplus/tools/aesEncryptByUserCenter");
		Map<String, String> stageVariables= new HashMap<String,String>();
		stageVariables.put("stage","release");
		req.setStageVariables(stageVariables);
		
		TencentFaas faas = new TencentFaas();
		faas.mainHandler(req);
	}

	public APIGatewayProxyResponseEvent mainHandler(APIGatewayProxyRequestEvent req) {

		if (cold_launch) {
			System.out.println("start spring boot application...");
			FsgplusFaasApplication.main(new String[] { "" });
			System.out.println("end start spring boot application...");
			cold_launch = false;
		}

		System.out.println("request info :  " + JSONObject.toJSONString(req));

		String path = req.getPath();
		System.out.println("request path: " + path);

		String method = req.getHttpMethod();
		System.out.println("request method: " + method);

		String body = req.getBody();
		System.out.println("Body: " + body);

		Map<String, String> hdrs = req.getHeaders();

		HttpMethod m = HttpMethod.resolve(method);
		HttpHeaders headers = new HttpHeaders();
		headers.setAll(hdrs);
		RestTemplate client = new RestTemplate();
		HttpEntity<String> entity = new HttpEntity<String>(body, headers);

		String url = "http://127.0.0.1:8080/" + path;

		System.out.println("send request: " + url + ", queryString: " +  req.getQueryString());
		ResponseEntity<String> response = client.exchange(url, m, entity, String.class, req.getQueryString());

		APIGatewayProxyResponseEvent resp = new APIGatewayProxyResponseEvent();
		resp.setStatusCode(Integer.valueOf(response.getStatusCodeValue()));
		HttpHeaders headers1 = response.getHeaders();
		resp.setHeaders(JSONObject.parseObject(JSONObject.toJSONString(headers1)));
		resp.setBody((String) response.getBody());
		System.out.println("response body: " + JSONObject.toJSONString(resp));
		
		return resp;
	}
}
