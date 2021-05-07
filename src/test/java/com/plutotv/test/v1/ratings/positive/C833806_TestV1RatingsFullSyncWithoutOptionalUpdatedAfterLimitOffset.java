/*
 * (c) 2021 Pluto Inc.
 */

package com.plutotv.test.v1.ratings.positive;

import com.plutotv.common.helper.validation.V1RatingsValidation;
import com.plutotv.common.layer.step.v1.ratings.RatingsSteps;
import com.plutotv.test.BaseTest;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static com.plutotv.common.Constants.REPL_TYPE_UPSERT;
import static org.apache.http.HttpStatus.SC_OK;

public class C833806_TestV1RatingsFullSyncWithoutOptionalUpdatedAfterLimitOffset extends BaseTest {
    private final RatingsSteps ratingsSteps = new RatingsSteps();
    private final V1RatingsValidation v1RatingsValidation = new V1RatingsValidation();

    //ToDo when access to mongo is available, add assertion that records fetched for delta-update are from defined time range
    @Test(groups = {"full"})
    public void testV1RatingsFullSyncWithOptionalUpdatedAfterLimitOffset() {
        Response v1RatingsFullSync = ratingsSteps.getV1RatingsFullSync(
                ratingsSteps.getV1RatingsMostRecentlyUpdatedRatingTimestamp());

        v1RatingsFullSync.then().statusCode(SC_OK);
        v1RatingsValidation.assertThatRatingsAttributesNotNullEmptyAndHaveExpectedFormat(
                ratingsSteps.getFirstRating(v1RatingsFullSync, REPL_TYPE_UPSERT));
    }
}
