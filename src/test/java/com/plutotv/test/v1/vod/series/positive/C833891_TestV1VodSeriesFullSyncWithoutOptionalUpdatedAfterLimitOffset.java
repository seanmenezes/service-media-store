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
import static org.apache.http.HttpStatus.SC_OK;

public class C833891_TestV1VodSeriesFullSyncWithoutOptionalUpdatedAfterLimitOffset extends BaseTest {
    private final VodSeriesSteps vodSeriesSteps = new VodSeriesSteps();
    private final V1VodSeriesValidation v1VodSeriesValidation = new V1VodSeriesValidation();

    @Test(groups = {"full"})
    public void testV1VodSeriesFullSyncWithoutOptionalUpdatedAfterLimitOffset() {
        String syncStop = vodSeriesSteps.getV1SeriesMostRecentlyUpdatedSeriesTimestamp();
        Response v1VodSeriesFullSync = vodSeriesSteps.getV1VodSeriesFullSync(syncStop);

        v1VodSeriesFullSync.then().statusCode(SC_OK);

        List<VodSerie> vodSeriesWithUpsertReplicationType =
                vodSeriesSteps.getVodSeriesWithUpsertReplicationType(v1VodSeriesFullSync);

        List<VodSerie> vodSeriesThatShouldNotBeReturnedForFullSync =
                vodSeriesSteps.getVodSeriesThatShouldNotBeReturnedForFullSync(
                        syncStop, vodSeriesWithUpsertReplicationType);

        v1VodSeriesValidation.assertThatThereAreNoVodSeriesThatNotFulfillWithSyncFilter(
                vodSeriesThatShouldNotBeReturnedForFullSync);
        v1VodSeriesValidation.assertThatVodSeriesAttributesNotNullEmptyAndHaveExpectedFormat(
                vodSeriesSteps.getFirstVodSeries(v1VodSeriesFullSync, REPL_TYPE_UPSERT));
    }
}
