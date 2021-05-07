package com.plutotv.common.model.support.v1.search.series;

import com.plutotv.common.model.bussines.v1.search.series.SearchSeries;
import com.plutotv.common.model.support.common.Meta;
import lombok.Data;

@Data
public class SearchSeriesDatum {
    private Meta meta;
    private SearchSeries payload;
}
