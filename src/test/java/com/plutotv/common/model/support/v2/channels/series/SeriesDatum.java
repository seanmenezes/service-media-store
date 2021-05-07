package com.plutotv.common.model.support.v2.channels.series;

import com.plutotv.common.model.bussines.v2.channels.Serie;
import com.plutotv.common.model.support.common.Meta;
import lombok.Data;

@Data
public class SeriesDatum {
    private Meta meta;
    private Serie payload;
}
