package com.plutotv.test.v1.timelines.positive;

import com.plutotv.common.helper.RequestHelper;
import com.plutotv.test.BaseTest;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.HashMap;

import static com.plutotv.common.Constants.RegEx.DATE_TIME;
import static com.plutotv.common.config.v1.channels.ChannelsEndpointV1Manager.getEndpoint;
import static com.plutotv.common.helper.RequestParameterHelper.REPLICATION_TYPE;
import static com.plutotv.common.url.UrlBuilder.buildRequestUrl;
import static com.plutotv.test.lib.api.coreapi.Constants.RegEx.ID;
import static com.plutotv.test.lib.api.coreapi.Constants.START_STOP_TIME_PARAMS;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.*;

public class C164256_VerifyGetTimelinesWithFullSync extends BaseTest {
    private final RequestHelper requestHelper = new RequestHelper();

    @Test(groups = {"full"})
    public void verifyGetTimelinesWithFullSync() {
        HashMap<String, String> params = new HashMap<>();
        params.put(REPLICATION_TYPE, "full-sync");
        params.putAll(START_STOP_TIME_PARAMS);
        Response response = requestHelper.getRequest(
                DEFAULT_HEADERS,
                params,
                buildRequestUrl(getEndpoint().v1ChannelsTimelines()));

        response.then()
                .statusCode(SC_OK);
        response.then()
                .body("data.meta.replicaAction", not(hasItems(not("upsert"))))
                .body("data.payload._id", not(hasItems(not(matchesPattern(ID)))))
                .body("data.payload.channel", not(hasItems(not(matchesPattern(ID)))))
                .body("data.payload.episode._id", not(hasItems(not(matchesPattern(ID)))))
                .body("data.payload.episode.number", not(hasItems(nullValue())))
                .body("data.payload.episode.description", not(hasItems(emptyOrNullString())))
                .body("data.payload.episode.duration", not(hasItems(lessThanOrEqualTo(0))))
                .body("data.payload.episode.originalContentDuration", not(hasItems(lessThanOrEqualTo(0))))
                .body("data.payload.episode.genre", not(hasItems(emptyOrNullString())))
                .body("data.payload.episode.subGenre", not(hasItems(emptyOrNullString())))
                .body("data.payload.episode.distributeAs.AVOD", not(hasItems(not(isA(Boolean.class)))))
                .body("data.payload.episode.rating", not(hasItems(emptyOrNullString())))
                .body("data.payload.episode.name", not(hasItems(emptyOrNullString())))
                .body("data.payload.episode.slug", hasItems(not(emptyOrNullString())))
                .body("data.payload.episode.poster.path", hasItems(startsWith("%PROTOCOL%://%HOSTNAME%/episodes")))
                .body("data.payload.episode.firstAired", hasItems(matchesPattern(DATE_TIME)))
                .body("data.payload.episode.firstAired", not(hasItems(emptyOrNullString())))
                .body("data.payload.episode.thumbnail.path", hasItems(startsWith("%PROTOCOL%://%HOSTNAME%/episode")))
                .body("data.payload.episode.liveBroadcast", not(hasItems(not(isA(Boolean.class)))))
                .body("data.payload.episode.poster.path", hasItems(startsWith("%PROTOCOL%://%HOSTNAME%/episodes")))
                .body("data.payload.episode.series._id", not(hasItems(not(matchesPattern(ID)))))
                .body("data.payload.episode.series.name", not(hasItems(emptyOrNullString())))
                .body("data.payload.episode.series.slug", not(hasItems(emptyOrNullString())))
                .body("data.payload.episode.series.type", not(hasItems(emptyOrNullString())))
                .body("data.payload.episode.series.tile.path", hasItems(startsWith("%PROTOCOL%://%HOSTNAME%/series")))
                .body("data.payload.episode.series.description", not(hasItems(emptyOrNullString())))
                .body("data.payload.episode.series.summary", not(hasItems(emptyOrNullString())))
                .body("data.payload.episode.series.featuredImage.path", hasItems(startsWith("%PROTOCOL%://%HOSTNAME%/series")))
                .body("data.payload.start", hasItems(matchesPattern(DATE_TIME)))
                .body("data.payload.start", not(hasItems(emptyOrNullString())))
                .body("data.payload.stop", hasItems(matchesPattern(DATE_TIME)))
                .body("data.payload.stop", not(hasItems(emptyOrNullString())))
                .body("data.payload.featured", not(hasItems(not(isA(Boolean.class)))))
                .body("data.payload.title", not(hasItems(emptyOrNullString())))
                .body("data.payload.createdAt", hasItems(matchesPattern(DATE_TIME)))
                .body("data.payload.updatedAt", hasItems(matchesPattern(DATE_TIME)));
    }
}
