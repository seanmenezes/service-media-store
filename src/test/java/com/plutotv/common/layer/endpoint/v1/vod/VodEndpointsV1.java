/*
 * (c) 2021 Pluto Inc.
 */

package com.plutotv.common.layer.endpoint.v1.vod;

import com.plutotv.common.config.v1.vod.VodEndpointV1;
import com.plutotv.common.config.v1.vod.VodEndpointV1Manager;
import com.plutotv.common.helper.RequestHelper;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import java.util.Map;

import static com.plutotv.common.url.UrlBuilder.buildRequestUrl;
import static com.plutotv.test.BaseTest.DEFAULT_HEADERS;

public class VodEndpointsV1 {
    private static final VodEndpointV1 ENDPOINT = VodEndpointV1Manager.getEndpoint();
    private final RequestHelper requestHelper = new RequestHelper();

    @Step("Get vod categories by /v1/vod/categories")
    public Response getVodCategories(Map<String, String> params) {
        return requestHelper.getRequest(DEFAULT_HEADERS, params, buildRequestUrl(ENDPOINT.v1VodCategories()));
    }

    @Step("Head vod categories by /v1/vod/categories")
    public Response headVodCategories() {
        return requestHelper.headRequest(DEFAULT_HEADERS, buildRequestUrl(ENDPOINT.v1VodCategories()));
    }

    @Step("Get vod category entries by /v1/vod/category-entries")
    public Response getVodCategoryEntries(Map<String, String> params) {
        return requestHelper.getRequest(DEFAULT_HEADERS, params, buildRequestUrl(ENDPOINT.v1VodCategoryEntries()));
    }

    @Step("Head vod category entries by /v1/vod/category-entries")
    public Response headVodCategoryEntries() {
        return requestHelper.headRequest(DEFAULT_HEADERS, buildRequestUrl(ENDPOINT.v1VodCategoryEntries()));
    }

    @Step("Get vod episodes by /v1/vod/episodes")
    public Response getVodEpisodes(Map<String, String> params) {
        return requestHelper.getRequest(DEFAULT_HEADERS, params, buildRequestUrl(ENDPOINT.v1VodEpisodes()));
    }

    @Step("Head vod episodes by /v1/vod/episodes")
    public Response headVodEpisodes() {
        return requestHelper.headRequest(DEFAULT_HEADERS, buildRequestUrl(ENDPOINT.v1VodEpisodes()));
    }

    @Step("Get vod series by /v1/vod/series")
    public Response getVodSeries(Map<String, String> params) {
        return requestHelper.getRequest(DEFAULT_HEADERS, params, buildRequestUrl(ENDPOINT.v1VodSeries()));
    }

    @Step("Head vod series by /v1/vod/series")
    public Response headVodSeries() {
        return requestHelper.headRequest(DEFAULT_HEADERS, buildRequestUrl(ENDPOINT.v1VodSeries()));
    }
}
