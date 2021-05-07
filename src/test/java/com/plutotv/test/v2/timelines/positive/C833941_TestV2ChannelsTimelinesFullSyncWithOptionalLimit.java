/*
 * (c) 2021 Pluto Inc.
 */

package com.plutotv.test.v2.timelines.positive;

import com.plutotv.common.layer.step.v2.channels.timelines.ChannelsTimelinesSteps;
import com.plutotv.test.BaseTest;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static com.plutotv.common.helper.RequestParameterHelper.getStartAndStopParams;
import static java.lang.Long.parseLong;
import static org.apache.http.HttpStatus.SC_OK;
import static org.assertj.core.api.Assertions.assertThat;

public class C833941_TestV2ChannelsTimelinesFullSyncWithOptionalLimit extends BaseTest {
    private final ChannelsTimelinesSteps channelsTimelinesSteps = new ChannelsTimelinesSteps();

    @Test(groups = {"full"})
    public void testV2ChannelsTimelinesFullSyncWithOptionalLimit() {
        String timelinesCountExpected = "1";

        Response v2ChannelsTimelinesFullSyncWithLimit =
                channelsTimelinesSteps.getV2ChannelsTimelinesFullSyncWithLimit(
                        timelinesCountExpected,
                        channelsTimelinesSteps.getV2ChannelsMostRecentlyUpdatedTimelineTimestamp(),
                        getStartAndStopParams(TIMELINE_RANGE_IN_HOURS));

        v2ChannelsTimelinesFullSyncWithLimit.then().statusCode(SC_OK);

        long timelinesCountActual = channelsTimelinesSteps.getTimelinesCount(v2ChannelsTimelinesFullSyncWithLimit);
        assertThatTimelinesCountAsExpected(timelinesCountExpected, timelinesCountActual);
    }

    @Step
    private void assertThatTimelinesCountAsExpected(String timelinesCountExpected, long timelinesCountActual) {
        assertThat(timelinesCountActual).as("Only %s timelines expected, but actually %s in response",
                timelinesCountExpected, timelinesCountActual)
                .isEqualTo(parseLong(timelinesCountExpected));
    }
}
