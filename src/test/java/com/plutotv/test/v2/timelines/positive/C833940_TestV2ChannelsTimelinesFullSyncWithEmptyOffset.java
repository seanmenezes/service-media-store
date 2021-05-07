/*
 * (c) 2021 Pluto Inc.
 */

package com.plutotv.test.v2.timelines.positive;

import com.plutotv.common.helper.validation.V2ChannelsTimelinesValidation;
import com.plutotv.common.layer.step.v2.channels.timelines.ChannelsTimelinesSteps;
import com.plutotv.common.model.bussines.v2.channels.Timeline;
import com.plutotv.test.BaseTest;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

import static com.plutotv.common.Constants.REPL_TYPE_UPSERT;
import static com.plutotv.common.helper.RequestParameterHelper.getStartAndStopParams;
import static java.lang.Integer.parseInt;
import static org.apache.commons.lang.StringUtils.EMPTY;
import static org.apache.http.HttpStatus.SC_OK;

public class C833940_TestV2ChannelsTimelinesFullSyncWithEmptyOffset extends BaseTest {
    private final ChannelsTimelinesSteps channelsTimelinesSteps = new ChannelsTimelinesSteps();
    private final V2ChannelsTimelinesValidation v2ChannelsTimelinesValidation = new V2ChannelsTimelinesValidation();

    @Test(groups = {"full"})
    public void testV2ChannelsTimelinesFullSyncWithEmptyOffset() {
        String offset = "0";
        String syncStop = channelsTimelinesSteps.getV2ChannelsMostRecentlyUpdatedTimelineTimestamp();
        Map<String, String> timeRange = getStartAndStopParams(TIMELINE_RANGE_IN_HOURS);

        Response v2ChannelsTimelinesFullSync = channelsTimelinesSteps.getV2ChannelsTimelinesFullSync(syncStop, timeRange);

        v2ChannelsTimelinesFullSync.then().statusCode(SC_OK);
        List<Timeline> firstTimelinesFullSync = channelsTimelinesSteps.getFirstNTimelines(
                parseInt(offset) + 1, v2ChannelsTimelinesFullSync);
        Timeline firstTimelineAfterOffsetExpected = firstTimelinesFullSync.get(parseInt(offset));

        Response v2ChannelsTimelinesFullSyncWithOffset =
                channelsTimelinesSteps.getV2ChannelsTimelinesFullSyncWithOffset(EMPTY, syncStop, timeRange);

        v2ChannelsTimelinesFullSyncWithOffset.then().statusCode(SC_OK);
        Timeline firstTimelineAfterOffsetActual =
                channelsTimelinesSteps.getFirstTimeline(v2ChannelsTimelinesFullSyncWithOffset, REPL_TYPE_UPSERT);
        v2ChannelsTimelinesValidation.assertThatTimelineOffsetAsExpected(
                firstTimelineAfterOffsetExpected, firstTimelineAfterOffsetActual);
    }
}
