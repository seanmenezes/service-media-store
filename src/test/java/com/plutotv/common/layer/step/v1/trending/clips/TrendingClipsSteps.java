package com.plutotv.common.layer.step.v1.trending.clips;

import com.plutotv.common.layer.endpoint.v1.trending.clips.TrendingClipsEndpointsV1;
import com.plutotv.common.model.bussines.v1.trending.clips.TrendingClip;
import com.plutotv.common.model.response.v1.trending.clips.TrendingClips;
import com.plutotv.common.model.support.v1.trending.clips.TrendingClipsDatum;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.plutotv.common.helper.HeaderHelper.LAST_MODIFIED_HEADER;
import static com.plutotv.common.helper.RequestParameterHelper.*;
import static java.lang.Long.parseLong;
import static java.lang.String.format;
import static java.time.Instant.parse;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toList;
import static org.apache.http.HttpStatus.SC_NO_CONTENT;
import static org.assertj.core.api.Assertions.assertThat;

public class TrendingClipsSteps {
    private final TrendingClipsEndpointsV1 trendingClipsEndpointsV1 = new TrendingClipsEndpointsV1();
    private final String LIMIT_VALUE = "10000";

    @Step
    public Response getV1TrendingClipsFullSync(String syncStop) {
        Map<String, String> requestParams = new HashMap<>();
        requestParams.put(REPLICATION_TYPE, "full-sync");
        requestParams.put(UPDATED_BEFORE, syncStop);
        requestParams.put(LIMIT, LIMIT_VALUE);
        return trendingClipsEndpointsV1.getTrendingClips(requestParams);
    }

    @Step
    public Response getV1TrendingClipsFullSyncWithTags(String syncStop, String tags) {
        Map<String, String> requestParams = new HashMap<>();
        requestParams.put(REPLICATION_TYPE, "full-sync");
        requestParams.put(UPDATED_BEFORE, syncStop);
        requestParams.put(LIMIT, LIMIT_VALUE);
        requestParams.put(TAGS, tags);
        return trendingClipsEndpointsV1.getTrendingClips(requestParams);
    }

    @Step
    public Response getV1TrendingClipsFullSyncWithMaxDuration(String syncStop, String maxDuration) {
        Map<String, String> requestParams = new HashMap<>();
        requestParams.put(REPLICATION_TYPE, "full-sync");
        requestParams.put(UPDATED_BEFORE, syncStop);
        requestParams.put(MAX_DURATION_SEC, maxDuration);
        requestParams.put(LIMIT, LIMIT_VALUE);
        return trendingClipsEndpointsV1.getTrendingClips(requestParams);
    }

    @Step
    public Response getV1TrendingClipsFullSyncWithLimit(String limit, String syncStop) {
        Map<String, String> requestParams = new HashMap<>();
        requestParams.put(REPLICATION_TYPE, "full-sync");
        requestParams.put(LIMIT, limit);
        requestParams.put(UPDATED_BEFORE, syncStop);
        return trendingClipsEndpointsV1.getTrendingClips(requestParams);
    }

    @Step
    public Response getV1TrendingClipsFullSyncWithOffset(String offset, String syncStop) {
        Map<String, String> requestParams = new HashMap<>();
        requestParams.put(REPLICATION_TYPE, "full-sync");
        requestParams.put(OFFSET, offset);
        requestParams.put(UPDATED_BEFORE, syncStop);
        return trendingClipsEndpointsV1.getTrendingClips(requestParams);
    }

    @Step
    public Response getV1TrendingClipsDeltaUpdate(String syncStart, String syncStop) {
        Map<String, String> requestParams = new HashMap<>();
        requestParams.put(REPLICATION_TYPE, "delta-update");
        requestParams.put(UPDATED_AFTER, syncStart);
        requestParams.put(UPDATED_BEFORE, syncStop);
        requestParams.put(LIMIT, LIMIT_VALUE);
        return trendingClipsEndpointsV1.getTrendingClips(requestParams);
    }

