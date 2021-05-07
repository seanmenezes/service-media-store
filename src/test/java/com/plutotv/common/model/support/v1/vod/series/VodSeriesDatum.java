package com.plutotv.common.model.support.v1.vod.series;

import com.plutotv.common.model.bussines.v1.vod.series.VodSerie;
import com.plutotv.common.model.support.common.Meta;
import lombok.Data;

@Data
public class VodSeriesDatum {
    private Meta meta;
    private VodSerie payload;
}
