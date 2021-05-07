/*
 * (c) 2021 Pluto Inc.
 */

package com.plutotv.common.config.mongo;

import org.aeonbits.owner.Config;

@Config.Sources({"classpath:endpoints.properties"})
public interface SteMongoEndpoint extends Config {

    @Key("v1.pluto-main.find")
    String v1PlutoMainFind();
}
