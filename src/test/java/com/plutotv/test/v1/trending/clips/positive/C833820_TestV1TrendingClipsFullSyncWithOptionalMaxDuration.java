/*
 * (c) 2021 Pluto Inc.
 */

package com.plutotv.test.v1.trending.clips.positive;

import com.plutotv.common.helper.validation.V1TrendingClipsValidation;
import com.plutotv.common.layer.step.v1.trending.clips.TrendingClipsSteps;
import com.plutotv.common.model.bussines.v1.trending.clips.TrendingClip;
import com.plutotv.test.BaseTest;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.List;

import static org.apache.http.HttpStatus.SC_OK;

public class C833820_TestV1TrendingClipsFullSyncWithOptionalMaxDuration extends BaseTest {
    private final TrendingClipsSteps trendingClipsSteps = new TrendingClipsSteps();
    private final V1TrendingClipsValidation v1TrendingClipsValidation = new V1TrendingClipsValidation();

    @Test(groups = {"full"})
    public void testV1TrendingClipsFullSyncWithOptionalMaxDuration() {
        Integer clipMaxDuration = 1800;
        Response v1TrendingClipsFullSyncWithMaxDuration = trendingClipsSteps.getV1TrendingClipsFullSyncWithMaxDuration(
                trendingClipsSteps.getV1TrendingClipsMostRecentlyUpdatedRatingTimestamp(),
                String.valueOf(clipMaxDuration));

        v1TrendingClipsFullSyncWithMaxDuration.then().statusCode(SC_OK);

        List<TrendingClip> trendingClipsWithUpsertReplicationType =
                trendingClipsSteps.getTrendingClipsWithUpsertReplicationType(v1TrendingClipsFullSyncWithMaxDuration);

        List<TrendingClip> trendingClipsWithNotExpectedDuration =
                trendingClipsSteps.getTrendingClipsThatHaveNotExpectedDuration(clipMaxDuration, trendingClipsWithUpsertReplicationType);

        v1TrendingClipsValidation.assertThatThereAreNoClipsWithNotExpectedDuration(
                clipMaxDuration, trendingClipsWithNotExpectedDuration);
    }
}
