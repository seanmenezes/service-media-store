/*
 * (c) 2021 Pluto Inc.
 */

package com.plutotv.test.v2.clips.positive;

import com.plutotv.common.layer.step.v2.channels.clips.ChannelsClipsSteps;
import com.plutotv.common.model.bussines.v2.channels.Clip;
import com.plutotv.test.BaseTest;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

import static com.plutotv.common.Constants.REPL_TYPE_UPSERT;
import static com.plutotv.common.helper.RequestParameterHelper.getStartAndStopParams;
import static java.lang.Integer.parseInt;
import static org.apache.http.HttpStatus.SC_OK;
import static org.assertj.core.api.Assertions.assertThat;

public class C833899_TestV2ChannelsClipsFullSyncWithOptionalOffset extends BaseTest {
    private final ChannelsClipsSteps channelsClipsSteps = new ChannelsClipsSteps();

    @Test(groups = {"full"})
    public void testV2ChannelsClipsFullSyncWithOptionalOffset() {
        String offset = "1";

        Map<String, String> timeRange = getStartAndStopParams(TIMELINE_RANGE_IN_HOURS);
        String syncStop = channelsClipsSteps.getV2ChannelsMostRecentlyUpdatedClipTimestamp();

        Response v2ChannelsClipsFullSync = channelsClipsSteps.getV2ChannelsClipsFullSync(syncStop, timeRange);
        v2ChannelsClipsFullSync.then().statusCode(SC_OK);

        List<Clip> firstClipsFullSync = channelsClipsSteps.getFirstNClips(parseInt(offset) + 1,
                v2ChannelsClipsFullSync);
        Clip firstClipAfterOffsetExpected = firstClipsFullSync.get(parseInt(offset));

        Response v2ChannelsClipsFullSyncWithOffset =
                channelsClipsSteps.getV2ChannelsClipsFullSyncWithOffset(offset, syncStop, timeRange);

        v2ChannelsClipsFullSyncWithOffset.then().statusCode(SC_OK);
        Clip firstClipAfterOffsetActual = channelsClipsSteps.getFirstClip(
                v2ChannelsClipsFullSyncWithOffset, REPL_TYPE_UPSERT);
        assertThatClipOffsetAsExpected(firstClipAfterOffsetExpected, firstClipAfterOffsetActual);
    }

    @Step
    private void assertThatClipOffsetAsExpected(Clip firstClipAfterOffsetExpected, Clip firstClipAfterOffsetActual) {
        assertThat(firstClipAfterOffsetExpected.getId())
                .as("First expected clip after offset is %s but actual is %s",
                        firstClipAfterOffsetExpected, firstClipAfterOffsetActual)
                .isEqualTo(firstClipAfterOffsetActual.getId());
    }
}
