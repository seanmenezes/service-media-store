/*
 * (c) 2021 Pluto Inc.
 */

package com.plutotv.test.v1.active.region.positive;

import com.plutotv.common.helper.validation.V1ActiveRegionsValidation;
import com.plutotv.common.layer.step.v1.active.regions.ActiveRegionsSteps;
import com.plutotv.common.model.bussines.v1.active.regions.ActiveRegion;
import com.plutotv.test.BaseTest;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.List;

import static com.plutotv.common.Constants.REPL_TYPE_UPSERT;
import static java.time.Instant.parse;
import static java.time.temporal.ChronoUnit.HOURS;
import static org.apache.http.HttpStatus.SC_OK;

public class C833794_TestV1ActiveRegionsDeltaUpdateWithoutOptionalLimitOffset extends BaseTest {
    private final ActiveRegionsSteps activeRegionsSteps = new ActiveRegionsSteps();
    private final V1ActiveRegionsValidation v1ActiveRegionsValidation = new V1ActiveRegionsValidation();

    @Test(groups = {"full"})
    public void testV1ActiveRegionsDeltaUpdateWithoutOptionalLimitOffset() {
        String syncStop = activeRegionsSteps.getV1RatingsMostRecentlyUpdatedActiveRegionTimestamp();
        String syncStart = parse(syncStop).minus(24, HOURS).toString();
        Response v1ActiveRegionsDeltaUpdate = activeRegionsSteps.getV1ActiveRegionsDeltaUpdate(syncStart, syncStop);

        v1ActiveRegionsDeltaUpdate.then().statusCode(SC_OK);

        List<ActiveRegion> activeRegions =
                activeRegionsSteps.getActiveRegionsWithUpsertReplicationType(v1ActiveRegionsDeltaUpdate);

        List<ActiveRegion> activeRegionsThatShouldNotBeReturned =
                activeRegionsSteps.getActiveRegionsThatShouldNotBeReturnedForDeltaUpdate(syncStop, syncStart, activeRegions);

        v1ActiveRegionsValidation.assertThatThereAreNoActiveRegionsThatNotFulfillWithSyncFilter(activeRegionsThatShouldNotBeReturned);

        v1ActiveRegionsValidation.assertThatActiveRegionsAttributesNotNullEmptyAndHaveExpectedFormat(
                activeRegionsSteps.getFirstActiveRegion(v1ActiveRegionsDeltaUpdate, REPL_TYPE_UPSERT));
    }
}
