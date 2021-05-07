/*
 * (c) 2021 Pluto Inc.
 */

package com.plutotv.test.v1.active.region.positive;

import com.plutotv.common.layer.step.v1.active.regions.ActiveRegionsSteps;
import com.plutotv.test.BaseTest;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static org.apache.http.HttpStatus.SC_OK;

public class C833793_TestV1ActiveRegionFullSyncWithOptionalLimit extends BaseTest {
    private final ActiveRegionsSteps activeRegionsSteps = new ActiveRegionsSteps();

    @Test(groups = {"full"})
    public void testV1ActiveRegionFullSyncWithOptionalLimit() {
        String activeRegionsCountExpected = "1";
        Response v1ActiveRegionsFullSyncWithLimit = activeRegionsSteps.getV1ActiveRegionsFullSyncWithLimit(
                activeRegionsCountExpected,
                activeRegionsSteps.getV1RatingsMostRecentlyUpdatedActiveRegionTimestamp());

        v1ActiveRegionsFullSyncWithLimit.then().statusCode(SC_OK);
        long activeRegionsCountActual = activeRegionsSteps.getActiveRegionsCount(v1ActiveRegionsFullSyncWithLimit);
        activeRegionsSteps.assertThatActiveRegionsLimitAsExpected(activeRegionsCountExpected, activeRegionsCountActual);
    }
}
