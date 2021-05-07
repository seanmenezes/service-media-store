/*
 * (c) 2021 Pluto Inc.
 */

package com.plutotv.test.v1.ratings.positive;

import com.plutotv.common.helper.validation.V1RatingsValidation;
import com.plutotv.common.layer.step.v1.ratings.RatingsSteps;
import com.plutotv.test.BaseTest;
import org.testng.annotations.Test;

public class C833800_TestHeadV1Ratings extends BaseTest {
    private final RatingsSteps ratingsSteps = new RatingsSteps();
    private final V1RatingsValidation v1RatingsValidation = new V1RatingsValidation();

    @Test(groups = {"full"})
    public void testHeadV1Ratings() {
        String lastModifiedHeader = ratingsSteps.getV1RatingsMostRecentlyUpdatedRatingTimestamp();

        v1RatingsValidation.assertThatLastModifiedHeaderPresent(lastModifiedHeader);
    }
}
