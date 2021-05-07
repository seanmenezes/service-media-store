package com.plutotv.common.model.mongo.common.filter;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class FilterByWhereCondition {
    @JsonProperty("$where")
    private String where;
}
