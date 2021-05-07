package com.plutotv.common.model.support.v2.channels.episodes;

import com.plutotv.common.model.bussines.v2.channels.Episode;
import com.plutotv.common.model.support.common.Meta;
import lombok.Data;

@Data
public class EpisodesDatum {
    private Meta meta;
    private Episode payload;
}
