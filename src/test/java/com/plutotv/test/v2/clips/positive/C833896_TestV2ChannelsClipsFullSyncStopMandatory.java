/*
 * (c) 2021 Pluto Inc.
 */

package com.plutotv.test.v2.clips.positive;

import com.plutotv.common.helper.validation.errors.ClientErrors;
import com.plutotv.common.helper.validation.errors.ErrorsValidation;
import com.plutotv.common.layer.endpoint.v2.channels.clips.ChannelsClipsEndpointsV2;
import com.plutotv.common.layer.step.v2.channels.clips.ChannelsClipsSteps;
import com.plutotv.common.model.response.errors.Http400Error;
import com.plutotv.test.BaseTest;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static com.plutotv.common.helper.RequestParameterHelper.*;
import static org.apache.http.HttpStatus.SC_BAD_REQUEST;

public class C833896_TestV2ChannelsClipsFullSyncStopMandatory extends BaseTest {
    private final ChannelsClipsSteps channelsClipsSteps = new ChannelsClipsSteps();
    private final ChannelsClipsEndpointsV2 channelsClipsEndpointsV2 = new ChannelsClipsEndpointsV2();
    private final ClientErrors clientErrors = new ClientErrors();
    private final ErrorsValidation errorsValidation = new ErrorsValidation();

    @Test(groups = {"full"})
    public void testV2ChannelsClipsFullSyncStartMandatory() {
        Http400Error expectedError =
                clientErrors.getExpectedHttp400Error("ValidationFailed: params [Stop] are invalid");

        Map<String, String> startAndStopParams = getStartAndStopParams(TIMELINE_RANGE_IN_HOURS);
        startAndStopParams.remove(STOP);

        Map<String, String> requestParams = new HashMap<>();
        requestParams.put(REPLICATION_TYPE, "full-sync");
        requestParams.put(UPDATED_BEFORE, channelsClipsSteps.getV2ChannelsMostRecentlyUpdatedClipTimestamp());
        requestParams.putAll(startAndStopParams);
        Response v2ChannelsClips = channelsClipsEndpointsV2.getChannelsClips(requestParams);

        v2ChannelsClips.then().statusCode(SC_BAD_REQUEST);
        Http400Error actualError = v2ChannelsClips.as(Http400Error.class);
        errorsValidation.assertThatErrorAsExpected(expectedError, actualError);
    }
}
