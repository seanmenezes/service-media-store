/*
 * (c) 2021 Pluto Inc.
 */

package com.plutotv.test.v2.timelines.positive;

import com.plutotv.common.helper.validation.errors.ClientErrors;
import com.plutotv.common.helper.validation.errors.ErrorsValidation;
import com.plutotv.common.layer.endpoint.v2.channels.timelines.ChannelsTimelinesEndpointsV2;
import com.plutotv.common.layer.step.v2.channels.timelines.ChannelsTimelinesSteps;
import com.plutotv.common.model.response.errors.Http400Error;
import com.plutotv.test.BaseTest;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static com.plutotv.common.helper.RequestParameterHelper.UPDATED_BEFORE;
import static com.plutotv.common.helper.RequestParameterHelper.getStartAndStopParams;
import static org.apache.http.HttpStatus.SC_BAD_REQUEST;

public class C833944_TestV2ChannelsTimelinesReplicationTypeMandatory extends BaseTest {
    private final ChannelsTimelinesEndpointsV2 channelsTimelinesEndpointsV2 = new ChannelsTimelinesEndpointsV2();
    private final ChannelsTimelinesSteps channelsTimelinesSteps = new ChannelsTimelinesSteps();
    private final ClientErrors clientErrors = new ClientErrors();
    private final ErrorsValidation errorsValidation = new ErrorsValidation();

    @Test(groups = {"full"})
    public void testV2ChannelsTimelinesReplicationTypeMandatory() {
        Http400Error expectedError =
                clientErrors.getExpectedHttp400Error("ValidationFailed: params [ReplicationType] are invalid");

        Map<String, String> requestParams = new HashMap<>();
        requestParams.put(UPDATED_BEFORE, channelsTimelinesSteps.getV2ChannelsMostRecentlyUpdatedTimelineTimestamp());
        requestParams.putAll(getStartAndStopParams(TIMELINE_RANGE_IN_HOURS));
        Response v2ChannelsTimelines = channelsTimelinesEndpointsV2.getChannelsTimelines(requestParams);

        v2ChannelsTimelines.then().statusCode(SC_BAD_REQUEST);
        Http400Error actualError = v2ChannelsTimelines.as(Http400Error.class);
        errorsValidation.assertThatErrorAsExpected(expectedError, actualError);
    }
}
