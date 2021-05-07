package com.plutotv.common.model.support.v1.search.timelines;

import com.plutotv.common.model.bussines.v1.search.timeline.SearchTimeline;
import com.plutotv.common.model.support.common.Meta;
import lombok.Data;

@Data
public class SearchTimelinesDatum {
    private Meta meta;
    private SearchTimeline payload;
}
