package com.plutotv.test.v1.channels.positive;

import com.plutotv.common.helper.RequestHelper;
import com.plutotv.test.BaseTest;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;

import static com.plutotv.common.config.v1.channels.ChannelsEndpointV1Manager.getEndpoint;
import static com.plutotv.common.helper.RequestParameterHelper.REPLICATION_TYPE;
import static com.plutotv.common.helper.RequestParameterHelper.UPDATED_AFTER;
import static com.plutotv.common.url.UrlBuilder.buildRequestUrl;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.not;

public class C164342_VerifyGetAllChannelsWithUpdatedAfter extends BaseTest {
    private final RequestHelper requestHelper = new RequestHelper();

    @Test(groups = {"full"})
    public void verifyGetAllChannelsWithUpdatedAfter() {
        HashMap<String, String> params = new HashMap<>();
        params.put(REPLICATION_TYPE, "full-sync");
        Response response = requestHelper.getRequest(
                DEFAULT_HEADERS,
                params,
                buildRequestUrl(getEndpoint().v1Channels()));

        response.then()
                .statusCode(SC_OK);
        String dateString = response.then()
                .extract()
                .path("data[0].payload.updatedAt");
        Instant date = Instant.parse(dateString);

        params.put(UPDATED_AFTER, String.valueOf(date.plus(1, ChronoUnit.HOURS)));
        response = requestHelper.getRequest(
                DEFAULT_HEADERS,
                params,
                buildRequestUrl(getEndpoint().v1Channels()));

        response.then()
                .statusCode(SC_OK);
        response.then()
                .body("data.payload.updatedAt", not(hasItems(dateString)));
    }
}
