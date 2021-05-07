package com.plutotv.common.model.bussines.v2.channels;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.plutotv.common.model.support.v2.channels.Source;
import lombok.Data;

import java.util.List;

@Data
public class Clip {
    @JsonProperty("_id")
    private String id;
    private String name;
    private String partner;
    private String author;
    private String thumbnail;
    private Boolean liveBroadcast;
    private String provider;
    private String code;
    private String internalCode;
    private String url;
    private List<String> tags;
    private List<Source> sources;
    private String updatedAt;
}
