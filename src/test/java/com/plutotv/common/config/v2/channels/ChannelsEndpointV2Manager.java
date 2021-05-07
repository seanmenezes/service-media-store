/*
 * (c) 2021 Pluto Inc.
 */

package com.plutotv.common.config.v2.channels;

import org.aeonbits.owner.ConfigCache;

public class ChannelsEndpointV2Manager {

    private ChannelsEndpointV2Manager() {
    }

    public static ChannelsEndpointV2 getEndpoint() {
        return ConfigCache.getOrCreate(ChannelsEndpointV2.class);
    }
}
