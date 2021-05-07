package com.plutotv.common.model.bussines.v1.search.series;

import com.plutotv.common.model.support.common.Distribution;
import com.plutotv.common.model.support.common.Image;
import com.plutotv.common.model.support.common.RegionFilter;
import lombok.Data;

import java.util.List;

@Data
public class SearchSeries {
    private String slug;
    private String name;
    private String description;
    private String type;
    private String rating;
    private String metadataLanguage;
    private List<Image> images;
    private boolean plutoOfficeOnly;
    private String createdAt;
    private String updatedAt;
    private RegionFilter regionFilter;
    private Distribution distribution;
}
