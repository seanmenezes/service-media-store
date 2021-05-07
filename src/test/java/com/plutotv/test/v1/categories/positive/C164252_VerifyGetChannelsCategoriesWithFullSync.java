package com.plutotv.test.v1.categories.positive;

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

public class C164252_VerifyGetChannelsCategoriesWithFullSync extends BaseTest {
    private final RequestHelper requestHelper = new RequestHelper();

    @Test(groups = {"full"})
    public void verifyGetChannelsCategoriesWithFullSync() {
        HashMap<String, String> params = new HashMap<>();
        params.put(REPLICATION_TYPE, "full-sync");
        Response response = requestHelper.getRequest(
                DEFAULT_HEADERS,
                params,
                buildRequestUrl(getEndpoint().v1ChannelsCategories()));

        response.then()
                .statusCode(SC_OK);
        response.then()
                .body("data.meta.replicaAction", not(hasItems(not("upsert"))))
                .body("data.payload._id", not(hasItems(not(matchesPattern(ID)))))
                .body("data.payload.name", hasItems(not(emptyOrNullString())))
                .body("data.payload.order", not(hasItems(nullValue())))
                .body("data.payload.svgImageUrl.path", hasItems(startsWith("http")))
                .body("data.payload.pngImageUrl.path", hasItems(startsWith("http")))
                .body("data.payload.createdAt", hasItems(matchesPattern(DATE_TIME)))
                .body("data.payload.updatedAt", hasItems(matchesPattern(DATE_TIME)));
    }
}
