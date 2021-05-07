package com.plutotv.common.model.bussines.v1.vod.series;

import com.plutotv.common.model.support.common.FeaturedImage;
import com.plutotv.common.model.support.common.Poster;
import com.plutotv.common.model.support.common.Tile;
import com.plutotv.common.model.support.common.AvailabilityWindows;
import com.plutotv.common.model.support.v1.vod.series.Season;
import lombok.Data;

import java.util.List;

@Data
public class VodSerie {
    private String id;
    private String name;
    private String summary;
    private String type;
    private String genre;
    private String description;
    private String rating;
    private List<String> ratingDescriptors;
    private FeaturedImage featuredImage;
    private Poster poster;
    private List<Season> seasons;
    private String slug;
    private Tile tile;
    private List<String> regionFilter;
    private AvailabilityWindows availabilityWindows;
    private String updatedAt;
}
