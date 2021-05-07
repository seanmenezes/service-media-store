package com.plutotv.test.v1.advanced_categories.negative;

import com.plutotv.common.helper.validation.NegativeParamsValidation;
import com.plutotv.test.BaseTest;
import org.testng.annotations.Test;

import static com.plutotv.common.config.v1.channels.ChannelsEndpointV1Manager.getEndpoint;
import static com.plutotv.common.helper.RequestParameterHelper.REPLICATION_TYPE;
import static com.plutotv.common.url.UrlBuilder.buildRequestUrl;

public class C415318_VerifyGetAdvancedCategoriesWithInvalidReplicationType extends BaseTest {
    private final NegativeParamsValidation validationHelper = new NegativeParamsValidation();

    @Test(groups = {"full","adv_cat"})
    public void verifyGetAdvancedCategoriesWithInvalidReplicationType() {
        validationHelper.negativeParamCheck(
                buildRequestUrl(getEndpoint().v1ChannelsAdvancedCategories()),
                REPLICATION_TYPE,
                "invalid");
    }
}