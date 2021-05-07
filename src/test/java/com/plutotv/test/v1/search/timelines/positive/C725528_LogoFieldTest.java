/*
 * (c) 2020 Pluto Inc.
 */

package com.plutotv.test.v1.search.timelines.positive;

import com.plutotv.common.config.v1.channels.ChannelsEndpointV1Manager;
import com.plutotv.common.helper.RequestHelper;
import com.plutotv.test.BaseTest;
import com.plutotv.test.lib.utils.asserts.Assert;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Random;

import static com.plutotv.common.config.v1.search.SearchEndpointV1Manager.getEndpoint;
import static com.plutotv.common.helper.RequestParameterHelper.LIMIT;
import static com.plutotv.common.helper.RequestParameterHelper.REPLICATION_TYPE;
import static com.plutotv.common.url.UrlBuilder.buildRequestUrl;
import static com.plutotv.test.lib.api.coreapi.Constants.START_STOP_TIME_PARAMS;
import static org.apache.http.HttpStatus.SC_OK;

public class C725528_LogoFieldTest extends BaseTest {
    private final RequestHelper requestHelper = new RequestHelper();

    @Test(groups = {"full"})
    public void logoFieldTest() {
        HashMap<String, String> params = new HashMap<>();
        params.put(REPLICATION_TYPE, "full-sync");
        params.putAll(START_STOP_TIME_PARAMS);
        Response response = requestHelper.getRequest(
                DEFAULT_HEADERS,
                params,
                buildRequestUrl(getEndpoint().v1SearchTimelines()));

        response.then()
                .statusCode(SC_OK);
        HashMap<String, Object> payload = response
                .path("data?.payload.findAll{ it -> it.channel.logo.path != ''}[" + new Random().nextInt(50) + "]");
        String actualLogoUrl = ((HashMap) ((HashMap) payload.get("channel")).get("logo")).get("path").toString();

        params.put(LIMIT, "10000");
        response = requestHelper.getRequest(
                DEFAULT_HEADERS,
                params,
                buildRequestUrl(ChannelsEndpointV1Manager.getEndpoint().v1ChannelsTimelines()));

        response.then()
                .statusCode(SC_OK);
        String channelId = response
                .path("data.payload.find { it -> it._id == '" + payload.get("slug") + "'}?.channel");

        params.put(LIMIT, "1000");
        response = requestHelper.getRequest(
                DEFAULT_HEADERS,
                params,
                buildRequestUrl(ChannelsEndpointV1Manager.getEndpoint().v1Channels()));

        response.then()
                .statusCode(SC_OK);
        String expectedLogoUrl = response
                .path("data.payload.find { it -> it._id == '" + channelId + "'}?.logo?.path");

        Assert.assertEquals(actualLogoUrl, expectedLogoUrl,
                "Logo url form search timeline is not equals logo url from relevant channel");
    }
}
