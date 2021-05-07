package com.plutotv.test.v1.search.channels.positive;

import com.plutotv.common.helper.RequestHelper;
import com.plutotv.test.BaseTest;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.HashMap;

import static com.plutotv.common.config.v1.search.SearchEndpointV1Manager.getEndpoint;
import static com.plutotv.common.helper.RequestParameterHelper.OFFSET;
import static com.plutotv.common.helper.RequestParameterHelper.REPLICATION_TYPE;
import static com.plutotv.common.url.UrlBuilder.buildRequestUrl;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.equalTo;

public class C164350_VerifyGetSearchChannelsWithOffset extends BaseTest {
    private final RequestHelper requestHelper = new RequestHelper();

    @Test(groups = {"full"})
    public void verifyGetSearchChannelsWithOffset() {
        int index = 5;
        HashMap<String, String> params = new HashMap<>();
        params.put(REPLICATION_TYPE, "full-sync");
        Response response = requestHelper.getRequest(
                DEFAULT_HEADERS,
                params,
                buildRequestUrl(getEndpoint().v1SearchChannels()));

        response.then()
                .statusCode(SC_OK);
        String channelId = response.then()
                .extract()
                .path("data[" + index + "].payload._id");

        params.put(OFFSET, String.valueOf(index));
        response = requestHelper.getRequest(
                DEFAULT_HEADERS,
                params,
                buildRequestUrl(getEndpoint().v1SearchChannels()));

        response.then()
                .statusCode(SC_OK);
        response.then()
                .body("data[0].payload._id", equalTo(channelId));
    }
}
