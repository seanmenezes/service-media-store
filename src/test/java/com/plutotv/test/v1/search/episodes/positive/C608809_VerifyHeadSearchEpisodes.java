package com.plutotv.test.v1.search.episodes.positive;

import com.plutotv.common.helper.RequestHelper;
import com.plutotv.test.BaseTest;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static com.plutotv.common.config.v1.search.SearchEndpointV1Manager.getEndpoint;
import static com.plutotv.common.url.UrlBuilder.buildRequestUrl;
import static org.apache.http.HttpStatus.SC_NO_CONTENT;
import static org.hamcrest.Matchers.emptyOrNullString;
import static org.hamcrest.Matchers.not;

public class C608809_VerifyHeadSearchEpisodes extends BaseTest {
    private final RequestHelper requestHelper = new RequestHelper();

    @Test(groups = {"full"})
    public void verifyHeadSearchEpisodes() {
        Response response = requestHelper.headRequest(
                DEFAULT_HEADERS,
                buildRequestUrl(getEndpoint().v1SearchEpisodes()));

        response.then()
                .statusCode(SC_NO_CONTENT);
        response.then()
                .header("Last-Modified", not(emptyOrNullString()));
    }
}
