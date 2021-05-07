package com.plutotv.test.v1.search.channels.positive;

import com.plutotv.common.helper.RequestHelper;
import com.plutotv.test.BaseTest;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.HashMap;

import static com.plutotv.common.config.v1.search.SearchEndpointV1Manager.getEndpoint;
import static com.plutotv.common.helper.RequestParameterHelper.REPLICATION_TYPE;
import static com.plutotv.common.url.UrlBuilder.buildRequestUrl;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.*;

public class C419219_VerifyGetSearchChannelsWithDeltaUpdate extends BaseTest {
    private final RequestHelper requestHelper = new RequestHelper();

    //ToDo re-write scenario when we will have read access to mongo
    @Test(groups = {"full"})
    public void verifyGetSearchChannelsWithDeltaUpdate() {
        HashMap<String, String> params = new HashMap<>();
        params.put(REPLICATION_TYPE, "delta-update");
        Response response = requestHelper.getRequest(
                DEFAULT_HEADERS,
                params,
                buildRequestUrl(getEndpoint().v1SearchChannels()));

        response.then()
                .statusCode(SC_OK);
        response.then()
                .body("data.meta.replicaAction", hasItems("remove"))
                .body("data.meta.id", hasItems(not(emptyOrNullString())));
//                .body("data.meta.replicaAction", hasItems("upsert"))
//                .body("data.payload.slug", not(hasItems(emptyOrNullString())))
//                .body("data.payload.name", not(hasItems(emptyOrNullString())))
//                .body("data.payload.summary", not(hasItems(emptyOrNullString())))
//                .body("data.payload.images.path", hasItems(hasItems(startsWith("%PROTOCOL%://%HOSTNAME%/channels"))))
//                .body("data.payload.images.title", hasItems(hasItems(not(emptyOrNullString()))))
//                .body("data.payload.createdAt", hasItems(matchesPattern(DATE_TIME)))
//                .body("data.payload.updatedAt", hasItems(matchesPattern(DATE_TIME)))
//                .body("data.payload.regionFilter.include", not(hasItems(empty())))
//                .body("data.payload.regionFilter.exclude", not(hasItems(empty())))
//                .body("data.payload.plutoOfficeOnly", not(hasItems(not(isA(Boolean.class)))))
//                .body("data.payload.distribution.include", not(hasItems(empty())))
//                .body("data.payload.distribution.exclude", not(hasItems(empty())));
    }
}