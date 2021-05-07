/*
 * (c) 2021 Pluto Inc.
 */

package com.plutotv.test.v2.series.positive;

import com.plutotv.common.helper.validation.V2ChannelsSeriesValidation;
import com.plutotv.common.layer.step.v2.channels.series.ChannelsSeriesSteps;
import com.plutotv.common.model.bussines.v2.channels.Serie;
import com.plutotv.test.BaseTest;
import io.qameta.allure.Description;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;

import static com.plutotv.common.helper.RequestParameterHelper.START;
import static com.plutotv.common.helper.RequestParameterHelper.STOP;
import static java.time.Instant.now;
import static java.time.Instant.parse;
import static java.time.temporal.ChronoUnit.HOURS;
import static org.apache.http.HttpStatus.SC_OK;

public class C833922_TestV2ChannelsSeriesUpdatedBeforeSyncWindowWithPrevStop extends BaseTest {
    private final ChannelsSeriesSteps channelsSeriesSteps = new ChannelsSeriesSteps();
    private final V2ChannelsSeriesValidation v2ChannelsSeriesValidation = new V2ChannelsSeriesValidation();

    @Description("Test selection of timelines that fulfill next condition: tl.start <= stop AND tl.start >= previousStop AND tl.updated_at <= updatedAfter")
    @Test(groups = {"full"})
    public void testV2ChannelsSeriesUpdatedBeforeSyncWindowWithPrevStop() {
        HashMap<String, String> timeRange = new HashMap<>();
        Instant now = now().truncatedTo(HOURS);
        timeRange.put(START, now.minus(1, HOURS).toString());
        timeRange.put(STOP, now.plus(24, HOURS).toString());

        Instant curStop = parse(timeRange.get(STOP));
        String prevStop = curStop.minus(1, HOURS).toString();

        String syncStopSeries = channelsSeriesSteps.getV2ChannelsMostRecentlyUpdatedSeriesTimestamp();
        String syncStart = syncStopSeries;
        Response v2ChannelsSeriesDeltaUpdate = channelsSeriesSteps.getV2ChannelsSeriesDeltaUpdate(
                syncStart,
                syncStopSeries,
                timeRange,
                prevStop);
        v2ChannelsSeriesDeltaUpdate.then().statusCode(SC_OK);

        List<Serie> seriesWithUpsertReplicationType =
                channelsSeriesSteps.getSeriesWithUpsertReplicationType(v2ChannelsSeriesDeltaUpdate);

        List<Serie> seriesThatShouldNotBeReturned =
                channelsSeriesSteps.getSeriesThatShouldNotBeReturnedForDeltaUpdateWithPrevStop(syncStart, seriesWithUpsertReplicationType);

        v2ChannelsSeriesValidation.assertThatThereAreNoSeriesThatNotFulfillWithSyncFilter(seriesThatShouldNotBeReturned);
    }
}
