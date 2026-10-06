package com.framework.api.client;

import com.framework.api.filters.ApiLoggingFilter;
import com.framework.config.ConfigReader;
import io.restassured.RestAssured;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;


public final class ApiClient {

	private ApiClient() {
	}

	private static RequestSpecification spec() {
		int timeout = ConfigReader.getInt("api.connection.timeout");
		RestAssuredConfig cfg = RestAssuredConfig.config().httpClient(HttpClientConfig.httpClientConfig()
				.setParam("http.connection.timeout", timeout).setParam("http.socket.timeout", timeout));
		RequestSpecification s = RestAssured.given().config(cfg).baseUri(ConfigReader.get("api.base.url"))
				.contentType(ContentType.JSON).accept(ContentType.JSON);
		if (ConfigReader.getBoolean("api.log.enabled"))
			s = s.filter(new ApiLoggingFilter());
		return s;
	}

	public static Response get(String path) {
		return spec().get(path);
	}

	public static Response post(String path, Object body) {
		return spec().body(body).post(path);
	}

	public static Response put(String path, Object body) {
		return spec().body(body).put(path);
	}

	public static Response delete(String path) {
		return spec().delete(path);
	}
}
