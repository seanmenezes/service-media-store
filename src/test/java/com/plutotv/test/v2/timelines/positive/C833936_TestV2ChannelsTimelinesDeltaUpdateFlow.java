/*
 * (c) 2021 Pluto Inc.
 */

package com.plutotv.test.v2.timelines.positive;

import com.plutotv.common.helper.validation.V2ChannelsTimelinesValidation;
import com.plutotv.common.layer.step.v2.channels.timelines.ChannelsTimelinesSteps;
import com.plutotv.test.BaseTest;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.Map;

import static com.plutotv.common.Constants.REPL_TYPE_UPSERT;
import static com.plutotv.common.helper.RequestParameterHelper.STOP;
import static com.plutotv.common.helper.RequestParameterHelper.getStartAndStopParams;
import static org.apache.http.HttpStatus.SC_OK;

public class C833936_TestV2ChannelsTimelinesDeltaUpdateFlow extends BaseTest {
    private final ChannelsTimelinesSteps channelsTimelinesSteps = new ChannelsTimelinesSteps();
    private final V2ChannelsTimelinesValidation v2ChannelsTimelinesValidation = new V2ChannelsTimelinesValidation();

    //updates emulation tool should be run. In SSM lambda-mock-updates/enabled set to true
    @Test(groups = {"v2channels_delta_update"})
    public void testV2ChannelsTimelinesDeltaUpdateFlow() {
        int awaitAtMostMin = 10;
        int pollIntervalSeconds = 5;

        String syncStop = channelsTimelinesSteps.getV2ChannelsMostRecentlyUpdatedTimelineTimestamp();
        Map<String, String> timeRange = getStartAndStopParams(TIMELINE_RANGE_IN_HOURS);
        Response v2ChannelsTimelines = channelsTimelinesSteps.getV2ChannelsTimelinesFullSync(syncStop, timeRange);
        v2ChannelsTimelines.then().statusCode(SC_OK);

        channelsTimelinesSteps.waitUntilV2ChannelsTimelinesUpdatedAfter(syncStop, awaitAtMostMin, pollIntervalSeconds);

        String syncStart = syncStop;
        syncStop = channelsTimelinesSteps.getV2ChannelsMostRecentlyUpdatedTimelineTimestamp();
        String prevStop = timeRange.get(STOP);
        timeRange = getStartAndStopParams(TIMELINE_RANGE_IN_HOURS);
        v2ChannelsTimelines = channelsTimelinesSteps.getV2ChannelsTimelinesDeltaUpdate(syncStart, syncStop, timeRange, prevStop);

        v2ChannelsTimelines.then().statusCode(SC_OK);
        v2ChannelsTimelinesValidation.assertThatFirstLevelChannelsTimelineAttributesPresentNotEmptyAndHaveExpectedFormat(
                channelsTimelinesSteps.getFirstTimeline(v2ChannelsTimelines, REPL_TYPE_UPSERT));
    }
}
