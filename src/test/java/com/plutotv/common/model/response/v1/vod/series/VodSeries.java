package com.plutotv.common.model.response.v1.vod.series;

import com.plutotv.common.model.support.v1.vod.series.VodSeriesDatum;
import lombok.Data;

import java.util.List;

@Data
public class VodSeries {
    private List<VodSeriesDatum> data;
}
