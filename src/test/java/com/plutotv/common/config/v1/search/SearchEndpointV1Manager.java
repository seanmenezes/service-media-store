/*
 * (c) 2021 Pluto Inc.
 */

package com.plutotv.common.config.v1.search;

import org.aeonbits.owner.ConfigCache;

public class SearchEndpointV1Manager {

    private SearchEndpointV1Manager() {
    }

    public static SearchEndpointV1 getEndpoint() {
        return ConfigCache.getOrCreate(SearchEndpointV1.class);
    }
}
