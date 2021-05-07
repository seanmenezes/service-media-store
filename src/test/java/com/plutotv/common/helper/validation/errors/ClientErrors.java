package com.plutotv.common.helper.validation.errors;

import com.plutotv.common.model.response.errors.Http400Error;

import static org.apache.http.HttpStatus.SC_BAD_REQUEST;

public class ClientErrors {

    public Http400Error getExpectedHttp400Error(String expectedErrorMessage) {
        return Http400Error.builder()
                .statusCode(SC_BAD_REQUEST)
                .error("Bad Request")
                .message(expectedErrorMessage)
                .build();
    }
}
