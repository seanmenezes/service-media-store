/*
 * (c) 2021 Pluto Inc.
 */

package com.plutotv.common.config.v1.active.regions;

import org.aeonbits.owner.ConfigCache;

public class ActiveRegionsEndpointV1Manager {

    private ActiveRegionsEndpointV1Manager() {
    }

    public static ActiveRegionsEndpointV1 getEndpoint() {
        return ConfigCache.getOrCreate(ActiveRegionsEndpointV1.class);
    }
}
