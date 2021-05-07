package com.plutotv.common.model.bussines.v1.vod.category_entries;

import lombok.Data;

@Data
public class VodCategoryEntry {
    private String id;
    private String type;
    private String categoryID;
    private String seriesID;
    private String episodeID;
    private String updatedAt;
}
