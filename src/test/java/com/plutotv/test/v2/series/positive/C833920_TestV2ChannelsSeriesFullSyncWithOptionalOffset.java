/*
 * (c) 2021 Pluto Inc.
 */

package com.plutotv.test.v2.series.positive;

import com.plutotv.common.layer.step.v2.channels.series.ChannelsSeriesSteps;
import com.plutotv.common.model.bussines.v2.channels.Serie;
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

public class C833920_TestV2ChannelsSeriesFullSyncWithOptionalOffset extends BaseTest {
    private final ChannelsSeriesSteps channelsSeriesSteps = new ChannelsSeriesSteps();

    @Test(groups = {"full"})
    public void testV2ChannelsSeriesFullSyncWithOptionalOffset() {
        String offset = "1";
        String syncStop = channelsSeriesSteps.getV2ChannelsMostRecentlyUpdatedSeriesTimestamp();
        Map<String, String> timeRange = getStartAndStopParams(TIMELINE_RANGE_IN_HOURS);

        Response v2ChannelsSeriesFullSync = channelsSeriesSteps.getV2ChannelsSeriesFullSync(syncStop, timeRange);

        v2ChannelsSeriesFullSync.then().statusCode(SC_OK);
        List<Serie> firstTimelinesFullSync = channelsSeriesSteps.getFirstNSeries(parseInt(offset) + 1,
                v2ChannelsSeriesFullSync);
        Serie firstSeriesAfterOffsetExpected = firstTimelinesFullSync.get(parseInt(offset));
        Response v2ChannelsSeriesFullSyncWithOffset = channelsSeriesSteps.getV2ChannelsSeriesFullSyncWithOffset(
                offset,
                syncStop,
                timeRange);

        v2ChannelsSeriesFullSyncWithOffset.then().statusCode(SC_OK);
        Serie firstSeriesAfterOffsetActual = channelsSeriesSteps.getFirstSeries(
                v2ChannelsSeriesFullSyncWithOffset, REPL_TYPE_UPSERT);
        assertThatSeriesOffsetAsExpected(firstSeriesAfterOffsetExpected, firstSeriesAfterOffsetActual);
    }

    @Step
    private void assertThatSeriesOffsetAsExpected(Serie firstSeriesAfterOffsetExpected, Serie firstSeriesAfterOffsetActual) {
        assertThat(firstSeriesAfterOffsetExpected.getId())
                .as("First expected series after offset is %s but actual is %s",
                        firstSeriesAfterOffsetExpected, firstSeriesAfterOffsetActual)
                .isEqualTo(firstSeriesAfterOffsetActual.getId());
    }
}
