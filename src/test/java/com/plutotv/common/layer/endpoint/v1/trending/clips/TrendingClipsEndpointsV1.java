/*
 * (c) 2021 Pluto Inc.
 */

package com.plutotv.common.layer.endpoint.v1.trending.clips;

import com.plutotv.common.config.v1.trending.clips.TrendingClipsEndpointV1;
import com.plutotv.common.config.v1.trending.clips.TrendingClipsEndpointV1Manager;
import com.plutotv.common.helper.RequestHelper;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import java.util.Map;

import static com.plutotv.common.url.UrlBuilder.buildRequestUrl;
import static com.plutotv.test.BaseTest.DEFAULT_HEADERS;

public class TrendingClipsEndpointsV1 {
    private static final TrendingClipsEndpointV1 ENDPOINT = TrendingClipsEndpointV1Manager.getEndpoint();
    private final RequestHelper requestHelper = new RequestHelper();

    @Step("Get trending clips by /v1/trending/clips")
    public Response getTrendingClips(Map<String, String> params) {
        return requestHelper.getRequest(DEFAULT_HEADERS, params, buildRequestUrl(ENDPOINT.v1TendingClips()));
    }

    @Step("Head trending clips by /v1/trending/clips")
    public Response headTrendingClips() {
        return requestHelper.headRequest(DEFAULT_HEADERS, buildRequestUrl(ENDPOINT.v1TendingClips()));
    }
}
