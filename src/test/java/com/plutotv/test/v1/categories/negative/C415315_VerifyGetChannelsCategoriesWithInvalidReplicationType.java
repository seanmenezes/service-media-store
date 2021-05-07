package com.plutotv.test.v1.categories.negative;

import com.plutotv.common.helper.validation.NegativeParamsValidation;
import com.plutotv.test.BaseTest;
import org.testng.annotations.Test;

import static com.plutotv.common.config.v1.channels.ChannelsEndpointV1Manager.getEndpoint;
import static com.plutotv.common.helper.RequestParameterHelper.REPLICATION_TYPE;
import static com.plutotv.common.url.UrlBuilder.buildRequestUrl;

public class C415315_VerifyGetChannelsCategoriesWithInvalidReplicationType extends BaseTest {
    private final NegativeParamsValidation validationHelper = new NegativeParamsValidation();

    @Test(groups = {"full"})
    public void verifyGetChannelsCategoriesWithInvalidReplicationType() {
        validationHelper.negativeParamCheck(
                buildRequestUrl(getEndpoint().v1ChannelsCategories()),
                REPLICATION_TYPE,
                "invalid");
    }
}