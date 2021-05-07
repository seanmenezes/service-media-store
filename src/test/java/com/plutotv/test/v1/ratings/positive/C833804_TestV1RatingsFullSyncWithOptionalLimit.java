/*
 * (c) 2021 Pluto Inc.
 */

package com.plutotv.test.v1.ratings.positive;

import com.plutotv.common.layer.step.v1.ratings.RatingsSteps;
import com.plutotv.test.BaseTest;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static org.apache.http.HttpStatus.SC_OK;

public class C833804_TestV1RatingsFullSyncWithOptionalLimit extends BaseTest {
    private final RatingsSteps ratingsSteps = new RatingsSteps();

    @Test(groups = {"full"})
    public void testV1RatingsFullSyncWithOptionalLimit() {
        String ratingsCountExpected = "1";
        Response v1RatingsFullSyncWithLimit = ratingsSteps.getV1RatingsFullSyncWithLimit(
                ratingsCountExpected,
                ratingsSteps.getV1RatingsMostRecentlyUpdatedRatingTimestamp());

        v1RatingsFullSyncWithLimit.then().statusCode(SC_OK);
        long ratingsCountActual = ratingsSteps.getRatingsCount(v1RatingsFullSyncWithLimit);
        ratingsSteps.assertThatRatingsLimitAsExpected(ratingsCountExpected, ratingsCountActual);
    }
}
