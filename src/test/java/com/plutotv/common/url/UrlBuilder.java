/*
 * (c) 2021 Pluto Inc.
 */

package com.plutotv.common.url;

import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import static com.plutotv.common.Constants.SCHEME;
import static com.plutotv.common.Constants.SERVICE_HOST;

public class UrlBuilder {

    public static String buildBaseUrl() {
        return UriComponentsBuilder.newInstance().scheme(SCHEME).host(SERVICE_HOST).build().toUriString();
    }

    public static String buildRequestUrl(String path) {
        return UriComponentsBuilder.newInstance().scheme(SCHEME).host(SERVICE_HOST).path(path).build().toUriString();
    }

    public static String buildRequestUrl(String host, String path) {
        return UriComponentsBuilder.newInstance().scheme(SCHEME).host(host).path(path).build().toUriString();
    }

    public static String buildRequestUrl(String path, MultiValueMap<String, String> queryParams) {
        return UriComponentsBuilder.newInstance().scheme(SCHEME).host(SERVICE_HOST).path(path).queryParams(queryParams).build().toUriString();
    }

    public static String buildRequestUrl(String host, String path, MultiValueMap<String, String> queryParams) {
        return UriComponentsBuilder.newInstance().scheme(SCHEME).host(host).path(path).queryParams(queryParams).build().toUriString();
    }

    public static String buildRequestUrl(String path, String... pathValues) {
        return UriComponentsBuilder.newInstance().scheme(SCHEME).host(SERVICE_HOST).path(path).build().expand(pathValues).toUriString();
    }

    public static String buildRequestUrl(String path, MultiValueMap<String, String> queryParams, String... pathValues) {
        return UriComponentsBuilder.newInstance().scheme(SCHEME).host(SERVICE_HOST).path(path).queryParams(queryParams).build().expand(pathValues).toUriString();
    }

}
