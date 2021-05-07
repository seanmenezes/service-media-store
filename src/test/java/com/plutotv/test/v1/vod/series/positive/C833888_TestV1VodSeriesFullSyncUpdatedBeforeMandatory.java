/*
 * (c) 2021 Pluto Inc.
 */

package com.plutotv.test.v1.vod.series.positive;

import com.plutotv.common.helper.validation.errors.ClientErrors;
import com.plutotv.common.helper.validation.errors.ErrorsValidation;
import com.plutotv.common.layer.endpoint.v1.vod.VodEndpointsV1;
import com.plutotv.common.model.response.errors.Http400Error;
import com.plutotv.test.BaseTest;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static com.plutotv.common.helper.RequestParameterHelper.REPLICATION_TYPE;
import static org.apache.http.HttpStatus.SC_BAD_REQUEST;

public class C833888_TestV1VodSeriesFullSyncUpdatedBeforeMandatory extends BaseTest {
    private final VodEndpointsV1 vodEndpointsV1 = new VodEndpointsV1();
    private final ClientErrors clientErrors = new ClientErrors();
    private final ErrorsValidation errorsValidation = new ErrorsValidation();

    @Test(groups = {"full"})
    public void testV1VodSeriesFullSyncUpdatedBeforeMandatory() {
        Http400Error expectedError =
                clientErrors.getExpectedHttp400Error("ValidationFailed: params [UpdatedBefore] are invalid");

        Map<String, String> requestParams = new HashMap<>();
        requestParams.put(REPLICATION_TYPE, "full-sync");
        Response vodSeries = vodEndpointsV1.getVodSeries(requestParams);

        vodSeries.then().statusCode(SC_BAD_REQUEST);

        Http400Error actualError = vodSeries.as(Http400Error.class);
        errorsValidation.assertThatErrorAsExpected(expectedError, actualError);
    }
}
