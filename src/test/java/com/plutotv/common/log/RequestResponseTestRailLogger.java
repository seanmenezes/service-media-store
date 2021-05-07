/*
 * (c) 2021 Pluto Inc.
 */

package com.plutotv.common.log;

import com.plutotv.test.lib.LogUtils;
import io.restassured.response.Response;

import java.time.LocalDateTime;
import java.util.Map;

import static com.plutotv.test.lib.api.coreapi.RestHelpers.paramsToString;
import static java.lang.String.format;

public class RequestResponseTestRailLogger {

    public void logRequest(String method, String url) {
        logTime();
        LogUtils.logInfoMessage(format("%s %s\n", method, url));
    }

    public void logRequest(String method, String url, Map<String, String> params) {
        logTime();
        LogUtils.logInfoMessage(format("%s %s%s\n", method, url, paramsToString(params)));
    }

    public void logHeadResponse(Response response) {
        LogUtils.logInfoMessage(format("X-Request-Id: %s\n, Last-Modified: %s\n",
                response.header("X-Request-Id"),
                response.getHeader("Last-Modified")));
    }

    public void logGetResponse(Response response) {
        LogUtils.logInfoMessage(format("X-Request-Id: %s\n", response.header("X-Request-Id")));
    }

    public void logTime() {
        LogUtils.logInfoMessage(format("Request made at: %s\n", LocalDateTime.now()));
    }
}
