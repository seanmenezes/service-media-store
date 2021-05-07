/*
 * (c) 2020 Pluto Inc.
 */

package com.plutotv.test;

import com.plutotv.common.helper.RequestHelper;
import com.plutotv.test.lib.StringUtils;
import com.plutotv.test.listeners.TestRailReporterListener;
import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.apache.commons.io.output.WriterOutputStream;
import org.testng.Reporter;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.io.*;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import static com.plutotv.common.Constants.RegEx.SERVICE_VERSION;
import static com.plutotv.common.config.v1.channels.ChannelsEndpointV1Manager.getEndpoint;
import static com.plutotv.common.helper.HeaderHelper.AUTHORIZATION_HEADER;
import static com.plutotv.common.mapper.CustomObjectMapper.getObjectMapperNotFailedWhenUnknownProperties;
import static com.plutotv.common.url.UrlBuilder.buildRequestUrl;
import static com.plutotv.common.util.JWTokenGenerator.generateJWTokenForMediaStore;
import static com.plutotv.common.util.JWTokenGenerator.generateJWTokenForMongo;
import static com.plutotv.test.lib.LogUtils.logSevereMessageThenFail;
import static com.plutotv.test.lib.api.coreapi.Constants.Headers.TEST_USER_AGENT;
import static java.lang.String.format;
import static java.lang.System.currentTimeMillis;
import static org.apache.http.HttpStatus.SC_OK;

public abstract class BaseTest {

    public static final Headers DEFAULT_HEADERS = new Headers(TEST_USER_AGENT, new Header(AUTHORIZATION_HEADER, generateJWTokenForMediaStore()));
    public static final Headers DEFAULT_HEADERS_STE_DAL = new Headers(new Header(AUTHORIZATION_HEADER, generateJWTokenForMongo()));
    private static final String LOG_DIR = new File("log").getAbsolutePath();
    private PrintStream printStream;
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");

    protected final int TIMELINE_RANGE_IN_HOURS = 13;
    private final RequestHelper requestHelper = new RequestHelper();

//    @BeforeMethod(alwaysRun = true)
//    public void logTestToFileStart(Method method, Object[] params) {
//        createLogDir();
//        logRequestResponseToFile(method);
//    }

//    @AfterMethod(alwaysRun = true)
//    public void logTestToFileStop(Method method, Object[] params) {
//        logRequestResponseToFileCleanUp();
//    }

    @BeforeSuite(alwaysRun = true)
    public void suiteSetup() {
        configRestAssured();
        validateServiceVersion();
    }

    @AfterSuite
    public void afterSuite() {
        System.out.println("End of Test Suite");
    }

    private void configRestAssured() {
        RestAssured.config = requestHelper.getObjectMapper(getObjectMapperNotFailedWhenUnknownProperties());
//        logRequestResponseToConsole();
        attachRequestResponseToAllure();
    }

    private void logRequestResponseToFile(Method method) {
        Timestamp timestamp = new Timestamp(currentTimeMillis());
        FileWriter fileWriter;
        try {
            fileWriter = new FileWriter(format("%s/%s_%s.log", LOG_DIR, method.getName(), sdf.format(timestamp)));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
        printStream = new PrintStream(new WriterOutputStream(fileWriter, Charset.defaultCharset()), true);
//two ways how to log to file, keep both, just for a while
//option1
//        RestAssured.config = RestAssured.config()
//                .logConfig(LogConfig.logConfig().defaultStream(printStream).enablePrettyPrinting(true));
//        RestAssured.requestSpecification = new RequestSpecBuilder().setConfig(RestAssured.config).build().log().all();
//        RestAssured.filters(ResponseLoggingFilter.logResponseTo(printStream, LogDetail.ALL));
//option2
        RestAssured.filters(
                ResponseLoggingFilter.logResponseTo(printStream, LogDetail.ALL),
                new RequestLoggingFilter(LogDetail.ALL, printStream));
    }

    private void logRequestResponseToFileCleanUp() {
        if (printStream != null) {
            printStream.close();
        }
    }

    private void createLogDir() {
        File dir = new File(LOG_DIR);
        if (!dir.exists()) {
            //noinspection ResultOfMethodCallIgnored
            dir.mkdirs();
        }
    }

    @Step("Validate media-store version")
    private void validateServiceVersion() {
        Response response = requestHelper.getRequest(buildRequestUrl(getEndpoint().v1HealthCheckInfo()));
        if (response.getStatusCode() != SC_OK) {
            logSevereMessageThenFail("Media-store service does not work, its status code is " + response.getStatusCode());
        }
        String version = response.then().extract().body().path("appVersion");
        Reporter.log("Service Version: " + version);
        TestRailReporterListener.setAppVersion(version);
        StringUtils.validateStringRegexFormat(version, SERVICE_VERSION,
                "Media-store version failed to match pattern for version value: " + version);
    }

    private void logRequestResponseToConsole() {
        RestAssured.requestSpecification = new RequestSpecBuilder().build().log().all();
        RestAssured.filters(new ResponseLoggingFilter());
    }

    private void attachRequestResponseToAllure() {
        RestAssured.filters(new AllureRestAssured());
    }
}
