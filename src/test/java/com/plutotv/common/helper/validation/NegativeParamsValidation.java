/*
 * (c) 2020 Pluto Inc.
 */

package com.plutotv.common.helper.validation;

import com.plutotv.common.helper.RequestHelper;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import java.util.HashMap;

import static com.plutotv.common.helper.RequestParameterHelper.*;
import static com.plutotv.test.BaseTest.DEFAULT_HEADERS;
import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.CoreMatchers.equalTo;

public class NegativeParamsValidation {
    private final RequestHelper requestHelper = new RequestHelper();

    @Step("Negative param check")
    public void negativeParamCheck(String url, String paramName, String paramValue) {
        Response response;

        HashMap<String, String> params = new HashMap<>();
        if (!url.contains("search") && !paramName.equalsIgnoreCase(REPLICATION_TYPE)) {
            params.put(REPLICATION_TYPE, "full-sync");
        }
        params.put(paramName, paramValue);
        response = requestHelper.getRequest(DEFAULT_HEADERS, params, url);

        badRequestCheck(response);

        if (paramName.equalsIgnoreCase(UPDATED_AFTER) || paramName.equalsIgnoreCase(UPDATED_BEFORE) || paramName.equalsIgnoreCase(START) || paramName.equalsIgnoreCase(STOP)) {
            response.then()
                    .body("message", equalTo("parsing time \"" + paramValue + "\" as \"2006-01-02T15:04:05Z07:00\": cannot parse \"" + paramValue + "\" as \"2006\""));
        } else if (paramName.equalsIgnoreCase(REPLICATION_TYPE)) {
            response.then()
                    .body("message", anyOf(
                            equalTo("ValidationFailed: params [ReplicationType] are invalid"),
                            equalTo("ValidationFailed: params [Start Stop ReplicationType] are invalid")
                    ));
        } else if (paramValue.equals("-1")) {
            if (paramName.equalsIgnoreCase(OFFSET)) {
                response.then()
                        .body("message", anyOf(
                                equalTo("ValidationFailed: params [Offset] are invalid"),
                                equalTo("ValidationFailed: params [Start Stop Offset] are invalid"),
                                equalTo("ValidationFailed: params [Offset Start Stop] are invalid"),
                                equalTo("ValidationFailed: params [Offset ReplicationType] are invalid"),
                                equalTo("ValidationFailed: params [Offset Start Stop ReplicationType] are invalid")
                        ));
            } else if (paramName.equalsIgnoreCase(LIMIT)) {
                response.then()
                        .body("message", anyOf(
                                equalTo("ValidationFailed: params [Limit] are invalid"),
                                equalTo("ValidationFailed: params [Start Stop Limit] are invalid"),
                                equalTo("ValidationFailed: params [Limit Start Stop] are invalid"),
                                equalTo("ValidationFailed: params [Limit ReplicationType] are invalid"),
                                equalTo("ValidationFailed: params [Limit Start Stop ReplicationType] are invalid")
                        ));
            }
        } else {
            response.then()
                    .body("message", equalTo("strconv.ParseInt: parsing \"" + paramValue + "\": invalid syntax"));
        }
    }

    private static void badRequestCheck(Response response) {
        response.then()
                .statusCode(SC_BAD_REQUEST)
                .body("statusCode", equalTo(SC_BAD_REQUEST))
                .body("error", equalTo("Bad Request"));
    }
}
