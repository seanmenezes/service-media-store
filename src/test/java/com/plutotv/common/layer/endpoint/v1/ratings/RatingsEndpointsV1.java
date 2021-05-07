/*
 * (c) 2021 Pluto Inc.
 */

package com.plutotv.common.layer.endpoint.v1.ratings;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.plutotv.common.config.v1.ratings.RatingsEndpointV1;
import com.plutotv.common.config.v1.ratings.RatingsEndpointV1Manager;
import com.plutotv.common.helper.RequestHelper;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import java.util.Map;

import static com.plutotv.common.url.UrlBuilder.buildRequestUrl;
import static com.plutotv.test.BaseTest.DEFAULT_HEADERS;

public class RatingsEndpointsV1 {
    private static final RatingsEndpointV1 ENDPOINT = RatingsEndpointV1Manager.getEndpoint();
    private final RequestHelper requestHelper = new RequestHelper();

    @Step("Get ratings by /v1/ratings")
    public Response getRatings(Map<String, String> params) {
        return requestHelper.getRequest(DEFAULT_HEADERS, params, buildRequestUrl(ENDPOINT.v1Ratings()));
    }

    @Step("Get ratings by /v1/ratings")
    public Response getRatings(Map<String, String> params, ObjectMapper objectMapper) {
        return requestHelper.getRequest(
                DEFAULT_HEADERS,
                params,
                buildRequestUrl(ENDPOINT.v1Ratings()),
                requestHelper.getRequestSpecWithObjectMapper(objectMapper));
    }

    @Step("Head ratings by /v1/ratings")
    public Response headRatings() {
        return requestHelper.headRequest(DEFAULT_HEADERS, buildRequestUrl(ENDPOINT.v1Ratings()));
    }
}
