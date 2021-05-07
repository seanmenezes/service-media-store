/*
 * (c) 2021 Pluto Inc.
 */

package com.plutotv.test.v1.ratings.descriptors.positive;

import com.plutotv.common.helper.validation.errors.ClientErrors;
import com.plutotv.common.helper.validation.errors.ErrorsValidation;
import com.plutotv.common.layer.endpoint.v1.ratings.RatingsDescriptorsEndpointsV1;
import com.plutotv.common.model.response.errors.Http400Error;
import com.plutotv.test.BaseTest;
import io.qameta.allure.Description;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static com.plutotv.common.helper.RequestParameterHelper.REPLICATION_TYPE;
import static org.apache.http.HttpStatus.SC_BAD_REQUEST;

public class C833811_TestV1RatingsDescriptorsFullSyncUpdatedBeforeMandatory extends BaseTest {
    private final RatingsDescriptorsEndpointsV1 ratingsDescriptorsEndpointsV1 = new RatingsDescriptorsEndpointsV1();
    private final ClientErrors clientErrors = new ClientErrors();
    private final ErrorsValidation errorsValidation = new ErrorsValidation();

    @Test(groups = {"full"})
    public void testV1RatingsFullSyncUpdatedBeforeMandatory() {
        Http400Error expectedError =
                clientErrors.getExpectedHttp400Error("ValidationFailed: params [UpdatedBefore] are invalid");

        Map<String, String> requestParams = new HashMap<>();
        requestParams.put(REPLICATION_TYPE, "full-sync");
        Response ratingsDescriptors = ratingsDescriptorsEndpointsV1.getRatingsDescriptors(requestParams);

        ratingsDescriptors.then().statusCode(SC_BAD_REQUEST);

        Http400Error actualError = ratingsDescriptors.as(Http400Error.class);
        errorsValidation.assertThatErrorAsExpected(expectedError, actualError);
    }
}
