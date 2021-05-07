/*
 * (c) 2021 Pluto Inc.
 */

package com.plutotv.test.v2.timelines.positive;

import com.plutotv.common.layer.step.v2.channels.timelines.ChannelsTimelinesSteps;
import com.plutotv.test.BaseTest;
import io.qameta.allure.Step;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class C833935_TestHeadV2ChannelsTimelines extends BaseTest {
    private final ChannelsTimelinesSteps channelsTimelinesSteps = new ChannelsTimelinesSteps();

    @Test(groups = {"full"})
    public void testHeadV2ChannelsTimelines() {
        String lastModifiedHeader = channelsTimelinesSteps.getV2ChannelsMostRecentlyUpdatedTimelineTimestamp();

        assertThatLastModifiedHeaderPresent(lastModifiedHeader);
    }

    @Step
    private void assertThatLastModifiedHeaderPresent(String lastModifiedHeader) {
        assertThat(lastModifiedHeader)
                .as("Last-Modified header for channels timeline should be present and not empty but it is %s",
                        lastModifiedHeader)
                .isNotEmpty();
    }
}
