/*
 * (c) 2021 Pluto Inc.
 */

package com.plutotv.test.v1.ratings.descriptors.positive;

import com.plutotv.common.layer.step.v1.ratings.RatingsDescriptorsSteps;
import com.plutotv.common.model.response.v1.ratings.descriptors.RatingsDescriptors;
import com.plutotv.test.BaseTest;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static com.plutotv.common.mapper.CustomObjectMapper.getObjectMapperThatFailOnUnknownProperties;
import static org.apache.http.HttpStatus.SC_OK;

public class C833810_TestV1RatingsDescriptorsFullSyncHasNoUnexpectedAttributesWithOptionalUpdatedAfterLimitOffset extends BaseTest {
    private final RatingsDescriptorsSteps ratingsDescriptorsSteps = new RatingsDescriptorsSteps();

    @Test(groups = {"full"})
    public void testV1RatingsDescriptorsFullSyncHasNoUnexpectedAttributesWithOptionalUpdatedAfterLimitOffset() {
        Response v1RatingsFullSync =
                ratingsDescriptorsSteps.getV1RatingsDescriptorsFullSync(
                        ratingsDescriptorsSteps.getV1RatingsMostRecentlyUpdatedRatingDescriptorTimestamp(),
                        getObjectMapperThatFailOnUnknownProperties());

        v1RatingsFullSync.then().statusCode(SC_OK);
        v1RatingsFullSync.as(RatingsDescriptors.class);
    }
}
