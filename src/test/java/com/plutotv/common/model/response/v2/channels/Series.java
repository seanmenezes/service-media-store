package com.plutotv.common.model.response.v2.channels;

import com.plutotv.common.model.support.v2.channels.series.SeriesDatum;
import lombok.Data;

import java.util.List;

@Data
public class Series {
    private List<SeriesDatum> data;
}
