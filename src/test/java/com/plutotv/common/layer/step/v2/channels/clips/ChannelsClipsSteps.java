/*
 * (c) 2021 Pluto Inc.
 */

package com.plutotv.common.layer.step.v2.channels.clips;

import com.plutotv.common.layer.endpoint.v2.channels.clips.ChannelsClipsEndpointsV2;
import com.plutotv.common.model.bussines.v2.channels.Clip;
import com.plutotv.common.model.response.v2.channels.Clips;
import com.plutotv.common.model.support.v2.channels.clips.ClipsDatum;
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

public class ChannelsClipsSteps {
    private final ChannelsClipsEndpointsV2 channelsClipsEndpointsV2 = new ChannelsClipsEndpointsV2();

    @Step
    public Response getV2ChannelsClipsFullSync(String syncStop, Map<String, String> timelineStartStop) {
        Map<String, String> requestParams = new HashMap<>();
        requestParams.put(REPLICATION_TYPE, "full-sync");
        requestParams.putAll(timelineStartStop);
        requestParams.put(UPDATED_BEFORE, syncStop);
        return channelsClipsEndpointsV2.getChannelsClips(requestParams);
    }

    @Step
    public Response getV2ChannelsClipsFullSyncWithLimit(String clipsCountExpected,
                                                        String syncStop,
                                                        Map<String, String> timelineStartStop) {
        Map<String, String> requestParams = new HashMap<>();
        requestParams.put(REPLICATION_TYPE, "full-sync");
        requestParams.putAll(timelineStartStop);
        requestParams.put(UPDATED_BEFORE, syncStop);
        requestParams.put(LIMIT, clipsCountExpected);
        return channelsClipsEndpointsV2.getChannelsClips(requestParams);
    }

    @Step
    public Response getV2ChannelsClipsFullSyncWithOffset(String offset,
                                                         String syncStop,
                                                         Map<String, String> timelineStartStop) {
        Map<String, String> requestParams = new HashMap<>();
        requestParams.put(REPLICATION_TYPE, "full-sync");
        requestParams.putAll(timelineStartStop);
        requestParams.put(UPDATED_BEFORE, syncStop);
        requestParams.put(OFFSET, offset);
        return channelsClipsEndpointsV2.getChannelsClips(requestParams);
    }

    @Step
    public Response getV2ChannelsClipsDeltaUpdate(String syncStart,
                                                  String syncStop,
                                                  Map<String, String> timelineStartStop,
                                                  String timelinePrevStop) {
        Map<String, String> requestParams = new HashMap<>();
        requestParams.put(REPLICATION_TYPE, "delta-update");
        requestParams.putAll(timelineStartStop);
        requestParams.put(PREV_STOP, valueOf(timelinePrevStop));
        requestParams.put(UPDATED_AFTER, syncStart);
        requestParams.put(UPDATED_BEFORE, syncStop);
        return channelsClipsEndpointsV2.getChannelsClips(requestParams);
    }

    @Step
    public Response getV2ChannelsClipsDeltaUpdate(String syncStart,
                                                  String syncStop,
                                                  Map<String, String> timelineStartStop) {
        Map<String, String> requestParams = new HashMap<>();
        requestParams.put(REPLICATION_TYPE, "delta-update");
        requestParams.putAll(timelineStartStop);
        requestParams.put(UPDATED_AFTER, syncStart);
        requestParams.put(UPDATED_BEFORE, syncStop);
        return channelsClipsEndpointsV2.getChannelsClips(requestParams);
    }

    @Step
    public void waitUntilV2ChannelsClipsUpdatedAfter(String timestamp, int awaitAtMost, int pollInterval) {
        await().pollInSameThread()
                .atMost(awaitAtMost, MINUTES)
                .pollInterval(pollInterval, SECONDS)
                .ignoreExceptions()
                .untilAsserted(() ->
                        assertThat(new DateTime(getV2ChannelsMostRecentlyUpdatedClipTimestamp()))
                                .isAfter(timestamp));
    }

    @Step
    public Clip getFirstClip(Response v2ChannelsClips, String replicationType) {
        return v2ChannelsClips.as(Clips.class).getData().stream()
                .filter(clip -> Objects.equals(replicationType, clip.getMeta().getReplicaAction()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        format("There are no clips with replication type %s for v2/channels/clips", replicationType)))
                .getPayload();
    }

    @Step
    public List<Clip> getFirstNClips(int clipsNumber, Response v2ChannelsClips) {
        List<Clip> clipList = v2ChannelsClips.as(Clips.class).getData().stream()
                .filter(clip -> Objects.equals("upsert", clip.getMeta().getReplicaAction()))
                .map(ClipsDatum::getPayload)
                .limit(clipsNumber)
                .collect(toList());
        org.assertj.core.api.Assertions.assertThat(clipList)
                .as("It is expected at least %s clips, but actually there are %s clips", clipsNumber, clipList.size())
                .hasSize(clipsNumber);
        return clipList;
    }

    @Step
    public String getV2ChannelsMostRecentlyUpdatedClipTimestamp() {
        Response response = channelsClipsEndpointsV2.headChannelsClips();
        response.then().statusCode(SC_NO_CONTENT);
        return response.getHeader(LAST_MODIFIED_HEADER);
    }

    @Step
    public long getClipsCount(Response v2ChannelsClips) {
        return v2ChannelsClips.as(Clips.class).getData().stream()
                .map(ClipsDatum::getPayload)
                .count();
    }

    @Step
    public List<Clip> getClipsWithUpsertReplicationType(Response v2ChannelsClips) {
        List<Clip> clips = v2ChannelsClips.as(Clips.class).getData().stream()
                .filter(clip -> Objects.equals("upsert", clip.getMeta().getReplicaAction()))
                .map(ClipsDatum::getPayload)
                .collect(toList());
        Assertions.assertThat(clips).as("it is expected that some clips with replicationType: upsert found").isNotEmpty();
        return clips;
    }

    @Step
    public List<Clip> getClipsThatShouldNotBeReturnedForDeltaUpdateWithoutPrevStop(String syncStop,
                                                                                   String syncStart,
                                                                                   List<Clip> clips) {
        return clips.stream()
                .filter(clip -> parse(clip.getUpdatedAt()).isBefore(parse(syncStart))
                        && parse(clip.getUpdatedAt()).isAfter(parse(syncStop)))
                .collect(toList());
    }

    @Step
    public List<Clip> getClipsThatShouldNotBeReturnedForDeltaUpdateWithPrevStop(String syncStart, List<Clip> clips) {
        return clips.stream()
                .filter(clip -> parse(clip.getUpdatedAt()).isAfter(parse(syncStart)))
                .collect(toList());
    }
}
