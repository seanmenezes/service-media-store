package com.plutotv.common.model.response.v1.active.regions;

import com.plutotv.common.model.support.v1.active.regions.ActiveRegionsDatum;
import lombok.Data;

import java.util.List;

@Data
public class ActiveRegions {
    private List<ActiveRegionsDatum> data;
}
