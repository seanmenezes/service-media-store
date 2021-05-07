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
import static org.apache.http.HttpStatus.SC_OK;

public class C833882_TestV1VodEpisodesFullSyncWithoutOptionalUpdatedAfterLimitOffset extends BaseTest {
    private final VodEpisodesSteps vodEpisodesSteps = new VodEpisodesSteps();
    private final V1VodEpisodesValidation v1VodEpisodesValidation = new V1VodEpisodesValidation();

    @Test(groups = {"full"})
    public void testV1VodEpisodesFullSyncWithoutOptionalUpdatedAfterLimitOffset() {
        String syncStop = vodEpisodesSteps.getV1VodEpisodesMostRecentlyUpdatedEpisodeTimestamp();
        Response v1VodEpisodesFullSync = vodEpisodesSteps.getV1VodEpisodesFullSync(syncStop);

        v1VodEpisodesFullSync.then().statusCode(SC_OK);

        List<VodEpisode> vodEpisodesWithUpsertReplicationType =
                vodEpisodesSteps.getVodEpisodesWithUpsertReplicationType(v1VodEpisodesFullSync);

        List<VodEpisode> vodEpisodesThatShouldNotBeReturned =
                vodEpisodesSteps.getVodEpisodesThatShouldNotBeReturnedForFullSync(
                        syncStop, vodEpisodesWithUpsertReplicationType);

        v1VodEpisodesValidation.assertThatThereAreNoVodEpisodesThatNotFulfillWithSyncFilter(
                vodEpisodesThatShouldNotBeReturned);
        v1VodEpisodesValidation.assertThatVodEpisodesAttributesNotNullEmptyAndHaveExpectedFormat(
                vodEpisodesSteps.getFirstVodEpisode(v1VodEpisodesFullSync, REPL_TYPE_UPSERT));
    }
}
