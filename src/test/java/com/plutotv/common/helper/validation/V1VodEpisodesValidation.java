package com.plutotv.common.helper.validation;

import com.plutotv.common.model.bussines.v1.vod.episode.VodEpisode;
import io.qameta.allure.Step;

import java.util.List;

import static com.plutotv.common.Constants.RegEx.DATE_TIME;
import static com.plutotv.test.lib.api.coreapi.Constants.RegEx.ID;
import static java.lang.Long.parseLong;
import static java.util.Objects.nonNull;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

public class V1VodEpisodesValidation {
    @Step
    public void assertThatVodEpisodesAttributesNotNullEmptyAndHaveExpectedFormat(VodEpisode vodEpisode) {
        assertSoftly(softly -> {
            softly.assertThat(vodEpisode.getId())
                    .as("id attr of vodEpisode should be present, not empty and match pattern, but it is %s",
                            vodEpisode.getId())
                    .isNotEmpty()
                    .matches(ID);
            softly.assertThat(vodEpisode.getType())
                    .as("type attr should be present, not empty, but it is %s for vodEpisode %s",
                            vodEpisode.getType(), vodEpisode.getId())
                    .isNotEmpty();
            softly.assertThat(vodEpisode.getName())
                    .as("name attr should be present, not empty, but it is %s for vodEpisode %s",
                            vodEpisode.getName(), vodEpisode.getId())
                    .isNotEmpty();
            softly.assertThat(vodEpisode.getDescription())
                    .as("description attr should be present, not empty, but it is %s for vodEpisode %s",
                            vodEpisode.getDescription(), vodEpisode.getId())
                    .isNotEmpty();
            softly.assertThat(vodEpisode.getDuration())
                    .as("duration attr should be present, not zero, but it is %s for vodEpisode %s",
                            vodEpisode.getDuration(), vodEpisode.getId())
                    .isNotNull()
                    .isGreaterThan(0);
            softly.assertThat(vodEpisode.getOriginalContentDuration())
                    .as("originalContentDuration attr should be present, not zero, but it is %s for vodEpisode %s",
                            vodEpisode.getOriginalContentDuration(), vodEpisode.getId())
                    .isNotNull()
                    .isGreaterThan(0);
            softly.assertThat(vodEpisode.getAdPodsDuration())
                    .as("adPodsDuration attr should be present, not zero, but it is %s for vodEpisode %s",
                            vodEpisode.getAdPodsDuration(), vodEpisode.getId())
                    .isNotNull()
                    .isGreaterThan(0);
            softly.assertThat(vodEpisode.getNumber())
                    .as("number attr should be present, not zero, but it is %s for vodEpisode %s",
                            vodEpisode.getNumber(), vodEpisode.getId())
                    .isNotNull()
                    .isGreaterThan(0);
            softly.assertThat(vodEpisode.getSeason())
                    .as("season attr should be present, not zero, but it is %s for vodEpisode %s",
                            vodEpisode.getSeason(), vodEpisode.getId())
                    .isNotNull()
                    .isGreaterThan(0);
            softly.assertThat(vodEpisode.getSlug())
                    .as("slug attr should be present, not empty, but it is %s for vodEpisode %s",
                            vodEpisode.getSlug(), vodEpisode.getId())
                    .isNotEmpty();
            softly.assertThat(vodEpisode.getSeriesID())
                    .as("seriesID attr should be present, not empty and match pattern, but it is %s for vodEpisode %s",
                            vodEpisode.getSeriesID(), vodEpisode.getId())
                    .isNotNull()
                    .matches(ID);

            softly.assertThat(vodEpisode.getScreenshot4_3())
                    .as("screenshot4_3 attr should be present, but it is %s for vodEpisode %s",
                            vodEpisode.getScreenshot4_3(), vodEpisode.getId())
                    .isNotNull();
            if (nonNull(vodEpisode.getScreenshot4_3())) {
                softly.assertThat(vodEpisode.getScreenshot4_3().getPath())
                        .as("screenshot4_3.path attr should be present and not empty, but it is %s for vodEpisode %s",
                                vodEpisode.getScreenshot4_3().getPath(), vodEpisode.getId())
                        .isNotEmpty()
                        .startsWith("%PROTOCOL%://%HOSTNAME%");
            }

            softly.assertThat(vodEpisode.getScreenshot16_9())
                    .as("screenshot16_9 attr should be present, but it is %s for vodEpisode %s",
                            vodEpisode.getScreenshot16_9(), vodEpisode.getId())
                    .isNotNull();
            if (nonNull(vodEpisode.getScreenshot16_9())) {
                softly.assertThat(vodEpisode.getScreenshot16_9().getPath())
                        .as("screenshot16_9.path attr should be present and not empty, but it is %s for vodEpisode %s",
                                vodEpisode.getScreenshot16_9().getPath(), vodEpisode.getId())
                        .isNotEmpty()
                        .startsWith("%PROTOCOL%://%HOSTNAME%");
            }

            softly.assertThat(vodEpisode.getPoster())
                    .as("poster attr should be present, but it is %s for vodEpisode %s",
                            vodEpisode.getPoster(), vodEpisode.getId())
                    .isNotNull();
            if (nonNull(vodEpisode.getPoster())) {
                softly.assertThat(vodEpisode.getPoster().getPath())
                        .as("poster.path attr should be present, not empty, but it is %s for vodEpisode %s",
                                vodEpisode.getPoster().getPath(), vodEpisode.getId())
                        .isNotEmpty()
                        .startsWith("%PROTOCOL%://%HOSTNAME%");
            }

            softly.assertThat(vodEpisode.getGenre())
                    .as("genre attr should be present, not empty, but it is %s for vodEpisode %s",
                            vodEpisode.getGenre(), vodEpisode.getId())
                    .isNotEmpty();
            softly.assertThat(vodEpisode.getAllotment())
                    .as("allotment attr should be present, not zero, but it is %s for vodEpisode %s",
                            vodEpisode.getAllotment(), vodEpisode.getId())
                    .isNotNull()
                    .isGreaterThan(0);
            softly.assertThat(vodEpisode.getRating())
                    .as("rating attr should be present, not empty, but it is %s for vodEpisode %s",
                            vodEpisode.getRating(), vodEpisode.getId())
                    .isNotEmpty();
            softly.assertThat(vodEpisode.getRegionFilter())
                    .as("regionFilter attr should be present, not empty, but it is %s for vodEpisode %s",
                            vodEpisode.getRegionFilter(), vodEpisode.getId())
                    .isNotEmpty();

            //ratingDescriptors is optional
            if (nonNull(vodEpisode.getRatingDescriptors())) {
                softly.assertThat(vodEpisode.getRatingDescriptors())
                        .as("ratingDescriptors attr should be not empty, but it is %s for vodEpisode %s",
                                vodEpisode.getRatingDescriptors(), vodEpisode.getId())
                        .hasSizeGreaterThan(0);
            }

            softly.assertThat(vodEpisode.getAvailabilityWindows())
                    .as("availabilityWindows attr should be present, but it is %s for vodEpisode %s",
                            vodEpisode.getAvailabilityWindows(), vodEpisode.getId())
                    .isNotNull();
            if (nonNull(vodEpisode.getAvailabilityWindows())) {
                softly.assertThat(vodEpisode.getAvailabilityWindows().getAVOD())
                        .as("availabilityWindows.aVOD attr should be present, not empty, but it is %s for vodEpisode %s",
                                vodEpisode.getAvailabilityWindows().getAVOD(), vodEpisode.getId())
                        .isNotEmpty();
                if (nonNull(vodEpisode.getAvailabilityWindows().getAVOD()) && vodEpisode.getAvailabilityWindows().getAVOD().size() > 0) {
                    vodEpisode.getAvailabilityWindows().getAVOD().forEach(aVod -> {
                        softly.assertThat(aVod.getStartDate())
                                .as("availabilityWindows.aVOD.startDate attr should be present, not empty and match format, but it is %s for vodEpisode %s",
                                        aVod.getStartDate(), vodEpisode.getId())
                                .isNotEmpty()
                                .matches(DATE_TIME);
                        softly.assertThat(aVod.getEndDate())
                                .as("availabilityWindows.aVOD.endDate attr should be present, not empty and match format, but it is %s for vodEpisode %s",
                                        aVod.getEndDate(), vodEpisode.getId())
                                .isNotEmpty()
                                .matches(DATE_TIME);
                    });
                }
            }

            softly.assertThat(vodEpisode.getUpdatedAt())
                    .as("updatedAt attr should be present, not empty and match pattern, but it is %s for vodEpisode %s",
                            vodEpisode.getUpdatedAt(), vodEpisode.getId())
                    .isNotEmpty()
                    .matches(DATE_TIME);
        });
    }

