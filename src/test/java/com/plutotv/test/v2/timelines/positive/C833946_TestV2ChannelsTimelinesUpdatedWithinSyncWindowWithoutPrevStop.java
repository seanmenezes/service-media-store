/*
 * (c) 2021 Pluto Inc.
 */

package com.plutotv.test.v2.timelines.positive;

import com.plutotv.common.helper.validation.V2ChannelsTimelinesValidation;
import com.plutotv.common.layer.step.v2.channels.timelines.ChannelsTimelinesSteps;
import com.plutotv.common.model.bussines.v2.channels.Timeline;
import com.plutotv.test.BaseTest;
import io.qameta.allure.Description;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;

import static com.plutotv.common.helper.RequestParameterHelper.START;
import static com.plutotv.common.helper.RequestParameterHelper.STOP;
import static java.time.Instant.parse;
import static java.time.temporal.ChronoUnit.HOURS;
import static org.apache.http.HttpStatus.SC_OK;

public class C833946_TestV2ChannelsTimelinesUpdatedWithinSyncWindowWithoutPrevStop extends BaseTest {
    private final ChannelsTimelinesSteps channelsTimelinesSteps = new ChannelsTimelinesSteps();
    private final V2ChannelsTimelinesValidation v2ChannelsTimelinesValidation = new V2ChannelsTimelinesValidation();

    @Description("Test selection of timelines that fulfill next condition: tl.start <= stop AND tl.stop >= start " +
            "AND tl.updated_at > updatedAfter AND tl.updated_at <= updatedBefore")
    @Test(groups = {"full"})
    public void testV2ChannelsTimelinesUpdatedWithinSyncWindowWithoutPrevStop() {
        HashMap<String, String> timeRange = new HashMap<>();
        Instant now = Instant.now().truncatedTo(HOURS);
        timeRange.put(START, now.minus(13, HOURS).toString());
        timeRange.put(STOP, now.plus(13, HOURS).toString());

        String syncStop = channelsTimelinesSteps.getV2ChannelsMostRecentlyUpdatedTimelineTimestamp();
        String syncStart = parse(syncStop).minus(13, HOURS).toString();

        Response v2ChannelsTimelinesDeltaUpdate = channelsTimelinesSteps.getV2ChannelsTimelinesDeltaUpdate(
                syncStart, syncStop, timeRange);
        v2ChannelsTimelinesDeltaUpdate.then().statusCode(SC_OK);

        List<Timeline> timelinesWithUpsertReplicationType =
                channelsTimelinesSteps.getTimelinesWithUpsertReplicationType(v2ChannelsTimelinesDeltaUpdate);

        List<Timeline> timelinesThatShouldNotBeReturned =
                channelsTimelinesSteps.getTimelinesThatShouldNotBeReturnedForDeltaUpdateWithoutPrevStop(timeRange,
                        syncStop,
                        syncStart,
                        timelinesWithUpsertReplicationType);
        v2ChannelsTimelinesValidation.assertThatThereAreNoTimelinesThatNotFulfillWithSyncFilter(timelinesThatShouldNotBeReturned);
    }
}
