/*
 * (c) 2021 Pluto Inc.
 */

package com.plutotv.test.v1.trending.clips.positive;

import com.plutotv.common.layer.step.v1.trending.clips.TrendingClipsSteps;
import com.plutotv.common.model.bussines.v1.trending.clips.TrendingClip;
import com.plutotv.test.BaseTest;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.List;

import static com.plutotv.common.Constants.REPL_TYPE_UPSERT;
import static java.lang.Integer.parseInt;
import static org.apache.http.HttpStatus.SC_OK;

public class C833821_TestV1TrendingClipsFullSyncWithOptionalOffset extends BaseTest {
    private final TrendingClipsSteps trendingClipsSteps = new TrendingClipsSteps();

    @Test(groups = {"full"})
    public void testV1TrendingClipsFullSyncWithOptionalOffset() {
        String offset = "1";
        String syncStop = trendingClipsSteps.getV1TrendingClipsMostRecentlyUpdatedRatingTimestamp();
        Response v1TrendingClipsFullSync = trendingClipsSteps.getV1TrendingClipsFullSync(syncStop);

        v1TrendingClipsFullSync.then().statusCode(SC_OK);

        List<TrendingClip> firstNTrendingClips = trendingClipsSteps.getFirstNTrendingClips(
                parseInt(offset) + 1, v1TrendingClipsFullSync);
        TrendingClip firstTrendingClipAfterOffsetExpected = firstNTrendingClips.get(parseInt(offset));

        Response v1TrendingClipsFullSyncWithOffset =
                trendingClipsSteps.getV1TrendingClipsFullSyncWithOffset(offset, syncStop);

        v1TrendingClipsFullSyncWithOffset.then().statusCode(SC_OK);

        TrendingClip firstTrendingClipAfterOffsetActual =
                trendingClipsSteps.getFirstTrendingClip(v1TrendingClipsFullSyncWithOffset, REPL_TYPE_UPSERT);
        trendingClipsSteps.assertThatTrendingClipsOffsetAsExpected(
                firstTrendingClipAfterOffsetExpected, firstTrendingClipAfterOffsetActual);
    }
}
