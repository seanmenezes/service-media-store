/*
 * (c) 2021 Pluto Inc.
 */

package com.plutotv.test.v2.clips.positive;

import com.plutotv.common.layer.step.v2.channels.clips.ChannelsClipsSteps;
import com.plutotv.test.BaseTest;
import io.qameta.allure.Step;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class C833893_TestHeadV2ChannelsClips extends BaseTest {
    private final ChannelsClipsSteps channelsClipsSteps = new ChannelsClipsSteps();

    @Test(groups = {"full"})
    public void testHeadV2ChannelsClips() {
        String lastModifiedHeader = channelsClipsSteps.getV2ChannelsMostRecentlyUpdatedClipTimestamp();

        assertThatLastModifiedHeaderPresent(lastModifiedHeader);
    }

    @Step
    private void assertThatLastModifiedHeaderPresent(String lastModifiedHeader) {
        assertThat(lastModifiedHeader)
                .as("Last-Modified header for channels clips should be present and not empty but it is %s",
                        lastModifiedHeader)
                .isNotEmpty();
    }
}
