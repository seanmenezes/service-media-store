package com.plutotv.test.v1.advanced_categories.positive;

import com.plutotv.common.helper.RequestHelper;
import com.plutotv.test.BaseTest;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;

import static com.plutotv.common.Constants.RegEx.DATE_TIME;
import static com.plutotv.common.config.v1.channels.ChannelsEndpointV1Manager.getEndpoint;
import static com.plutotv.common.helper.RequestParameterHelper.REPLICATION_TYPE;
import static com.plutotv.common.helper.RequestParameterHelper.UPDATED_AFTER;
import static com.plutotv.common.url.UrlBuilder.buildRequestUrl;
import static com.plutotv.test.lib.api.coreapi.Constants.RegEx.ID;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.*;

public class C415309_VerifyGetAdvancedCategoriesWithDeltaUpdate extends BaseTest {
    private final RequestHelper requestHelper = new RequestHelper();

    @Test(groups = {"full","adv_cat"})
    public void verifyGetAdvancedCategoriesWithDeltaUpdate() {
        HashMap<String, String> params = new HashMap<>();
        params.put(REPLICATION_TYPE, "full-sync");
        Response response = requestHelper.getRequest(
                DEFAULT_HEADERS,
                params,
                buildRequestUrl(getEndpoint().v1ChannelsAdvancedCategories()));

        response.then()
                .statusCode(SC_OK);
        String dateString = response.then()
                .extract()
                .path("data[0].payload.updatedAt");
        Instant date = Instant.parse(dateString);

        params.put(REPLICATION_TYPE, "delta-update");
        params.put(UPDATED_AFTER, String.valueOf(date.plus(1, ChronoUnit.HOURS)));
        response = requestHelper.getRequest(
                DEFAULT_HEADERS,
                params,
                buildRequestUrl(getEndpoint().v1ChannelsAdvancedCategories()));

        response.then()
                .statusCode(SC_OK);
        response.then()
                .body("data.payload.updatedAt", not(hasItems(dateString)))
                .body("data.meta.replicaAction", not(hasItems(not(anyOf(is("upsert"), is("remove"))))))
                .body("data.payload._id", not(hasItems(not(matchesPattern(ID)))))
                .body("data.payload.name", hasItems(not(emptyOrNullString())))
                .body("data.payload.order", not(hasItems(nullValue())))
                .body("data.payload.svgImageUrl.path", hasItems(startsWith("http")))
                .body("data.payload.pngImageUrl.path", hasItems(startsWith("http")))
                .body("data.payload.createdAt", hasItems(matchesPattern(DATE_TIME)))
                .body("data.payload.updatedAt", hasItems(matchesPattern(DATE_TIME)));
    }
}
