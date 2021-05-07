/*
 * (c) 2021 Pluto Inc.
 */

package com.plutotv.test.v1.vod.episodes.positive;

import com.plutotv.common.helper.validation.V1VodEpisodesValidation;
import com.plutotv.common.layer.step.v1.vod.VodEpisodesSteps;
import com.plutotv.test.BaseTest;
import org.testng.annotations.Test;

public class C833875_TestHeadV1VodEpisodes extends BaseTest {
    private final VodEpisodesSteps vodEpisodesSteps = new VodEpisodesSteps();
    private final V1VodEpisodesValidation v1VodEpisodesValidation = new V1VodEpisodesValidation();

    @Test(groups = {"full"})
    public void testHeadV1VodEpisodes() {
        String lastModifiedHeader = vodEpisodesSteps.getV1VodEpisodesMostRecentlyUpdatedEpisodeTimestamp();

        v1VodEpisodesValidation.assertThatLastModifiedHeaderPresent(lastModifiedHeader);
    }
}
