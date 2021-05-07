package com.plutotv.common.helper.validation;

import com.plutotv.common.model.bussines.v1.ratings.RatingDescriptor;
import io.qameta.allure.Step;

import static com.plutotv.test.lib.api.coreapi.Constants.RegEx.ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

public class V1RatingsDescriptorsValidation {
    @Step
    public void assertThatRatingsDescriptorsAttributesNotNullEmptyAndHaveExpectedFormat(RatingDescriptor ratingDescriptor) {
        assertSoftly(softly -> {
            softly.assertThat(ratingDescriptor.getId())
                    .as("id attr of ratingDescriptor should be present, not empty and match pattern, but it is %s",
                            ratingDescriptor.getId())
                    .isNotEmpty()
                    .matches(ID);
            softly.assertThat(ratingDescriptor.getDisplayName())
                    .as("displayName attr should be present, not empty, but it is %s for ratingDescriptor %s",
                            ratingDescriptor.getDisplayName(), ratingDescriptor.getId())
                    .isNotEmpty();
            //label can be empty
            softly.assertThat(ratingDescriptor.getLabel())
                    .as("label attr should be present, but it is %s for ratingDescriptor %s",
                            ratingDescriptor.getLabel(), ratingDescriptor.getId())
                    .isNotNull();
            softly.assertThat(ratingDescriptor.getRegion())
                    .as("region attr should be present, not empty, but it is %s for ratingDescriptor %s",
                            ratingDescriptor.getRegion(), ratingDescriptor.getId())
                    .isNotEmpty();
            softly.assertThat(ratingDescriptor.getSlug())
                    .as("slug attr should be present, not empty, but it is %s for ratingDescriptor %s",
                            ratingDescriptor.getSlug(), ratingDescriptor.getId())
                    .isNotEmpty();
        });
    }

    @Step
    public void assertThatLastModifiedHeaderPresent(String lastModifiedHeader) {
        assertThat(lastModifiedHeader)
                .as("Last-Modified header for ratings descriptors should be present and not empty but it is %s",
                        lastModifiedHeader)
                .isNotEmpty();
    }
}
