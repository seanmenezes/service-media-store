/*
 * (c) 2021 Pluto Inc.
 */

package com.plutotv.test.v2.clips.positive;

import com.plutotv.common.helper.validation.V2ChannelsClipsValidation;
import com.plutotv.common.layer.step.v2.channels.clips.ChannelsClipsSteps;
import com.plutotv.common.model.bussines.v2.channels.Clip;
import com.plutotv.test.BaseTest;
import io.qameta.allure.Description;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;

import static com.plutotv.common.helper.RequestParameterHelper.START;
import static com.plutotv.common.helper.RequestParameterHelper.STOP;
import static java.time.Instant.now;
import static java.time.Instant.parse;
import static java.time.temporal.ChronoUnit.HOURS;
import static org.apache.http.HttpStatus.SC_OK;

public class C833902_TestV2ChannelsClipsUpdatedBeforeSyncWindowWithPrevStop extends BaseTest {
    private final ChannelsClipsSteps channelsClipsSteps = new ChannelsClipsSteps();
    private final V2ChannelsClipsValidation v2ChannelsClipsValidation = new V2ChannelsClipsValidation();

    @Description("Test selection of timelines that fulfill next condition: tl.start <= stop AND tl.start >= previousStop AND tl.updated_at <= updatedAfter")
    @Test(groups = {"full"})
    public void testV2ChannelsClipsUpdatedBeforeSyncWindowWithPrevStop() {
        HashMap<String, String> timeRange = new HashMap<>();
        Instant now = now().truncatedTo(HOURS);
        timeRange.put(START, now.minus(1, HOURS).toString());
        timeRange.put(STOP, now.plus(13, HOURS).toString());

        Instant curStop = parse(timeRange.get(STOP));
        String prevStop = curStop.minus(1, HOURS).toString();
        String syncStop = channelsClipsSteps.getV2ChannelsMostRecentlyUpdatedClipTimestamp();
        String syncStart = syncStop;
        Response v2ChannelsClipsDeltaUpdate = channelsClipsSteps.getV2ChannelsClipsDeltaUpdate(
                syncStart,
                syncStop,
                timeRange,
                prevStop);
        v2ChannelsClipsDeltaUpdate.then().statusCode(SC_OK);

        List<Clip> clipsWithUpsertReplicationType =
                channelsClipsSteps.getClipsWithUpsertReplicationType(v2ChannelsClipsDeltaUpdate);

        List<Clip> clipsThatShouldNotBeReturned =
                channelsClipsSteps.getClipsThatShouldNotBeReturnedForDeltaUpdateWithPrevStop(
                        syncStart,
                        clipsWithUpsertReplicationType);

        v2ChannelsClipsValidation.assertThatThereAreNoClipsThatNotFulfillWithSyncFilter(clipsThatShouldNotBeReturned);
    }
}
