/*
 * (c) 2021 Pluto Inc.
 */

package com.plutotv.test.v1.active.region.positive;

import com.plutotv.common.helper.validation.V1ActiveRegionsValidation;
import com.plutotv.common.layer.step.v1.active.regions.ActiveRegionsSteps;
import com.plutotv.test.BaseTest;
import org.testng.annotations.Test;

public class C833792_TestHeadV1ActiveRegions extends BaseTest {
    private final ActiveRegionsSteps activeRegionsSteps = new ActiveRegionsSteps();
    private final V1ActiveRegionsValidation v1ActiveRegionsValidation = new V1ActiveRegionsValidation();

    @Test(groups = {"full"})
    public void testHeadV1ActiveRegions() {
        String lastModifiedHeader = activeRegionsSteps.getV1RatingsMostRecentlyUpdatedActiveRegionTimestamp();

        v1ActiveRegionsValidation.assertThatLastModifiedHeaderPresent(lastModifiedHeader);
    }
}
