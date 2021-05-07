package com.plutotv.test.v1.search.channels.negative;

import com.plutotv.common.helper.validation.NegativeParamsValidation;
import com.plutotv.test.BaseTest;
import org.testng.annotations.Test;

import static com.plutotv.common.config.v1.search.SearchEndpointV1Manager.getEndpoint;
import static com.plutotv.common.helper.RequestParameterHelper.REPLICATION_TYPE;
import static com.plutotv.common.url.UrlBuilder.buildRequestUrl;

public class C419222_VerifyGetSearchChannelsWithNullReplicationType extends BaseTest {
    private final NegativeParamsValidation validationHelper = new NegativeParamsValidation();

    @Test(groups = {"full"})
    public void verifyGetSearchChannelsWithNullReplicationType() {
        validationHelper.negativeParamCheck(
                buildRequestUrl(getEndpoint().v1SearchChannels()),
                REPLICATION_TYPE, "null");
    }
}