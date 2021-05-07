package com.plutotv.common.model.response.v2.channels;

import com.plutotv.common.model.support.v2.channels.episodes.EpisodesDatum;
import lombok.Data;

import java.util.List;

@Data
public class Episodes {
    private List<EpisodesDatum> data;
}
