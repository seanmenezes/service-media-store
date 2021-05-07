package com.plutotv.common.model.support.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class DistributeAs {
    @JsonProperty("AVOD")
    private Boolean aVOD;
}
