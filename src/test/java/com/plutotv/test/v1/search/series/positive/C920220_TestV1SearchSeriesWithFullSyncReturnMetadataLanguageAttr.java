package com.plutotv.test.v1.search.series.positive;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.plutotv.common.layer.step.mongo.MongoSteps;
import com.plutotv.common.layer.step.v1.search.series.SearchSeriesSteps;
import com.plutotv.common.model.mongo.collections.series.MongoSeries;
import com.plutotv.common.model.mongo.collections.series.MongoSeriesDatum;
import com.plutotv.common.model.support.v1.search.series.SearchSeriesDatum;
import com.plutotv.test.BaseTest;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static com.plutotv.common.Constants.REPL_TYPE_UPSERT;
import static org.apache.http.HttpStatus.SC_OK;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class C920220_TestV1SearchSeriesWithFullSyncReturnMetadataLanguageAttr extends BaseTest {
    private final SearchSeriesSteps searchSeriesSteps = new SearchSeriesSteps();
    private final MongoSteps mongoSteps = new MongoSteps();

    @Test(groups = {"use_mongo"})
    public void testV1SearchSeriesWithFullSyncReturnMetadataLanguageAttr() throws JsonProcessingException {
        Response v1SearchSeriesFullSync = searchSeriesSteps.getV1SearchSeriesFullSync(
                searchSeriesSteps.getV1SearchSeriesMostRecentlyUpdatedSeriesTimestamp());
        v1SearchSeriesFullSync.then().statusCode(SC_OK);

        SearchSeriesDatum apiSeries = searchSeriesSteps.getFirstSeries(v1SearchSeriesFullSync, REPL_TYPE_UPSERT);

        MongoSeries mongoSeries = mongoSteps.findCollectionById(
                apiSeries.getMeta().getId(),
                "series",
                MongoSeries.class);

        MongoSeriesDatum mongoSeriesDatum = mongoSeries.getData().stream()
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Can not get series collection from mongo"));

        assertThat(apiSeries.getPayload().getMetadataLanguage())
                .as("metadataLanguage attr is absent or null in API response for series: %s", apiSeries.getMeta().getId())
                .isNotNull();

        assertThat(apiSeries.getPayload().getMetadataLanguage())
                .as("value of metadataLanguage from API is not the same as in mongo for series: %s", apiSeries.getMeta().getId())
                .isEqualTo(mongoSeriesDatum.getMetadataLanguage());
    }
}
