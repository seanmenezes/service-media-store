package com.plutotv.test.v1.advanced_categories.positive;

import com.plutotv.common.helper.RequestHelper;
import com.plutotv.test.BaseTest;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.HashMap;

import static com.plutotv.common.config.v1.channels.ChannelsEndpointV1Manager.getEndpoint;
import static com.plutotv.common.helper.RequestParameterHelper.OFFSET;
import static com.plutotv.common.helper.RequestParameterHelper.REPLICATION_TYPE;
import static com.plutotv.common.url.UrlBuilder.buildRequestUrl;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.equalTo;

public class C164347_VerifyGetAdvancedCategoriesWithOffset extends BaseTest {
    private final RequestHelper requestHelper = new RequestHelper();

    @Test(groups = {"full","adv_cat"})
    public void verifyGetAdvancedCategoriesWithOffset() {
        int index = 5;
        HashMap<String, String> params = new HashMap<>();
        params.put(REPLICATION_TYPE, "full-sync");
        Response response = requestHelper.getRequest(
                DEFAULT_HEADERS,
                params,
                buildRequestUrl(getEndpoint().v1ChannelsAdvancedCategories()));

        response.then()
                .statusCode(SC_OK);
        String categoryId = response.then()
                .extract()
                .path("data[" + index + "].payload._id");

        params.put(OFFSET, String.valueOf(index));
        response = requestHelper.getRequest(
                DEFAULT_HEADERS,
                params,
                buildRequestUrl(getEndpoint().v1ChannelsAdvancedCategories()));

        response.then()
                .statusCode(SC_OK);
        response.then()
                .body("data[0].payload._id", equalTo(categoryId));
    }
}
