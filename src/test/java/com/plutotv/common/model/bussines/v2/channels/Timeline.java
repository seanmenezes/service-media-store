package com.plutotv.common.model.bussines.v2.channels;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Timeline {
    @JsonProperty("_id")
    private String id;
    private String channel;
    private String episodeId;
    private String start;
    private String stop;
    private String updatedAt;
}
