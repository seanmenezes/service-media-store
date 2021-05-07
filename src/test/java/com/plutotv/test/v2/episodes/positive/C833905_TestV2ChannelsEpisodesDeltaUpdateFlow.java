/*
 * (c) 2021 Pluto Inc.
 */

package com.plutotv.test.v2.episodes.positive;

import com.plutotv.common.helper.validation.V2ChannelsEpisodesValidation;
import com.plutotv.common.layer.step.v2.channels.episodes.ChannelsEpisodesSteps;
import com.plutotv.test.BaseTest;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.Map;

import static com.plutotv.common.Constants.REPL_TYPE_UPSERT;
import static com.plutotv.common.helper.RequestParameterHelper.STOP;
import static com.plutotv.common.helper.RequestParameterHelper.getStartAndStopParams;
import static org.apache.http.HttpStatus.SC_OK;

public class C833905_TestV2ChannelsEpisodesDeltaUpdateFlow extends BaseTest {
    private final ChannelsEpisodesSteps channelsEpisodesSteps = new ChannelsEpisodesSteps();
    private final V2ChannelsEpisodesValidation v2ChannelsEpisodesValidation = new V2ChannelsEpisodesValidation();

    //updates emulation tool should be run. In SSM lambda-mock-updates/enabled set to true
    @Test(groups = {"v2channels_delta_update"})
    public void testV2ChannelsEpisodesDeltaUpdateFlow() {
        int awaitAtMostMin = 10;
        int pollIntervalSeconds = 5;

        String syncStop = channelsEpisodesSteps.getV2ChannelsMostRecentlyUpdatedEpisodeTimestamp();
        Map<String, String> timeRange = getStartAndStopParams(TIMELINE_RANGE_IN_HOURS);
        Response v2ChannelsEpisodes = channelsEpisodesSteps.getV2ChannelsEpisodesFullSync(syncStop, timeRange);

        v2ChannelsEpisodes.then().statusCode(SC_OK);

        channelsEpisodesSteps.waitUntilV2ChannelsEpisodesUpdatedAfter(syncStop, awaitAtMostMin, pollIntervalSeconds);

        String syncStart = syncStop;
        syncStop = channelsEpisodesSteps.getV2ChannelsMostRecentlyUpdatedEpisodeTimestamp();
        String prevStop = timeRange.get(STOP);
        timeRange = getStartAndStopParams(TIMELINE_RANGE_IN_HOURS);
        v2ChannelsEpisodes = channelsEpisodesSteps.getV2ChannelsEpisodesDeltaUpdate(
                syncStart,
                syncStop,
                timeRange,
                prevStop);

        v2ChannelsEpisodes.then().statusCode(SC_OK);
        v2ChannelsEpisodesValidation.assertThatEpisodeAttributesNotNullEmptyHaveExpectedFormat(
                channelsEpisodesSteps.getFirstEpisode(v2ChannelsEpisodes, REPL_TYPE_UPSERT));
    }
}
