package com.plutotv.test.v1.search.episodes.positive;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.plutotv.common.layer.step.mongo.MongoSteps;
import com.plutotv.common.layer.step.v1.search.episodes.SearchEpisodesSteps;
import com.plutotv.common.model.mongo.collections.episodes.MongoEpisodes;
import com.plutotv.common.model.mongo.collections.episodes.MongoEpisodesDatum;
import com.plutotv.common.model.support.v1.search.episodes.SearchEpisodeDatum;
import com.plutotv.test.BaseTest;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static com.plutotv.common.Constants.REPL_TYPE_UPSERT;
import static org.apache.http.HttpStatus.SC_OK;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class C920219_TestV1SearchEpisodesWithFullSyncReturnMetadataLanguageAttr extends BaseTest {
    private final SearchEpisodesSteps searchEpisodesSteps = new SearchEpisodesSteps();
    private final MongoSteps mongoSteps = new MongoSteps();

    @Test(groups = {"use_mongo"})
    public void testV1SearchEpisodesWithFullSyncReturnMetadataLanguageAttr() throws JsonProcessingException {
        Response v1SearchEpisodesFullSync = searchEpisodesSteps.getV1SearchEpisodesFullSync(
                searchEpisodesSteps.getV1SearchEpisodesMostRecentlyUpdatedEpisodeTimestamp());
        v1SearchEpisodesFullSync.then().statusCode(SC_OK);

        SearchEpisodeDatum apiEpisodes = searchEpisodesSteps.getFirstEpisode(v1SearchEpisodesFullSync, REPL_TYPE_UPSERT);

        MongoEpisodes mongoEpisodes = mongoSteps.findCollectionById(
                apiEpisodes.getMeta().getId(),
                "episodes",
                MongoEpisodes.class);

        MongoEpisodesDatum mongoEpisodesDatum = mongoEpisodes.getData().stream()
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Can not get episodes collection from mongo"));

        assertThat(apiEpisodes.getPayload().getMetadataLanguage())
                .as("metadataLanguage attr is absent or null in API response for episode: %s", apiEpisodes.getMeta().getId())
                .isNotNull();

        assertThat(apiEpisodes.getPayload().getMetadataLanguage())
                .as("value of metadataLanguage from API is not the same as in mongo for episode: %s", apiEpisodes.getMeta().getId())
                .isEqualTo(mongoEpisodesDatum.getMetadataLanguage());
    }
}
