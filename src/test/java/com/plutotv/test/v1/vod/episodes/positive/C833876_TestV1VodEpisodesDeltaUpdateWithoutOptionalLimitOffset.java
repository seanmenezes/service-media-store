/*
 * (c) 2021 Pluto Inc.
 */

package com.plutotv.test.v1.vod.episodes.positive;

import com.plutotv.common.helper.validation.V1VodEpisodesValidation;
import com.plutotv.common.layer.step.v1.vod.VodEpisodesSteps;
import com.plutotv.common.model.bussines.v1.vod.episode.VodEpisode;
import com.plutotv.test.BaseTest;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.List;

import static com.plutotv.common.Constants.REPL_TYPE_UPSERT;
import static java.time.Instant.parse;
import static java.time.temporal.ChronoUnit.HOURS;
import static org.apache.http.HttpStatus.SC_OK;

public class C833876_TestV1VodEpisodesDeltaUpdateWithoutOptionalLimitOffset extends BaseTest {
    private final VodEpisodesSteps vodEpisodesSteps = new VodEpisodesSteps();
    private final V1VodEpisodesValidation v1VodEpisodesValidation = new V1VodEpisodesValidation();

    @Test(groups = {"full"})
    public void testV1VodEpisodesDeltaUpdateWithoutOptionalLimitOffset() {
        String syncStop = vodEpisodesSteps.getV1VodEpisodesMostRecentlyUpdatedEpisodeTimestamp();
        String syncStart = parse(syncStop).minus(24, HOURS).toString();
        Response v1VodEpisodesDeltaUpdate = vodEpisodesSteps.getV1VodEpisodesDeltaUpdate(syncStart, syncStop);
        v1VodEpisodesDeltaUpdate.then().statusCode(SC_OK);

        List<VodEpisode> vodEpisodes = vodEpisodesSteps.getVodEpisodesWithUpsertReplicationType(v1VodEpisodesDeltaUpdate);

        List<VodEpisode> vodEpisodesThatShouldNotBeReturned =
                vodEpisodesSteps.getVodEpisodesThatShouldNotBeReturnedForDeltaUpdate(syncStop, syncStart, vodEpisodes);

        v1VodEpisodesValidation.assertThatThereAreNoVodEpisodesThatNotFulfillWithSyncFilter(
                vodEpisodesThatShouldNotBeReturned);
        v1VodEpisodesValidation.assertThatVodEpisodesAttributesNotNullEmptyAndHaveExpectedFormat(
                vodEpisodesSteps.getFirstVodEpisode(v1VodEpisodesDeltaUpdate, REPL_TYPE_UPSERT));
    }
}
