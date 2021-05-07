/*
 * (c) 2021 Pluto Inc.
 */

package com.plutotv.test.v1.ratings.positive;

import com.plutotv.common.layer.step.v1.ratings.RatingsSteps;
import com.plutotv.common.model.bussines.v1.ratings.Rating;
import com.plutotv.test.BaseTest;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.List;

import static com.plutotv.common.Constants.REPL_TYPE_UPSERT;
import static java.lang.Integer.parseInt;
import static org.apache.http.HttpStatus.SC_OK;

public class C833805_TestV1RatingsFullSyncWithOptionalOffset extends BaseTest {
    private final RatingsSteps ratingsSteps = new RatingsSteps();

    @Test(groups = {"full"})
    public void testV1RatingsFullSyncWithOptionalOffset() {
        String offset = "1";
        String syncStop = ratingsSteps.getV1RatingsMostRecentlyUpdatedRatingTimestamp();
        Response v1RatingsFullSync = ratingsSteps.getV1RatingsFullSync(syncStop);

        v1RatingsFullSync.then().statusCode(SC_OK);

        List<Rating> firstNRatingsFullSync = ratingsSteps.getFirstNRatings(
                parseInt(offset) + 1, v1RatingsFullSync);
        Rating firstRatingAfterOffsetExpected = firstNRatingsFullSync.get(parseInt(offset));

        Response v1RatingsFullSyncWithOffset = ratingsSteps.getV1RatingsFullSyncWithOffset(offset, syncStop);

        v1RatingsFullSyncWithOffset.then().statusCode(SC_OK);

        Rating firstRatingAfterOffsetActual = ratingsSteps.getFirstRating(v1RatingsFullSyncWithOffset, REPL_TYPE_UPSERT);
        ratingsSteps.assertThatV1RatingsOffsetAsExpected(firstRatingAfterOffsetExpected, firstRatingAfterOffsetActual);
    }
}
