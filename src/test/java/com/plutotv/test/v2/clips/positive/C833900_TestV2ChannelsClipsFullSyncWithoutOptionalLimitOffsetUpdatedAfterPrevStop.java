/*
 * (c) 2021 Pluto Inc.
 */

package com.plutotv.test.v2.clips.positive;

import com.plutotv.common.helper.validation.V2ChannelsClipsValidation;
import com.plutotv.common.layer.step.v2.channels.clips.ChannelsClipsSteps;
import com.plutotv.test.BaseTest;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static com.plutotv.common.Constants.REPL_TYPE_UPSERT;
import static com.plutotv.common.helper.RequestParameterHelper.getStartAndStopParams;
import static org.apache.http.HttpStatus.SC_OK;

public class C833900_TestV2ChannelsClipsFullSyncWithoutOptionalLimitOffsetUpdatedAfterPrevStop extends BaseTest {
    private final ChannelsClipsSteps channelsClipsSteps = new ChannelsClipsSteps();
    private final V2ChannelsClipsValidation v2ChannelsClipsValidation = new V2ChannelsClipsValidation();

    @Test(groups = {"full"})
    public void testV2ChannelsClipsFullSyncWithoutOptionalLimitOffsetUpdatedAfterPrevStop() {
        Response v2ChannelsClips = channelsClipsSteps.getV2ChannelsClipsFullSync(
                channelsClipsSteps.getV2ChannelsMostRecentlyUpdatedClipTimestamp(),
                getStartAndStopParams(TIMELINE_RANGE_IN_HOURS));

        v2ChannelsClips.then().statusCode(SC_OK);
        v2ChannelsClipsValidation.assertThatClipAttributesNotNullEmptyAndHaveExpectedFormat(
                channelsClipsSteps.getFirstClip(v2ChannelsClips, REPL_TYPE_UPSERT));
    }
}
