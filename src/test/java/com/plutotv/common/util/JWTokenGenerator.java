/*
 * (c) 2020 Pluto Inc.
 */

package com.plutotv.common.util;

import com.plutotv.common.config.PropertiesHolder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.restassured.config.EncoderConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;

import java.util.Base64;

import static com.plutotv.test.lib.api.coreapi.Constants.Headers.TEST_USER_AGENT;
import static com.plutotv.test.lib.security.CryptoSecrets.decryptString;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static java.lang.String.format;
import static java.lang.System.getProperty;
import static org.apache.http.HttpStatus.SC_OK;


public class JWTokenGenerator {

    private static final String CONTENT_TYPE = "application/x-amz-json-1.1";
    private static final String ACTIVE_KID = "active-kid";
    private static final String MEDIA_STORE_SECRET = "/production/media-store-secrets/";
    private static final String MONGO_SECRET = "/production/ste-mongo-secrets/";
    private static final String MEDIA_STORE_ACTIVE_KID = MEDIA_STORE_SECRET + ACTIVE_KID;
    private static final String MONGO_ACTIVE_KID = MONGO_SECRET + ACTIVE_KID;


    public static String generateJWTokenForMediaStore() {
        String activeKidId = getActiveKidIdFromSSMParamStore(MEDIA_STORE_ACTIVE_KID);
        String activeKidSecretValue = getActiveKidSecretValueFromSSMParamStoreBy(MEDIA_STORE_SECRET, activeKidId);
        String encodedActiveKidSecretValue = encodeActiveKidSecretValue(activeKidSecretValue);
        byte[] decodedActiveKidSecretValue = decodeActiveKidSecretValue(encodedActiveKidSecretValue);
        return format("Bearer %s", buildJWToken(activeKidId, decodedActiveKidSecretValue));
    }

    public static String generateJWTokenForMongo() {
        String activeKidId = getActiveKidIdFromSSMParamStore(MONGO_ACTIVE_KID);
        String activeKidSecretValue = getActiveKidSecretValueFromSSMParamStoreBy(MONGO_SECRET, activeKidId);
        String encodedActiveKidSecretValue = encodeActiveKidSecretValue(activeKidSecretValue);
        byte[] decodedActiveKidSecretValue = decodeActiveKidSecretValue(encodedActiveKidSecretValue);
        return format("Bearer %s", buildJWToken(activeKidId, decodedActiveKidSecretValue));
    }

    private static String getActiveKidIdFromSSMParamStore(String activeKid) {
        Response responseWithID = given()
                .config(getRestAssuredConfig())
                .headers(getHeadersForSSMParameter())
                .body("{\"Name\":\"" + activeKid + "\"}")
                .post(getConfigServiceUrl())
                .thenReturn();
        responseWithID.then().statusCode(SC_OK);
        return responseWithID.jsonPath().getString("Parameter.Value");
    }

    private static String getActiveKidSecretValueFromSSMParamStoreBy(String secret, String activeKidId) {
        Response responseWithValue = given()
                .config(getRestAssuredConfig())
                .headers(getHeadersForSSMParameter())
                .body("{\"Name\": \"" + secret + activeKidId + "\"\n}")
                .post(getConfigServiceUrl())
                .thenReturn();
        responseWithValue.then().statusCode(SC_OK);
        return responseWithValue.jsonPath().getString("Parameter.Value");
    }

    private static byte[] decodeActiveKidSecretValue(String encodedActiveKidSecretValue) {
        return Base64.getDecoder().decode(encodedActiveKidSecretValue);
    }

    private static String encodeActiveKidSecretValue(String activeKidSecretValue) {
        return Base64.getEncoder().encodeToString(activeKidSecretValue.getBytes());
    }

    private static String buildJWToken(String activeKidId, byte[] decodedActiveKidSecretValue) {
        return Jwts.builder()
                .setHeaderParam("alg", "HS256")
                .setHeaderParam("kid", activeKidId)
                .setHeaderParam("typ", "JWT")
                .setPayload("{" +
                        "   \"exp\": 1915873609\n" +
                        "}")
                .signWith(Keys.hmacShaKeyFor(decodedActiveKidSecretValue))
                .compact();
    }

    private static Headers getHeadersForSSMParameter() {
        Header contentType = new Header("Content-Type", CONTENT_TYPE);
        Header xAmzTarget = new Header("x-amz-target", "AmazonSSM.GetParameter");
        Header region = new Header("region", "us-east-1");
        Header xAccessToken = getAccessTokenHeader();
        return new Headers(TEST_USER_AGENT, region, contentType, xAmzTarget, xAccessToken);
    }

    private static Header getAccessTokenHeader() {
        return isEnvPreProd() ?
                new Header("x-access-token", decryptString(PropertiesHolder.getProperty("PREPROD.ACCESSTOKEN"))) :
                new Header("x-access-token", decryptString(PropertiesHolder.getProperty("PROD.ACCESSTOKEN")));
    }

    private static RestAssuredConfig getRestAssuredConfig() {
        return RestAssuredConfig.config().encoderConfig(EncoderConfig.encoderConfig().encodeContentTypeAs(CONTENT_TYPE, JSON));
    }

    private static String getConfigServiceUrl() {
        return isEnvPreProd() ? "https://service-config.plutopreprod.tv" : "https://service-config.clusters.pluto.tv";
    }

    private static boolean isEnvPreProd() {
        return getProperty("env", "not_set").toLowerCase().contains("PREPROD".toLowerCase());
    }
}
