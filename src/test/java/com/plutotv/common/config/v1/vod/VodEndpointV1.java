/*
 * (c) 2021 Pluto Inc.
 */

package com.plutotv.common.config.v1.vod;

import org.aeonbits.owner.Config;

@Config.Sources({"classpath:endpoints.properties"})
public interface VodEndpointV1 extends Config {

    @Key("v1.vod.categories")
    String v1VodCategories();

    @Key("v1.vod.category-entries")
    String v1VodCategoryEntries();

    @Key("v1.vod.episodes")
    String v1VodEpisodes();

    @Key("v1.vod.series")
    String v1VodSeries();

}
