package com.plutotv.test.v1.search.timelines.negative;

import com.plutotv.common.helper.validation.NegativeParamsValidation;
import com.plutotv.test.BaseTest;
import org.testng.annotations.Test;

import static com.plutotv.common.config.v1.search.SearchEndpointV1Manager.getEndpoint;
import static com.plutotv.common.helper.RequestParameterHelper.REPLICATION_TYPE;
import static com.plutotv.common.url.UrlBuilder.buildRequestUrl;

public class C419230_VerifyGetSearchTimelinesWithInvalidReplicationType extends BaseTest {
    private final NegativeParamsValidation validationHelper = new NegativeParamsValidation();

    @Test(groups = {"full"})
    public void verifyGetSearchTimelinesWithInvalidReplicationType() {
        validationHelper.negativeParamCheck(
                buildRequestUrl(getEndpoint().v1SearchTimelines()),
                REPLICATION_TYPE,
                "invalid");
    }
}