/*
 * (c) 2021 Pluto Inc.
 */

package com.plutotv.test.v2.series.positive;

import com.plutotv.common.layer.step.v2.channels.series.ChannelsSeriesSteps;
import com.plutotv.test.BaseTest;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.Map;

import static com.plutotv.common.Constants.REPL_TYPE_UPSERT;
import static com.plutotv.common.helper.RequestParameterHelper.STOP;
import static com.plutotv.common.helper.RequestParameterHelper.getStartAndStopParams;
import static com.plutotv.common.helper.validation.V2ChannelsSeriesValidation.assertThatSeriesAttributesNotNullEmptyAndHaveExpectedFormat;
import static org.apache.http.HttpStatus.SC_OK;

public class C833915_TestV2ChannelsSeriesDeltaUpdateFlow extends BaseTest {
    private final ChannelsSeriesSteps channelsSeriesSteps = new ChannelsSeriesSteps();

    //updates emulation tool should be run. In SSM lambda-mock-updates/enabled set to true
    @Test(groups = {"v2channels_delta_update"})
    public void testV2ChannelsSeriesDeltaUpdateFlow() {
        int awaitAtMostMin = 10;
        int pollIntervalSeconds = 5;

        String syncStop = channelsSeriesSteps.getV2ChannelsMostRecentlyUpdatedSeriesTimestamp();
        Map<String, String> timeRange = getStartAndStopParams(TIMELINE_RANGE_IN_HOURS);
        Response v2ChannelsSeries = channelsSeriesSteps.getV2ChannelsSeriesFullSync(syncStop, timeRange);

        v2ChannelsSeries.then().statusCode(SC_OK);

        channelsSeriesSteps.waitUntilV2ChannelsSeriesUpdatedAfter(syncStop, awaitAtMostMin, pollIntervalSeconds);

        String syncStart = syncStop;
        syncStop = channelsSeriesSteps.getV2ChannelsMostRecentlyUpdatedSeriesTimestamp();
        String prevStop = timeRange.get(STOP);
        timeRange = getStartAndStopParams(TIMELINE_RANGE_IN_HOURS);
        v2ChannelsSeries = channelsSeriesSteps.getV2ChannelsSeriesDeltaUpdate(syncStart, syncStop, timeRange, prevStop);

        v2ChannelsSeries.then().statusCode(SC_OK);
        assertThatSeriesAttributesNotNullEmptyAndHaveExpectedFormat(
                channelsSeriesSteps.getFirstSeries(v2ChannelsSeries, REPL_TYPE_UPSERT));
    }
}
