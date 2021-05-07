package com.plutotv.common.model.bussines.v2.channels;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.plutotv.common.model.support.common.FeaturedImage;
import com.plutotv.common.model.support.common.Tile;
import lombok.Data;

@Data
public class Serie {
    @JsonProperty("_id")
    private String id;
    private String name;
    private String slug;
    private String type;
    private Tile tile;
    private String description;
    private String summary;
    private FeaturedImage featuredImage;
    private String updatedAt;
}
