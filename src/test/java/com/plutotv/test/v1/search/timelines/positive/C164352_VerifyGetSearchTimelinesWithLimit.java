package com.plutotv.test.v1.search.timelines.positive;

import com.plutotv.common.helper.RequestHelper;
import com.plutotv.test.BaseTest;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.HashMap;

import static com.plutotv.common.config.v1.search.SearchEndpointV1Manager.getEndpoint;
import static com.plutotv.common.helper.RequestParameterHelper.LIMIT;
import static com.plutotv.common.helper.RequestParameterHelper.REPLICATION_TYPE;
import static com.plutotv.common.url.UrlBuilder.buildRequestUrl;
import static com.plutotv.test.lib.api.coreapi.Constants.START_STOP_TIME_PARAMS;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.hasSize;

public class C164352_VerifyGetSearchTimelinesWithLimit extends BaseTest {
    private final RequestHelper requestHelper = new RequestHelper();

    @Test(groups = {"full"})
    public void verifyGetSearchTimelinesWithLimit() {
        int limit = 3;
        HashMap<String, String> params = new HashMap<>();
        params.put(REPLICATION_TYPE, "full-sync");
        params.put(LIMIT, String.valueOf(limit));
        params.putAll(START_STOP_TIME_PARAMS);
        Response response = requestHelper.getRequest(
                DEFAULT_HEADERS,
                params,
                buildRequestUrl(getEndpoint().v1SearchTimelines()));

        response.then()
                .statusCode(SC_OK);
        response.then()
                .body("data", hasSize(limit));
    }
}
