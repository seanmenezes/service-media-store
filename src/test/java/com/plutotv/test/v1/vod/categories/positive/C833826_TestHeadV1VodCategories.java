/*
 * (c) 2021 Pluto Inc.
 */

package com.plutotv.test.v1.vod.categories.positive;

import com.plutotv.common.layer.step.v1.vod.VodCategoriesSteps;
import com.plutotv.test.BaseTest;
import io.qameta.allure.Step;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class C833826_TestHeadV1VodCategories extends BaseTest {
    private final VodCategoriesSteps vodCategoriesSteps = new VodCategoriesSteps();

    @Test(groups = {"full"})
    public void testHeadV1VodCategories() {
        String lastModifiedHeader = vodCategoriesSteps.getV1VodCategoriesMostRecentlyUpdatedCategoryTimestamp();

        assertThatLastModifiedHeaderPresent(lastModifiedHeader);
    }

    @Step
    private void assertThatLastModifiedHeaderPresent(String lastModifiedHeader) {
        assertThat(lastModifiedHeader)
                .as("Last-Modified header for vod categories should be present and not empty but it is %s",
                        lastModifiedHeader)
                .isNotEmpty();
    }
}
