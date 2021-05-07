package com.plutotv.test.v1.categories.negative;

import com.plutotv.common.helper.validation.NegativeParamsValidation;
import com.plutotv.test.BaseTest;
import org.testng.annotations.Test;

import static com.plutotv.common.config.v1.channels.ChannelsEndpointV1Manager.getEndpoint;
import static com.plutotv.common.url.UrlBuilder.buildRequestUrl;

public class C164275_VerifyGetChannelsCategoriesWithInvalidLimit extends BaseTest {
    private final NegativeParamsValidation validationHelper = new NegativeParamsValidation();

    @Test(groups = {"full"})
    public void verifyGetChannelsCategoriesWithInvalidLimit() {
        validationHelper.negativeParamCheck(
                buildRequestUrl(getEndpoint().v1ChannelsCategories()),
                "limit", "-1");
    }
}