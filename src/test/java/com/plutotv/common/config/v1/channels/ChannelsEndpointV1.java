/*
 * (c) 2021 Pluto Inc.
 */

package com.plutotv.common.config.v1.channels;

import org.aeonbits.owner.Config;

@Config.Sources({"classpath:endpoints.properties"})
public interface ChannelsEndpointV1 extends Config {

    @Key("v1.healthcheck.info")
    String v1HealthCheckInfo();

    @Key("v1.channels")
    String v1Channels();

    @Key("v1.channels.categories")
    String v1ChannelsCategories();

    @Key("v1.channels.advanced-categories")
    String v1ChannelsAdvancedCategories();

    @Key("v1.channels.timelines")
    String v1ChannelsTimelines();

}
