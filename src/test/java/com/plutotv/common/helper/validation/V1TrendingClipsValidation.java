package com.plutotv.common.helper.validation;

import com.plutotv.common.model.bussines.v1.trending.clips.TrendingClip;
import com.plutotv.common.model.mongo.collections.clips.MongoClipDatum;
import com.plutotv.common.model.response.v1.trending.clips.TrendingClips;
import com.plutotv.common.model.support.v1.trending.clips.TrendingClipsDatum;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import java.util.List;

import static com.plutotv.common.Constants.RegEx.DATE_TIME;
import static com.plutotv.test.lib.api.coreapi.Constants.RegEx.ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

public class V1TrendingClipsValidation {
    @Step
    public void assertThatTrendingClipsAttributesNotNullEmptyAndHaveExpectedFormat(TrendingClip trendingClip) {
        assertSoftly(softly -> {
            softly.assertThat(trendingClip.getId())
                    .as("id attr of trendingClip should be present, not empty and match pattern, but it is %s",
                            trendingClip.getId())
                    .isNotEmpty()
                    .matches(ID);
            softly.assertThat(trendingClip.getName())
                    .as("name attr should be present, not empty, but it is %s for trendingClip %s",
                            trendingClip.getName(), trendingClip.getId())
                    .isNotEmpty();
            softly.assertThat(trendingClip.getAuthorName())
                    .as("authorName attr should be present, not empty, but it is %s for trendingClip %s",
                            trendingClip.getAuthorName(), trendingClip.getId())
                    .isNotEmpty();
            softly.assertThat(trendingClip.getUrl())
                    .as("url attr should be present, not empty, but it is %s for trendingClip %s",
                            trendingClip.getUrl(), trendingClip.getId())
                    .isNotEmpty()
                    .startsWith("http");
            softly.assertThat(trendingClip.getThumbnail())
                    .as("thumbnail attr should be present, not empty, but it is %s for trendingClip %s",
                            trendingClip.getThumbnail(), trendingClip.getId())
                    .isNotEmpty()
                    .startsWith("http");

            softly.assertThat(trendingClip.getDuration())
                    .as("duration attr should be present, has value more than zero, but it is %s for trendingClip %s",
                            trendingClip.getDuration(), trendingClip.getId())
                    .isNotNull()
                    .isGreaterThan(0);
            softly.assertThat(trendingClip.getCreatedAt())
                    .as("createdAt attr should be present, not empty and match pattern, but it is %s for trendingClip %s",
                            trendingClip.getCreatedAt(), trendingClip.getId())
                    .isNotEmpty()
                    .matches(DATE_TIME);
            softly.assertThat(trendingClip.getUpdatedAt())
                    .as("updatedAt attr should be present, not empty and match pattern, but it is %s for trendingClip %s",
                            trendingClip.getUpdatedAt(), trendingClip.getId())
                    .isNotEmpty()
                    .matches(DATE_TIME);
        });
    }

    @Step
    public void assertThatThereAreNoTrendingClipsThatNotFulfillWithSyncFilter(List<TrendingClip> trendingClips) {
        assertThat(trendingClips)
                .as("trendingClipsThatShouldNotBeReturned list should be empty, but it has trendingClips %s",
                        trendingClips)
                .isEmpty();
    }

    @Step
    public void assertThatLastModifiedHeaderPresent(String lastModifiedHeader) {
        assertThat(lastModifiedHeader)
                .as("Last-Modified header for trending clips should be present and not empty but it is %s",
                        lastModifiedHeader)
                .isNotEmpty();
    }

    @Step
    public void assertThatThereAreNoClipsWithNotExpectedDuration(Integer clipMaxDuration, List<TrendingClip> trendingClips) {
        assertThat(trendingClips)
                .as("There are trending clips with duration greater then expected %s",
                        trendingClips, clipMaxDuration)
                .hasSize(0);
    }

    @Step
    public void assertThatClipFromMongoFoundByTag(MongoClipDatum clipExpected, Response v1TrendingClips) {
        assertThat(v1TrendingClips.as(TrendingClips.class).getData().stream()
                .map(TrendingClipsDatum::getPayload)
                .anyMatch(trendingClip -> trendingClip.getId().equalsIgnoreCase(clipExpected.getId())))
                .as("It is expected clip %s is in response", clipExpected)
                .isTrue();
    }
}
