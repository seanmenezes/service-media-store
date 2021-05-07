package com.plutotv.common.helper.validation;

import com.plutotv.common.model.bussines.v2.channels.Serie;
import io.qameta.allure.Step;

import java.util.List;

import static com.plutotv.common.Constants.RegEx.DATE_TIME;
import static com.plutotv.test.lib.api.coreapi.Constants.RegEx.ID;
import static java.util.Objects.nonNull;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

public class V2ChannelsSeriesValidation {
    @Step
    public static void assertThatSeriesAttributesNotNullEmptyAndHaveExpectedFormat(Serie series) {
        assertSoftly(softly -> {
            softly.assertThat(series.getId())
                    .as("_id attr of series should be present, not empty and match pattern, but it is %s",
                            series.getId())
                    .isNotEmpty()
                    .matches(ID);
            softly.assertThat(series.getName())
                    .as("name attr should be present, not empty, but it is %s for series %s",
                            series.getName(), series.getId())
                    .isNotEmpty();
            softly.assertThat(series.getSlug())
                    .as("slug attr should be present, not empty, but it is %s for series %s",
                            series.getSlug(), series.getId())
                    .isNotEmpty();
            softly.assertThat(series.getType())
                    .as("type attr should be present, not empty, but it is %s for series %s",
                            series.getType(), series.getId())
                    .isNotEmpty();

            softly.assertThat(series.getTile())
                    .as("tile attr should be present, not empty, but it is %s for series %s",
                            series.getTile(), series.getId())
                    .isNotNull();
            if (nonNull(series.getTile())) {
                softly.assertThat(series.getTile().getPath())
                        .as("tile.path attr should be present, not empty and match pattern, but it is %s for series %s",
                                series.getTile().getPath(), series.getId())
                        .isNotNull()
                        .startsWith("%PROTOCOL%://%HOSTNAME%");
            }

            softly.assertThat(series.getDescription())
                    .as("description attr should be present, not empty, but it is %s for series %s",
                            series.getDescription(), series.getId())
                    .isNotEmpty();
            softly.assertThat(series.getSummary())
                    .as("summary attr should be present, not empty, but it is %s for series %s",
                            series.getSummary(), series.getId())
                    .isNotEmpty();

            softly.assertThat(series.getFeaturedImage())
                    .as("featuredImage attr should be present, not empty, but it is %s for series %s",
                            series.getFeaturedImage(), series.getId())
                    .isNotNull();
            if (nonNull(series.getFeaturedImage())) {
                softly.assertThat(series.getFeaturedImage().getPath())
                        .as("featuredImage.path attr should be present, not empty and match pattern, but it is %s for series %s",
                                series.getFeaturedImage().getPath(), series.getId())
                        .isNotNull()
                        .startsWith("%PROTOCOL%://%HOSTNAME%");
            }

            softly.assertThat(series.getUpdatedAt())
                    .as("updatedAt attr should be present, not empty and match pattern, but it is %s for series %s",
                            series.getUpdatedAt(), series.getId())
                    .isNotEmpty()
                    .matches(DATE_TIME);
        });
    }

    @Step
    public void assertThatThereAreNoSeriesThatNotFulfillWithSyncFilter(List<Serie> seriesThatShouldNotBeReturned) {
        assertThat(seriesThatShouldNotBeReturned)
                .as("seriesThatShouldNotBeReturned list should be empty, but it has series %s",
                        seriesThatShouldNotBeReturned)
                .isEmpty();
    }
}
