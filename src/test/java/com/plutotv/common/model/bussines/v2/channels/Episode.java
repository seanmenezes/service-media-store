package com.plutotv.common.model.bussines.v2.channels;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.plutotv.common.model.support.common.DistributeAs;
import com.plutotv.common.model.support.common.FeaturedImage;
import com.plutotv.common.model.support.common.Poster;
import com.plutotv.common.model.support.v2.channels.*;
import com.plutotv.common.model.support.v2.channels.episodes.EpisodeSource;
import lombok.Data;

import java.util.List;

@Data
public class Episode {
    @JsonProperty("_id")
    private String id;
    private Integer number;
    private String description;
    private Integer duration;
    private Integer originalContentDuration;
    private String genre;
    private String subGenre;
    private DistributeAs distributeAs;
    private String rating;
    private String name;
    private String slug;
    private Poster poster;
    private String firstAired;
    private Thumbnail thumbnail;
    private Boolean liveBroadcast;
    private FeaturedImage featuredImage;
    private List<EpisodeSource> sources;
    private String seriesId;
    private String displayOnGuide;
    private String updatedAt;
}
