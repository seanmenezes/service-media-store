/*
 * (c) 2021 Pluto Inc.
 */

package com.plutotv.test.v1.vod.categories.positive;

import com.plutotv.common.helper.validation.V1VodCategoriesValidation;
import com.plutotv.common.layer.step.v1.vod.VodCategoriesSteps;
import com.plutotv.test.BaseTest;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static org.apache.http.HttpStatus.SC_OK;

public class C833829_TestV1VodCategoriesFullSyncWithOptionalLimit extends BaseTest {
    private final VodCategoriesSteps vodCategoriesSteps = new VodCategoriesSteps();
    private final V1VodCategoriesValidation v1VodCategoriesValidation = new V1VodCategoriesValidation();

    @Test(groups = {"full"})
    public void testV1VodCategoriesFullSyncWithOptionalLimit() {
        String vodCategoriesCountExpected = "1";
        Response v1VodCategoriesFullSyncWithLimit = vodCategoriesSteps.getV1VodCategoriesFullSyncWithLimit(
                vodCategoriesCountExpected,
                vodCategoriesSteps.getV1VodCategoriesMostRecentlyUpdatedCategoryTimestamp());

        v1VodCategoriesFullSyncWithLimit.then().statusCode(SC_OK);
        long vodCategoriesCountActual = vodCategoriesSteps.getVodCategoriesCount(v1VodCategoriesFullSyncWithLimit);
        v1VodCategoriesValidation.assertThatVodCategoriesLimitAsExpected(
                vodCategoriesCountExpected, vodCategoriesCountActual);
    }
}
