/*
 * (c) 2021 Pluto Inc.
 */

package com.plutotv.test.v2.episodes.positive;

import com.plutotv.common.layer.step.v2.channels.episodes.ChannelsEpisodesSteps;
import com.plutotv.test.BaseTest;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static com.plutotv.common.helper.RequestParameterHelper.getStartAndStopParams;
import static java.lang.Long.parseLong;
import static org.apache.http.HttpStatus.SC_OK;
import static org.assertj.core.api.Assertions.assertThat;

public class C833909_TestV2ChannelsEpisodesFullSyncWithOptionalLimit extends BaseTest {
    private final ChannelsEpisodesSteps channelsEpisodesSteps = new ChannelsEpisodesSteps();

    @Test(groups = {"full"})
    public void testV2ChannelsEpisodesFullSyncWithOptionalLimit() {
        String episodesCountExpected = "1";

        Response v2ChannelsEpisodesFullSyncWithLimit = channelsEpisodesSteps.getV2ChannelsEpisodesFullSyncWithLimit(
                episodesCountExpected,
                channelsEpisodesSteps.getV2ChannelsMostRecentlyUpdatedEpisodeTimestamp(),
                getStartAndStopParams(TIMELINE_RANGE_IN_HOURS));

        v2ChannelsEpisodesFullSyncWithLimit.then().statusCode(SC_OK);
        long episodesCountActual = channelsEpisodesSteps.getEpisodesCount(v2ChannelsEpisodesFullSyncWithLimit);
        assertThatEpisodesLimitAsExpected(episodesCountExpected, episodesCountActual);
    }

    @Step
    private void assertThatEpisodesLimitAsExpected(String episodesCountExpected, long episodesCountActual) {
        assertThat(episodesCountActual)
                .as("Only %s episodes expected, but actually %s in response",
                        episodesCountExpected, episodesCountActual)
                .isEqualTo(parseLong(episodesCountExpected));
    }
}
