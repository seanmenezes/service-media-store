package com.plutotv.common.model.response.v1.trending.clips;

import com.plutotv.common.model.support.v1.trending.clips.TrendingClipsDatum;
import lombok.Data;

import java.util.List;

@Data
public class TrendingClips {
    private List<TrendingClipsDatum> data;
}
