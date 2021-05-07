package com.plutotv.common.helper.validation.errors;

import com.plutotv.common.model.response.errors.Http400Error;
import io.qameta.allure.Step;

import static org.assertj.core.api.Assertions.assertThat;

public class ErrorsValidation {

    @Step
    public void assertThatErrorAsExpected(Http400Error expectedError, Http400Error actualError) {
        assertThat(actualError)
                .as("Expected error is %s but actual is %s", expectedError, actualError)
                .isEqualTo(expectedError);
    }
}
