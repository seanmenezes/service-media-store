/*
 * (c) 2021 Pluto Inc.
 */

package com.plutotv.test.v1.vod.episodes.positive;

import com.plutotv.common.helper.validation.errors.ClientErrors;
import com.plutotv.common.helper.validation.errors.ErrorsValidation;
import com.plutotv.common.layer.endpoint.v1.vod.VodEndpointsV1;
import com.plutotv.common.layer.step.v1.vod.VodEpisodesSteps;
import com.plutotv.common.model.response.errors.Http400Error;
import com.plutotv.test.BaseTest;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static com.plutotv.common.helper.RequestParameterHelper.UPDATED_BEFORE;
import static org.apache.http.HttpStatus.SC_BAD_REQUEST;

public class C833884_TestV1VodEpisodesReplicationTypeMandatory extends BaseTest {
    private final VodEndpointsV1 vodEndpointsV1 = new VodEndpointsV1();
    private final VodEpisodesSteps vodEpisodesSteps = new VodEpisodesSteps();
    private final ClientErrors clientErrors = new ClientErrors();
    private final ErrorsValidation errorsValidation = new ErrorsValidation();

    @Test(groups = {"full"})
    public void testV1VodEpisodesReplicationTypeMandatory() {
        Http400Error expectedError =
                clientErrors.getExpectedHttp400Error("ValidationFailed: params [ReplicationType] are invalid");

        Map<String, String> requestParams = new HashMap<>();
        requestParams.put(UPDATED_BEFORE, vodEpisodesSteps.getV1VodEpisodesMostRecentlyUpdatedEpisodeTimestamp());
        Response vodEpisodes = vodEndpointsV1.getVodEpisodes(requestParams);

        vodEpisodes.then().statusCode(SC_BAD_REQUEST);

        Http400Error actualError = vodEpisodes.as(Http400Error.class);
        errorsValidation.assertThatErrorAsExpected(expectedError, actualError);
    }
}
