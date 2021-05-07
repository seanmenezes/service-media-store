/*
 * (c) 2021 Pluto Inc.
 */

package com.plutotv.common.config.v1.active.regions;

import org.aeonbits.owner.Config;

@Config.Sources({"classpath:endpoints.properties"})
public interface ActiveRegionsEndpointV1 extends Config {

    @Key("v1.active-regions")
    String v1ActiveRegions();

}
