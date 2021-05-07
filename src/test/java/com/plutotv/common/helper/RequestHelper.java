/*
 * (c) 2021 Pluto Inc.
 */

package com.plutotv.common.helper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.plutotv.common.log.RequestResponseTestRailLogger;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.config.ObjectMapperConfig.objectMapperConfig;
import static io.restassured.http.ContentType.JSON;

public class RequestHelper {
    private final RequestResponseTestRailLogger requestResponseTestRailLogger = new RequestResponseTestRailLogger();
    private static final String HEAD = "HEAD";
    private static final String GET = "GET";

    public Response headRequest(Headers headers, String url) {
        requestResponseTestRailLogger.logRequest(HEAD, url);
        Response response = given()
                .headers(headers)
                .head(url)
                .thenReturn();
        requestResponseTestRailLogger.logHeadResponse(response);
        return response;
    }

    public Response getRequest(String url) {
        requestResponseTestRailLogger.logRequest(GET, url);
        Response response = given()
                .accept(JSON)
                .get(url)
                .thenReturn();
        requestResponseTestRailLogger.logGetResponse(response);
        return response;
    }

    public Response getRequest(Headers headers, String url) {
        requestResponseTestRailLogger.logRequest(GET, url);
        Response response = given()
                .headers(headers)
                .accept(JSON)
                .get(url)
                .thenReturn();
        requestResponseTestRailLogger.logGetResponse(response);
        return response;
    }

    public Response getRequest(Headers headers, Map<String, String> params, String url) {
        requestResponseTestRailLogger.logRequest(GET, url, params);
        Response response = given()
                .headers(headers)
                .accept(JSON)
                .params(params)
                .get(url)
                .thenReturn();
        requestResponseTestRailLogger.logGetResponse(response);
        return response;
    }

    public Response getRequest(Headers headers, Map<String, String> params, String url, RequestSpecification reqSpec) {
        requestResponseTestRailLogger.logRequest(GET, url, params);
        Response response = given()
                .headers(headers)
                .spec(reqSpec)
                .params(params)
                .when()
                .get(url)
                .thenReturn();
        requestResponseTestRailLogger.logGetResponse(response);
        return response;
    }

    public Response postRequest(Headers headers, Map<String, String> params, String body, String url) {
        return given()
                .headers(headers)
                .accept(JSON)
                .contentType(JSON)
                .params(params)
                .body(body)
                .post(url)
                .thenReturn();
    }

    public Response postRequest(Headers headers, String body, String url) {
        return given()
                .headers(headers)
                .accept(JSON)
                .contentType(JSON)
                .body(body)
                .post(url)
                .thenReturn();
    }

    public RequestSpecification getRequestSpecWithObjectMapper(ObjectMapper objectMapper) {
        return new RequestSpecBuilder().setConfig(getObjectMapper(objectMapper)).build();
    }

    public RestAssuredConfig getObjectMapper(ObjectMapper objectMapper) {
        return RestAssured.config().objectMapperConfig(
                objectMapperConfig().jackson2ObjectMapperFactory((cls, charset) -> objectMapper));
    }
}
