package com.plutotv.common.model.response.v1.search.series;

import com.plutotv.common.model.support.v1.search.series.SearchSeriesDatum;
import lombok.Data;

import java.util.List;

@Data
public class Series {
    private List<SearchSeriesDatum> data;
}
