/*
 * (c) 2021 Pluto Inc.
 */

package com.plutotv.common.config.v1.ratings;

import org.aeonbits.owner.Config;

@Config.Sources({"classpath:endpoints.properties"})
public interface RatingsEndpointV1 extends Config {

    @Key("v1.ratings")
    String v1Ratings();

    @Key("v1.ratings.descriptors")
    String v1RatingsDescriptors();
}
