/*
 * (c) 2021 Pluto Inc.
 */

package com.plutotv.test.v1.trending.clips.positive;

import com.plutotv.common.helper.validation.errors.ClientErrors;
import com.plutotv.common.helper.validation.errors.ErrorsValidation;
import com.plutotv.common.layer.endpoint.v1.trending.clips.TrendingClipsEndpointsV1;
import com.plutotv.common.layer.step.v1.trending.clips.TrendingClipsSteps;
import com.plutotv.common.model.response.errors.Http400Error;
import com.plutotv.test.BaseTest;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static com.plutotv.common.helper.RequestParameterHelper.UPDATED_BEFORE;
import static org.apache.http.HttpStatus.SC_BAD_REQUEST;

public class C833825_TestV1TrendingClipsReplicationTypeMandatory extends BaseTest {
    private final TrendingClipsEndpointsV1 trendingClipsEndpointsV1 = new TrendingClipsEndpointsV1();
    private final TrendingClipsSteps trendingClipsSteps = new TrendingClipsSteps();
    private final ClientErrors clientErrors = new ClientErrors();
    private final ErrorsValidation errorsValidation = new ErrorsValidation();

    @Test(groups = {"full"})
    public void testV1TrendingClipsReplicationTypeMandatory() {
        Http400Error expectedError =
                clientErrors.getExpectedHttp400Error("ValidationFailed: params [ReplicationType] are invalid");

        Map<String, String> requestParams = new HashMap<>();
        requestParams.put(UPDATED_BEFORE, trendingClipsSteps.getV1TrendingClipsMostRecentlyUpdatedRatingTimestamp());
        Response trendingClips = trendingClipsEndpointsV1.getTrendingClips(requestParams);

        trendingClips.then().statusCode(SC_BAD_REQUEST);

        Http400Error actualError = trendingClips.as(Http400Error.class);
        errorsValidation.assertThatErrorAsExpected(expectedError, actualError);
    }
}
