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
import static java.lang.Integer.parseInt;
import static org.apache.http.HttpStatus.SC_OK;

public class C833871_TestV1VodCategoryEntriesFullSyncWithOptionalOffset extends BaseTest {
    private final VodCategoryEntriesSteps vodCategoryEntriesSteps = new VodCategoryEntriesSteps();
    private final V1VodCategoryEntriesValidation v1VodCategoryEntriesValidation = new V1VodCategoryEntriesValidation();

    @Test(groups = {"full"})
    public void testV1VodCategoryEntriesFullSyncWithOptionalOffset() {
        String offset = "1";
        String syncStop = vodCategoryEntriesSteps.getV1VodCategoryEntriesMostRecentlyUpdatedCategoryEntryTimestamp();
        Response v1VodCategoryEntriesFullSync = vodCategoryEntriesSteps.getV1VodCategoryEntriesFullSync(syncStop);

        v1VodCategoryEntriesFullSync.then().statusCode(SC_OK);

        List<VodCategoryEntry> firstNVodCategoryEntries =
                vodCategoryEntriesSteps.getFirstNVodCategoryEntries(
                        parseInt(offset) + 1, v1VodCategoryEntriesFullSync);
        VodCategoryEntry firstVodCategoryEntryAfterOffsetExpected = firstNVodCategoryEntries.get(parseInt(offset));

        Response v1VodCategoryEntriesFullSyncWithOffset =
                vodCategoryEntriesSteps.getV1VodCategoryEntriesFullSyncWithOffset(offset, syncStop);

        v1VodCategoryEntriesFullSyncWithOffset.then().statusCode(SC_OK);

        VodCategoryEntry firstVodCategoryAfterOffsetActual =
                vodCategoryEntriesSteps.getFirstVodCategoryEntry(v1VodCategoryEntriesFullSyncWithOffset, REPL_TYPE_UPSERT);
        v1VodCategoryEntriesValidation.assertThatVodCategoryEntriesAsExpected(
                firstVodCategoryEntryAfterOffsetExpected,
                firstVodCategoryAfterOffsetActual);
    }
}
