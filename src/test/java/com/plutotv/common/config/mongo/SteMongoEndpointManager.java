/*
 * (c) 2021 Pluto Inc.
 */

package com.plutotv.common.config.mongo;

import org.aeonbits.owner.ConfigCache;

public class SteMongoEndpointManager {

    private SteMongoEndpointManager() {
    }

    public static SteMongoEndpoint getEndpoint() {
        return ConfigCache.getOrCreate(SteMongoEndpoint.class);
    }
}
