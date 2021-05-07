package com.plutotv.common.model.response.v1.search.episodes;

import com.plutotv.common.model.support.v1.search.episodes.SearchEpisodeDatum;
import lombok.Data;

import java.util.List;

@Data
public class Episodes {
    private List<SearchEpisodeDatum> data;
}
