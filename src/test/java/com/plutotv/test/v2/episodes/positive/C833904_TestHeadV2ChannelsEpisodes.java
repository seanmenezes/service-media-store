/*
 * (c) 2021 Pluto Inc.
 */

package com.plutotv.test.v2.episodes.positive;

import com.plutotv.common.layer.step.v2.channels.episodes.ChannelsEpisodesSteps;
import com.plutotv.test.BaseTest;
import io.qameta.allure.Step;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class C833904_TestHeadV2ChannelsEpisodes extends BaseTest {
    private final ChannelsEpisodesSteps channelsEpisodesSteps = new ChannelsEpisodesSteps();

    @Test(groups = {"full"})
    public void testHeadV2ChannelsEpisodes() {
        String lastModifiedHeader = channelsEpisodesSteps.getV2ChannelsMostRecentlyUpdatedEpisodeTimestamp();

        assertThatLastModifiedHeaderPresent(lastModifiedHeader);
    }

    @Step
    private void assertThatLastModifiedHeaderPresent(String lastModifiedHeader) {
        assertThat(lastModifiedHeader)
                .as("Last-Modified header for channels episodes should be present and not empty but it is %s",
                        lastModifiedHeader)
                .isNotEmpty();
    }
}
