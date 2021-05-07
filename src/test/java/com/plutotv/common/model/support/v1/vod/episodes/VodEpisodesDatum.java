package com.plutotv.common.model.support.v1.vod.episodes;

import com.plutotv.common.model.bussines.v1.vod.episode.VodEpisode;
import com.plutotv.common.model.support.common.Meta;
import lombok.Data;

@Data
public class VodEpisodesDatum {
    private Meta meta;
    private VodEpisode payload;
}
