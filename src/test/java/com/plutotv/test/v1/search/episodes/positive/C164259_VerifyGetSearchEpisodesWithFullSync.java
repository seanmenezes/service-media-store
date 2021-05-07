package com.plutotv.test.v1.search.episodes.positive;

import com.plutotv.common.helper.RequestHelper;
import com.plutotv.test.BaseTest;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.HashMap;

import static com.plutotv.common.Constants.RegEx.DATE_TIME;
import static com.plutotv.common.config.v1.search.SearchEndpointV1Manager.getEndpoint;
import static com.plutotv.common.helper.RequestParameterHelper.REPLICATION_TYPE;
import static com.plutotv.common.url.UrlBuilder.buildRequestUrl;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.*;

public class C164259_VerifyGetSearchEpisodesWithFullSync extends BaseTest {
    private final RequestHelper requestHelper = new RequestHelper();

    @Test(groups = {"full"})
    public void verifyGetSearchEpisodesWithFullSync() {
        HashMap<String, String> params = new HashMap<>();
        params.put(REPLICATION_TYPE, "full-sync");
        Response response = requestHelper.getRequest(
                DEFAULT_HEADERS,
                params,
                buildRequestUrl(getEndpoint().v1SearchEpisodes()));

        response.then()
                .statusCode(SC_OK);
        response.then()
                .body("data.meta.replicaAction", not(hasItems(not("upsert"))))
                .body("data.payload.description", hasItems(not(emptyOrNullString())))
                .body("data.payload.slug", hasItems(not(emptyOrNullString())))
                .body("data.payload.rating", hasItems(not(emptyOrNullString())))
                .body("data.payload.genre", hasItems(not(emptyOrNullString())))
                .body("data.payload.type", hasItems(anyOf(equalTo("episode"), equalTo("movie"))))
                .body("data.payload.name", hasItems(not(emptyOrNullString())))
                .body("data.payload.images.path", hasItems(hasItems(startsWith("%PROTOCOL%://%HOSTNAME%/"))))
                .body("data.payload.images.title", hasItems(hasItems(not(emptyOrNullString()))))
                .body("data.payload.createdAt", hasItems(matchesPattern(DATE_TIME)))
                .body("data.payload.updatedAt", hasItems(matchesPattern(DATE_TIME)))
                .body("data.payload.status", hasItems(anyOf(equalTo("on air"), equalTo("completed"))))
                .body("data.payload.distributeAs.AVOD", not(hasItems(not(isA(Boolean.class)))))
                .body("data.payload.availabilityWindows.AVOD.startDate", not(hasItems(emptyOrNullString())))
                .body("data.payload.availabilityWindows.AVOD.endDate", not(hasItems(emptyOrNullString())))
                .body("data.payload.regionFilter.include", hasItems(not(empty())))
                .body("data.payload.regionFilter.exclude", hasItems(not(empty())))
                .body("data.payload.distribution.include", hasItems(not(empty())))
                .body("data.payload.distribution.exclude", hasItems(not(empty())));
    }
}
