package com.plutotv.common.model.mongo.filter.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ById {
    @JsonProperty("_id")
    private Id id;
}
