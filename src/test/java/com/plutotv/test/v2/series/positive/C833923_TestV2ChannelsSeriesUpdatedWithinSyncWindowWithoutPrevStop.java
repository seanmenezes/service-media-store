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
import static java.time.Instant.parse;
import static java.time.temporal.ChronoUnit.HOURS;
import static org.apache.http.HttpStatus.SC_OK;

public class C833923_TestV2ChannelsSeriesUpdatedWithinSyncWindowWithoutPrevStop extends BaseTest {
    private final ChannelsSeriesSteps channelsSeriesSteps = new ChannelsSeriesSteps();
    private final V2ChannelsSeriesValidation v2ChannelsSeriesValidation = new V2ChannelsSeriesValidation();

    @Description("Test selection of timelines that fulfill next condition: tl.start <= stop AND tl.stop >= start " +
            "AND tl.updated_at > updatedAfter AND tl.updated_at <= updatedBefore")
    @Test(groups = {"full"})
    public void testV2ChannelsSeriesUpdatedWithinSyncWindowWithoutPrevStop() {
        HashMap<String, String> timeRange = new HashMap<>();
        Instant now = Instant.now().truncatedTo(HOURS);
        timeRange.put(START, now.minus(13, HOURS).toString());
        timeRange.put(STOP, now.plus(13, HOURS).toString());

        String syncStop = channelsSeriesSteps.getV2ChannelsMostRecentlyUpdatedSeriesTimestamp();
        String syncStart = parse(syncStop).minus(13, HOURS).toString();
        Response v2ChannelsSeriesDeltaUpdate = channelsSeriesSteps.getV2ChannelsSeriesDeltaUpdate(
                syncStart, syncStop, timeRange);
        v2ChannelsSeriesDeltaUpdate.then().statusCode(SC_OK);

        List<Serie> seriesWithUpsertReplicationType =
                channelsSeriesSteps.getSeriesWithUpsertReplicationType(v2ChannelsSeriesDeltaUpdate);

        List<Serie> seriesThatShouldNotBeReturned =
                channelsSeriesSteps.getSeriesThatShouldNotBeReturnedForDeltaUpdateWithoutPrevStop(syncStop,
                        syncStart,
                        seriesWithUpsertReplicationType);

        v2ChannelsSeriesValidation.assertThatThereAreNoSeriesThatNotFulfillWithSyncFilter(seriesThatShouldNotBeReturned);
    }
}
