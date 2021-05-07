/*
 * (c) 2021 Pluto Inc.
 */

package com.plutotv.test.v1.active.region.positive;

import com.plutotv.common.layer.step.v1.active.regions.ActiveRegionsSteps;
import com.plutotv.common.model.bussines.v1.active.regions.ActiveRegion;
import com.plutotv.test.BaseTest;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.List;

import static com.plutotv.common.Constants.REPL_TYPE_UPSERT;
import static java.lang.Integer.parseInt;
import static org.apache.http.HttpStatus.SC_OK;

public class C833796_TestV1ActiveRegionsFullSyncWithOptionalOffset extends BaseTest {
    private final ActiveRegionsSteps activeRegionsSteps = new ActiveRegionsSteps();

    @Test(groups = {"full"})
    public void testV1ActiveRegionsFullSyncWithOptionalOffset() {
        String offset = "1";
        String syncStop = activeRegionsSteps.getV1RatingsMostRecentlyUpdatedActiveRegionTimestamp();
        Response v1ActiveRegionsFullSync = activeRegionsSteps.getV1ActiveRegionsFullSync(syncStop);

        v1ActiveRegionsFullSync.then().statusCode(SC_OK);

        List<ActiveRegion> firstNActiveRegions = activeRegionsSteps.getFirstNActiveRegions(
                parseInt(offset) + 1, v1ActiveRegionsFullSync);
        ActiveRegion firstActiveRegionAfterOffsetExpected = firstNActiveRegions.get(parseInt(offset));

        Response v1ActiveRegionsFullSyncWithOffset =
                activeRegionsSteps.getV1ActiveRegionsFullSyncWithOffset(offset, syncStop);

        v1ActiveRegionsFullSyncWithOffset.then().statusCode(SC_OK);

        ActiveRegion firstActiveRegionAfterOffsetActual =
                activeRegionsSteps.getFirstActiveRegion(v1ActiveRegionsFullSyncWithOffset, REPL_TYPE_UPSERT);
        activeRegionsSteps.assertThatV1ActiveRegionsOffsetAsExpected(
                firstActiveRegionAfterOffsetExpected, firstActiveRegionAfterOffsetActual);
    }

}
