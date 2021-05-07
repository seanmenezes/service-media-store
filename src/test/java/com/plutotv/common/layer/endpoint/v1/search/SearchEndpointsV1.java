/*
 * (c) 2021 Pluto Inc.
 */

package com.plutotv.common.layer.endpoint.v1.search;

import com.plutotv.common.config.v1.search.SearchEndpointV1;
import com.plutotv.common.config.v1.search.SearchEndpointV1Manager;
import com.plutotv.common.helper.RequestHelper;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import java.util.Map;

import static com.plutotv.common.url.UrlBuilder.buildRequestUrl;
import static com.plutotv.test.BaseTest.DEFAULT_HEADERS;

public class SearchEndpointsV1 {
    private final RequestHelper requestHelper = new RequestHelper();
    private static final SearchEndpointV1 ENDPOINT = SearchEndpointV1Manager.getEndpoint();

    @Step("Search for channels by /v1/search/channels")
    public Response getV1SearchChannels(Map<String, String> params) {
        return requestHelper.getRequest(DEFAULT_HEADERS, params, buildRequestUrl(ENDPOINT.v1SearchChannels()));
    }

    @Step("Get timestamp of most recently modified channel by /v1/search/channels")
    public Response getSearchChannelsLastModifiedDateTime() {
        return requestHelper.headRequest(DEFAULT_HEADERS, buildRequestUrl(ENDPOINT.v1SearchChannels()));
    }

    @Step("Search for series by /v1/search/series")
    public Response getV1SearchSeries(Map<String, String> params) {
        return requestHelper.getRequest(DEFAULT_HEADERS, params, buildRequestUrl(ENDPOINT.v1SearchSeries()));
    }

    @Step("Get timestamp of most recently modified series by /v1/search/series")
    public Response getSearchSeriesLastModifiedDateTime() {
        return requestHelper.headRequest(DEFAULT_HEADERS, buildRequestUrl(ENDPOINT.v1SearchSeries()));
    }

    @Step("Search for episodes by /v1/search/episodes")
    public Response getV1SearchEpisodes(Map<String, String> params) {
        return requestHelper.getRequest(DEFAULT_HEADERS, params, buildRequestUrl(ENDPOINT.v1SearchEpisodes()));
    }

    @Step("Get timestamp of most recently modified episode by /v1/search/episodes")
    public Response getSearchEpisodesLastModifiedDateTime() {
        return requestHelper.headRequest(DEFAULT_HEADERS, buildRequestUrl(ENDPOINT.v1SearchEpisodes()));
    }

    @Step("Search for timelines by /v1/search/timelines")
    public Response getV1SearchTimelines(Map<String, String> params) {
        return requestHelper.getRequest(DEFAULT_HEADERS, params, buildRequestUrl(ENDPOINT.v1SearchTimelines()));
    }

    @Step("Get timestamp of most recently modified timeline by /v1/search/timelines")
    public Response getSearchTimelinesLastModifiedDateTime() {
        return requestHelper.headRequest(DEFAULT_HEADERS, buildRequestUrl(ENDPOINT.v1SearchTimelines()));
    }
}
