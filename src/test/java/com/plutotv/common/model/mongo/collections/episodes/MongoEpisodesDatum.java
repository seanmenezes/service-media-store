package com.plutotv.common.model.mongo.collections.episodes;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class MongoEpisodesDatum {
    @JsonProperty("_id")
    private String id;
    private String metadataLanguage;
}
