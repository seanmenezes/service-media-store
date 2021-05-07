package com.plutotv.test.v1.advanced_categories.negative;

import com.plutotv.common.helper.validation.NegativeParamsValidation;
import com.plutotv.test.BaseTest;
import org.testng.annotations.Test;

import static com.plutotv.common.config.v1.channels.ChannelsEndpointV1Manager.getEndpoint;
import static com.plutotv.common.helper.RequestParameterHelper.UPDATED_BEFORE;
import static com.plutotv.common.url.UrlBuilder.buildRequestUrl;

public class C608971_VerifyGetAdvancedCategoriesWithNullUpdatedBefore extends BaseTest {
    private final NegativeParamsValidation validationHelper = new NegativeParamsValidation();

    @Test(groups = {"full","adv_cat"})
    public void verifyGetAdvancedCategoriesWithNullUpdatedBefore() {
        validationHelper.negativeParamCheck(
                buildRequestUrl(getEndpoint().v1ChannelsAdvancedCategories()),
                UPDATED_BEFORE,
                "null");
    }
}