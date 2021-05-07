package com.plutotv.test.v1.search.episodes.negative;

import com.plutotv.common.helper.validation.NegativeParamsValidation;
import com.plutotv.test.BaseTest;
import org.testng.annotations.Test;

import static com.plutotv.common.config.v1.search.SearchEndpointV1Manager.getEndpoint;
import static com.plutotv.common.url.UrlBuilder.buildRequestUrl;

public class C608986_VerifyGetSearchEpisodesWithNullUpdatedBefore extends BaseTest {
    private final NegativeParamsValidation validationHelper = new NegativeParamsValidation();

    @Test(groups = {"full"})
    public void verifyGetSearchEpisodesWithNullUpdatedBefore() {
        validationHelper.negativeParamCheck(
                buildRequestUrl(getEndpoint().v1SearchEpisodes()),
                "updatedBefore", "null");
    }
}