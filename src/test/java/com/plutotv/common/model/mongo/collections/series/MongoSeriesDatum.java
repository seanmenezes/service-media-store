package com.plutotv.common.model.mongo.collections.series;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class MongoSeriesDatum {
    @JsonProperty("_id")
    private String id;
    private String metadataLanguage;
}
