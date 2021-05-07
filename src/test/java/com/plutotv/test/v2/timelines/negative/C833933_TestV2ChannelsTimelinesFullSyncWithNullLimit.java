/*
 * (c) 2021 Pluto Inc.
 */

package com.plutotv.test.v2.timelines.negative;

import com.plutotv.common.helper.validation.errors.ClientErrors;
import com.plutotv.common.helper.validation.errors.ErrorsValidation;
import com.plutotv.common.layer.step.v2.channels.timelines.ChannelsTimelinesSteps;
import com.plutotv.common.model.response.errors.Http400Error;
import com.plutotv.test.BaseTest;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static com.plutotv.common.helper.RequestParameterHelper.getStartAndStopParams;
import static org.apache.http.HttpStatus.SC_BAD_REQUEST;

public class C833933_TestV2ChannelsTimelinesFullSyncWithNullLimit extends BaseTest {
    private final ChannelsTimelinesSteps channelsTimelinesSteps = new ChannelsTimelinesSteps();
    private final ClientErrors clientErrors = new ClientErrors();
    private final ErrorsValidation errorsValidation = new ErrorsValidation();

    @Test(groups = {"full"})
    public void testV2ChannelsTimelinesFullSyncWithNullLimit() {
        Http400Error expectedError =
                clientErrors.getExpectedHttp400Error(
                        "strconv.ParseInt: parsing \"null\": invalid syntax");

        Response v2ChannelsTimelinesFullSyncWithNullLimit =
                channelsTimelinesSteps.getV2ChannelsTimelinesFullSyncWithLimit(
                        "null",
                        channelsTimelinesSteps.getV2ChannelsMostRecentlyUpdatedTimelineTimestamp(),
                        getStartAndStopParams(TIMELINE_RANGE_IN_HOURS));

        v2ChannelsTimelinesFullSyncWithNullLimit.then().statusCode(SC_BAD_REQUEST);
        Http400Error actualError = v2ChannelsTimelinesFullSyncWithNullLimit.as(Http400Error.class);
        errorsValidation.assertThatErrorAsExpected(expectedError, actualError);
    }
}
