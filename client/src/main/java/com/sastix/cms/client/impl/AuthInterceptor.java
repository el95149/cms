/**
 * 
 */
package com.sastix.cms.client.impl;

import java.io.IOException;

import org.apache.commons.codec.binary.Base64;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

/**
 * Custom Spring HTTP client Interceptor, supporting Base64 encoded HTTP authentication.
 * @author <a href=
 *         "mailto:angelosanagnostopoulos@runbox.com">aanagnostopoulos</a>
 *
 */
public class AuthInterceptor implements ClientHttpRequestInterceptor {

	private String user;
	
	private String pass;
	
	public AuthInterceptor(String user, String pass) {
		super();
		this.user = user;
		this.pass = pass;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.http.client.ClientHttpRequestInterceptor#intercept(
	 * org.springframework.http.HttpRequest, byte[],
	 * org.springframework.http.client.ClientHttpRequestExecution)
	 */
	@Override
	public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
			throws IOException {
		String plainCredentials = user+":"+pass;
		String base64Credentials = new String(Base64.encodeBase64(plainCredentials.getBytes()));

		HttpHeaders headers = request.getHeaders();
		headers.add("Authorization", "Basic " + base64Credentials);
		return execution.execute(request, body);
	}

}
