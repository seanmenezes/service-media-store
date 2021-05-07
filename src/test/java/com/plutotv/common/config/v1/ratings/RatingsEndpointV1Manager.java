/*
 * (c) 2021 Pluto Inc.
 */

package com.plutotv.common.config.v1.ratings;

import org.aeonbits.owner.ConfigCache;

public class RatingsEndpointV1Manager {

    private RatingsEndpointV1Manager() {
    }

    public static RatingsEndpointV1 getEndpoint() {
        return ConfigCache.getOrCreate(RatingsEndpointV1.class);
    }
}
