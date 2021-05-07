/*
 * (c) 2021 Pluto Inc.
 */

package com.plutotv.test.v2.timelines.negative;

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

import static com.plutotv.common.helper.RequestParameterHelper.*;
import static org.apache.http.HttpStatus.SC_BAD_REQUEST;

public class C833929_TestV2ChannelsTimelinesFullSyncNullStart extends BaseTest {
    private final ChannelsTimelinesEndpointsV2 channelsTimelinesEndpointsV2 = new ChannelsTimelinesEndpointsV2();
    private final ChannelsTimelinesSteps channelsTimelinesSteps = new ChannelsTimelinesSteps();
    private final ClientErrors clientErrors = new ClientErrors();
    private final ErrorsValidation errorsValidation = new ErrorsValidation();

    @Test(groups = {"full"})
    public void testV2ChannelsTimelinesFullSyncNullStart() {
        Http400Error expectedError =
                clientErrors.getExpectedHttp400Error(
                        "parsing time \"null\" as \"2006-01-02T15:04:05Z07:00\": cannot parse \"null\" as \"2006\"");

        Map<String, String> startAndStopParams = getStartAndStopParams(TIMELINE_RANGE_IN_HOURS);
        startAndStopParams.remove(START);
        startAndStopParams.put(START, "null");

        Map<String, String> requestParams = new HashMap<>();
        requestParams.put(REPLICATION_TYPE, "full-sync");
        requestParams.put(UPDATED_BEFORE, channelsTimelinesSteps.getV2ChannelsMostRecentlyUpdatedTimelineTimestamp());
        requestParams.putAll(startAndStopParams);
        Response v2ChannelsTimelines = channelsTimelinesEndpointsV2.getChannelsTimelines(requestParams);

        v2ChannelsTimelines.then().statusCode(SC_BAD_REQUEST);
        Http400Error actualError = v2ChannelsTimelines.as(Http400Error.class);
        errorsValidation.assertThatErrorAsExpected(expectedError, actualError);
    }
}
