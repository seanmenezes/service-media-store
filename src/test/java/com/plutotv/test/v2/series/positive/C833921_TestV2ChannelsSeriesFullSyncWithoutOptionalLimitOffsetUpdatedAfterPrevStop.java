/*
 * (c) 2021 Pluto Inc.
 */

package com.plutotv.test.v2.series.positive;

import com.plutotv.common.layer.step.v2.channels.series.ChannelsSeriesSteps;
import com.plutotv.test.BaseTest;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static com.plutotv.common.Constants.REPL_TYPE_UPSERT;
import static com.plutotv.common.helper.RequestParameterHelper.getStartAndStopParams;
import static com.plutotv.common.helper.validation.V2ChannelsSeriesValidation.assertThatSeriesAttributesNotNullEmptyAndHaveExpectedFormat;
import static org.apache.http.HttpStatus.SC_OK;

public class C833921_TestV2ChannelsSeriesFullSyncWithoutOptionalLimitOffsetUpdatedAfterPrevStop extends BaseTest {
    private final ChannelsSeriesSteps channelsSeriesSteps = new ChannelsSeriesSteps();

    @Test(groups = {"full"})
    public void testV2ChannelsSeriesFullSyncWithoutOptionalLimitOffsetUpdatedAfterPrevStop() {
        Response v2ChannelsSeries = channelsSeriesSteps.getV2ChannelsSeriesFullSync(
                channelsSeriesSteps.getV2ChannelsMostRecentlyUpdatedSeriesTimestamp(),
                getStartAndStopParams(TIMELINE_RANGE_IN_HOURS));

        v2ChannelsSeries.then().statusCode(SC_OK);
        assertThatSeriesAttributesNotNullEmptyAndHaveExpectedFormat(
                channelsSeriesSteps.getFirstSeries(v2ChannelsSeries, REPL_TYPE_UPSERT));
    }
}
