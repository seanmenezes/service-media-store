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
import static org.apache.http.HttpStatus.SC_OK;

public class C833831_TestV1VodCategoriesFullSyncWithoutOptionalUpdatedAfterLimitOffset extends BaseTest {
    private final VodCategoriesSteps vodCategoriesSteps = new VodCategoriesSteps();
    private final V1VodCategoriesValidation v1VodCategoriesValidation = new V1VodCategoriesValidation();

    @Test(groups = {"full"})
    public void testV1VodCategoriesFullSyncWithoutOptionalUpdatedAfterLimitOffset() {
        String syncStop = vodCategoriesSteps.getV1VodCategoriesMostRecentlyUpdatedCategoryTimestamp();
        Response v1VodCategoriesFullSync = vodCategoriesSteps.getV1VodCategoriesFullSync(syncStop);

        v1VodCategoriesFullSync.then().statusCode(SC_OK);

        List<VodCategory> vodCategoriesWithUpsertReplicationType =
                vodCategoriesSteps.getVodCategoriesWithUpsertReplicationType(v1VodCategoriesFullSync);

        List<VodCategory> vodCategoriesThatShouldNotBeReturned =
                vodCategoriesSteps.getVodCategoriesThatShouldNotBeReturnedForFullSync(
                        syncStop, vodCategoriesWithUpsertReplicationType);

        v1VodCategoriesValidation.assertThatThereAreNoVodCategoriesThatNotFulfillWithSyncFilter(
                vodCategoriesThatShouldNotBeReturned);
        v1VodCategoriesValidation.assertThatVodCategoriesAttributesNotNullEmptyAndHaveExpectedFormat(
                vodCategoriesSteps.getFirstVodCategory(v1VodCategoriesFullSync, REPL_TYPE_UPSERT));
    }
}
