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
import static java.lang.Integer.parseInt;
import static org.apache.http.HttpStatus.SC_OK;

public class C833880_TestV1VodEpisodesFullSyncWithOptionalOffset extends BaseTest {
    private final VodEpisodesSteps vodEpisodesSteps = new VodEpisodesSteps();
    private final V1VodEpisodesValidation v1VodEpisodesValidation = new V1VodEpisodesValidation();

    @Test(groups = {"full"})
    public void testV1VodEpisodesFullSyncWithOptionalOffset() {
        String offset = "1";
        String syncStop = vodEpisodesSteps.getV1VodEpisodesMostRecentlyUpdatedEpisodeTimestamp();
        Response v1VodEpisodesFullSync = vodEpisodesSteps.getV1VodEpisodesFullSync(syncStop);

        v1VodEpisodesFullSync.then().statusCode(SC_OK);

        List<VodEpisode> firstNVodEpisodes =
                vodEpisodesSteps.getFirstNVodEpisodes(parseInt(offset) + 1, v1VodEpisodesFullSync);
        VodEpisode firstVodEpisodeAfterOffsetExpected = firstNVodEpisodes.get(parseInt(offset));

        Response v1VodEpisodesFullSyncWithOffset =
                vodEpisodesSteps.getV1VodEpisodesFullSyncWithOffset(offset, syncStop);

        v1VodEpisodesFullSyncWithOffset.then().statusCode(SC_OK);

        VodEpisode firstVodEpisodeAfterOffsetActual =
                vodEpisodesSteps.getFirstVodEpisode(v1VodEpisodesFullSyncWithOffset, REPL_TYPE_UPSERT);
        v1VodEpisodesValidation.assertThatVodEpisodesOffsetAsExpected(
                firstVodEpisodeAfterOffsetExpected, firstVodEpisodeAfterOffsetActual);
    }
}
