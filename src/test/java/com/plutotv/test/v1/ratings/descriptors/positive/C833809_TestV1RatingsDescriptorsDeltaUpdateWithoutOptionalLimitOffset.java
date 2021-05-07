/*
 * (c) 2021 Pluto Inc.
 */

package com.plutotv.test.v1.ratings.descriptors.positive;

import com.plutotv.common.helper.validation.V1RatingsDescriptorsValidation;
import com.plutotv.common.layer.step.v1.ratings.RatingsDescriptorsSteps;
import com.plutotv.test.BaseTest;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.time.Instant;

import static com.plutotv.common.Constants.REPL_TYPE_UPSERT;
import static java.time.temporal.ChronoUnit.HOURS;
import static org.apache.http.HttpStatus.SC_OK;

public class C833809_TestV1RatingsDescriptorsDeltaUpdateWithoutOptionalLimitOffset extends BaseTest {
    private final RatingsDescriptorsSteps ratingsDescriptorsSteps = new RatingsDescriptorsSteps();
    private final V1RatingsDescriptorsValidation v1RatingsDescriptorsValidation = new V1RatingsDescriptorsValidation();

    //ToDo when access to mongo is available, add assertion that records fetched for delta-update are from defined time range
    @Test(groups = {"full"})
    public void testV1RatingsDescriptorsDeltaUpdateWithoutOptionalLimitOffset() {
        String syncStop = ratingsDescriptorsSteps.getV1RatingsMostRecentlyUpdatedRatingDescriptorTimestamp();
        String syncStart = Instant.parse(syncStop).minus(24, HOURS).toString();
        Response ratingsDescriptorsDeltaUpdate = ratingsDescriptorsSteps.getV1RatingsDeltaUpdate(syncStart, syncStop);

        ratingsDescriptorsDeltaUpdate.then().statusCode(SC_OK);
        v1RatingsDescriptorsValidation.assertThatRatingsDescriptorsAttributesNotNullEmptyAndHaveExpectedFormat(
                ratingsDescriptorsSteps.getFirstRatingDescriptor(ratingsDescriptorsDeltaUpdate, REPL_TYPE_UPSERT));
    }
}
