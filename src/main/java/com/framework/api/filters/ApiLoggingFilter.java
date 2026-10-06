package com.framework.api.filters;

import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class ApiLoggingFilter implements Filter {

	private static final Logger log = LogManager.getLogger(ApiLoggingFilter.class);

	@Override
	public Response filter(FilterableRequestSpecification req, FilterableResponseSpecification res, FilterContext ctx) {
		Response response = ctx.next(req, res);
		log.info("API {} {} | request body: {}", req.getMethod(), req.getURI(),
				req.getBody() == null ? "-" : req.getBody());
		log.info("API response: {} {} | body: {}", response.statusCode(), response.statusLine(), response.asString());
		return response;
	}
}
