package com.plutotv.common.model.support.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.plutotv.common.model.support.v1.vod.series.AVOD;
import lombok.Data;

import java.util.List;

@Data
public class AvailabilityWindows {
    @JsonProperty("AVOD")
    private List<AVOD> aVOD;
}
