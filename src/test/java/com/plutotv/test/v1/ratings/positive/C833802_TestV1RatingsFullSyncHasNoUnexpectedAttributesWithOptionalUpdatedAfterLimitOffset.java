/*
 * (c) 2021 Pluto Inc.
 */

package com.plutotv.test.v1.ratings.positive;

import com.plutotv.common.layer.step.v1.ratings.RatingsSteps;
import com.plutotv.common.model.response.v1.ratings.Ratings;
import com.plutotv.test.BaseTest;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static com.plutotv.common.mapper.CustomObjectMapper.getObjectMapperThatFailOnUnknownProperties;
import static org.apache.http.HttpStatus.SC_OK;

public class C833802_TestV1RatingsFullSyncHasNoUnexpectedAttributesWithOptionalUpdatedAfterLimitOffset extends BaseTest {

    private final RatingsSteps ratingsSteps = new RatingsSteps();

    @Test(groups = {"full"})
    public void testV1RatingsFullSyncHasNoUnexpectedAttributesWithOptionalUpdatedAfterLimitOffset() {
        Response v1RatingsFullSync =
                ratingsSteps.getV1RatingsFullSync(
                        ratingsSteps.getV1RatingsMostRecentlyUpdatedRatingTimestamp(),
                        getObjectMapperThatFailOnUnknownProperties());

        v1RatingsFullSync.then().statusCode(SC_OK);
        v1RatingsFullSync.as(Ratings.class);
    }
}
