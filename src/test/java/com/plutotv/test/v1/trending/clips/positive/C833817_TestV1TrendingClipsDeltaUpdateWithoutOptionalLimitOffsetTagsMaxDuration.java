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

import static com.plutotv.common.Constants.REPL_TYPE_UPSERT;
import static java.time.Instant.parse;
import static java.time.temporal.ChronoUnit.HOURS;
import static org.apache.http.HttpStatus.SC_OK;

public class C833817_TestV1TrendingClipsDeltaUpdateWithoutOptionalLimitOffsetTagsMaxDuration extends BaseTest {
    private final TrendingClipsSteps trendingClipsSteps = new TrendingClipsSteps();
    private final V1TrendingClipsValidation v1TrendingClipsValidation = new V1TrendingClipsValidation();

    @Test(groups = {"full"})
    public void testV1TrendingClipsDeltaUpdateWithoutOptionalLimitOffsetTagsMaxDuration() {
        String syncStop = trendingClipsSteps.getV1TrendingClipsMostRecentlyUpdatedRatingTimestamp();
        String syncStart = parse(syncStop).minus(24, HOURS).toString();
        Response v1TrendingClipsDeltaUpdate = trendingClipsSteps.getV1TrendingClipsDeltaUpdate(syncStart, syncStop);

        v1TrendingClipsDeltaUpdate.then().statusCode(SC_OK);

        List<TrendingClip> trendingClipsWithUpsertReplicationType =
                trendingClipsSteps.getTrendingClipsWithUpsertReplicationType(v1TrendingClipsDeltaUpdate);

        List<TrendingClip> trendingClipsThatShouldNotBeReturned =
                trendingClipsSteps.getTrendingClipsThatShouldNotBeReturnedForDeltaUpdate(
                        syncStop,
                        syncStart,
                        trendingClipsWithUpsertReplicationType);

        v1TrendingClipsValidation.assertThatThereAreNoTrendingClipsThatNotFulfillWithSyncFilter(trendingClipsThatShouldNotBeReturned);

        v1TrendingClipsValidation.assertThatTrendingClipsAttributesNotNullEmptyAndHaveExpectedFormat(
                trendingClipsSteps.getFirstTrendingClip(v1TrendingClipsDeltaUpdate, REPL_TYPE_UPSERT));
    }
}
