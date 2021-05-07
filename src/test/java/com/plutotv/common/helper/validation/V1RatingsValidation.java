package com.plutotv.common.helper.validation;

import com.plutotv.common.model.bussines.v1.ratings.Rating;
import io.qameta.allure.Step;

import static com.plutotv.test.lib.api.coreapi.Constants.RegEx.ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

public class V1RatingsValidation {
    @Step
    public void assertThatRatingsAttributesNotNullEmptyAndHaveExpectedFormat(Rating rating) {
        assertSoftly(softly -> {
            softly.assertThat(rating.getId())
                    .as("id attr of rating should be present, not empty and match pattern, but it is %s",
                            rating.getId())
                    .isNotEmpty()
                    .matches(ID);
            softly.assertThat(rating.getValue())
                    .as("value attr should be present, not empty, but it is %s for rating %s",
                            rating.getValue(), rating.getId())
                    .isNotEmpty();
            softly.assertThat(rating.getRegion())
                    .as("region attr should be present, not empty, but it is %s for rating %s",
                            rating.getRegion(), rating.getId())
                    .isNotEmpty();
            softly.assertThat(rating.getWeight())
                    .as("weight attr should be present and not zero, but it is %s for rating %s",
                            rating.getWeight(), rating.getId())
                    .isNotNull()
                    .isGreaterThan(0);
        });
    }

    @Step
    public void assertThatLastModifiedHeaderPresent(String lastModifiedHeader) {
        assertThat(lastModifiedHeader)
                .as("Last-Modified header for ratings should be present and not empty but it is %s",
                        lastModifiedHeader)
                .isNotEmpty();
    }
}
