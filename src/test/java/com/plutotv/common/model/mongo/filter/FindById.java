package com.plutotv.common.model.mongo.filter;

import com.plutotv.common.model.mongo.filter.models.ById;
import lombok.Data;

@Data
public class FindById {
    private String collection;
    private ById filter;
}
