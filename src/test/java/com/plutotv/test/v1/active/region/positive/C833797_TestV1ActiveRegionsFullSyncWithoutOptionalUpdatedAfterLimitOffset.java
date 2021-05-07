/*
 * (c) 2021 Pluto Inc.
 */

package com.plutotv.test.v1.active.region.positive;

import com.plutotv.common.helper.validation.V1ActiveRegionsValidation;
import com.plutotv.common.layer.step.v1.active.regions.ActiveRegionsSteps;
import com.plutotv.common.model.bussines.v1.active.regions.ActiveRegion;
import com.plutotv.common.model.response.v1.active.regions.ActiveRegions;
import com.plutotv.common.model.support.v1.active.regions.ActiveRegionsDatum;
import com.plutotv.test.BaseTest;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Objects;

import static com.plutotv.common.Constants.REPL_TYPE_UPSERT;
import static java.time.Instant.parse;
import static java.util.stream.Collectors.toList;
import static org.apache.http.HttpStatus.SC_OK;
import static org.assertj.core.api.Assertions.assertThat;

public class C833797_TestV1ActiveRegionsFullSyncWithoutOptionalUpdatedAfterLimitOffset extends BaseTest {
    private final ActiveRegionsSteps activeRegionsSteps = new ActiveRegionsSteps();
    private final V1ActiveRegionsValidation v1ActiveRegionsValidation = new V1ActiveRegionsValidation();

    @Test(groups = {"full"})
    public void testV1ActiveRegionsFullSyncWithoutOptionalUpdatedAfterLimitOffset() {
        String syncStop = activeRegionsSteps.getV1RatingsMostRecentlyUpdatedActiveRegionTimestamp();
        Response v1ActiveRegionsFullSync = activeRegionsSteps.getV1ActiveRegionsFullSync(
                syncStop);

        v1ActiveRegionsFullSync.then().statusCode(SC_OK);
        List<ActiveRegion> activeRegions = v1ActiveRegionsFullSync.as(ActiveRegions.class).getData().stream()
                .filter(activeRegion -> Objects.equals("upsert", activeRegion.getMeta().getReplicaAction()))
                .map(ActiveRegionsDatum::getPayload)
                .collect(toList());
        assertThat(activeRegions).as("it is expected that some activeRegions with replicationType: upsert found").isNotEmpty();

        List<ActiveRegion> activeRegionsThatShouldNotBeReturned = activeRegions.stream().filter(activeRegion ->
                parse(activeRegion.getUpdatedAt()).isAfter(parse(syncStop)))
                .collect(toList());

        assertThat(activeRegionsThatShouldNotBeReturned)
                .as("activeRegionsThatShouldNotBeReturned list should be empty, but it has activeRegions %s",
                        activeRegionsThatShouldNotBeReturned)
                .isEmpty();

        v1ActiveRegionsValidation.assertThatActiveRegionsAttributesNotNullEmptyAndHaveExpectedFormat(
                activeRegionsSteps.getFirstActiveRegion(v1ActiveRegionsFullSync, REPL_TYPE_UPSERT));
    }
}
