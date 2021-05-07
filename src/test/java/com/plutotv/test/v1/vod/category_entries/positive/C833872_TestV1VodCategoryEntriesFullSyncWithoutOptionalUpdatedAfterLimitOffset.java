/*
 * (c) 2021 Pluto Inc.
 */

package com.plutotv.test.v1.vod.category_entries.positive;

import com.plutotv.common.helper.validation.V1VodCategoryEntriesValidation;
import com.plutotv.common.layer.step.v1.vod.VodCategoryEntriesSteps;
import com.plutotv.common.model.bussines.v1.vod.category_entries.VodCategoryEntry;
import com.plutotv.test.BaseTest;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.List;

import static com.plutotv.common.Constants.REPL_TYPE_UPSERT;
import static org.apache.http.HttpStatus.SC_OK;

public class C833872_TestV1VodCategoryEntriesFullSyncWithoutOptionalUpdatedAfterLimitOffset extends BaseTest {
    private final VodCategoryEntriesSteps vodCategoryEntriesSteps = new VodCategoryEntriesSteps();
    private final V1VodCategoryEntriesValidation v1VodCategoryEntriesValidation = new V1VodCategoryEntriesValidation();

    @Test(groups = {"full"})
    public void testV1VodCategoryEntriesFullSyncWithoutOptionalUpdatedAfterLimitOffset() {
        String syncStop = vodCategoryEntriesSteps.getV1VodCategoryEntriesMostRecentlyUpdatedCategoryEntryTimestamp();
        Response v1VodCategoryEntriesFullSync = vodCategoryEntriesSteps.getV1VodCategoryEntriesFullSync(syncStop);

        v1VodCategoryEntriesFullSync.then().statusCode(SC_OK);

        List<VodCategoryEntry> vodCategoryEntriesWithUpsertReplicationType =
                vodCategoryEntriesSteps.getVodCategoryEntriesWithUpsertReplicationType(v1VodCategoryEntriesFullSync);

        List<VodCategoryEntry> vodCategoryEntriesThatShouldNotBeReturned =
                vodCategoryEntriesSteps.getVodCategoryEntriesThatShouldNotBeReturnedForFullSync(
                        syncStop, vodCategoryEntriesWithUpsertReplicationType);

        v1VodCategoryEntriesValidation.assertThatThereAreNoVodCategoryEntriesThatNotFulfillWithSyncFilter(
                vodCategoryEntriesThatShouldNotBeReturned);
        v1VodCategoryEntriesValidation.assertThatVodCategoryEntriesAttributesNotNullEmptyAndHaveExpectedFormat(
                vodCategoryEntriesSteps.getFirstVodCategoryEntry(v1VodCategoryEntriesFullSync, REPL_TYPE_UPSERT));
    }
}
