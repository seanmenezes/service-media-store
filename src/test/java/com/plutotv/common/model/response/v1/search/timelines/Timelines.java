package com.plutotv.common.model.response.v1.search.timelines;

import com.plutotv.common.model.support.v1.search.timelines.SearchTimelinesDatum;
import lombok.Data;

import java.util.List;

@Data
public class Timelines {
    private List<SearchTimelinesDatum> data;
}
