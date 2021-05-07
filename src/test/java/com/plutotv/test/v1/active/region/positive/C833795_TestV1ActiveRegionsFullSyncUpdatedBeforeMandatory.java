/*
 * (c) 2021 Pluto Inc.
 */

package com.plutotv.test.v1.active.region.positive;

import com.plutotv.common.helper.validation.errors.ClientErrors;
import com.plutotv.common.helper.validation.errors.ErrorsValidation;
import com.plutotv.common.layer.endpoint.v1.active.regions.ActiveRegionsEndpointsV1;
import com.plutotv.common.model.response.errors.Http400Error;
import com.plutotv.test.BaseTest;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static com.plutotv.common.helper.RequestParameterHelper.REPLICATION_TYPE;
import static org.apache.http.HttpStatus.SC_BAD_REQUEST;

public class C833795_TestV1ActiveRegionsFullSyncUpdatedBeforeMandatory extends BaseTest {
    private final ActiveRegionsEndpointsV1 activeRegionsEndpointsV1 = new ActiveRegionsEndpointsV1();
    private final ClientErrors clientErrors = new ClientErrors();
    private final ErrorsValidation errorsValidation = new ErrorsValidation();

    @Test(groups = {"full"})
    public void testV1RatingsFullSyncUpdatedBeforeMandatory() {
        Http400Error expectedError =
                clientErrors.getExpectedHttp400Error("ValidationFailed: params [UpdatedBefore] are invalid");

        Map<String, String> requestParams = new HashMap<>();
        requestParams.put(REPLICATION_TYPE, "full-sync");
        Response activeRegions = activeRegionsEndpointsV1.getActiveRegions(requestParams);

        activeRegions.then().statusCode(SC_BAD_REQUEST);

        Http400Error actualError = activeRegions.as(Http400Error.class);
        errorsValidation.assertThatErrorAsExpected(expectedError, actualError);
    }
}
