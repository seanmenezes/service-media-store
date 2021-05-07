/*
 * (c) 2021 Pluto Inc.
 */

package com.plutotv.test.v1.vod.episodes.positive;

import com.plutotv.common.helper.validation.V1VodEpisodesValidation;
import com.plutotv.common.layer.step.v1.vod.VodEpisodesSteps;
import com.plutotv.test.BaseTest;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static org.apache.http.HttpStatus.SC_OK;

public class C833879_TestV1VodEpisodesFullSyncWithOptionalLimit extends BaseTest {
    private final VodEpisodesSteps vodEpisodesSteps = new VodEpisodesSteps();
    private final V1VodEpisodesValidation v1VodEpisodesValidation = new V1VodEpisodesValidation();

    @Test(groups = {"full"})
    public void testV1VodEpisodesFullSyncWithOptionalLimit() {
        String vodEpisodesCountExpected = "1";
        Response v1VodEpisodesFullSyncWithLimit = vodEpisodesSteps.getV1VodEpisodesFullSyncWithLimit(
                vodEpisodesCountExpected,
                vodEpisodesSteps.getV1VodEpisodesMostRecentlyUpdatedEpisodeTimestamp());

        v1VodEpisodesFullSyncWithLimit.then().statusCode(SC_OK);
        long vodEpisodesCountActual = vodEpisodesSteps.getVodEpisodesCount(v1VodEpisodesFullSyncWithLimit);
        v1VodEpisodesValidation.assertThatVodEpisodesLimitAsExpected(vodEpisodesCountExpected, vodEpisodesCountActual);
    }
}
