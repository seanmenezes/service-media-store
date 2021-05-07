/*
 * (c) 2021 Pluto Inc.
 */

package com.plutotv.test.v2.series.positive;

import com.plutotv.common.layer.step.v2.channels.series.ChannelsSeriesSteps;
import com.plutotv.test.BaseTest;
import io.qameta.allure.Step;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class C833914_TestHeadV2ChannelsSeries extends BaseTest {
    private final ChannelsSeriesSteps channelsSeriesSteps = new ChannelsSeriesSteps();

    @Test(groups = {"full"})
    public void testHeadV2ChannelsSeries() {
        String lastModifiedHeader = channelsSeriesSteps.getV2ChannelsMostRecentlyUpdatedSeriesTimestamp();

        assertThatLastModifiedHeaderPresent(lastModifiedHeader);
    }

    @Step
    private void assertThatLastModifiedHeaderPresent(String lastModifiedHeader) {
        assertThat(lastModifiedHeader)
                .as("Last-Modified header for channels series should be present and not empty but it is %s",
                        lastModifiedHeader)
                .isNotEmpty();
    }
}
