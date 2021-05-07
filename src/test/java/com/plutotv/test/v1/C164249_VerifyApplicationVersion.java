/*
 * (c) 2020 Pluto Inc.
 */

package com.plutotv.test.v1;

import com.plutotv.common.helper.RequestHelper;
import com.plutotv.test.BaseTest;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static com.plutotv.common.config.v1.channels.ChannelsEndpointV1Manager.getEndpoint;
import static com.plutotv.common.url.UrlBuilder.buildRequestUrl;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.*;

public class C164249_VerifyApplicationVersion extends BaseTest {
    private final RequestHelper requestHelper = new RequestHelper();

    @Test(groups = {"full"})
    public void verifyApplicationVersion() {
        Response response = requestHelper.getRequest(
                DEFAULT_HEADERS,
                buildRequestUrl(getEndpoint().v1HealthCheckInfo()));

        response.then()
                .statusCode(SC_OK);
        response.then()
                .body("appName", equalTo("service-media-store"))
                .body("appVersion", notNullValue())
                .body("clusterName", anyOf(equalTo("mediastore-grn"), equalTo("mediastore-blu"), equalTo("infrastructure")))
                .body("clusterVersion", notNullValue())
                .body("git.hash", not(emptyOrNullString()))
                .body("git.branch", not(emptyOrNullString()))
                .body("git.url", equalTo("github.com/Pluto-tv/service-media-store"))
                .body("aws.region", anyOf(equalTo("us-east-1"), equalTo("us-east-2")));
    }
}

