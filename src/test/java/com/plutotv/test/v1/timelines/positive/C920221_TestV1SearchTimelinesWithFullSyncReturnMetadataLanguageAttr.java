package com.plutotv.test.v1.timelines.positive;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.plutotv.common.layer.step.mongo.MongoSteps;
import com.plutotv.common.layer.step.v1.search.timelines.SearchTimelinesSteps;
import com.plutotv.common.model.mongo.collections.episodes.MongoEpisodes;
import com.plutotv.common.model.mongo.collections.episodes.MongoEpisodesDatum;
import com.plutotv.common.model.mongo.collections.timelines.MongoTimelines;
import com.plutotv.common.model.mongo.collections.timelines.MongoTimelinesDatum;
import com.plutotv.common.model.support.v1.search.timelines.SearchTimelinesDatum;
import com.plutotv.test.BaseTest;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static com.plutotv.common.Constants.REPL_TYPE_UPSERT;
import static com.plutotv.common.helper.RequestParameterHelper.getStartAndStopParams;
import static org.apache.http.HttpStatus.SC_OK;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class C920221_TestV1SearchTimelinesWithFullSyncReturnMetadataLanguageAttr extends BaseTest {
    private final SearchTimelinesSteps searchTimelinesSteps = new SearchTimelinesSteps();
    private final MongoSteps mongoSteps = new MongoSteps();

    @Test(groups = {"use_mongo"})
    public void testV1SearchTimelinesWithFullSyncReturnMetadataLanguageAttr() throws JsonProcessingException {
        Response v1SearchTimelinesFullSync = searchTimelinesSteps.getV1SearchTimelinesFullSync(
                searchTimelinesSteps.getV1SearchTimelinesMostRecentlyUpdatedTimelineTimestamp(),
                getStartAndStopParams(TIMELINE_RANGE_IN_HOURS));
        v1SearchTimelinesFullSync.then().statusCode(SC_OK);

        SearchTimelinesDatum apiTimelines = searchTimelinesSteps.getFirstTimeline(v1SearchTimelinesFullSync, REPL_TYPE_UPSERT);

        assertThat(apiTimelines.getPayload().getMetadataLanguage())
                .as("metadataLanguage attr is absent or null in API response for timeline: %s", apiTimelines.getMeta().getId())
                .isNotNull();

        MongoTimelines mongoTimelines = mongoSteps.findCollectionById(
                apiTimelines.getMeta().getId(),
                "timelines",
                MongoTimelines.class);

        MongoTimelinesDatum mongoTimelinesDatum = mongoTimelines.getData().stream()
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Can not get timelines collection from mongo"));

        MongoEpisodes mongoEpisodes = mongoSteps.findCollectionById(
                apiTimelines.getPayload().getEpisodeID(),
                "episodes",
                MongoEpisodes.class);

        MongoEpisodesDatum mongoEpisodesDatum = mongoEpisodes.getData().stream()
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Can not get episodes collection from mongo"));

        assertThat(apiTimelines.getPayload().getEpisodeID())
                .as("episode in timelines from API is not the same as in mongo for timeline: %s", apiTimelines.getMeta().getId())
                .isEqualTo(mongoTimelinesDatum.getEpisode());

        assertThat(apiTimelines.getPayload().getMetadataLanguage())
                .as("value of metadataLanguage from timelines API is not the same as in mongo for episode: %s", mongoEpisodesDatum.getId())
                .isEqualTo(mongoEpisodesDatum.getMetadataLanguage());
    }
}
