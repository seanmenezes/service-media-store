package com.plutotv.common.layer.step.v1.vod;

import com.plutotv.common.layer.endpoint.v1.vod.VodEndpointsV1;
import com.plutotv.common.model.bussines.v1.vod.series.VodSerie;
import com.plutotv.common.model.response.v1.vod.series.VodSeries;
import com.plutotv.common.model.support.v1.vod.series.VodSeriesDatum;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.plutotv.common.helper.HeaderHelper.LAST_MODIFIED_HEADER;
import static com.plutotv.common.helper.RequestParameterHelper.*;
import static java.lang.String.format;
import static java.time.Instant.parse;
import static java.util.stream.Collectors.toList;
import static org.apache.http.HttpStatus.SC_NO_CONTENT;
import static org.assertj.core.api.Assertions.assertThat;

public class VodSeriesSteps {
    private final VodEndpointsV1 vodEndpointsV1 = new VodEndpointsV1();

    @Step
    public Response getV1VodSeriesFullSync(String syncStop) {
        Map<String, String> requestParams = new HashMap<>();
        requestParams.put(REPLICATION_TYPE, "full-sync");
        requestParams.put(UPDATED_BEFORE, syncStop);
        return vodEndpointsV1.getVodSeries(requestParams);
    }

    @Step
    public Response getV1VodSeriesFullSyncWithLimit(String limit, String syncStop) {
        Map<String, String> requestParams = new HashMap<>();
        requestParams.put(REPLICATION_TYPE, "full-sync");
        requestParams.put(LIMIT, limit);
        requestParams.put(UPDATED_BEFORE, syncStop);
        return vodEndpointsV1.getVodSeries(requestParams);
    }

    @Step
    public Response getV1VodSeriesFullSyncWithOffset(String offset, String syncStop) {
        Map<String, String> requestParams = new HashMap<>();
        requestParams.put(REPLICATION_TYPE, "full-sync");
        requestParams.put(OFFSET, offset);
        requestParams.put(UPDATED_BEFORE, syncStop);
        return vodEndpointsV1.getVodSeries(requestParams);
    }

    @Step
    public Response getV1VodSeriesDeltaUpdate(String syncStart, String syncStop) {
        Map<String, String> requestParams = new HashMap<>();
        requestParams.put(REPLICATION_TYPE, "delta-update");
        requestParams.put(UPDATED_AFTER, syncStart);
        requestParams.put(UPDATED_BEFORE, syncStop);
        return vodEndpointsV1.getVodSeries(requestParams);
    }

    @Step
    public String getV1SeriesMostRecentlyUpdatedSeriesTimestamp() {
        Response response = vodEndpointsV1.headVodSeries();
        response.then().statusCode(SC_NO_CONTENT);
        return response.getHeader(LAST_MODIFIED_HEADER);
    }

    @Step
    public VodSerie getFirstVodSeries(Response v1VodSeries, String replicationType) {
        return v1VodSeries.as(VodSeries.class).getData().stream()
                .filter(series -> Objects.equals(replicationType, series.getMeta().getReplicaAction()))
                .map(VodSeriesDatum::getPayload)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        format("There are no vod series with replication type %s for /v1/vod/series", replicationType)));
    }

    @Step
    public List<VodSerie> getFirstNVodSeries(int vodSeriesNumber, Response v1VodSeries) {
        List<VodSerie> vodSeriesList = v1VodSeries.as(VodSeries.class).getData().stream()
                .filter(series -> Objects.equals("upsert", series.getMeta().getReplicaAction()))
                .map(VodSeriesDatum::getPayload)
                .limit(vodSeriesNumber)
                .collect(toList());
        assertThat(vodSeriesList)
                .as("It is expected at least %s vod series, but actually there are %s series",
                        vodSeriesNumber, vodSeriesList.size())
                .hasSize(vodSeriesNumber);
        return vodSeriesList;
    }

    @Step
    public List<VodSerie> getVodSeriesWithUpsertReplicationType(Response v1VodSeries) {
        List<VodSerie> vodSeries = v1VodSeries.as(VodSeries.class).getData().stream()
                .filter(vodCategoryEntry -> Objects.equals("upsert", vodCategoryEntry.getMeta().getReplicaAction()))
                .map(VodSeriesDatum::getPayload)
                .collect(toList());
        assertThat(vodSeries)
                .as("it is expected that some vod series with replicationType: upsert found")
                .isNotEmpty();
        return vodSeries;
    }

    @Step
    public List<VodSerie> getVodSeriesThatShouldNotBeReturnedForDeltaUpdate(String syncStop,
                                                                            String syncStart,
                                                                            List<VodSerie> vodSeries) {
        return vodSeries.stream()
                .filter(vodSerie -> parse(vodSerie.getUpdatedAt()).isBefore(parse(syncStart))
                        && parse(vodSerie.getUpdatedAt()).equals(parse(syncStart))
                        && parse(vodSerie.getUpdatedAt()).isAfter(parse(syncStop)))
                .collect(toList());
    }

    @Step
    public List<VodSerie> getVodSeriesThatShouldNotBeReturnedForFullSync(String syncStop, List<VodSerie> vodSeries) {
        return vodSeries.stream()
                .filter(vodSerie -> parse(vodSerie.getUpdatedAt()).isAfter(parse(syncStop)))
                .collect(toList());
    }

    @Step
    public long getVodSeriesCount(Response v1VodSeries) {
        return v1VodSeries.as(VodSeries.class).getData().stream()
                .map(VodSeriesDatum::getPayload)
                .count();
    }
}
