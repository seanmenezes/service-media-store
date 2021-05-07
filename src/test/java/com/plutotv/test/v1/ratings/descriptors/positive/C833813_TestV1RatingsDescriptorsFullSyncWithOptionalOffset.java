/*
 * (c) 2021 Pluto Inc.
 */

package com.plutotv.test.v1.ratings.descriptors.positive;

import com.plutotv.common.layer.step.v1.ratings.RatingsDescriptorsSteps;
import com.plutotv.common.model.bussines.v1.ratings.RatingDescriptor;
import com.plutotv.test.BaseTest;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.List;

import static com.plutotv.common.Constants.REPL_TYPE_UPSERT;
import static java.lang.Integer.parseInt;
import static org.apache.http.HttpStatus.SC_OK;

public class C833813_TestV1RatingsDescriptorsFullSyncWithOptionalOffset extends BaseTest {
    private final RatingsDescriptorsSteps ratingsDescriptorsSteps = new RatingsDescriptorsSteps();

    @Test(groups = {"full"})
    public void testV1RatingsDescriptorsFullSyncWithOptionalOffset() {
        String offset = "1";
        String syncStop = ratingsDescriptorsSteps.getV1RatingsMostRecentlyUpdatedRatingDescriptorTimestamp();
        Response v1RatingsDescriptorsFullSync = ratingsDescriptorsSteps.getV1RatingsDescriptorsFullSync(syncStop);

        v1RatingsDescriptorsFullSync.then().statusCode(SC_OK);

        List<RatingDescriptor> firstNRatingsDescriptors = ratingsDescriptorsSteps.getFirstNRatingsDescriptors(
                parseInt(offset) + 1, v1RatingsDescriptorsFullSync);
        RatingDescriptor firstRatingDescriptorAfterOffsetExpected = firstNRatingsDescriptors.get(parseInt(offset));

        Response v1RatingsDescriptorsFullSyncWithOffset =
                ratingsDescriptorsSteps.getV1RatingsDescriptorsFullSyncWithOffset(offset, syncStop);

        v1RatingsDescriptorsFullSyncWithOffset.then().statusCode(SC_OK);

        RatingDescriptor firstRatingDescriptorAfterOffsetActual =
                ratingsDescriptorsSteps.getFirstRatingDescriptor(v1RatingsDescriptorsFullSyncWithOffset, REPL_TYPE_UPSERT);
        ratingsDescriptorsSteps.assertThatV1RatingsDescriptorsOffsetAsExpected(
                firstRatingDescriptorAfterOffsetExpected, firstRatingDescriptorAfterOffsetActual);
    }
}
