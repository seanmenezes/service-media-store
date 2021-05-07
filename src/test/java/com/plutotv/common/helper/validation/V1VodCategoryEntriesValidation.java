package com.plutotv.common.helper.validation;

import com.plutotv.common.model.bussines.v1.vod.category_entries.VodCategoryEntry;
import io.qameta.allure.Step;

import java.util.List;

import static com.plutotv.common.Constants.RegEx.DATE_TIME;
import static com.plutotv.test.lib.api.coreapi.Constants.RegEx.ID;
import static java.lang.Long.parseLong;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

public class V1VodCategoryEntriesValidation {
    @Step
    public void assertThatVodCategoryEntriesAttributesNotNullEmptyAndHaveExpectedFormat(VodCategoryEntry vodCategoryEntry) {
        assertSoftly(softly -> {
            softly.assertThat(vodCategoryEntry.getId())
                    .as("id attr of vodCategoryEntry should be present, not empty and match pattern, but it is %s",
                            vodCategoryEntry.getId())
                    .isNotEmpty()
                    .matches(ID);
            softly.assertThat(vodCategoryEntry.getCategoryID())
                    .as("categoryID attr should be present, not empty and match pattern, but it is %s for vodCategoryEntry %s",
                            vodCategoryEntry.getCategoryID(), vodCategoryEntry.getId())
                    .isNotEmpty()
                    .matches(ID);
            softly.assertThat(vodCategoryEntry.getType())
                    .as("type attr should be present, not empty, but it is %s for vodCategoryEntry %s",
                            vodCategoryEntry.getType(), vodCategoryEntry.getId())
                    .isNotEmpty();

            //ToDo check if there are other types available except series, movie
            if ("movie".equalsIgnoreCase(vodCategoryEntry.getType())) {
                softly.assertThat(vodCategoryEntry.getEpisodeID())
                        .as("episodeID attr should be present, not empty and match pattern, but it is %s for vodCategoryEntry %s",
                                vodCategoryEntry.getEpisodeID(), vodCategoryEntry.getId())
                        .isNotNull()
                        .matches(ID);
            }

            if ("series".equalsIgnoreCase(vodCategoryEntry.getType())) {
                softly.assertThat(vodCategoryEntry.getSeriesID())
                        .as("seriesID attr should be present, not empty and match pattern, but it is %s for vodCategoryEntry %s",
                                vodCategoryEntry.getSeriesID(), vodCategoryEntry.getId())
                        .isNotNull()
                        .matches(ID);
            }

            softly.assertThat(vodCategoryEntry.getUpdatedAt())
                    .as("updatedAt attr should be present, not empty and match pattern, but it is %s for vodCategoryEntry %s",
                            vodCategoryEntry.getUpdatedAt(), vodCategoryEntry.getId())
                    .isNotEmpty()
                    .matches(DATE_TIME);
        });
    }

    @Step
    public void assertThatThereAreNoVodCategoryEntriesThatNotFulfillWithSyncFilter(List<VodCategoryEntry> vodCategoryEntries) {
        assertThat(vodCategoryEntries)
                .as("vodCategoryEntriesThatShouldNotBeReturned list should be empty, but it has vodCategoryEntries %s",
                        vodCategoryEntries)
                .isEmpty();
    }

    @Step
    public void assertThatVodCategoryEntriesAsExpected(VodCategoryEntry firstVodCategoryEntryAfterOffsetExpected,
                                                       VodCategoryEntry firstVodCategoryAfterOffsetActual) {
        assertThat(firstVodCategoryEntryAfterOffsetExpected.getId())
                .as("First expected vod category entry after offset is %s but actual is %s",
                        firstVodCategoryEntryAfterOffsetExpected, firstVodCategoryAfterOffsetActual)
                .isEqualTo(firstVodCategoryAfterOffsetActual.getId());
    }

    @Step
    public void assertThatVodCategoryEntriesLimitAsExpected(String vodCategoryEntriesCountExpected,
                                                            long vodCategoryEntriesCountActual) {
        assertThat(vodCategoryEntriesCountActual)
                .as("Only %s vod category entries expected, but actually %s in response",
                        vodCategoryEntriesCountExpected, vodCategoryEntriesCountActual)
                .isEqualTo(parseLong(vodCategoryEntriesCountExpected));
    }
}
