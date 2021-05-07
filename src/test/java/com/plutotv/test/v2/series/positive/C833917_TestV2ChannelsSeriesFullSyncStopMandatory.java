/*
 * (c) 2021 Pluto Inc.
 */

package com.plutotv.test.v2.series.positive;

import com.plutotv.common.helper.validation.errors.ClientErrors;
import com.plutotv.common.helper.validation.errors.ErrorsValidation;
import com.plutotv.common.layer.endpoint.v2.channels.series.ChannelsSeriesEndpointsV2;
import com.plutotv.common.layer.step.v2.channels.series.ChannelsSeriesSteps;
import com.plutotv.common.model.response.errors.Http400Error;
import com.plutotv.test.BaseTest;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static com.plutotv.common.helper.RequestParameterHelper.*;
import static org.apache.http.HttpStatus.SC_BAD_REQUEST;

public class C833917_TestV2ChannelsSeriesFullSyncStopMandatory extends BaseTest {
    private final ChannelsSeriesSteps channelsSeriesSteps = new ChannelsSeriesSteps();
    private final ChannelsSeriesEndpointsV2 channelsSeriesEndpointsV2 = new ChannelsSeriesEndpointsV2();
    private final ClientErrors clientErrors = new ClientErrors();
    private final ErrorsValidation errorsValidation = new ErrorsValidation();

    @Test(groups = {"full"})
    public void testV2ChannelsSeriesFullSyncStopMandatory() {
        Http400Error expectedError =
                clientErrors.getExpectedHttp400Error("ValidationFailed: params [Stop] are invalid");

        Map<String, String> startAndStopParams = getStartAndStopParams(TIMELINE_RANGE_IN_HOURS);
        startAndStopParams.remove(STOP);

        Map<String, String> requestParams = new HashMap<>();
        requestParams.put(REPLICATION_TYPE, "full-sync");
        requestParams.put(UPDATED_BEFORE, channelsSeriesSteps.getV2ChannelsMostRecentlyUpdatedSeriesTimestamp());
        requestParams.putAll(startAndStopParams);
        Response v2ChannelsSeries = channelsSeriesEndpointsV2.getChannelsSeries(requestParams);

        v2ChannelsSeries.then().statusCode(SC_BAD_REQUEST);
        Http400Error actualError = v2ChannelsSeries.as(Http400Error.class);
        errorsValidation.assertThatErrorAsExpected(expectedError, actualError);
    }
}
