/*
 * (c) 2021 Pluto Inc.
 */

package com.plutotv.test.v1.vod.category_entries.positive;

import com.plutotv.common.layer.step.v1.vod.VodCategoryEntriesSteps;
import com.plutotv.test.BaseTest;
import io.qameta.allure.Step;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class C833865_TestHeadV1VodCategoryEntries extends BaseTest {
    private final VodCategoryEntriesSteps vodCategoryEntriesSteps = new VodCategoryEntriesSteps();

    @Test(groups = {"full"})
    public void testHeadV1VodCategoryEntries() {
        String lastModifiedHeader = vodCategoryEntriesSteps.getV1VodCategoryEntriesMostRecentlyUpdatedCategoryEntryTimestamp();

        assertThatLastModifiedHeaderPresent(lastModifiedHeader);
    }

    @Step
    private void assertThatLastModifiedHeaderPresent(String lastModifiedHeader) {
        assertThat(lastModifiedHeader)
                .as("Last-Modified header for vod category entries should be present and not empty but it is %s",
                        lastModifiedHeader)
                .isNotEmpty();
    }
}
