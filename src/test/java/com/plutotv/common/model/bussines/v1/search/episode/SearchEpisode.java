package com.plutotv.common.model.bussines.v1.search.episode;

import com.plutotv.common.model.support.common.*;
import lombok.Data;

import java.util.List;

@Data
public class SearchEpisode {
    private String description;
    private String slug;
    private String rating;
    private String genre;
    private String type;
    private String name;
    private List<Image> images;
    private String createdAt;
    private String updatedAt;
    private String status;
    private DistributeAs distributeAs;
    private AvailabilityWindows availabilityWindows;
    private RegionFilter regionFilter;
    private Distribution distribution;
    private String metadataLanguage;
    private boolean plutoOfficeOnly;
}
