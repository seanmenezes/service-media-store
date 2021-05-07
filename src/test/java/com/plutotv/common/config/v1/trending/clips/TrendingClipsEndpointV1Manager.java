/*
 * (c) 2021 Pluto Inc.
 */

package com.plutotv.common.config.v1.trending.clips;

import org.aeonbits.owner.ConfigCache;

public class TrendingClipsEndpointV1Manager {

    private TrendingClipsEndpointV1Manager() {
    }

    public static TrendingClipsEndpointV1 getEndpoint() {
        return ConfigCache.getOrCreate(TrendingClipsEndpointV1.class);
    }
}
