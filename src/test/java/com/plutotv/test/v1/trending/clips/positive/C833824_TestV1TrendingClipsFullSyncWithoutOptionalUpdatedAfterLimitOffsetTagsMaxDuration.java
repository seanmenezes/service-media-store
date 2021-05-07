/*
 * (c) 2021 Pluto Inc.
 */

package com.plutotv.test.v1.trending.clips.positive;

import com.plutotv.common.helper.validation.V1TrendingClipsValidation;
import com.plutotv.common.layer.step.v1.trending.clips.TrendingClipsSteps;
import com.plutotv.test.BaseTest;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static com.plutotv.common.Constants.REPL_TYPE_UPSERT;
import static org.apache.http.HttpStatus.SC_OK;

public class C833824_TestV1TrendingClipsFullSyncWithoutOptionalUpdatedAfterLimitOffsetTagsMaxDuration extends BaseTest {
    private final TrendingClipsSteps trendingClipsSteps = new TrendingClipsSteps();
    private final V1TrendingClipsValidation v1TrendingClipsValidation = new V1TrendingClipsValidation();

    @Test(groups = {"full"})
    public void testV1TrendingClipsFullSyncWithoutOptionalUpdatedAfterLimitOffsetTagsMaxDuration() {
        Response v1TrendingClipsFullSync = trendingClipsSteps.getV1TrendingClipsFullSync(
                trendingClipsSteps.getV1TrendingClipsMostRecentlyUpdatedRatingTimestamp());

        v1TrendingClipsFullSync.then().statusCode(SC_OK);
        v1TrendingClipsValidation.assertThatTrendingClipsAttributesNotNullEmptyAndHaveExpectedFormat(
                trendingClipsSteps.getFirstTrendingClip(v1TrendingClipsFullSync, REPL_TYPE_UPSERT));
    }
}
