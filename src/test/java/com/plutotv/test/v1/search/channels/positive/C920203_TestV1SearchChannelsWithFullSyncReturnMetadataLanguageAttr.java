package com.plutotv.test.v1.search.channels.positive;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.plutotv.common.layer.step.mongo.MongoSteps;
import com.plutotv.common.layer.step.v1.search.channels.SearchChannelsSteps;
import com.plutotv.common.model.mongo.collections.channels.MongoChannels;
import com.plutotv.common.model.mongo.collections.channels.MongoChannelsDatum;
import com.plutotv.common.model.support.v1.search.channels.SearchChannelDatum;
import com.plutotv.test.BaseTest;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static com.plutotv.common.Constants.REPL_TYPE_UPSERT;
import static org.apache.http.HttpStatus.SC_OK;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class C920203_TestV1SearchChannelsWithFullSyncReturnMetadataLanguageAttr extends BaseTest {
    private final SearchChannelsSteps searchChannelsSteps = new SearchChannelsSteps();
    private final MongoSteps mongoSteps = new MongoSteps();

    @Test(groups = {"use_mongo"})
    public void testV1SearchChannelsWithFullSyncReturnMetadataLanguageAttr() throws JsonProcessingException {
        Response v1SearchSeriesFullSync = searchChannelsSteps.getV1SearchChannelsFullSync(
                searchChannelsSteps.getV1SearchChannelsMostRecentlyUpdatedChannelTimestamp());
        v1SearchSeriesFullSync.then().statusCode(SC_OK);

        SearchChannelDatum apiChannels = searchChannelsSteps.getFirstChannel(v1SearchSeriesFullSync, REPL_TYPE_UPSERT);

        MongoChannels mongoChannels = mongoSteps.findCollectionById(
                apiChannels.getMeta().getId(),
                "channels",
                MongoChannels.class);

        MongoChannelsDatum mongoChannelsDatum = mongoChannels.getData().stream()
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Can not get channels collection from mongo"));

        assertThat(apiChannels.getPayload().getMetadataLanguage())
                .as("metadataLanguage attr is absent or null in API response for channel: %s", apiChannels.getMeta().getId())
                .isNotNull();

        assertThat(apiChannels.getPayload().getMetadataLanguage())
                .as("value of metadataLanguage from API is not the same as in mongo for channels: %s", apiChannels.getMeta().getId())
                .isEqualTo(mongoChannelsDatum.getMetadataLanguage());
    }
}
