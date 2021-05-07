/*
 * (c) 2021 Pluto Inc.
 */

package com.plutotv.test.v2.episodes.positive;

import com.plutotv.common.helper.validation.V2ChannelsEpisodesValidation;
import com.plutotv.common.layer.step.v2.channels.episodes.ChannelsEpisodesSteps;
import com.plutotv.test.BaseTest;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static com.plutotv.common.Constants.REPL_TYPE_UPSERT;
import static com.plutotv.common.helper.RequestParameterHelper.getStartAndStopParams;
import static org.apache.http.HttpStatus.SC_OK;

public class C833911_TestV2ChannelsEpisodesFullSyncWithoutOptionalLimitOffsetUpdatedAfterPrevStop extends BaseTest {
    private final ChannelsEpisodesSteps channelsEpisodesSteps = new ChannelsEpisodesSteps();
    private final V2ChannelsEpisodesValidation v2ChannelsEpisodesValidation = new V2ChannelsEpisodesValidation();

    @Test(groups = {"full"})
    public void testV2ChannelsEpisodesFullSyncWithoutOptionalLimitOffsetUpdatedAfterPrevStop() {
        Response v2ChannelsEpisodes = channelsEpisodesSteps.getV2ChannelsEpisodesFullSync(
                channelsEpisodesSteps.getV2ChannelsMostRecentlyUpdatedEpisodeTimestamp(),
                getStartAndStopParams(TIMELINE_RANGE_IN_HOURS));

        v2ChannelsEpisodes.then().statusCode(SC_OK);
        v2ChannelsEpisodesValidation.assertThatEpisodeAttributesNotNullEmptyHaveExpectedFormat(
                channelsEpisodesSteps.getFirstEpisode(v2ChannelsEpisodes, REPL_TYPE_UPSERT));
    }
}
