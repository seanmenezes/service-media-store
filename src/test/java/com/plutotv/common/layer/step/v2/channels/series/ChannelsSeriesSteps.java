/*
 * (c) 2021 Pluto Inc.
 */

package com.plutotv.common.layer.step.v2.channels.series;

import com.plutotv.common.layer.endpoint.v2.channels.series.ChannelsSeriesEndpointsV2;
import com.plutotv.common.model.bussines.v2.channels.Serie;
import com.plutotv.common.model.response.v2.channels.Series;
import com.plutotv.common.model.support.v2.channels.series.SeriesDatum;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import org.joda.time.DateTime;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.plutotv.common.helper.HeaderHelper.LAST_MODIFIED_HEADER;
import static com.plutotv.common.helper.RequestParameterHelper.*;
import static java.lang.String.format;
import static java.lang.String.valueOf;
import static java.time.Instant.parse;
import static java.util.concurrent.TimeUnit.MINUTES;
import static java.util.concurrent.TimeUnit.SECONDS;
import static java.util.stream.Collectors.toList;
import static org.apache.http.HttpStatus.SC_NO_CONTENT;
import static org.assertj.jodatime.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

public class ChannelsSeriesSteps {
    private final ChannelsSeriesEndpointsV2 channelsSeriesEndpointsV2 = new ChannelsSeriesEndpointsV2();

    @Step
    public Response getV2ChannelsSeriesFullSync(String syncStop,
                                                Map<String, String> timelineStartStop) {
        Map<String, String> requestParams = new HashMap<>();
        requestParams.put(REPLICATION_TYPE, "full-sync");
        requestParams.putAll(timelineStartStop);
        requestParams.put(UPDATED_BEFORE, syncStop);
        return channelsSeriesEndpointsV2.getChannelsSeries(requestParams);
    }

    @Step
    public Response getV2ChannelsSeriesFullSyncWithLimit(String seriesCountExpected,
                                                         String syncStop,
                                                         Map<String, String> startStop) {
        Map<String, String> requestParams = new HashMap<>();
        requestParams.put(REPLICATION_TYPE, "full-sync");
        requestParams.putAll(startStop);
        requestParams.put(UPDATED_BEFORE, syncStop);
        requestParams.put(LIMIT, seriesCountExpected);
        return channelsSeriesEndpointsV2.getChannelsSeries(requestParams);
    }

    @Step
    public Response getV2ChannelsSeriesFullSyncWithOffset(String offset,
                                                          String syncStop,
                                                          Map<String, String> timelineStartStop) {
        Map<String, String> requestParams = new HashMap<>();
        requestParams.put(REPLICATION_TYPE, "full-sync");
        requestParams.putAll(timelineStartStop);
        requestParams.put(UPDATED_BEFORE, syncStop);
        requestParams.put(OFFSET, offset);
        return channelsSeriesEndpointsV2.getChannelsSeries(requestParams);
    }

    @Step
    public Response getV2ChannelsSeriesDeltaUpdate(String syncStart,
                                                   String syncStop,
                                                   Map<String, String> timelineStartStop,
                                                   String timelinePrevStop) {
        Map<String, String> requestParams = new HashMap<>();
        requestParams.put(REPLICATION_TYPE, "delta-update");
        requestParams.putAll(timelineStartStop);
        requestParams.put(PREV_STOP, valueOf(timelinePrevStop));
        requestParams.put(UPDATED_AFTER, syncStart);
        requestParams.put(UPDATED_BEFORE, syncStop);
        return channelsSeriesEndpointsV2.getChannelsSeries(requestParams);
    }

    @Step
    public Response getV2ChannelsSeriesDeltaUpdate(String syncStart,
                                                   String syncStop,
                                                   Map<String, String> timelineStartStop) {
        Map<String, String> requestParams = new HashMap<>();
        requestParams.put(REPLICATION_TYPE, "delta-update");
        requestParams.putAll(timelineStartStop);
        requestParams.put(UPDATED_AFTER, syncStart);
        requestParams.put(UPDATED_BEFORE, syncStop);
        return channelsSeriesEndpointsV2.getChannelsSeries(requestParams);
    }

    @Step
    public void waitUntilV2ChannelsSeriesUpdatedAfter(String timestamp,
                                                      int awaitAtMost,
                                                      int pollInterval) {
        await().pollInSameThread()
                .atMost(awaitAtMost, MINUTES)
                .pollInterval(pollInterval, SECONDS)
                .ignoreExceptions()
                .untilAsserted(() ->
                        assertThat(new DateTime(getV2ChannelsMostRecentlyUpdatedSeriesTimestamp()))
                                .isAfter(timestamp));
    }

    @Step
    public Serie getFirstSeries(Response v2ChannelsSeries, String replicationType) {
        return v2ChannelsSeries.as(Series.class).getData().stream()
                .filter(series -> Objects.equals(replicationType, series.getMeta().getReplicaAction()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        format("There are no series with replication type %s for v2/channels/series", replicationType)))
                .getPayload();
    }

    @Step
    public List<Serie> getFirstNSeries(int seriesNumber, Response v2ChannelsSeries) {
        List<Serie> seriesList = v2ChannelsSeries.as(Series.class).getData().stream()
                .filter(series -> Objects.equals("upsert", series.getMeta().getReplicaAction()))
                .map(SeriesDatum::getPayload)
                .limit(seriesNumber)
                .collect(toList());
        org.assertj.core.api.Assertions.assertThat(seriesList)
                .as("It is expected at least %s series, but actually there are %s series", seriesNumber, seriesList.size())
                .hasSize(seriesNumber);
        return seriesList;
    }

    @Step
    public String getV2ChannelsMostRecentlyUpdatedSeriesTimestamp() {
        Response response = channelsSeriesEndpointsV2.headChannelsSeries();
        response.then().statusCode(SC_NO_CONTENT);
        return response.getHeader(LAST_MODIFIED_HEADER);
    }

    @Step
    public List<Serie> getSeriesWithUpsertReplicationType(Response v2ChannelsSeriesDeltaUpdate) {
        List<Serie> series = v2ChannelsSeriesDeltaUpdate.as(Series.class).getData().stream()
                .filter(serie -> Objects.equals("upsert", serie.getMeta().getReplicaAction()))
                .map(SeriesDatum::getPayload)
                .collect(toList());
        Assertions.assertThat(series)
                .as("it is expected that some series with replicationType: upsert found")
                .isNotEmpty();
        return series;
    }

    @Step
    public List<Serie> getSeriesThatShouldNotBeReturnedForDeltaUpdateWithoutPrevStop(String syncStop,
                                                                                     String syncStart,
                                                                                     List<Serie> series) {
        return series.stream()
                .filter(serie -> parse(serie.getUpdatedAt()).isBefore(parse(syncStart))
                        && parse(serie.getUpdatedAt()).isAfter(parse(syncStop)))
                .collect(toList());
    }

    @Step
    public List<Serie> getSeriesThatShouldNotBeReturnedForDeltaUpdateWithPrevStop(String syncStart, List<Serie> series) {
        return series.stream()
                .filter(serie -> parse(serie.getUpdatedAt()).isAfter(parse(syncStart)))
                .collect(toList());
    }

    @Step
    public long getSeriesCount(Response v2ChannelsSeries) {
        return v2ChannelsSeries.as(Series.class).getData().stream()
                .map(SeriesDatum::getPayload)
                .count();
    }
}
