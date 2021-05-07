/*
 * (c) 2021 Pluto Inc.
 */

package com.plutotv.test.v1.trending.clips.positive;

import com.plutotv.common.helper.validation.V1TrendingClipsValidation;
import com.plutotv.common.layer.step.v1.trending.clips.TrendingClipsSteps;
import com.plutotv.test.BaseTest;
import org.testng.annotations.Test;

public class C833816_TestHeadV1TrendingClips extends BaseTest {
    private final TrendingClipsSteps trendingClipsSteps = new TrendingClipsSteps();
    private final V1TrendingClipsValidation v1TrendingClipsValidation = new V1TrendingClipsValidation();

    @Test(groups = {"full"})
    public void testHeadV1TrendingClips() {
        String lastModifiedHeader = trendingClipsSteps.getV1TrendingClipsMostRecentlyUpdatedRatingTimestamp();

        v1TrendingClipsValidation.assertThatLastModifiedHeaderPresent(lastModifiedHeader);
    }
}
