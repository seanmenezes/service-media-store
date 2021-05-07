package com.plutotv.common.model.support.v2.channels;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Source {
    @JsonProperty("_id")
    private String id;
    private String type;
    private String file;
}