    @Step
    public String getV1TrendingClipsMostRecentlyUpdatedRatingTimestamp() {
        Response response = trendingClipsEndpointsV1.headTrendingClips();
        response.then().statusCode(SC_NO_CONTENT);
        return response.getHeader(LAST_MODIFIED_HEADER);
    }

    @Step
    public TrendingClip getFirstTrendingClip(Response v1TrendingClips, String replicationType) {
        return v1TrendingClips.as(TrendingClips.class).getData().stream()
                .filter(rating -> Objects.equals(replicationType, rating.getMeta().getReplicaAction()))
                .map(TrendingClipsDatum::getPayload)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        format("There are no trending clips with replication type %s for /v1/trending/clips", replicationType)));
    }

    @Step
    public List<TrendingClip> getFirstNTrendingClips(int trendingClipsNumber, Response v1TrendingClips) {
        List<TrendingClip> trendingClips = v1TrendingClips.as(TrendingClips.class).getData().stream()
                .filter(rating -> Objects.equals("upsert", rating.getMeta().getReplicaAction()))
                .map(TrendingClipsDatum::getPayload)
                .limit(trendingClipsNumber)
                .collect(toList());
        assertThat(trendingClips)
                .as("It is expected at least %s trending clips, but actually there are %s ratings", trendingClipsNumber, trendingClips.size())
                .hasSize(trendingClipsNumber);
        return trendingClips;
    }

    @Step
    public void assertThatTrendingClipsLimitAsExpected(String trendingClipsCountExpected, long trendingClipsCountActual) {
        assertThat(trendingClipsCountActual)
                .as("Only %s trending clips expected, but actually %s in response",
                        trendingClipsCountExpected, trendingClipsCountActual)
                .isEqualTo(parseLong(trendingClipsCountExpected));
    }

    @Step
    public long getTrendingClipsCount(Response v1TrendingClipsFullSyncWithLimit) {
        return v1TrendingClipsFullSyncWithLimit.as(TrendingClips.class).getData().stream()
                .map(TrendingClipsDatum::getPayload)
                .count();
    }

    @Step
    public List<TrendingClip> getTrendingClipsWithUpsertReplicationType(Response v1TrendingClips) {
        List<TrendingClip> trendingClips = v1TrendingClips.as(TrendingClips.class).getData().stream()
                .filter(trendingClip -> Objects.equals("upsert", trendingClip.getMeta().getReplicaAction()))
                .map(TrendingClipsDatum::getPayload)
                .collect(toList());
        assertThat(trendingClips).as("it is expected that some trendingClips with replicationType: upsert found").isNotEmpty();
        return trendingClips;
    }

    @Step
    public List<TrendingClip> getTrendingClipsThatShouldNotBeReturnedForDeltaUpdate(String syncStop,
                                                                                    String syncStart,
                                                                                    List<TrendingClip> trendingClips) {
        return trendingClips.stream()
                .filter(trendingClip -> parse(trendingClip.getUpdatedAt()).isBefore(parse(syncStart))
                        && parse(trendingClip.getUpdatedAt()).equals(parse(syncStart))
                        && parse(trendingClip.getUpdatedAt()).isAfter(parse(syncStop)))
                .collect(toList());
    }

    @Step
    public void assertThatTrendingClipsOffsetAsExpected(TrendingClip firstTrendingClipAfterOffsetExpected,
                                                        TrendingClip firstTrendingClipAfterOffsetActual) {
        assertThat(firstTrendingClipAfterOffsetExpected.getId())
                .as("First expected trending clip after offset is %s but actual is %s",
                        firstTrendingClipAfterOffsetExpected, firstTrendingClipAfterOffsetActual)
                .isEqualTo(firstTrendingClipAfterOffsetActual.getId());
    }

    @Step
    public List<TrendingClip> getTrendingClipsThatHaveNotExpectedDuration(Integer clipMaxDuration, List<TrendingClip> trendingClips) {
        return trendingClips.stream()
                .filter(trendingClip -> nonNull(trendingClip.getDuration()) && trendingClip.getDuration() > clipMaxDuration)
                .collect(toList());
    }
}
