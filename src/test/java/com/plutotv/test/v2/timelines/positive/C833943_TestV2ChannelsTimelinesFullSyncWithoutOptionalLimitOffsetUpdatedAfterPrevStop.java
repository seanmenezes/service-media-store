/*
 * (c) 2021 Pluto Inc.
 */

package com.plutotv.test.v2.timelines.positive;

import com.plutotv.common.helper.validation.V2ChannelsTimelinesValidation;
import com.plutotv.common.layer.step.v2.channels.timelines.ChannelsTimelinesSteps;
import com.plutotv.test.BaseTest;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static com.plutotv.common.Constants.REPL_TYPE_UPSERT;
import static com.plutotv.common.helper.RequestParameterHelper.getStartAndStopParams;
import static org.apache.http.HttpStatus.SC_OK;

public class C833943_TestV2ChannelsTimelinesFullSyncWithoutOptionalLimitOffsetUpdatedAfterPrevStop extends BaseTest {
    private final ChannelsTimelinesSteps channelsTimelinesSteps = new ChannelsTimelinesSteps();
    private final V2ChannelsTimelinesValidation v2ChannelsTimelinesValidation = new V2ChannelsTimelinesValidation();

    @Test(groups = {"full"})
    public void testV2ChannelsTimelinesFullSyncWithoutOptionalLimitOffsetUpdatedAfterPrevStop() {
        Response v2ChannelsTimelines = channelsTimelinesSteps.getV2ChannelsTimelinesFullSync(
                channelsTimelinesSteps.getV2ChannelsMostRecentlyUpdatedTimelineTimestamp(),
                getStartAndStopParams(TIMELINE_RANGE_IN_HOURS));

        v2ChannelsTimelines.then().statusCode(SC_OK);
        v2ChannelsTimelinesValidation.assertThatFirstLevelChannelsTimelineAttributesPresentNotEmptyAndHaveExpectedFormat(
                channelsTimelinesSteps.getFirstTimeline(v2ChannelsTimelines, REPL_TYPE_UPSERT));
    }
}
