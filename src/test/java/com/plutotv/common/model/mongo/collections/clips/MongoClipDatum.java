package com.plutotv.common.model.mongo.collections.clips;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class MongoClipDatum {
    @JsonProperty("_id")
    private String id;
    private List<String> tags;
}
