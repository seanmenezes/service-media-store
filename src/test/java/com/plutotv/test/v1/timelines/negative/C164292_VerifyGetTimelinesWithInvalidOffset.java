package com.plutotv.test.v1.timelines.negative;

import com.plutotv.common.helper.validation.NegativeParamsValidation;
import com.plutotv.test.BaseTest;
import org.testng.annotations.Test;

import static com.plutotv.common.config.v1.channels.ChannelsEndpointV1Manager.getEndpoint;
import static com.plutotv.common.helper.RequestParameterHelper.OFFSET;
import static com.plutotv.common.url.UrlBuilder.buildRequestUrl;

public class C164292_VerifyGetTimelinesWithInvalidOffset extends BaseTest {
    private final NegativeParamsValidation validationHelper = new NegativeParamsValidation();

    @Test(groups = {"full"})
    public void verifyGetTimelinesWithInvalidOffset() {
        validationHelper.negativeParamCheck(
                buildRequestUrl(getEndpoint().v1ChannelsTimelines()),
                OFFSET, "-1");
    }
}