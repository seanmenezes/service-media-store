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
import static java.lang.Integer.parseInt;
import static org.apache.http.HttpStatus.SC_OK;

public class C833890_TestV1VodSeriesFullSyncWithOptionalOffset extends BaseTest {
    private final VodSeriesSteps vodSeriesSteps = new VodSeriesSteps();
    private final V1VodSeriesValidation v1VodSeriesValidation = new V1VodSeriesValidation();

    @Test(groups = {"full"})
    public void testV1VodSeriesFullSyncWithOptionalOffset() {
        String offset = "1";
        String syncStop = vodSeriesSteps.getV1SeriesMostRecentlyUpdatedSeriesTimestamp();
        Response v1VodSeriesFullSync = vodSeriesSteps.getV1VodSeriesFullSync(syncStop);

        v1VodSeriesFullSync.then().statusCode(SC_OK);

        List<VodSerie> firstNVodSeries =
                vodSeriesSteps.getFirstNVodSeries(parseInt(offset) + 1, v1VodSeriesFullSync);
        VodSerie firstVodSeriesAfterOffsetExpected = firstNVodSeries.get(parseInt(offset));

        Response v1VodSeriesFullSyncWithOffset =
                vodSeriesSteps.getV1VodSeriesFullSyncWithOffset(offset, syncStop);

        v1VodSeriesFullSyncWithOffset.then().statusCode(SC_OK);

        VodSerie firstVodSeriesAfterOffsetActual =
                vodSeriesSteps.getFirstVodSeries(v1VodSeriesFullSyncWithOffset, REPL_TYPE_UPSERT);
        v1VodSeriesValidation.assertThatVodEpisodesOffsetAsExpected(
                firstVodSeriesAfterOffsetExpected, firstVodSeriesAfterOffsetActual);
    }
}
