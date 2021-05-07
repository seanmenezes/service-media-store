/*
 * (c) 2021 Pluto Inc.
 */

package com.plutotv.common.config.v1.vod;

import org.aeonbits.owner.ConfigCache;

public class VodEndpointV1Manager {

    private VodEndpointV1Manager() {
    }

    public static VodEndpointV1 getEndpoint() {
        return ConfigCache.getOrCreate(VodEndpointV1.class);
    }
}
