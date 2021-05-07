/*
 * (c) 2021 Pluto Inc.
 */

package com.plutotv.common.config.v1.search;

import org.aeonbits.owner.Config;

@Config.Sources({"classpath:endpoints.properties"})
public interface SearchEndpointV1 extends Config {

    @Key("v1.search.channels")
    String v1SearchChannels();

    @Key("v1.search.episodes")
    String v1SearchEpisodes();

    @Key("v1.search.series")
    String v1SearchSeries();

    @Key("v1.search.timelines")
    String v1SearchTimelines();

}
