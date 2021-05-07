package com.plutotv.common.model.mongo.collections.timelines;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class MongoTimelinesDatum {
    @JsonProperty("__v")
    private int v;
    @JsonProperty("_id")
    private String id;
    private String channel;
    private String createdAt;
    private String episode;
    private boolean featured;
    private String start;
    private String stop;
    private String updatedAt;
}
