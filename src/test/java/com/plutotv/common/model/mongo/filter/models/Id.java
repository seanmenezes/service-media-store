package com.plutotv.common.model.mongo.filter.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Id {
    @JsonProperty("$oid")
    private String id;
}
