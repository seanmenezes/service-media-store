package com.plutotv.test.v1.search.timelines.positive;

import com.plutotv.common.helper.RequestHelper;
import com.plutotv.test.BaseTest;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.HashMap;

import static com.plutotv.common.Constants.RegEx.DATE_TIME;
import static com.plutotv.common.config.v1.search.SearchEndpointV1Manager.getEndpoint;
import static com.plutotv.common.helper.RequestParameterHelper.REPLICATION_TYPE;
import static com.plutotv.common.url.UrlBuilder.buildRequestUrl;
import static com.plutotv.test.lib.api.coreapi.Constants.RegEx.ID;
import static com.plutotv.test.lib.api.coreapi.Constants.START_STOP_TIME_PARAMS;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.*;

public class C164260_VerifyGetSearchTimelinesWithFullSync extends BaseTest {
    private final RequestHelper requestHelper = new RequestHelper();

    @Test(groups = {"full"})
    public void verifyGetSearchTimelinesWithFullSync() {
        HashMap<String, String> params = new HashMap<>();
        params.put(REPLICATION_TYPE, "full-sync");
        params.putAll(START_STOP_TIME_PARAMS);
        Response response = requestHelper.getRequest(
                DEFAULT_HEADERS,
                params,
                buildRequestUrl(getEndpoint().v1SearchTimelines()));

        response.then()
                .statusCode(SC_OK);
        response.then()
                .body("data.meta.replicaAction", not(hasItems(not("upsert"))))
                .body("data.payload.slug", not(hasItems(not(matchesPattern(ID)))))
                .body("data.payload.name", hasItems(not(emptyOrNullString())))
                .body("data.payload.images.path", hasItems(hasItems(startsWith("%PROTOCOL%://%HOSTNAME%/"))))
                .body("data.payload.images.title", hasItems(hasItems(not(emptyOrNullString()))))
                .body("data.payload.start", not(hasItems(emptyOrNullString())))
                .body("data.payload.stop", not(hasItems(emptyOrNullString())))
                .body("data.payload.channel.slug", not(hasItems(emptyOrNullString())))
                .body("data.payload.channel.name", not(hasItems(emptyOrNullString())))
                .body("data.payload.createdAt", hasItems(matchesPattern(DATE_TIME)))
                .body("data.payload.updatedAt", hasItems(matchesPattern(DATE_TIME)))
                .body("data.payload.regionFilter.include", not(hasItems(empty())))
                .body("data.payload.regionFilter.exclude", not(hasItems(empty())))
                .body("data.payload.plutoOfficeOnly", not(hasItems(not(isA(Boolean.class)))))
                .body("data.payload.distribution.include", not(hasItems(empty())))
                .body("data.payload.distribution.exclude", not(hasItems(empty())));
    }
}
