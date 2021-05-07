package com.plutotv.common.model.bussines.v1.vod.episode;

import com.plutotv.common.model.support.common.AvailabilityWindows;
import com.plutotv.common.model.support.common.Poster;
import com.plutotv.common.model.support.v1.vod.episodes.Screenshot16_9;
import com.plutotv.common.model.support.v1.vod.episodes.Screenshot4_3;
import lombok.Data;

import java.util.List;

@Data
public class VodEpisode {
    private String id;
    private String type;
    private String name;
    private String description;
    private Integer duration;
    private Integer originalContentDuration;
    private Integer adPodsDuration;
    private Integer number;
    private Integer season;
    private String slug;
    private String seriesID;
    private Screenshot4_3 screenshot4_3;
    private Screenshot16_9 screenshot16_9;
    private Poster poster;
    private String genre;
    private Integer allotment;
    private String rating;
    private List<String> regionFilter;
    private List<String> ratingDescriptors;
    private AvailabilityWindows availabilityWindows;
    private String updatedAt;
}
