/*
 * (c) 2021 Pluto Inc.
 */

package com.plutotv.test.v2.episodes.positive;

import com.plutotv.common.helper.validation.errors.ClientErrors;
import com.plutotv.common.helper.validation.errors.ErrorsValidation;
import com.plutotv.common.layer.endpoint.v2.channels.episodes.ChannelsEpisodesEndpointsV2;
import com.plutotv.common.model.response.errors.Http400Error;
import com.plutotv.test.BaseTest;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static com.plutotv.common.helper.RequestParameterHelper.REPLICATION_TYPE;
import static com.plutotv.common.helper.RequestParameterHelper.getStartAndStopParams;
import static org.apache.http.HttpStatus.SC_BAD_REQUEST;

public class C833908_TestV2ChannelsEpisodesFullSyncUpdatedBeforeMandatory extends BaseTest {
    private final ChannelsEpisodesEndpointsV2 channelsEpisodesEndpointsV2 = new ChannelsEpisodesEndpointsV2();
    private final ClientErrors clientErrors = new ClientErrors();
    private final ErrorsValidation errorsValidation = new ErrorsValidation();

    @Test(groups = {"full"})
    public void testV2ChannelsEpisodesFullSyncUpdatedBeforeMandatory() {
        Http400Error expectedError =
                clientErrors.getExpectedHttp400Error("ValidationFailed: params [UpdatedBefore] are invalid");

        Map<String, String> requestParams = new HashMap<>();
        requestParams.put(REPLICATION_TYPE, "full-sync");
        requestParams.putAll(getStartAndStopParams(TIMELINE_RANGE_IN_HOURS));
        Response v2ChannelsEpisodes = channelsEpisodesEndpointsV2.getChannelsEpisodes(requestParams);

        v2ChannelsEpisodes.then().statusCode(SC_BAD_REQUEST);
        Http400Error actualError = v2ChannelsEpisodes.as(Http400Error.class);
        errorsValidation.assertThatErrorAsExpected(expectedError, actualError);
    }
}
