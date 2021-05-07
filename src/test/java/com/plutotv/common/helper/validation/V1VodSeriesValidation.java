package com.plutotv.common.helper.validation;

import com.plutotv.common.model.bussines.v1.vod.series.VodSerie;
import io.qameta.allure.Step;

import java.util.List;

import static com.plutotv.common.Constants.RegEx.DATE_TIME;
import static com.plutotv.test.lib.api.coreapi.Constants.RegEx.ID;
import static java.lang.Long.parseLong;
import static java.util.Objects.nonNull;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

public class V1VodSeriesValidation {
    @Step
    public void assertThatVodSeriesAttributesNotNullEmptyAndHaveExpectedFormat(VodSerie vodSerie) {
        assertSoftly(softly -> {
            softly.assertThat(vodSerie.getId())
                    .as("id attr of vodSerie should be present, not empty and match pattern, but it is %s",
                            vodSerie.getId())
                    .isNotEmpty()
                    .matches(ID);
            softly.assertThat(vodSerie.getName())
                    .as("name attr should be present, not empty, but it is %s for vodSerie %s",
                            vodSerie.getName(), vodSerie.getId())
                    .isNotEmpty();
            softly.assertThat(vodSerie.getSummary())
                    .as("summary attr should be present, not empty, but it is %s for vodSerie %s",
                            vodSerie.getSummary(), vodSerie.getId())
                    .isNotEmpty();
            softly.assertThat(vodSerie.getType())
                    .as("type attr should be present, not empty, but it is %s for vodSerie %s",
                            vodSerie.getType(), vodSerie.getId())
                    .isNotEmpty();
            softly.assertThat(vodSerie.getGenre())
                    .as("genre attr should be present, not empty, but it is %s for vodSerie %s",
                            vodSerie.getGenre(), vodSerie.getId())
                    .isNotEmpty();
            softly.assertThat(vodSerie.getDescription())
                    .as("description attr should be present, not empty, but it is %s for vodSerie %s",
                            vodSerie.getDescription(), vodSerie.getId())
                    .isNotEmpty();
            softly.assertThat(vodSerie.getRating())
                    .as("rating attr should be present, not empty, but it is %s for vodSerie %s",
                            vodSerie.getRating(), vodSerie.getId())
                    .isNotEmpty();

            //ratingDescriptors is optional
            if (nonNull(vodSerie.getRatingDescriptors())) {
                softly.assertThat(vodSerie.getRatingDescriptors())
                        .as("ratingDescriptors attr should be not empty, but it is %s for vodSerie %s",
                                vodSerie.getRatingDescriptors(), vodSerie.getId())
                        .hasSizeGreaterThan(0);
            }

            softly.assertThat(vodSerie.getFeaturedImage())
                    .as("featuredImage attr should be present, but it is %s for vodSerie %s",
                            vodSerie.getFeaturedImage(), vodSerie.getId())
                    .isNotNull();
            if (nonNull(vodSerie.getFeaturedImage())) {
                softly.assertThat(vodSerie.getFeaturedImage().getPath())
                        .as("featuredImage.path attr should be present and not empty, but it is %s for vodSerie %s",
                                vodSerie.getFeaturedImage().getPath(), vodSerie.getId())
                        .isNotEmpty()
                        .startsWith("%PROTOCOL%://%HOSTNAME%");
            }

            softly.assertThat(vodSerie.getPoster())
                    .as("poster attr should be present, but it is %s for vodSerie %s",
                            vodSerie.getPoster(), vodSerie.getId())
                    .isNotNull();
            if (nonNull(vodSerie.getPoster())) {
                softly.assertThat(vodSerie.getPoster().getPath())
                        .as("poster.path attr should be present, not empty, but it is %s for vodSerie %s",
                                vodSerie.getPoster().getPath(), vodSerie.getId())
                        .isNotEmpty()
                        .startsWith("%PROTOCOL%://%HOSTNAME%");
            }

            softly.assertThat(vodSerie.getSeasons())
                    .as("seasons attr should be present, not zero, but it is %s for vodSerie %s",
                            vodSerie.getSeasons(), vodSerie.getId())
                    .isNotEmpty();
            if (nonNull(vodSerie.getSeasons()) && vodSerie.getSeasons().size() > 0) {
                vodSerie.getSeasons().forEach(season ->
                        softly.assertThat(season.getNumber())
                                .as("seasons.number attr should be present, not zero, but it is %s for vodSerie %s",
                                        vodSerie.getSeasons(), vodSerie.getId())
                                .isNotNull()
                                .isGreaterThan(0));
            }

            softly.assertThat(vodSerie.getSlug())
                    .as("slug attr should be present, not empty, but it is %s for vodSerie %s",
                            vodSerie.getSlug(), vodSerie.getId())
                    .isNotEmpty();

            softly.assertThat(vodSerie.getTile())
                    .as("tile attr should be present, but it is %s for vodSerie %s",
                            vodSerie.getTile(), vodSerie.getId())
                    .isNotNull();
            if (nonNull(vodSerie.getTile())) {
                softly.assertThat(vodSerie.getTile().getPath())
                        .as("tile.path attr should be present, not empty and match pattern, but it is %s for vodSerie %s",
                                vodSerie.getTile().getPath(), vodSerie.getId())
                        .isNotEmpty()
                        .startsWith("%PROTOCOL%://%HOSTNAME%");
            }

            softly.assertThat(vodSerie.getRegionFilter())
                    .as("regionFilter attr should be present, not empty, but it is %s for vodSerie %s",
                            vodSerie.getRegionFilter(), vodSerie.getId())
                    .isNotEmpty();

            softly.assertThat(vodSerie.getAvailabilityWindows())
                    .as("availabilityWindows attr should be present, but it is %s for vodSerie %s",
                            vodSerie.getAvailabilityWindows(), vodSerie.getId())
                    .isNotNull();
            if (nonNull(vodSerie.getAvailabilityWindows())) {
                softly.assertThat(vodSerie.getAvailabilityWindows().getAVOD())
                        .as("availabilityWindows.aVOD attr should be present, not empty, but it is %s for vodSerie %s",
                                vodSerie.getAvailabilityWindows().getAVOD(), vodSerie.getId())
                        .isNotEmpty();
                if (nonNull(vodSerie.getAvailabilityWindows().getAVOD()) && vodSerie.getAvailabilityWindows().getAVOD().size() > 0) {
                    vodSerie.getAvailabilityWindows().getAVOD().forEach(aVod -> {
                        softly.assertThat(aVod.getStartDate())
                                .as("availabilityWindows.aVOD.startDate attr should be present, not empty and match format, but it is %s for vodSerie %s",
                                        aVod.getStartDate(), vodSerie.getId())
                                .isNotEmpty()
                                .matches(DATE_TIME);
                        softly.assertThat(aVod.getEndDate())
                                .as("availabilityWindows.aVOD.endDate attr should be present, not empty and match format, but it is %s for vodSerie %s",
                                        aVod.getEndDate(), vodSerie.getId())
                                .isNotEmpty()
                                .matches(DATE_TIME);
                    });
                }
            }

            softly.assertThat(vodSerie.getUpdatedAt())
                    .as("updatedAt attr should be present, not empty and match pattern, but it is %s for vodSerie %s",
                            vodSerie.getUpdatedAt(), vodSerie.getId())
                    .isNotEmpty()
                    .matches(DATE_TIME);
        });
    }

