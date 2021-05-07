package com.plutotv.common.model.bussines.v1.search.timeline;

import com.plutotv.common.model.support.common.DistributeAs;
import com.plutotv.common.model.support.common.Distribution;
import com.plutotv.common.model.support.common.Image;
import com.plutotv.common.model.support.common.RegionFilter;
import com.plutotv.common.model.support.v1.search.timelines.Channel;
import lombok.Data;

import java.util.List;

@Data
public class SearchTimeline {
    private String slug;
    private String episodeID;
    private String seriesID;
    private String name;
    private List<Image> images;
    private String start;
    private String stop;
    private Channel channel;
    private String createdAt;
    private String updatedAt;
    private RegionFilter regionFilter;
    private boolean plutoOfficeOnly;
    private Distribution distribution;
    private String rating;
    private DistributeAs distributeAs;
    private String metadataLanguage;
}
