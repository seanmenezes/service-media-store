/*
 * (c) 2021 Pluto Inc.
 */

package com.plutotv.test.v1.ratings.descriptors.positive;

import com.plutotv.common.helper.validation.V1RatingsDescriptorsValidation;
import com.plutotv.common.layer.step.v1.ratings.RatingsDescriptorsSteps;
import com.plutotv.test.BaseTest;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static com.plutotv.common.Constants.REPL_TYPE_UPSERT;
import static org.apache.http.HttpStatus.SC_OK;

public class C833814_TestV1RatingsDescriptorsFullSyncWithoutOptionalUpdatedAfterLimitOffset extends BaseTest {
    private final RatingsDescriptorsSteps ratingsDescriptorsSteps = new RatingsDescriptorsSteps();
    private final V1RatingsDescriptorsValidation v1RatingsDescriptorsValidation = new V1RatingsDescriptorsValidation();

    //ToDo when access to mongo is available, add assertion that records fetched for delta-update are from defined time range
    @Test(groups = {"full"})
    public void testV1RatingsDescriptorsFullSyncWithoutOptionalUpdatedAfterLimitOffset() {
        Response v1RatingsDescriptorsFullSync = ratingsDescriptorsSteps.getV1RatingsDescriptorsFullSync(
                ratingsDescriptorsSteps.getV1RatingsMostRecentlyUpdatedRatingDescriptorTimestamp());

        v1RatingsDescriptorsFullSync.then().statusCode(SC_OK);
        v1RatingsDescriptorsValidation.assertThatRatingsDescriptorsAttributesNotNullEmptyAndHaveExpectedFormat(
                ratingsDescriptorsSteps.getFirstRatingDescriptor(v1RatingsDescriptorsFullSync, REPL_TYPE_UPSERT));
    }
}
