package com.plutotv.test.v1.search.episodes.positive;

import com.plutotv.common.helper.RequestHelper;
import com.plutotv.test.BaseTest;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.HashMap;

import static com.plutotv.common.config.v1.search.SearchEndpointV1Manager.getEndpoint;
import static com.plutotv.common.helper.RequestParameterHelper.REPLICATION_TYPE;
import static com.plutotv.common.url.UrlBuilder.buildRequestUrl;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.stringContainsInOrder;

public class C609157_CheckEpisodesUseFeaturedImages extends BaseTest {
    private final RequestHelper requestHelper = new RequestHelper();

    @Test(groups = {"full"})
    public void checkEpisodesUseFeaturedImages() {
        HashMap<String, String> params = new HashMap<>();
        params.put(REPLICATION_TYPE, "full-sync");
        Response response = requestHelper.getRequest(
                DEFAULT_HEADERS,
                params,
                buildRequestUrl(getEndpoint().v1SearchEpisodes()));

        response.then()
                .statusCode(SC_OK);
        response.then()
                .body("data.payload.images.path", hasItems(hasItems(stringContainsInOrder("//%HOSTNAME%/series/"))))
                .body("data.payload.images.path", hasItems(hasItems(stringContainsInOrder("/featuredImage.jpg"))));
    }
}
