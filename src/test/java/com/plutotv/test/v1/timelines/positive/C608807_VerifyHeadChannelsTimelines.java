package com.plutotv.test.v1.timelines.positive;

import com.plutotv.common.helper.RequestHelper;
import com.plutotv.test.BaseTest;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static com.plutotv.common.config.v1.channels.ChannelsEndpointV1Manager.getEndpoint;
import static com.plutotv.common.url.UrlBuilder.buildRequestUrl;
import static org.apache.http.HttpStatus.SC_NO_CONTENT;
import static org.hamcrest.Matchers.emptyOrNullString;
import static org.hamcrest.Matchers.not;

public class C608807_VerifyHeadChannelsTimelines extends BaseTest {
    private final RequestHelper requestHelper = new RequestHelper();

    @Test(groups = {"full"})
    public void verifyHeadChannelsTimelines() {
        Response response = requestHelper.headRequest(
                DEFAULT_HEADERS,
                buildRequestUrl(getEndpoint().v1ChannelsTimelines()));

        response.then()
                .statusCode(SC_NO_CONTENT);
        response.then()
                .header("Last-Modified", not(emptyOrNullString()));
    }
}