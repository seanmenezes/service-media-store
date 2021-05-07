/*
 * (c) 2021 Pluto Inc.
 */

package com.plutotv.test.v1.vod.series.positive;

import com.plutotv.common.helper.validation.V1VodSeriesValidation;
import com.plutotv.common.layer.step.v1.vod.VodSeriesSteps;
import com.plutotv.common.model.bussines.v1.vod.series.VodSerie;
import com.plutotv.test.BaseTest;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.List;

import static com.plutotv.common.Constants.REPL_TYPE_UPSERT;
import static java.time.Instant.parse;
import static java.time.temporal.ChronoUnit.HOURS;
import static org.apache.http.HttpStatus.SC_OK;

public class C833887_TestV1VodSeriesDeltaUpdateWithoutOptionalLimitOffset extends BaseTest {
    private final VodSeriesSteps vodSeriesSteps = new VodSeriesSteps();
    private final V1VodSeriesValidation v1VodSeriesValidation = new V1VodSeriesValidation();

    @Test(groups = {"full"})
    public void testV1VodSeriesDeltaUpdateWithoutOptionalLimitOffset() {
        String syncStop = vodSeriesSteps.getV1SeriesMostRecentlyUpdatedSeriesTimestamp();
        String syncStart = parse(syncStop).minus(24, HOURS).toString();
        Response v1VodSeriesDeltaUpdate = vodSeriesSteps.getV1VodSeriesDeltaUpdate(syncStart, syncStop);
        v1VodSeriesDeltaUpdate.then().statusCode(SC_OK);

        List<VodSerie> vodSeries = vodSeriesSteps.getVodSeriesWithUpsertReplicationType(v1VodSeriesDeltaUpdate);

        List<VodSerie> vodEpisodesThatShouldNotBeReturned =
                vodSeriesSteps.getVodSeriesThatShouldNotBeReturnedForDeltaUpdate(syncStop, syncStart, vodSeries);

        v1VodSeriesValidation.assertThatThereAreNoVodSeriesThatNotFulfillWithSyncFilter(
                vodEpisodesThatShouldNotBeReturned);
        v1VodSeriesValidation.assertThatVodSeriesAttributesNotNullEmptyAndHaveExpectedFormat(
                vodSeriesSteps.getFirstVodSeries(v1VodSeriesDeltaUpdate, REPL_TYPE_UPSERT));
    }
}
