/*
 * (c) 2021 Pluto Inc.
 */

package com.plutotv.test.v1.vod.categories.positive;

import com.plutotv.common.helper.validation.V1VodCategoriesValidation;
import com.plutotv.common.layer.step.v1.vod.VodCategoriesSteps;
import com.plutotv.common.model.bussines.v1.vod.category.VodCategory;
import com.plutotv.test.BaseTest;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.List;

import static com.plutotv.common.Constants.REPL_TYPE_UPSERT;
import static java.lang.Integer.parseInt;
import static org.apache.http.HttpStatus.SC_OK;

public class C833830_TestV1VodCategoriesFullSyncWithOptionalOffset extends BaseTest {
    private final VodCategoriesSteps vodCategoriesSteps = new VodCategoriesSteps();
    private final V1VodCategoriesValidation v1VodCategoriesValidation = new V1VodCategoriesValidation();

    @Test(groups = {"full"})
    public void testV1VodCategoriesFullSyncWithOptionalOffset() {
        String offset = "1";
        String syncStop = vodCategoriesSteps.getV1VodCategoriesMostRecentlyUpdatedCategoryTimestamp();
        Response v1VodCategoriesFullSync = vodCategoriesSteps.getV1VodCategoriesFullSync(syncStop);

        v1VodCategoriesFullSync.then().statusCode(SC_OK);

        List<VodCategory> firstNVodCategories = vodCategoriesSteps.getFirstNVodCategories(
                parseInt(offset) + 1, v1VodCategoriesFullSync);
        VodCategory firstVodCategoryAfterOffsetExpected = firstNVodCategories.get(parseInt(offset));

        Response v1VodCategoriesFullSyncWithOffset =
                vodCategoriesSteps.getV1VodCategoriesFullSyncWithOffset(offset, syncStop);

        v1VodCategoriesFullSyncWithOffset.then().statusCode(SC_OK);

        VodCategory firstVodCategoryAfterOffsetActual =
                vodCategoriesSteps.getFirstVodCategory(v1VodCategoriesFullSyncWithOffset, REPL_TYPE_UPSERT);
        v1VodCategoriesValidation.assertThatVodCategoryOffsetAsExpected(firstVodCategoryAfterOffsetExpected,
                firstVodCategoryAfterOffsetActual);
    }
}
