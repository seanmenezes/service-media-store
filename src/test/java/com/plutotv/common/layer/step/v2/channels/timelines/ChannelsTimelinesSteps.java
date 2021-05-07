/*
 * (c) 2021 Pluto Inc.
 */

package com.plutotv.common.layer.step.v2.channels.timelines;

import com.plutotv.common.layer.endpoint.v2.channels.timelines.ChannelsTimelinesEndpointsV2;
import com.plutotv.common.model.bussines.v2.channels.Timeline;
import com.plutotv.common.model.response.v2.channels.Timelines;
import com.plutotv.common.model.support.v2.channels.timelines.TimelinesDatum;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import org.joda.time.DateTime;

import java.time.Instant;
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

public class ChannelsTimelinesSteps {
    private final ChannelsTimelinesEndpointsV2 channelsTimelinesEndpointsV2 = new ChannelsTimelinesEndpointsV2();

    @Step
    public Response getV2ChannelsTimelinesFullSync(String syncStop,
                                                   Map<String, String> timelineStartStop) {
        Map<String, String> requestParams = new HashMap<>();
        requestParams.put(REPLICATION_TYPE, "full-sync");
        requestParams.putAll(timelineStartStop);
        requestParams.put(UPDATED_BEFORE, syncStop);
        return channelsTimelinesEndpointsV2.getChannelsTimelines(requestParams);
    }

    @Step
    public Response getV2ChannelsTimelinesFullSyncWithLimit(String timelinesCountExpected,
                                                            String syncStop,
                                                            Map<String, String> timelineStartStop) {
        Map<String, String> requestParams = new HashMap<>();
        requestParams.put(REPLICATION_TYPE, "full-sync");
        requestParams.putAll(timelineStartStop);
        requestParams.put(UPDATED_BEFORE, syncStop);
        requestParams.put(LIMIT, timelinesCountExpected);
        return channelsTimelinesEndpointsV2.getChannelsTimelines(requestParams);
    }

    @Step
    public Response getV2ChannelsTimelinesFullSyncWithOffset(String offset,
                                                             String syncStop,
                                                             Map<String, String> timelineStartStop) {
        Map<String, String> requestParams = new HashMap<>();
        requestParams.put(REPLICATION_TYPE, "full-sync");
        requestParams.putAll(timelineStartStop);
        requestParams.put(UPDATED_BEFORE, syncStop);
        requestParams.put(OFFSET, offset);
        return channelsTimelinesEndpointsV2.getChannelsTimelines(requestParams);
    }

    @Step
    public Response getV2ChannelsTimelinesDeltaUpdate(String syncStart,
                                                      String syncStop,
                                                      Map<String, String> timelineStartStop,
                                                      String timelinePrevStop) {
        Map<String, String> requestParams = new HashMap<>();
        requestParams.put(REPLICATION_TYPE, "delta-update");
        requestParams.putAll(timelineStartStop);
        requestParams.put(PREV_STOP, valueOf(timelinePrevStop));
        requestParams.put(UPDATED_AFTER, syncStart);
        requestParams.put(UPDATED_BEFORE, syncStop);
        return channelsTimelinesEndpointsV2.getChannelsTimelines(requestParams);
    }

    @Step
    public Response getV2ChannelsTimelinesDeltaUpdate(String syncStart,
                                                      String syncStop,
                                                      Map<String, String> timelineStartStop) {
        Map<String, String> requestParams = new HashMap<>();
        requestParams.put(REPLICATION_TYPE, "delta-update");
        requestParams.putAll(timelineStartStop);
        requestParams.put(UPDATED_AFTER, syncStart);
        requestParams.put(UPDATED_BEFORE, syncStop);
        return channelsTimelinesEndpointsV2.getChannelsTimelines(requestParams);
    }

    @Step
    public void waitUntilV2ChannelsTimelinesUpdatedAfter(String timestamp, int awaitAtMost, int pollInterval) {
        await().pollInSameThread()
                .atMost(awaitAtMost, MINUTES)
                .pollInterval(pollInterval, SECONDS)
                .ignoreExceptions()
                .untilAsserted(() ->
                        assertThat(new DateTime(getV2ChannelsMostRecentlyUpdatedTimelineTimestamp())).isAfter(timestamp));
    }

    @Step
    public Timeline getFirstTimeline(Response v2ChannelsTimelines, String replicationType) {
        return v2ChannelsTimelines.as(Timelines.class).getData().stream()
                .filter(timeline -> Objects.equals(replicationType, timeline.getMeta().getReplicaAction()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        format("There are no timelines with replication type %s for v2/channels/timelines", replicationType)))
                .getPayload();
    }

    @Step
    public List<Timeline> getFirstNTimelines(int timelinesNumber, Response v2ChannelsTimelines) {
        List<Timeline> timelineList = v2ChannelsTimelines.as(Timelines.class).getData().stream()
                .filter(timeline -> Objects.equals("upsert", timeline.getMeta().getReplicaAction()))
                .map(TimelinesDatum::getPayload)
                .limit(timelinesNumber)
                .collect(toList());
        org.assertj.core.api.Assertions.assertThat(timelineList)
                .as("It is expected at least %s timelines, but actually there are %s timelines", timelinesNumber, timelineList.size())
                .hasSize(timelinesNumber);
        return timelineList;
    }

    @Step
    public String getV2ChannelsMostRecentlyUpdatedTimelineTimestamp() {
        Response response = channelsTimelinesEndpointsV2.headChannelsTimelines();
        response.then().statusCode(SC_NO_CONTENT);
        return response.getHeader(LAST_MODIFIED_HEADER);
    }

    @Step
    public List<Timeline> getTimelinesWithUpsertReplicationType(Response v2ChannelsTimelines) {
        List<Timeline> timelines = v2ChannelsTimelines.as(Timelines.class).getData().stream()
                .filter(timeline -> Objects.equals("upsert", timeline.getMeta().getReplicaAction()))
                .map(TimelinesDatum::getPayload)
                .collect(toList());
        Assertions.assertThat(timelines).as("it is expected that some timelines with replicationType: upsert found").isNotEmpty();
        return timelines;
    }

    @Step
    public List<Timeline> getTimelinesThatShouldNotBeReturnedForDeltaUpdateWithoutPrevStop(HashMap<String, String> timeRange,
                                                                                           String syncStop,
                                                                                           String syncStart,
                                                                                           List<Timeline> timelines) {
        return timelines.stream().filter(tl ->
                parse(tl.getStart()).isAfter(parse(timeRange.get(STOP)))
                        && parse(tl.getStop()).isBefore(parse(timeRange.get(START)))
                        && parse(tl.getUpdatedAt()).isBefore(parse(syncStart))
                        && parse(tl.getUpdatedAt()).isAfter(parse(syncStop)))
                .collect(toList());
    }

    @Step
    public List<Timeline> getTimelinesThatShouldNotBeReturnedForDeltaUpdateWithPrevStop(Instant curStop,
                                                                                        String prevStop,
                                                                                        String syncStart,
                                                                                        List<Timeline> timelines) {
        return timelines.stream()
                .filter(tl -> parse(tl.getStart()).isAfter(curStop)
                        && parse(tl.getStart()).isBefore(parse(prevStop))
                        && parse(tl.getUpdatedAt()).isAfter(parse(syncStart)))
                .collect(toList());
    }

    @Step
    public long getTimelinesCount(Response v2ChannelsTimelines) {
        return v2ChannelsTimelines.as(Timelines.class).getData().stream()
                .map(TimelinesDatum::getPayload)
                .count();
    }
}
