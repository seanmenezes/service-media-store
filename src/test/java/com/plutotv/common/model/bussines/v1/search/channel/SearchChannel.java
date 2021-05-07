package com.plutotv.common.model.bussines.v1.search.channel;

import com.plutotv.common.model.support.common.Distribution;
import com.plutotv.common.model.support.common.Image;
import com.plutotv.common.model.support.common.RegionFilter;
import lombok.Data;

import java.util.List;

@Data
public class SearchChannel {
    private String slug;
    private String name;
    private int number;
    private String summary;
    private List<Image> images;
    private String createdAt;
    private String updatedAt;
    private RegionFilter regionFilter;
    private boolean plutoOfficeOnly;
    private Distribution distribution;
    private String metadataLanguage;
}
