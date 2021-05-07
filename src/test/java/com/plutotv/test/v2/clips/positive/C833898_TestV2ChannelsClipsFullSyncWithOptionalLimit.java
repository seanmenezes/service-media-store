/*
 * (c) 2021 Pluto Inc.
 */

package com.plutotv.test.v2.clips.positive;

import com.plutotv.common.layer.step.v2.channels.clips.ChannelsClipsSteps;
import com.plutotv.test.BaseTest;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static com.plutotv.common.helper.RequestParameterHelper.getStartAndStopParams;
import static java.lang.Long.parseLong;
import static org.apache.http.HttpStatus.SC_OK;
import static org.assertj.core.api.Assertions.assertThat;

public class C833898_TestV2ChannelsClipsFullSyncWithOptionalLimit extends BaseTest {
    private final ChannelsClipsSteps channelsClipsSteps = new ChannelsClipsSteps();

    @Test(groups = {"full"})
    public void testV2ChannelsClipsFullSyncWithOptionalLimit() {
        String clipsCountExpected = "1";

        Response v2ChannelsClipsFullSyncWithLimit = channelsClipsSteps.getV2ChannelsClipsFullSyncWithLimit(
                clipsCountExpected,
                channelsClipsSteps.getV2ChannelsMostRecentlyUpdatedClipTimestamp(),
                getStartAndStopParams(TIMELINE_RANGE_IN_HOURS));

        v2ChannelsClipsFullSyncWithLimit.then().statusCode(SC_OK);
        long clipsCountActual = channelsClipsSteps.getClipsCount(v2ChannelsClipsFullSyncWithLimit);
        assertThatClipsCountAsExpected(clipsCountExpected, clipsCountActual);
    }

    @Step
    private void assertThatClipsCountAsExpected(String clipsCountExpected, long clipsCountActual) {
        assertThat(clipsCountActual)
                .as("Only %s clips expected, but actually %s in response",
                        clipsCountExpected, clipsCountActual)
                .isEqualTo(parseLong(clipsCountExpected));
    }
}
