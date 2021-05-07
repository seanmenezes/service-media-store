/*
 * (c) 2021 Pluto Inc.
 */

package com.plutotv.test.v1.vod.series.positive;

import com.plutotv.common.helper.validation.V1VodSeriesValidation;
import com.plutotv.common.layer.step.v1.vod.VodSeriesSteps;
import com.plutotv.test.BaseTest;
import org.testng.annotations.Test;

public class C833886_TestHeadV1VodSeries extends BaseTest {
    private final VodSeriesSteps vodSeriesSteps = new VodSeriesSteps();
    private final V1VodSeriesValidation v1VodSeriesValidation = new V1VodSeriesValidation();

    @Test(groups = {"full"})
    public void testHeadV1VodSeries() {
        String lastModifiedHeader = vodSeriesSteps.getV1SeriesMostRecentlyUpdatedSeriesTimestamp();

        v1VodSeriesValidation.assertThatLastModifiedHeaderPresent(lastModifiedHeader);
    }
}
