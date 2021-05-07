/*
 * (c) 2021 Pluto Inc.
 */

package com.plutotv.common.layer.endpoint.mongo;

import com.plutotv.common.config.mongo.SteMongoEndpoint;
import com.plutotv.common.config.mongo.SteMongoEndpointManager;
import com.plutotv.common.helper.RequestHelper;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import java.util.Map;

import static com.plutotv.common.Constants.STE_MONGO_HOST;
import static com.plutotv.common.url.UrlBuilder.buildRequestUrl;
import static com.plutotv.test.BaseTest.DEFAULT_HEADERS_STE_DAL;

public class MongoEndpoints {
    private static final SteMongoEndpoint ENDPOINT = SteMongoEndpointManager.getEndpoint();
    private final RequestHelper requestHelper = new RequestHelper();

    @Step("Find collection by /v1/pluto-main/find")
    public Response findCollection(Map<String, String> params, String body) {
        return requestHelper.postRequest(DEFAULT_HEADERS_STE_DAL,
                params,
                body,
                buildRequestUrl(STE_MONGO_HOST, ENDPOINT.v1PlutoMainFind()));
    }

    @Step("Find collection by /v1/pluto-main/find")
    public Response findCollection(String body) {
        return requestHelper.postRequest(DEFAULT_HEADERS_STE_DAL,
                body,
                buildRequestUrl(STE_MONGO_HOST, ENDPOINT.v1PlutoMainFind()));
    }
}
