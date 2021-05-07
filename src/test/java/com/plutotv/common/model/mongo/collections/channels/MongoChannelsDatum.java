package com.plutotv.common.model.mongo.collections.channels;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class MongoChannelsDatum {
    @JsonProperty("_id")
    private String id;
    private String metadataLanguage;
}
