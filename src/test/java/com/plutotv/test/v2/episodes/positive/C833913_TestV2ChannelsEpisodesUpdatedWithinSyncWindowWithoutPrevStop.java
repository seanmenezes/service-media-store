/*
 * (c) 2021 Pluto Inc.
 */

package com.plutotv.test.v2.episodes.positive;

import com.plutotv.common.helper.validation.V2ChannelsEpisodesValidation;
import com.plutotv.common.layer.step.v2.channels.episodes.ChannelsEpisodesSteps;
import com.plutotv.common.model.bussines.v2.channels.Episode;
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

public class C833913_TestV2ChannelsEpisodesUpdatedWithinSyncWindowWithoutPrevStop extends BaseTest {
    private final ChannelsEpisodesSteps channelsEpisodesSteps = new ChannelsEpisodesSteps();
    private final V2ChannelsEpisodesValidation v2ChannelsEpisodesValidation = new V2ChannelsEpisodesValidation();

    @Description("Test selection of timelines that fulfill next condition: tl.start <= stop AND tl.stop >= start " +
            "AND tl.updated_at > updatedAfter AND tl.updated_at <= updatedBefore")
    @Test(groups = {"full"})
    public void testV2ChannelsEpisodesUpdatedWithinSyncWindowWithoutPrevStop() {
        HashMap<String, String> timeRange = new HashMap<>();
        Instant now = Instant.now().truncatedTo(HOURS);
        timeRange.put(START, now.minus(1, HOURS).toString());
        timeRange.put(STOP, now.plus(24, HOURS).toString());

        String syncStop = channelsEpisodesSteps.getV2ChannelsMostRecentlyUpdatedEpisodeTimestamp();
        String esyncStart = parse(syncStop).minus(1, HOURS).toString();
        Response v2ChannelsEpisodes = channelsEpisodesSteps.getV2ChannelsEpisodesDeltaUpdate(esyncStart, syncStop, timeRange);
        v2ChannelsEpisodes.then().statusCode(SC_OK);

        List<Episode> episodesWithUpsertReplicationType =
                channelsEpisodesSteps.getEpisodesWithUpsertReplicationType(v2ChannelsEpisodes);

        List<Episode> episodesThatShouldNotBeReturned =
                channelsEpisodesSteps.getEpisodesThatShouldNotBeReturnedForDeltaUpdateWithoutPrevStop(
                        syncStop,
                        esyncStart,
                        episodesWithUpsertReplicationType);

        v2ChannelsEpisodesValidation.assertThatThereAreNoEpisodesThatNotFulfillWithSyncFilter(episodesThatShouldNotBeReturned);
    }
}
