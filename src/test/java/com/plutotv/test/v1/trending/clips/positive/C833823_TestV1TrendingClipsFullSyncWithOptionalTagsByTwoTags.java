/*
 * (c) 2021 Pluto Inc.
 */

package com.plutotv.test.v1.trending.clips.positive;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.plutotv.common.helper.validation.V1TrendingClipsValidation;
import com.plutotv.common.layer.step.mongo.MongoSteps;
import com.plutotv.common.layer.step.v1.trending.clips.TrendingClipsSteps;
import com.plutotv.common.model.mongo.collections.clips.MongoClipDatum;
import com.plutotv.common.model.mongo.collections.clips.MongoClips;
import com.plutotv.test.BaseTest;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static java.lang.String.format;
import static org.apache.http.HttpStatus.SC_OK;

public class C833823_TestV1TrendingClipsFullSyncWithOptionalTagsByTwoTags extends BaseTest {
    private final TrendingClipsSteps trendingClipsSteps = new TrendingClipsSteps();
    private final MongoSteps mongoSteps = new MongoSteps();
    private final V1TrendingClipsValidation v1TrendingClipsValidation = new V1TrendingClipsValidation();

    @Test(groups = {"use_mongo"})
    public void testV1TrendingClipsFullSyncWithOptionalTagsByTwoTags() throws JsonProcessingException {
        MongoClips clipsFromMongo = mongoSteps.findCollectionByWhereCondition(
                "this.tags.length > 1", "clips", MongoClips.class);

        MongoClipDatum mongoClipDatumWithTwoTagsExpected = getClipWithTwoTags(clipsFromMongo);

        Response v1TrendingClipsFullSyncWithTags = trendingClipsSteps.getV1TrendingClipsFullSyncWithTags(
                trendingClipsSteps.getV1TrendingClipsMostRecentlyUpdatedRatingTimestamp(),
                format("%s,%s", mongoClipDatumWithTwoTagsExpected.getTags().get(0), mongoClipDatumWithTwoTagsExpected.getTags().get(1)));
        v1TrendingClipsFullSyncWithTags.then().statusCode(SC_OK);

        v1TrendingClipsValidation.assertThatClipFromMongoFoundByTag(mongoClipDatumWithTwoTagsExpected, v1TrendingClipsFullSyncWithTags);
    }

    @Step
    private MongoClipDatum getClipWithTwoTags(MongoClips clipsFromMongo) {
        return clipsFromMongo.getData().stream()
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("There are no clips with more than 1 tag in mongo"));
    }
}
