package com.plutotv.test.v1.search.timelines.positive;

import com.plutotv.common.helper.RequestHelper;
import com.plutotv.test.BaseTest;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;

import static com.plutotv.common.config.v1.search.SearchEndpointV1Manager.getEndpoint;
import static com.plutotv.common.helper.RequestParameterHelper.REPLICATION_TYPE;
import static com.plutotv.common.helper.RequestParameterHelper.UPDATED_BEFORE;
import static com.plutotv.common.url.UrlBuilder.buildRequestUrl;
import static com.plutotv.test.lib.api.coreapi.Constants.START_STOP_TIME_PARAMS;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.lessThan;

public class C608800_VerifyGetSearchTimelinesWithUpdatedBefore extends BaseTest {
    private final RequestHelper requestHelper = new RequestHelper();

    @Test(groups = {"full"})
    public void verifyGetSearchTimelinesWithUpdatedBefore() {
        HashMap<String, String> params = new HashMap<>();
        params.put(REPLICATION_TYPE, "full-sync");
        params.putAll(START_STOP_TIME_PARAMS);
        Response response = requestHelper.getRequest(
                DEFAULT_HEADERS,
                params,
                buildRequestUrl(getEndpoint().v1SearchTimelines()));

        response.then()
                .statusCode(SC_OK);
        Instant date = Instant.parse(response.path("data[0].payload.updatedAt"));

        List<HashMap> data = response.path("data");
        int initRequestSize = data.size();

        params.put(UPDATED_BEFORE, String.valueOf(date.minus(15, ChronoUnit.DAYS)));
        response = requestHelper.getRequest(
                DEFAULT_HEADERS,
                params,
                buildRequestUrl(getEndpoint().v1SearchTimelines()));

        response.then()
                .statusCode(SC_OK);
        response.then()
                .body("data", hasSize(lessThan(initRequestSize)));
    }
}