    @Step
    public void assertThatThereAreNoVodEpisodesThatNotFulfillWithSyncFilter(List<VodEpisode> vodEpisodes) {
        assertThat(vodEpisodes)
                .as("vodEpisodesThatShouldNotBeReturned list should be empty, but it has vodEpisodes %s",
                        vodEpisodes)
                .isEmpty();
    }

    @Step
    public void assertThatVodEpisodesOffsetAsExpected(VodEpisode firstVodEpisodeAfterOffsetExpected, VodEpisode firstVodEpisodeAfterOffsetActual) {
        assertThat(firstVodEpisodeAfterOffsetExpected.getId())
                .as("First expected vod episode after offset is %s but actual is %s",
                        firstVodEpisodeAfterOffsetExpected, firstVodEpisodeAfterOffsetActual)
                .isEqualTo(firstVodEpisodeAfterOffsetActual.getId());
    }

    @Step
    public void assertThatVodEpisodesLimitAsExpected(String vodEpisodesCountExpected,
                                                     long vodEpisodesCountActual) {
        assertThat(vodEpisodesCountActual)
                .as("Only %s vod episodes expected, but actually %s in response",
                        vodEpisodesCountExpected, vodEpisodesCountActual)
                .isEqualTo(parseLong(vodEpisodesCountExpected));
    }

    @Step
    public void assertThatLastModifiedHeaderPresent(String lastModifiedHeader) {
        assertThat(lastModifiedHeader)
                .as("Last-Modified header for vod category episode should be present and not empty but it is %s",
                        lastModifiedHeader)
                .isNotEmpty();
    }
}
