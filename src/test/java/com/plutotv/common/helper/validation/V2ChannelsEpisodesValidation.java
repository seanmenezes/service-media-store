package com.plutotv.common.helper.validation;

import com.plutotv.common.model.bussines.v2.channels.Episode;
import io.qameta.allure.Step;

import java.util.List;

import static com.plutotv.common.Constants.RegEx.DATE_TIME;
import static com.plutotv.test.lib.api.coreapi.Constants.RegEx.ID;
import static java.util.Objects.nonNull;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

public class V2ChannelsEpisodesValidation {
    @Step
    public void assertThatEpisodeAttributesNotNullEmptyHaveExpectedFormat(Episode episode) {
        assertSoftly(softly -> {
            softly.assertThat(episode.getId())
                    .as("_id attr of episode should be present, not empty and match pattern, but it is %s", episode.getId())
                    .isNotEmpty()
                    .matches(ID);
            softly.assertThat(episode.getNumber())
                    .as("number attr should be present and not zero, but it is %s for episode %s",
                            episode.getNumber(), episode.getId())
                    .isNotNull()
                    .isGreaterThan(0);
            softly.assertThat(episode.getDescription())
                    .as("description attr should be present, not empty, but it is %s for episode %s",
                            episode.getDescription(), episode.getId())
                    .isNotEmpty();
            softly.assertThat(episode.getDuration())
                    .as("duration attr should be number more than zero, but it is %s for episode %s",
                            episode.getDuration(), episode.getId())
                    .isNotNull()
                    .isGreaterThan(0);
            softly.assertThat(episode.getOriginalContentDuration())
                    .as("originalContentDuration should be number more than zero, but it is %s for episode %s",
                            episode.getOriginalContentDuration(), episode.getId())
                    .isNotNull()
                    .isGreaterThan(0);
            softly.assertThat(episode.getGenre())
                    .as("genre attr should be present, not empty, but it is %s for episode %s",
                            episode.getGenre(), episode.getId())
                    .isNotEmpty();
            softly.assertThat(episode.getSubGenre())
                    .as("subGenre attr should be present, not empty, but it is %s for episode %s",
                            episode.getSubGenre(), episode.getId())
                    .isNotEmpty();

            softly.assertThat(episode.getDistributeAs())
                    .as("distributeAs attr should be present, not empty, but it is %s for episode %s",
                            episode.getDistributeAs(), episode.getId())
                    .isNotNull();
            if (nonNull(episode.getDistributeAs())) {
                softly.assertThat(episode.getDistributeAs().getAVOD())
                        .as("distributeAs.aVOD attr should be present and have value, but it is %s for episode %s",
                                episode.getDistributeAs().getAVOD(), episode.getId())
                        .isNotNull()
                        .isIn(true, false);
            }

            softly.assertThat(episode.getRating())
                    .as("rating attr should be present, not empty, but it is %s for episode %s",
                            episode.getRating(), episode.getId())
                    .isNotEmpty();
            softly.assertThat(episode.getName())
                    .as("name attr should be present, not empty, but it is %s for episode %s",
                            episode.getName(), episode.getId())
                    .isNotEmpty();

            if (nonNull(episode.getSlug())) {
                softly.assertThat(episode.getSlug())
                        .as("slug attr should be present, but it is %s for episode %s",
                                episode.getSlug(), episode.getId())
                        .isNotNull();
            }

            softly.assertThat(episode.getPoster())
                    .as("poster attr should be present, not empty, but it is %s for episode %s",
                            episode.getPoster(), episode.getId())
                    .isNotNull();
            if (nonNull(episode.getPoster())) {
                softly.assertThat(episode.getPoster().getPath())
                        .as("poster.path attr should be present, not empty and match pattern, but it is %s for episode %s",
                                episode.getPoster().getPath(), episode.getId())
                        .isNotNull()
                        .startsWith("%PROTOCOL%://%HOSTNAME%");
            }

            softly.assertThat(episode.getFirstAired())
                    .as("firstAired attr should be present, not empty, but it is %s for episode %s",
                            episode.getFirstAired(), episode.getId())
                    .isNotEmpty();

            softly.assertThat(episode.getThumbnail())
                    .as("thumbnail attr should be present, not empty, but it is %s for episode %s",
                            episode.getThumbnail(), episode.getId())
                    .isNotNull();
            if (nonNull(episode.getThumbnail().getPath())) {
                softly.assertThat(episode.getPoster().getPath())
                        .as("thumbnail.path attr should be present, not empty and match pattern, but it is %s for episode %s",
                                episode.getThumbnail().getPath(), episode.getId())
                        .isNotNull()
                        .startsWith("%PROTOCOL%://%HOSTNAME%");
            }

            softly.assertThat(episode.getLiveBroadcast())
                    .as("liveBroadcast should be present and have value, but it is %s for episode %s",
                            episode.getLiveBroadcast(), episode.getId())
                    .isNotNull()
                    .isIn(true, false);

            softly.assertThat(episode.getFeaturedImage())
                    .as("featuredImage attr should be present, not empty, but it is %s for episode %s",
                            episode.getFeaturedImage(), episode.getId())
                    .isNotNull();
            if (nonNull(episode.getFeaturedImage().getPath())) {
                softly.assertThat(episode.getPoster().getPath())
                        .as("featuredImage.path attr should be present, not empty and match pattern, but it is %s for episode %s",
                                episode.getFeaturedImage().getPath(), episode.getId())
                        .isNotNull()
                        .startsWith("%PROTOCOL%://");
            }

            softly.assertThat(episode.getSources())
                    .as("sources attr should be present, not empty, but it is %s for episode %s",
                            episode.getSources(), episode.getId())
                    .isNotEmpty();
            if (nonNull(episode.getSources()) && episode.getSources().size() > 0) {
                episode.getSources().forEach(source -> {
                    softly.assertThat(source.getClipId())
                            .as("sources.clipId attr should be present, not empty and match pattern but it is %s for episode %s",
                                    source.getClipId(), episode.getId())
                            .isNotEmpty()
                            .matches(ID);
                    softly.assertThat(source.getDuration())
                            .as("sources.duration attr should be present and its value should be not zero, but it is %s for episode %s",
                                    source.getDuration(), episode.getId())
                            .isNotNull()
                            .isGreaterThan(0);
                    softly.assertThat(source.getInPoint())
                            .as("sources.inPoint attr should be present and it is positive number, but it is %s for episode %s",
                                    source.getInPoint(), episode.getId())
                            .isNotNull()
                            .isBetween(0, Integer.MAX_VALUE);
                    softly.assertThat(source.getOutPoint())
                            .as("sources.outPoint attr should be present and its value should be not zero, but it is %s for episode %s",
                                    source.getOutPoint(), episode.getId())
                            .isNotNull()
                            .isGreaterThan(0);

                    //episode.sources.adPods optional and might have zero ads if present
                    if (nonNull(source.getAdPods())) {
                        softly.assertThat(source.getAdPods())
                                .as("sources.adPods attr should be a collection, but it is %s for episode %s",
                                        source.getAdPods(), episode.getId())
                                .hasSizeGreaterThanOrEqualTo(0);
                    }
                    if (nonNull(source.getAdPods()) && source.getAdPods().size() > 0) {
                        source.getAdPods().forEach(adPod -> {
                            softly.assertThat(adPod.getStartAt())
                                    .as("sources.adPod.startAt attr should be present and its value should be not zero, but it is %s for episode %s with clip %s",
                                            adPod.getStartAt(), episode.getId(), source.getClipId())
                                    .isNotNull()
                                    .isBetween(0, Integer.MAX_VALUE);
                            softly.assertThat(adPod.getDuration())
                                    .as("sources.adPod.duration attr should be present and its value should be not zero, but it is %s for episode %s with clip %s",
                                            adPod.getDuration(), episode.getId(), source.getClipId())
                                    .isNotNull()
                                    .isGreaterThan(0);
                            softly.assertThat(adPod.getPodType())
                                    .as("sources.adPod.podType attr should be present and not empty, but it is %s for episode %s with clip %s",
                                            adPod.getPodType(), episode.getId(), source.getClipId())
                                    .isNotEmpty();
                            softly.assertThat(adPod.getPartnerProvided())
                                    .as("sources.adPod.partnerProvided attr should be present and has value, but it is %s for episode %s with clip %s",
                                            adPod.getPartnerProvided(), episode.getId(), source.getClipId())
                                    .isNotNull()
                                    .isIn(true, false);
                            softly.assertThat(adPod.getDurationLocked())
                                    .as("sources.adPod.durationLocked attr should be present and has value, but it is %s for episode %s with clip %s",
                                            adPod.getDurationLocked(), episode.getId(), source.getClipId())
                                    .isNotNull()
                                    .isIn(true, false);
                            softly.assertThat(adPod.getVerified())
                                    .as("sources.adPod.verified attr should be present and has value, but it is %s for episode %s with clip %s",
                                            adPod.getVerified(), episode.getId(), source.getClipId())
                                    .isNotNull()
                                    .isIn(true, false);
                        });
                    }
                });
            }

            softly.assertThat(episode.getSeriesId())
                    .as("seriesId attr should be present, not empty and match pattern, but it is %s for episode %s",
                            episode.getSeriesId(), episode.getId())
                    .isNotEmpty()
                    .matches(ID);
            softly.assertThat(episode.getDisplayOnGuide())
                    .as("displayOnGuide attr should be present, not empty and have specific value, but it is %s for episode %s",
                            episode.getDisplayOnGuide(), episode.getId())
                    .isNotEmpty()
                    .isIn("mixed", "series", "episode");
            softly.assertThat(episode.getUpdatedAt())
                    .as("updatedAt attr should be present, not empty and match pattern, but it is %s for episode %s",
                            episode.getUpdatedAt(), episode.getId())
                    .isNotEmpty()
                    .matches(DATE_TIME);
        });
    }

    @Step
    public void assertThatThereAreNoEpisodesThatNotFulfillWithSyncFilter(List<Episode> episodesThatShouldNotBeReturned) {
        assertThat(episodesThatShouldNotBeReturned)
                .as("episodesThatShouldNotBeReturned list should be empty, but it has episodes %s",
                        episodesThatShouldNotBeReturned)
                .isEmpty();
    }
}
