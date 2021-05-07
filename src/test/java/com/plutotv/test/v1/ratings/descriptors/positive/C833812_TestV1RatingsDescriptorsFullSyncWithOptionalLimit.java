/*
 * (c) 2021 Pluto Inc.
 */

package com.plutotv.test.v1.ratings.descriptors.positive;

import com.plutotv.common.layer.step.v1.ratings.RatingsDescriptorsSteps;
import com.plutotv.test.BaseTest;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static org.apache.http.HttpStatus.SC_OK;

public class C833812_TestV1RatingsDescriptorsFullSyncWithOptionalLimit extends BaseTest {
    private final RatingsDescriptorsSteps ratingsDescriptorsSteps = new RatingsDescriptorsSteps();

    @Test(groups = {"full"})
    public void testV1RatingsDescriptorsFullSyncWithOptionalLimit() {
        String ratingsDescriptorsCountExpected = "1";
        Response v1RatingsDescriptorsFullSyncWithLimit = ratingsDescriptorsSteps.getV1RatingsDescriptorsFullSyncWithLimit(
                ratingsDescriptorsCountExpected,
                ratingsDescriptorsSteps.getV1RatingsMostRecentlyUpdatedRatingDescriptorTimestamp());

        v1RatingsDescriptorsFullSyncWithLimit.then().statusCode(SC_OK);
        long ratingsDescriptorsCountActual =
                ratingsDescriptorsSteps.getRatingsDescriptorsCount(v1RatingsDescriptorsFullSyncWithLimit);
        ratingsDescriptorsSteps.assertThatRatingsDescriptorsLimitAsExpected(
                ratingsDescriptorsCountExpected, ratingsDescriptorsCountActual);
    }
}
