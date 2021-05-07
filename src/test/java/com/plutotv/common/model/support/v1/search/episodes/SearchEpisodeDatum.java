package com.plutotv.common.model.support.v1.search.episodes;

import com.plutotv.common.model.bussines.v1.search.episode.SearchEpisode;
import com.plutotv.common.model.support.common.Meta;
import lombok.Data;

@Data
public class SearchEpisodeDatum {
    private Meta meta;
    private SearchEpisode payload;
}
