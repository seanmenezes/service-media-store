package com.plutotv.common.helper.validation;

import com.plutotv.common.model.bussines.v2.channels.Clip;
import io.qameta.allure.Step;

import java.util.List;

import static com.plutotv.common.Constants.RegEx.DATE_TIME;
import static com.plutotv.test.lib.api.coreapi.Constants.RegEx.ID;
import static java.util.Objects.nonNull;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

public class V2ChannelsClipsValidation {
    @Step
    public void assertThatClipAttributesNotNullEmptyAndHaveExpectedFormat(Clip clip) {
        assertSoftly(softly -> {
            softly.assertThat(clip.getId())
                    .as("_id attr of clip should be present, not empty and match pattern, but it is %s", clip.getId())
                    .isNotEmpty()
                    .matches(ID);
            softly.assertThat(clip.getName())
                    .as("name attr should be present, not empty, but it is %s for clip %s",
                            clip.getName(), clip.getId())
                    .isNotEmpty();
            softly.assertThat(clip.getPartner())
                    .as("partner attr should be present, not empty, but it is %s for clip %s",
                            clip.getPartner(), clip.getId())
                    .isNotEmpty();
            softly.assertThat(clip.getAuthor())
                    .as("author attr should be present, not empty, but it is %s for clip %s",
                            clip.getAuthor(), clip.getId())
                    .isNotEmpty();
            softly.assertThat(clip.getThumbnail())
                    .as("thumbnail attr should be present, not empty, but it is %s for clip %s",
                            clip.getThumbnail(), clip.getId())
                    .isNotEmpty();
            softly.assertThat(clip.getLiveBroadcast())
                    .as("liveBroadcast attr should be present and have value, but it is %s for clip %s",
                            clip.getLiveBroadcast(), clip.getId())
                    .isNotNull()
                    .isIn(true, false);
            softly.assertThat(clip.getProvider())
                    .as("provider attr should be present, not empty, but it is %s for clip %s",
                            clip.getProvider(), clip.getId())
                    .isNotEmpty();
            softly.assertThat(clip.getCode())
                    .as("code attr should be present, not empty, but it is %s for clip %s",
                            clip.getCode(), clip.getId())
                    .isNotEmpty();

            //internalCode optional
            if (nonNull(clip.getInternalCode())) {
                softly.assertThat(clip.getInternalCode())
                        .as("internalCode attr should be not empty, but it is %s for clip %s",
                                clip.getInternalCode(), clip.getId())
                        .isNotEmpty();
            }

            softly.assertThat(clip.getUrl())
                    .as("url attr should be present, not empty, but it is %s for clip %s",
                            clip.getUrl(), clip.getId())
                    .isNotEmpty();

            softly.assertThat(clip.getTags())
                    .as("tags attr should be present, not empty, but it is %s for clip %s",
                            clip.getTags(), clip.getId())
                    .isNotNull()
                    .hasSizeGreaterThan(0);
            if (nonNull(clip.getTags()) && clip.getTags().size() > 0) {
                clip.getTags().forEach(tag ->
                        softly.assertThat(tag)
                                .as("clip.tag attr should not be empty, but it is empty for clip %s",
                                        clip.getId())
                                .isNotEmpty()
                );
            }

            softly.assertThat(clip.getSources())
                    .as("sources attr should be present, not empty, but it is %s for clip %s",
                            clip.getSources(), clip.getId())
                    .isNotNull()
                    .hasSizeGreaterThan(0);
            if (nonNull(clip.getSources()) && clip.getSources().size() > 0) {
                clip.getSources().forEach(source -> {
                    softly.assertThat(source.getId())
                            .as("clip.sources._id attr should be present and not empty, but it is %s for clip %s",
                                    source.getId(), clip.getId())
                            .isNotEmpty();
                    softly.assertThat(source.getType())
                            .as("clip.sources.type attr should be present and not empty, but it is %s for clip %s",
                                    source.getId(), clip.getId())
                            .isNotEmpty();
                    softly.assertThat(source.getFile())
                            .as("clip.sources.file attr should be present and not empty, but it is %s for clip %s",
                                    source.getId(), clip.getId())
                            .isNotEmpty();
                });
            }

            softly.assertThat(clip.getUpdatedAt())
                    .as("updatedAt attr should be present, not empty and match pattern, but it is %s for clip %s",
                            clip.getUpdatedAt(), clip.getId())
                    .isNotEmpty()
                    .matches(DATE_TIME);
        });
    }

    @Step
    public void assertThatThereAreNoClipsThatNotFulfillWithSyncFilter(List<Clip> clipsThatShouldNotBeReturned) {
        assertThat(clipsThatShouldNotBeReturned)
                .as("clipsThatShouldNotBeReturned list should be empty, but it has clips %s",
                        clipsThatShouldNotBeReturned)
                .isEmpty();
    }
}
