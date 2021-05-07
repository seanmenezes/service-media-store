package com.plutotv.common.model.support.v1.trending.clips;

import com.plutotv.common.model.bussines.v1.trending.clips.TrendingClip;
import com.plutotv.common.model.support.common.Meta;
import lombok.Data;

@Data
public class TrendingClipsDatum {
    private Meta meta;
    private TrendingClip payload;
}
