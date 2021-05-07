/*
 * (c) 2021 Pluto Inc.
 */

package com.plutotv.test.v1.trending.clips.positive;

import com.plutotv.common.layer.step.v1.trending.clips.TrendingClipsSteps;
import com.plutotv.test.BaseTest;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static org.apache.http.HttpStatus.SC_OK;

public class C833819_TestV1TrendingClipsFullSyncWithOptionalLimit extends BaseTest {
    private final TrendingClipsSteps trendingClipsSteps = new TrendingClipsSteps();

    @Test(groups = {"full"})
    public void testV1TrendingClipsFullSyncWithOptionalLimit() {
        String trendingClipsCountExpected = "1";
        Response v1TrendingClipsFullSyncWithLimit = trendingClipsSteps.getV1TrendingClipsFullSyncWithLimit(
                trendingClipsCountExpected,
                trendingClipsSteps.getV1TrendingClipsMostRecentlyUpdatedRatingTimestamp());

        v1TrendingClipsFullSyncWithLimit.then().statusCode(SC_OK);
        long trendingClipsCountActual = trendingClipsSteps.getTrendingClipsCount(v1TrendingClipsFullSyncWithLimit);
        trendingClipsSteps.assertThatTrendingClipsLimitAsExpected(trendingClipsCountExpected, trendingClipsCountActual);
    }
}
