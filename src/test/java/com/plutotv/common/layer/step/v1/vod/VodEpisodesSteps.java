package com.plutotv.common.layer.step.v1.vod;

import com.plutotv.common.layer.endpoint.v1.vod.VodEndpointsV1;
import com.plutotv.common.model.bussines.v1.vod.episode.VodEpisode;
import com.plutotv.common.model.response.v1.vod.episodes.VodEpisodes;
import com.plutotv.common.model.support.v1.vod.episodes.VodEpisodesDatum;
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

public class VodEpisodesSteps {
    private final VodEndpointsV1 vodEndpointsV1 = new VodEndpointsV1();

    @Step
    public Response getV1VodEpisodesFullSync(String syncStop) {
        Map<String, String> requestParams = new HashMap<>();
        requestParams.put(REPLICATION_TYPE, "full-sync");
        requestParams.put(UPDATED_BEFORE, syncStop);
        return vodEndpointsV1.getVodEpisodes(requestParams);
    }

    @Step
    public Response getV1VodEpisodesFullSyncWithLimit(String limit, String syncStop) {
        Map<String, String> requestParams = new HashMap<>();
        requestParams.put(REPLICATION_TYPE, "full-sync");
        requestParams.put(LIMIT, limit);
        requestParams.put(UPDATED_BEFORE, syncStop);
        return vodEndpointsV1.getVodEpisodes(requestParams);
    }

    @Step
    public Response getV1VodEpisodesFullSyncWithOffset(String offset, String syncStop) {
        Map<String, String> requestParams = new HashMap<>();
        requestParams.put(REPLICATION_TYPE, "full-sync");
        requestParams.put(OFFSET, offset);
        requestParams.put(UPDATED_BEFORE, syncStop);
        return vodEndpointsV1.getVodEpisodes(requestParams);
    }

    @Step
    public Response getV1VodEpisodesDeltaUpdate(String syncStart, String syncStop) {
        Map<String, String> requestParams = new HashMap<>();
        requestParams.put(REPLICATION_TYPE, "delta-update");
        requestParams.put(UPDATED_AFTER, syncStart);
        requestParams.put(UPDATED_BEFORE, syncStop);
        return vodEndpointsV1.getVodEpisodes(requestParams);
    }

    @Step
    public String getV1VodEpisodesMostRecentlyUpdatedEpisodeTimestamp() {
        Response response = vodEndpointsV1.headVodEpisodes();
        response.then().statusCode(SC_NO_CONTENT);
        return response.getHeader(LAST_MODIFIED_HEADER);
    }

    @Step
    public VodEpisode getFirstVodEpisode(Response v1VodEpisodes, String replicationType) {
        return v1VodEpisodes.as(VodEpisodes.class).getData().stream()
                .filter(rating -> Objects.equals(replicationType, rating.getMeta().getReplicaAction()))
                .map(VodEpisodesDatum::getPayload)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        format("There are no vod episodes with replication type %s for /v1/vod/episodes", replicationType)));
    }

    @Step
    public List<VodEpisode> getFirstNVodEpisodes(int vodEpisodesNumber, Response v1VodEpisodes) {
        List<VodEpisode> vodEpisodeList = v1VodEpisodes.as(VodEpisodes.class).getData().stream()
                .filter(rating -> Objects.equals("upsert", rating.getMeta().getReplicaAction()))
                .map(VodEpisodesDatum::getPayload)
                .limit(vodEpisodesNumber)
                .collect(toList());
        assertThat(vodEpisodeList)
                .as("It is expected at least %s vod episodes, but actually there are %s episodes",
                        vodEpisodesNumber, vodEpisodeList.size())
                .hasSize(vodEpisodesNumber);
        return vodEpisodeList;
    }

    @Step
    public List<VodEpisode> getVodEpisodesWithUpsertReplicationType(Response v1VodEpisodes) {
        List<VodEpisode> vodEpisodes = v1VodEpisodes.as(VodEpisodes.class).getData().stream()
                .filter(vodCategoryEntry -> Objects.equals("upsert", vodCategoryEntry.getMeta().getReplicaAction()))
                .map(VodEpisodesDatum::getPayload)
                .collect(toList());
        assertThat(vodEpisodes)
                .as("it is expected that some vod episodes with replicationType: upsert found")
                .isNotEmpty();
        return vodEpisodes;
    }

    @Step
    public List<VodEpisode> getVodEpisodesThatShouldNotBeReturnedForDeltaUpdate(String syncStop,
                                                                                String syncStart,
                                                                                List<VodEpisode> vodEpisodes) {
        return vodEpisodes.stream()
                .filter(vodEpisode -> parse(vodEpisode.getUpdatedAt()).isBefore(parse(syncStart))
                        && parse(vodEpisode.getUpdatedAt()).equals(parse(syncStart))
                        && parse(vodEpisode.getUpdatedAt()).isAfter(parse(syncStop)))
                .collect(toList());
    }

    @Step
    public long getVodEpisodesCount(Response v1VodEpisodes) {
        return v1VodEpisodes.as(VodEpisodes.class).getData().stream()
                .map(VodEpisodesDatum::getPayload)
                .count();
    }

    @Step
    public List<VodEpisode> getVodEpisodesThatShouldNotBeReturnedForFullSync(String syncStop, List<VodEpisode> vodEpisodes) {
        return vodEpisodes.stream()
                .filter(vodEpisode -> parse(vodEpisode.getUpdatedAt()).isAfter(parse(syncStop)))
                .collect(toList());
    }
}
