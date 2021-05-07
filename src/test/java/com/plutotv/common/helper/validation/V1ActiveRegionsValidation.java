package com.plutotv.common.helper.validation;

import com.plutotv.common.model.bussines.v1.active.regions.ActiveRegion;
import io.qameta.allure.Step;

import java.util.List;

import static com.plutotv.common.Constants.RegEx.DATE_TIME;
import static com.plutotv.test.lib.api.coreapi.Constants.RegEx.ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

public class V1ActiveRegionsValidation {
    @Step
    public void assertThatActiveRegionsAttributesNotNullEmptyAndHaveExpectedFormat(ActiveRegion activeRegion) {
        assertSoftly(softly -> {
            softly.assertThat(activeRegion.getId())
                    .as("id attr of activeRegion should be present, not empty and match pattern, but it is %s",
                            activeRegion.getId())
                    .isNotEmpty()
                    .matches(ID);
            softly.assertThat(activeRegion.getUpdatedAt())
                    .as("updatedAt attr should be present, not empty and match pattern, but it is %s for activeRegion %s",
                            activeRegion.getUpdatedAt(), activeRegion.getId())
                    .isNotEmpty()
                    .matches(DATE_TIME);
            softly.assertThat(activeRegion.getCode())
                    .as("code attr should be present, not empty, but it is %s for activeRegion %s",
                            activeRegion.getCode(), activeRegion.getId())
                    .isNotEmpty();
            softly.assertThat(activeRegion.getName())
                    .as("name attr should be present, not empty, but it is %s for activeRegion %s",
                            activeRegion.getName(), activeRegion.getId())
                    .isNotEmpty();
            softly.assertThat(activeRegion.getTerritories())
                    .as("territories attr should be present, not empty, but it is %s for activeRegion %s",
                            activeRegion.getTerritories(), activeRegion.getId())
                    .isNotNull()
                    .hasSizeGreaterThan(0);
        });
    }

    @Step
    public void assertThatLastModifiedHeaderPresent(String lastModifiedHeader) {
        assertThat(lastModifiedHeader)
                .as("Last-Modified header for active region should be present and not empty but it is %s",
                        lastModifiedHeader)
                .isNotEmpty();
    }

    @Step
    public void assertThatThereAreNoActiveRegionsThatNotFulfillWithSyncFilter(List<ActiveRegion> activeRegions) {
        assertThat(activeRegions)
                .as("activeRegionsThatShouldNotBeReturned list should be empty, but it has activeRegions %s",
                        activeRegions)
                .isEmpty();
    }
}
