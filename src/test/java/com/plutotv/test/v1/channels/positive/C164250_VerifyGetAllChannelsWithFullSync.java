package com.plutotv.test.v1.channels.positive;

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
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.*;

public class C164250_VerifyGetAllChannelsWithFullSync extends BaseTest {
    private final RequestHelper requestHelper = new RequestHelper();

    @Test(groups = {"full"})
    public void verifyGetAllChannelsWithFullSync() {
        HashMap<String, String> params = new HashMap<>();
        params.put(REPLICATION_TYPE, "full-sync");
        Response response = requestHelper.getRequest(
                DEFAULT_HEADERS,
                params,
                buildRequestUrl(getEndpoint().v1Channels()));

        response.then()
                .statusCode(SC_OK);
        response.then()
                .body("data.meta.replicaAction", not(hasItems(not("upsert"))))
                .body("data.payload._id", not(hasItems(not(matchesPattern(ID)))))
                .body("data.payload.autoSchedule", not(hasItems(not(isA(Boolean.class)))))
                .body("data.payload.category", not(hasItems(emptyOrNullString())))
                .body("data.payload.chatEnabled", not(hasItems(not(isA(Boolean.class)))))
                .body("data.payload.directOnly", hasItems(isA(Boolean.class)))
                .body("data.payload.distribution.include", not(hasItems(empty())))
                .body("data.payload.distribution.exclude", not(hasItems(empty())))
                .body("data.payload.episodesQueue", hasItems(not(empty())))
                .body("data.payload.episodesQueue._id", not(hasItems(hasItems(not(matchesPattern(ID))))))
                .body("data.payload.episodesQueue.episode", not(hasItems(hasItems(not(matchesPattern(ID))))))
                .body("data.payload.episodesQueue.order", not(hasItems(hasItems(nullValue()))))
                .body("data.payload.featuredImage.path", hasItems(startsWith("%PROTOCOL%://%HOSTNAME%/channels")))
                .body("data.payload.hash", not(hasItems(emptyOrNullString())))
                .body("data.payload.logo.path", hasItems(startsWith("%PROTOCOL%://%HOSTNAME%/channels")))
                .body("data.payload.name", not(hasItems(emptyOrNullString())))
                .body("data.payload.number", not(hasItems(nullValue())))
                .body("data.payload.regionFilter.include", not(hasItems(empty())))
                .body("data.payload.regionFilter.exclude", not(hasItems(empty())))
                .body("data.payload.dmaDistribution", hasItems(not(empty())))
                .body("data.payload.slug", not(hasItems(emptyOrNullString())))
                .body("data.payload.summary", not(hasItems(emptyOrNullString())))
                .body("data.payload.thumbnail.path", hasItems(startsWith("%PROTOCOL%://%HOSTNAME%/channels")))
                .body("data.payload.tile.path", hasItems(startsWith("http")))
                .body("data.payload.type", hasItems(anyOf(equalTo("temporary"), equalTo("permanent"))))
                .body("data.payload.visibility", hasItems("everyone"))
                .body("data.payload.isStitched", hasItems(isA(Boolean.class)))
                .body("data.payload.featuredOrder", hasItems(notNullValue()))
                .body("data.payload.activeRegion", not(hasItems(emptyOrNullString())))
                .body("data.payload.plutoOfficeOnly", not(hasItems(not(isA(Boolean.class)))))
                .body("data.payload.solidLogoPNG.path", hasItems(startsWith("http")))
                .body("data.payload.solidLogoSVG.path", hasItems(startsWith("http")))
                .body("data.payload.cohortMask", hasItems(notNullValue()))
                .body("data.payload.colorLogoPNG.path", hasItems(startsWith("http")))
                .body("data.payload.colorLogoSVG.path", hasItems(startsWith("http")))
                .body("data.payload.categories", hasItems(not(empty())))
                .body("data.payload.categories.catId", not(hasItems(hasItems(not(matchesPattern(ID))))))
                .body("data.payload.categories.order", not(hasItems(hasItems(nullValue()))))
                .body("data.payload.createdAt", hasItems(matchesPattern(DATE_TIME)))
                .body("data.payload.updatedAt", hasItems(matchesPattern(DATE_TIME)));
    }
}
