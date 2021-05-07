/*
 * (c) 2021 Pluto Inc.
 */

package com.plutotv.test.v1.ratings.descriptors.positive;

import com.plutotv.common.helper.validation.V1RatingsDescriptorsValidation;
import com.plutotv.common.layer.step.v1.ratings.RatingsDescriptorsSteps;
import com.plutotv.test.BaseTest;
import org.testng.annotations.Test;

public class C833808_TestHeadV1RatingsDescriptors extends BaseTest {
    private final RatingsDescriptorsSteps ratingsDescriptorsSteps = new RatingsDescriptorsSteps();
    private final V1RatingsDescriptorsValidation v1RatingsDescriptorsValidation = new V1RatingsDescriptorsValidation();

    @Test(groups = {"full"})
    public void testHeadV1RatingsDescriptors() {
        String lastModifiedHeader = ratingsDescriptorsSteps.getV1RatingsMostRecentlyUpdatedRatingDescriptorTimestamp();

        v1RatingsDescriptorsValidation.assertThatLastModifiedHeaderPresent(lastModifiedHeader);
    }
}
