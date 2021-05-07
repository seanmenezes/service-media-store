package com.plutotv.common.helper.validation;

import com.plutotv.common.model.bussines.v2.channels.Timeline;
import io.qameta.allure.Step;

import java.util.List;

import static com.plutotv.common.Constants.RegEx.DATE_TIME;
import static com.plutotv.test.lib.api.coreapi.Constants.RegEx.ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

public class V2ChannelsTimelinesValidation {
    @Step
    public void assertThatFirstLevelChannelsTimelineAttributesPresentNotEmptyAndHaveExpectedFormat(Timeline timeline) {
        assertSoftly(softly -> {
            softly.assertThat(timeline.getId())
                    .as("_id attr of timeline should be present, not empty and match pattern, but it is %s", timeline.getId())
                    .isNotEmpty()
                    .matches(ID);
            softly.assertThat(timeline.getChannel())
                    .as("channel attr should be present, not empty and match pattern, but it is %s, for timeline %s",
                            timeline.getChannel(), timeline.getId())
                    .isNotEmpty()
                    .matches(ID);
            softly.assertThat(timeline.getEpisodeId())
                    .as("episodeId attr should be present, not empty and match pattern, but it is %s, for timeline %s",
                            timeline.getEpisodeId(), timeline.getId())
                    .isNotEmpty()
                    .matches(ID);
            softly.assertThat(timeline.getStart())
                    .as("start attr should be present, not empty and match pattern, but it is %s, for timeline %s",
                            timeline.getStart(), timeline.getId())
                    .isNotEmpty()
                    .matches(DATE_TIME);
            softly.assertThat(timeline.getStop())
                    .as("stop attr should be present, not empty and match pattern, but it is %s, for timeline %s",
                            timeline.getStop(), timeline.getId())
                    .isNotEmpty()
                    .matches(DATE_TIME);
            softly.assertThat(timeline.getUpdatedAt())
                    .as("updatedAt attr should be present, not empty and match pattern, but it is %s, for timeline %s",
                            timeline.getUpdatedAt(), timeline.getId())
                    .isNotEmpty()
                    .matches(DATE_TIME);
        });
    }

    @Step
    public void assertThatThereAreNoTimelinesThatNotFulfillWithSyncFilter(List<Timeline> timelines) {
        assertThat(timelines)
                .as("timelines list should be empty, but it has timelines %s",
                        timelines)
                .isEmpty();
    }

    @Step
    public void assertThatTimelineOffsetAsExpected(Timeline firstTimelineAfterOffsetExpected, Timeline firstTimelineAfterOffsetActual) {
        assertThat(firstTimelineAfterOffsetExpected.getId())
                .as("First expected timeline after offset is %s but actual is %s",
                        firstTimelineAfterOffsetExpected, firstTimelineAfterOffsetActual)
                .isEqualTo(firstTimelineAfterOffsetActual.getId());
    }
}
