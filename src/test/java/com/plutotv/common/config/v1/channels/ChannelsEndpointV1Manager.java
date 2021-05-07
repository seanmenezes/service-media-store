/*
 * (c) 2021 Pluto Inc.
 */

package com.plutotv.common.config.v1.channels;

import org.aeonbits.owner.ConfigCache;

public class ChannelsEndpointV1Manager {

    private ChannelsEndpointV1Manager() {
    }

    public static ChannelsEndpointV1 getEndpoint() {
        return ConfigCache.getOrCreate(ChannelsEndpointV1.class);
    }
}
