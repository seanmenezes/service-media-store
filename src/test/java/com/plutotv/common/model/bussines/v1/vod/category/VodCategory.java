package com.plutotv.common.model.bussines.v1.vod.category;

import com.plutotv.common.model.support.common.Distribution;
import com.plutotv.common.model.support.common.RegionFilter;
import com.plutotv.common.model.support.v1.vod.categories.*;
import lombok.Data;

@Data
public class VodCategory {
    private String id;
    private String name;
    private String description;
    private Boolean plutoOfficeOnly;
    private Integer order;
    private RegionFilter regionFilter;
    private Distribution distribution;
    private IconPng iconPng;
    private IconSvg iconSvg;
    private ImageFeatured imageFeatured;
    private String updatedAt;
}
