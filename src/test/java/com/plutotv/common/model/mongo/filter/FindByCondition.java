package com.plutotv.common.model.mongo.filter;

import com.plutotv.common.model.mongo.filter.models.WhereCondition;
import lombok.Data;

@Data
public class FindByCondition {
    private String collection;
    private WhereCondition filter;
}
