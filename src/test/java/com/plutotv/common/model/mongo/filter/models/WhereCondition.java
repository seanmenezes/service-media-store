package com.plutotv.common.model.mongo.filter.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class WhereCondition {
    @JsonProperty("$where")
    private String where;
}
