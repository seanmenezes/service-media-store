package com.plutotv.common.model.support.v1.active.regions;

import com.plutotv.common.model.bussines.v1.active.regions.ActiveRegion;
import com.plutotv.common.model.support.common.Meta;
import lombok.Data;

@Data
public class ActiveRegionsDatum {
    private Meta meta;
    private ActiveRegion payload;
}