    @Step
    public void assertThatThereAreNoVodSeriesThatNotFulfillWithSyncFilter(List<VodSerie> vodSeries) {
        assertThat(vodSeries)
                .as("vodSeriesThatShouldNotBeReturned list should be empty, but it has vodSeries %s",
                        vodSeries)
                .isEmpty();
    }

    @Step
    public void assertThatVodEpisodesOffsetAsExpected(
            VodSerie firstVodSeriesAfterOffsetExpected, VodSerie firstVodSeriesAfterOffsetActual) {
        assertThat(firstVodSeriesAfterOffsetExpected.getId())
                .as("First expected vod series after offset is %s but actual is %s",
                        firstVodSeriesAfterOffsetExpected, firstVodSeriesAfterOffsetActual)
                .isEqualTo(firstVodSeriesAfterOffsetActual.getId());
    }

    @Step
    public void assertThatVodSeriesLimitAsExpected(String vodSeriesCountExpected,
                                                   long vodSeriesCountActual) {
        assertThat(vodSeriesCountActual)
                .as("Only %s vod series expected, but actually %s in response",
                        vodSeriesCountExpected, vodSeriesCountActual)
                .isEqualTo(parseLong(vodSeriesCountExpected));
    }

    @Step
    public void assertThatLastModifiedHeaderPresent(String lastModifiedHeader) {
        assertThat(lastModifiedHeader)
                .as("Last-Modified header for vod series should be present and not empty but it is %s",
                        lastModifiedHeader)
                .isNotEmpty();
    }
}
