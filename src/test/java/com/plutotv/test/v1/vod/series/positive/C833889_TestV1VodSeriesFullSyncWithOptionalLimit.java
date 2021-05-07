/*
 * (c) 2021 Pluto Inc.
 */

package com.plutotv.test.v1.vod.series.positive;

import com.plutotv.common.helper.validation.V1VodSeriesValidation;
import com.plutotv.common.layer.step.v1.vod.VodSeriesSteps;
import com.plutotv.test.BaseTest;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static org.apache.http.HttpStatus.SC_OK;

public class C833889_TestV1VodSeriesFullSyncWithOptionalLimit extends BaseTest {
    private final VodSeriesSteps vodSeriesSteps = new VodSeriesSteps();
    private final V1VodSeriesValidation v1VodSeriesValidation = new V1VodSeriesValidation();

    @Test(groups = {"full"})
    public void testV1VodSeriesFullSyncWithOptionalLimit() {
        String vodSeriesCountExpected = "1";
        Response v1VodSeriesFullSyncWithLimit = vodSeriesSteps.getV1VodSeriesFullSyncWithLimit(
                vodSeriesCountExpected,
                vodSeriesSteps.getV1SeriesMostRecentlyUpdatedSeriesTimestamp());

        v1VodSeriesFullSyncWithLimit.then().statusCode(SC_OK);
        long vodSeriesCountActual = vodSeriesSteps.getVodSeriesCount(v1VodSeriesFullSyncWithLimit);
        v1VodSeriesValidation.assertThatVodSeriesLimitAsExpected(vodSeriesCountExpected, vodSeriesCountActual);
    }
}
