/*
 * (c) 2021 Pluto Inc.
 */

package com.plutotv.common.layer.step.v2.channels.episodes;

import com.plutotv.common.layer.endpoint.v2.channels.episodes.ChannelsEpisodesEndpointsV2;
import com.plutotv.common.model.bussines.v2.channels.Episode;
import com.plutotv.common.model.response.v2.channels.Episodes;
import com.plutotv.common.model.support.v2.channels.episodes.EpisodesDatum;
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

public class ChannelsEpisodesSteps {
    private final ChannelsEpisodesEndpointsV2 channelsEpisodesEndpointsV2 = new ChannelsEpisodesEndpointsV2();

    @Step
    public Response getV2ChannelsEpisodesFullSync(String syncStop, Map<String, String> timelineStartStop) {
        Map<String, String> requestParams = new HashMap<>();
        requestParams.put(REPLICATION_TYPE, "full-sync");
        requestParams.putAll(timelineStartStop);
        requestParams.put(UPDATED_BEFORE, syncStop);
        return channelsEpisodesEndpointsV2.getChannelsEpisodes(requestParams);
    }

    @Step
    public Response getV2ChannelsEpisodesFullSyncWithLimit(String episodesCountExpected,
                                                           String syncStop,
                                                           Map<String, String> timelineStartStop) {
        Map<String, String> requestParams = new HashMap<>();
        requestParams.put(REPLICATION_TYPE, "full-sync");
        requestParams.putAll(timelineStartStop);
        requestParams.put(UPDATED_BEFORE, syncStop);
        requestParams.put(LIMIT, episodesCountExpected);
        return channelsEpisodesEndpointsV2.getChannelsEpisodes(requestParams);
    }

    @Step
    public Response getV2ChannelsEpisodesFullSyncWithOffset(String offset,
                                                            String syncStop,
                                                            Map<String, String> timelineStartStop) {
        Map<String, String> requestParams = new HashMap<>();
        requestParams.put(REPLICATION_TYPE, "full-sync");
        requestParams.putAll(timelineStartStop);
        requestParams.put(UPDATED_BEFORE, syncStop);
        requestParams.put(OFFSET, offset);
        return channelsEpisodesEndpointsV2.getChannelsEpisodes(requestParams);
    }

    @Step
    public Response getV2ChannelsEpisodesDeltaUpdate(String syncStart,
                                                     String syncStop,
                                                     Map<String, String> timelineStartStop,
                                                     String timelinePrevStop) {
        Map<String, String> requestParams = new HashMap<>();
        requestParams.put(REPLICATION_TYPE, "delta-update");
        requestParams.putAll(timelineStartStop);
        requestParams.put(PREV_STOP, valueOf(timelinePrevStop));
        requestParams.put(UPDATED_AFTER, syncStart);
        requestParams.put(UPDATED_BEFORE, syncStop);
        return channelsEpisodesEndpointsV2.getChannelsEpisodes(requestParams);
    }

    @Step
    public Response getV2ChannelsEpisodesDeltaUpdate(String syncStart,
                                                     String syncStop,
                                                     Map<String, String> timelineStartStop) {
        Map<String, String> requestParams = new HashMap<>();
        requestParams.put(REPLICATION_TYPE, "delta-update");
        requestParams.putAll(timelineStartStop);
        requestParams.put(UPDATED_AFTER, syncStart);
        requestParams.put(UPDATED_BEFORE, syncStop);
        return channelsEpisodesEndpointsV2.getChannelsEpisodes(requestParams);
    }

    @Step
    public void waitUntilV2ChannelsEpisodesUpdatedAfter(String timestamp,
                                                        int awaitAtMost,
                                                        int pollInterval) {
        await().pollInSameThread()
                .atMost(awaitAtMost, MINUTES)
                .pollInterval(pollInterval, SECONDS)
                .ignoreExceptions()
                .untilAsserted(() ->
                        assertThat(new DateTime(getV2ChannelsMostRecentlyUpdatedEpisodeTimestamp()))
                                .isAfter(timestamp));
    }

    @Step
    public Episode getFirstEpisode(Response v2ChannelsEpisodes, String replicationType) {
        return v2ChannelsEpisodes.as(Episodes.class).getData().stream()
                .filter(episode -> Objects.equals(replicationType, episode.getMeta().getReplicaAction()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        format("There are no episodes with replication type %s for v2/channels/episodes", replicationType)))
                .getPayload();
    }

    @Step
    public List<Episode> getFirstNEpisodes(int episodesNumber, Response v2ChannelsEpisodes) {
        List<Episode> episodeList = v2ChannelsEpisodes.as(Episodes.class).getData().stream()
                .filter(episode -> Objects.equals("upsert", episode.getMeta().getReplicaAction()))
                .map(EpisodesDatum::getPayload)
                .limit(episodesNumber)
                .collect(toList());
        org.assertj.core.api.Assertions.assertThat(episodeList)
                .as("It is expected at least %s episodes, but actually there are %s episodes", episodesNumber, episodeList.size())
                .hasSize(episodesNumber);
        return episodeList;
    }

    @Step
    public String getV2ChannelsMostRecentlyUpdatedEpisodeTimestamp() {
        Response response = channelsEpisodesEndpointsV2.headChannelsEpisodes();
        response.then().statusCode(SC_NO_CONTENT);
        return response.getHeader(LAST_MODIFIED_HEADER);
    }

    @Step
    public List<Episode> getEpisodesWithUpsertReplicationType(Response v2ChannelsEpisodes) {
        List<Episode> episodes = v2ChannelsEpisodes.as(Episodes.class).getData().stream()
                .filter(timeline -> Objects.equals("upsert", timeline.getMeta().getReplicaAction()))
                .map(EpisodesDatum::getPayload)
                .collect(toList());
        Assertions.assertThat(episodes).as("it is expected that some episodes with replicationType: upsert found").isNotEmpty();
        return episodes;
    }

    @Step
    public List<Episode> getEpisodesThatShouldNotBeReturnedForDeltaUpdateWithoutPrevStop(String syncStop,
                                                                                         String syncStart,
                                                                                         List<Episode> episodes) {
        return episodes.stream()
                .filter(episode -> parse(episode.getUpdatedAt()).isBefore(parse(syncStart))
                        && parse(episode.getUpdatedAt()).isAfter(parse(syncStop)))
                .collect(toList());
    }

    @Step
    public List<Episode> getEpisodesThatShouldNotBeReturnedForDeltaUpdateWithPrevStop(String syncStart,
                                                                                      List<Episode> episodes) {
        return episodes.stream()
                .filter(episode -> parse(episode.getUpdatedAt()).isAfter(parse(syncStart)))
                .collect(toList());
    }

    @Step
    public long getEpisodesCount(Response v2ChannelsEpisodes) {
        return v2ChannelsEpisodes.as(Episodes.class).getData().stream()
                .map(EpisodesDatum::getPayload)
                .count();
    }
}
