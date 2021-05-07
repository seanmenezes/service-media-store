package com.plutotv.common.model.bussines.v1.trending.clips;

import lombok.Data;

@Data
public class TrendingClip {
    private String id;
    private String name;
    private String authorName;
    private String url;
    private String thumbnail;
    private Integer duration;
    private String createdAt;
    private String updatedAt;
}
