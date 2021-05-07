/*
 * (c) 2021 Pluto Inc.
 */

package com.plutotv.common.config.v2.channels;

import org.aeonbits.owner.Config;

@Config.Sources({"classpath:endpoints.properties"})
public interface ChannelsEndpointV2 extends Config {

    @Key("v2.channels.timelines")
    String v2ChannelsTimelines();

    @Key("v2.channels.episodes")
    String v2ChannelsEpisodes();

    @Key("v2.channels.series")
    String v2ChannelsSeries();

    @Key("v2.channels.clips")
    String v2ChannelsClips();

}
