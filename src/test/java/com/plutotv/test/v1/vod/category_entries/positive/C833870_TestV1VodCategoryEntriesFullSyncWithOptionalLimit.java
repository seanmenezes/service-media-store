/*
 * (c) 2021 Pluto Inc.
 */

package com.plutotv.test.v1.vod.category_entries.positive;

import com.plutotv.common.helper.validation.V1VodCategoryEntriesValidation;
import com.plutotv.common.layer.step.v1.vod.VodCategoryEntriesSteps;
import com.plutotv.test.BaseTest;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static org.apache.http.HttpStatus.SC_OK;

public class C833870_TestV1VodCategoryEntriesFullSyncWithOptionalLimit extends BaseTest {
    private final VodCategoryEntriesSteps vodCategoryEntriesSteps = new VodCategoryEntriesSteps();
    private final V1VodCategoryEntriesValidation v1VodCategoryEntriesValidation = new V1VodCategoryEntriesValidation();

    @Test(groups = {"full"})
    public void testV1VodCategoryEntriesFullSyncWithOptionalLimit() {
        String vodCategoriesCountExpected = "1";
        Response v1VodCategoryEntriesFullSyncWithLimit = vodCategoryEntriesSteps.getV1VodCategoryEntriesFullSyncWithLimit(
                vodCategoriesCountExpected,
                vodCategoryEntriesSteps.getV1VodCategoryEntriesMostRecentlyUpdatedCategoryEntryTimestamp());

        v1VodCategoryEntriesFullSyncWithLimit.then().statusCode(SC_OK);
        long vodCategoryEntriesCountActual = vodCategoryEntriesSteps.getVodCategoryEntriesCount(
                v1VodCategoryEntriesFullSyncWithLimit);
        v1VodCategoryEntriesValidation.assertThatVodCategoryEntriesLimitAsExpected(
                vodCategoriesCountExpected, vodCategoryEntriesCountActual);
    }
}
