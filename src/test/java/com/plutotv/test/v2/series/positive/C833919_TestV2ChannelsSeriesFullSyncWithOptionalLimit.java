/*
 * (c) 2021 Pluto Inc.
 */

package com.plutotv.test.v2.series.positive;

import com.plutotv.common.layer.step.v2.channels.series.ChannelsSeriesSteps;
import com.plutotv.test.BaseTest;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static com.plutotv.common.helper.RequestParameterHelper.getStartAndStopParams;
import static java.lang.Long.parseLong;
import static org.apache.http.HttpStatus.SC_OK;
import static org.assertj.core.api.Assertions.assertThat;

public class C833919_TestV2ChannelsSeriesFullSyncWithOptionalLimit extends BaseTest {
    private final ChannelsSeriesSteps channelsSeriesSteps = new ChannelsSeriesSteps();

    @Test(groups = {"full"})
    public void testV2ChannelsSeriesFullSyncWithOptionalLimit() {
        String seriesCountExpected = "1";

        Response v2ChannelsSeriesFullSyncWithLimit = channelsSeriesSteps.getV2ChannelsSeriesFullSyncWithLimit(
                seriesCountExpected,
                channelsSeriesSteps.getV2ChannelsMostRecentlyUpdatedSeriesTimestamp(),
                getStartAndStopParams(TIMELINE_RANGE_IN_HOURS));

        v2ChannelsSeriesFullSyncWithLimit.then().statusCode(SC_OK);
        long seriesCountActual = channelsSeriesSteps.getSeriesCount(v2ChannelsSeriesFullSyncWithLimit);
        assertThatSeriesCountAsExpected(seriesCountExpected, seriesCountActual);
    }

    @Step
    private void assertThatSeriesCountAsExpected(String seriesCountExpected, long seriesCountActual) {
        assertThat(seriesCountActual)
                .as("%s series expected, but actually %s in response",
                        seriesCountExpected, seriesCountActual)
                .isEqualTo(parseLong(seriesCountExpected));
    }
}
