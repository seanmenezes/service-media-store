package com.plutotv.test.v1.search.timelines.negative;

import com.plutotv.common.helper.validation.NegativeParamsValidation;
import com.plutotv.test.BaseTest;
import org.testng.annotations.Test;

import static com.plutotv.common.config.v1.search.SearchEndpointV1Manager.getEndpoint;
import static com.plutotv.common.url.UrlBuilder.buildRequestUrl;

public class C164315_VerifyGetSearchTimelinesWithNullStart extends BaseTest {
    private final NegativeParamsValidation validationHelper = new NegativeParamsValidation();

    @Test(groups = {"full"})
    public void verifyGetSearchTimelinesWithNullStart() {
        validationHelper.negativeParamCheck(
                buildRequestUrl(getEndpoint().v1SearchTimelines()),
                "start", "null");
    }
}