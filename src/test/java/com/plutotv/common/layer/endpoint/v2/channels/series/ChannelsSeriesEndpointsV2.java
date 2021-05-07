/*
 * (c) 2021 Pluto Inc.
 */

package com.plutotv.common.layer.endpoint.v2.channels.series;

import com.plutotv.common.config.v2.channels.ChannelsEndpointV2;
import com.plutotv.common.config.v2.channels.ChannelsEndpointV2Manager;
import com.plutotv.common.helper.RequestHelper;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import java.util.Map;

import static com.plutotv.common.url.UrlBuilder.buildRequestUrl;
import static com.plutotv.test.BaseTest.DEFAULT_HEADERS;

public class ChannelsSeriesEndpointsV2 {

    private static final ChannelsEndpointV2 ENDPOINT = ChannelsEndpointV2Manager.getEndpoint();
    private final RequestHelper requestHelper = new RequestHelper();

    @Step("Get v2/channels/series")
    public Response getChannelsSeries(Map<String, String> params) {
        return requestHelper.getRequest(DEFAULT_HEADERS, params, buildRequestUrl(ENDPOINT.v2ChannelsSeries()));
    }

    @Step("Head v2/channels/series")
    public Response headChannelsSeries() {
        return requestHelper.headRequest(DEFAULT_HEADERS, buildRequestUrl(ENDPOINT.v2ChannelsSeries()));
    }
}
