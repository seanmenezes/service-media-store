package com.plutotv.common.model.support.v2.channels.episodes;

import com.plutotv.common.model.support.v2.channels.AdPod;
import lombok.Data;

import java.util.List;

@Data
public class EpisodeSource {
    private String clipId;
    private Integer duration;
    private Integer inPoint;
    private Integer outPoint;
    private List<AdPod> adPods;
}
