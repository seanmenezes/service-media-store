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
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static org.apache.http.HttpStatus.SC_OK;

public class C833822_TestV1TrendingClipsFullSyncWithOptionalTagsByOneTag extends BaseTest {
    private final TrendingClipsSteps trendingClipsSteps = new TrendingClipsSteps();
    private final MongoSteps mongoSteps = new MongoSteps();
    private final V1TrendingClipsValidation v1TrendingClipsValidation = new V1TrendingClipsValidation();

    @Test(groups = {"use_mongo"})
    public void testV1TrendingClipsFullSyncWithOptionalTagsByOneTag() throws JsonProcessingException {
        MongoClips clipsFromMongo = mongoSteps.findCollectionByWhereCondition(
                "this.tags.length > 0", "clips", MongoClips.class);

        MongoClipDatum mongoClipDatumWithTagsExpected = getClipWithTags(clipsFromMongo);

        Response v1TrendingClipsFullSyncWithTags = trendingClipsSteps.getV1TrendingClipsFullSyncWithTags(
                trendingClipsSteps.getV1TrendingClipsMostRecentlyUpdatedRatingTimestamp(),
                mongoClipDatumWithTagsExpected.getTags().get(0));
        v1TrendingClipsFullSyncWithTags.then().statusCode(SC_OK);

        v1TrendingClipsValidation.assertThatClipFromMongoFoundByTag(mongoClipDatumWithTagsExpected, v1TrendingClipsFullSyncWithTags);
    }

    private MongoClipDatum getClipWithTags(MongoClips clipsFromMongo) {
        return clipsFromMongo.getData().stream()
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("There are no clips with tags in mongo"));
    }
}
