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
import static java.time.Instant.parse;
import static java.time.temporal.ChronoUnit.HOURS;
import static org.apache.http.HttpStatus.SC_OK;

public class C833867_TestV1VodCategoryEntriesDeltaUpdateWithoutOptionalLimitOffset extends BaseTest {
    private final VodCategoryEntriesSteps vodCategoryEntriesSteps = new VodCategoryEntriesSteps();
    private final V1VodCategoryEntriesValidation v1VodCategoryEntriesValidation = new V1VodCategoryEntriesValidation();

    @Test(groups = {"full"})
    public void testV1VodCategoryEntriesDeltaUpdateWithoutOptionalLimitOffset() {
        String syncStop = vodCategoryEntriesSteps.getV1VodCategoryEntriesMostRecentlyUpdatedCategoryEntryTimestamp();
        String syncStart = parse(syncStop).minus(24, HOURS).toString();
        Response v1VodCategoryEntriesDeltaUpdate = vodCategoryEntriesSteps.getV1VodCategoryEntriesDeltaUpdate(
                syncStart,
                syncStop);
        v1VodCategoryEntriesDeltaUpdate.then().statusCode(SC_OK);

        List<VodCategoryEntry> vodCategoryEntries =
                vodCategoryEntriesSteps.getVodCategoryEntriesWithUpsertReplicationType(v1VodCategoryEntriesDeltaUpdate);

        List<VodCategoryEntry> vodCategoryEntriesThatShouldNotBeReturned =
                vodCategoryEntriesSteps.getVodCategoryEntriesThatShouldNotBeReturnedForDeltaUpdate(
                        syncStop,
                        syncStart,
                        vodCategoryEntries);

        v1VodCategoryEntriesValidation.assertThatThereAreNoVodCategoryEntriesThatNotFulfillWithSyncFilter(
                vodCategoryEntriesThatShouldNotBeReturned);
        v1VodCategoryEntriesValidation.assertThatVodCategoryEntriesAttributesNotNullEmptyAndHaveExpectedFormat(
                vodCategoryEntriesSteps.getFirstVodCategoryEntry(v1VodCategoryEntriesDeltaUpdate, REPL_TYPE_UPSERT));
    }
}
