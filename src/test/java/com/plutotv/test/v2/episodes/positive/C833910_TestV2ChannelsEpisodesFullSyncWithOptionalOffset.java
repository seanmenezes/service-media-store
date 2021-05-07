/*
 * (c) 2021 Pluto Inc.
 */

package com.plutotv.test.v2.episodes.positive;

import com.plutotv.common.layer.step.v2.channels.episodes.ChannelsEpisodesSteps;
import com.plutotv.common.model.bussines.v2.channels.Episode;
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

public class C833910_TestV2ChannelsEpisodesFullSyncWithOptionalOffset extends BaseTest {
    private final ChannelsEpisodesSteps channelsEpisodesSteps = new ChannelsEpisodesSteps();

    @Test(groups = {"full"})
    public void testV2ChannelsEpisodesFullSyncWithOptionalOffset() {
        String offset = "1";

        Map<String, String> timeRange = getStartAndStopParams(TIMELINE_RANGE_IN_HOURS);
        String syncStop = channelsEpisodesSteps.getV2ChannelsMostRecentlyUpdatedEpisodeTimestamp();

        Response v2ChannelsEpisodesFullSync = channelsEpisodesSteps.getV2ChannelsEpisodesFullSync(syncStop, timeRange);
        v2ChannelsEpisodesFullSync.then().statusCode(SC_OK);

        List<Episode> firstEpisodesFullSync = channelsEpisodesSteps.getFirstNEpisodes(
                parseInt(offset) + 1,
                v2ChannelsEpisodesFullSync);
        Episode firstEpisodeAfterOffsetExpected = firstEpisodesFullSync.get(parseInt(offset));

        Response v2ChannelsEpisodesFullSyncWithOffset =
                channelsEpisodesSteps.getV2ChannelsEpisodesFullSyncWithOffset(offset, syncStop, timeRange);

        v2ChannelsEpisodesFullSyncWithOffset.then().statusCode(SC_OK);
        Episode firstEpisodeAfterOffsetActual = channelsEpisodesSteps.getFirstEpisode(
                v2ChannelsEpisodesFullSyncWithOffset, REPL_TYPE_UPSERT);
        assertThatEpisodesOffsetAsEcpected(firstEpisodeAfterOffsetExpected, firstEpisodeAfterOffsetActual);
    }

    @Step
    private void assertThatEpisodesOffsetAsEcpected(Episode firstEpisodeAfterOffsetExpected, Episode firstEpisodeAfterOffsetActual) {
        assertThat(firstEpisodeAfterOffsetExpected.getId())
                .as("First expected episode after offset is %s but actual is %s",
                        firstEpisodeAfterOffsetExpected, firstEpisodeAfterOffsetActual)
                .isEqualTo(firstEpisodeAfterOffsetActual.getId());
    }
}
