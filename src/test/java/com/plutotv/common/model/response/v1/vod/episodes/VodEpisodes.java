package com.plutotv.common.model.response.v1.vod.episodes;

import com.plutotv.common.model.support.v1.vod.episodes.VodEpisodesDatum;
import lombok.Data;

import java.util.List;

@Data
public class VodEpisodes {
    private List<VodEpisodesDatum> data;
}
