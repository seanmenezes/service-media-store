/*
 * (c) 2021 Pluto Inc.
 */

package com.plutotv.common.layer.endpoint.v1.active.regions;

import com.plutotv.common.config.v1.active.regions.ActiveRegionsEndpointV1;
import com.plutotv.common.config.v1.active.regions.ActiveRegionsEndpointV1Manager;
import com.plutotv.common.helper.RequestHelper;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import java.util.Map;

import static com.plutotv.common.url.UrlBuilder.buildRequestUrl;
import static com.plutotv.test.BaseTest.DEFAULT_HEADERS;

public class ActiveRegionsEndpointsV1 {
    private static final ActiveRegionsEndpointV1 ENDPOINT = ActiveRegionsEndpointV1Manager.getEndpoint();
    private final RequestHelper requestHelper = new RequestHelper();

    @Step("Get active regions by /v1/active-regions")
    public Response getActiveRegions(Map<String, String> params) {
        return requestHelper.getRequest(DEFAULT_HEADERS, params, buildRequestUrl(ENDPOINT.v1ActiveRegions()));
    }

    @Step("Head active regions by /v1/active-regions")
    public Response headActiveRegions() {
        return requestHelper.headRequest(DEFAULT_HEADERS, buildRequestUrl(ENDPOINT.v1ActiveRegions()));
    }
}

