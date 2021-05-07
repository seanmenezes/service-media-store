/*
 * (c) 2021 Pluto Inc.
 */

package com.plutotv.test.v2.clips.positive;

import com.plutotv.common.helper.validation.V2ChannelsClipsValidation;
import com.plutotv.common.layer.step.v2.channels.clips.ChannelsClipsSteps;
import com.plutotv.test.BaseTest;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.Map;

import static com.plutotv.common.Constants.REPL_TYPE_UPSERT;
import static com.plutotv.common.helper.RequestParameterHelper.STOP;
import static com.plutotv.common.helper.RequestParameterHelper.getStartAndStopParams;
import static org.apache.http.HttpStatus.SC_OK;

public class C833894_TestV2ChannelsClipsDeltaUpdateFlow extends BaseTest {
    private final ChannelsClipsSteps channelsClipsSteps = new ChannelsClipsSteps();
    private final V2ChannelsClipsValidation v2ChannelsClipsValidation = new V2ChannelsClipsValidation();

    //updates emulation tool should be run. In SSM lambda-mock-updates/enabled set to true
    @Test(groups = {"v2channels_delta_update"})
    public void testV2ChannelsClipsDeltaUpdateFlow() {
        int awaitAtMostMin = 10;
        int pollIntervalSeconds = 5;

        String syncStop = channelsClipsSteps.getV2ChannelsMostRecentlyUpdatedClipTimestamp();
        Map<String, String> timeRange = getStartAndStopParams(TIMELINE_RANGE_IN_HOURS);
        Response v2ChannelsClips = channelsClipsSteps.getV2ChannelsClipsFullSync(syncStop, timeRange);

        v2ChannelsClips.then().statusCode(SC_OK);

        channelsClipsSteps.waitUntilV2ChannelsClipsUpdatedAfter(syncStop, awaitAtMostMin, pollIntervalSeconds);

        String syncStart = syncStop;
        syncStop = channelsClipsSteps.getV2ChannelsMostRecentlyUpdatedClipTimestamp();
        String prevStop = timeRange.get(STOP);
        timeRange = getStartAndStopParams(TIMELINE_RANGE_IN_HOURS);
        v2ChannelsClips = channelsClipsSteps.getV2ChannelsClipsDeltaUpdate(syncStart, syncStop, timeRange, prevStop);

        v2ChannelsClips.then().statusCode(SC_OK);
        v2ChannelsClipsValidation.assertThatClipAttributesNotNullEmptyAndHaveExpectedFormat(
                channelsClipsSteps.getFirstClip(v2ChannelsClips, REPL_TYPE_UPSERT));
    }
}
