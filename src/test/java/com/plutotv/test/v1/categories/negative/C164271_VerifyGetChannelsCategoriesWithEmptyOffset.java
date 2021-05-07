package com.plutotv.test.v1.categories.negative;

import com.plutotv.common.helper.validation.NegativeParamsValidation;
import com.plutotv.test.BaseTest;
import org.testng.annotations.Test;

import static com.plutotv.common.config.v1.channels.ChannelsEndpointV1Manager.getEndpoint;
import static com.plutotv.common.helper.RequestParameterHelper.OFFSET;
import static com.plutotv.common.url.UrlBuilder.buildRequestUrl;

public class C164271_VerifyGetChannelsCategoriesWithEmptyOffset extends BaseTest {
    private final NegativeParamsValidation validationHelper = new NegativeParamsValidation();

    @Test(groups = {"full"})
    public void verifyGetChannelsCategoriesWithEmptyOffset() {
        validationHelper.negativeParamCheck(
                buildRequestUrl(getEndpoint().v1ChannelsCategories()),
                OFFSET,
                " ");
    }
}