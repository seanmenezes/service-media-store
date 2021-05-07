package com.plutotv.common.model.support.v2.channels;

import lombok.Data;

@Data
public class AdPod {
    private Integer startAt;
    private Integer duration;
    private String podType;
    private Boolean partnerProvided;
    private Boolean durationLocked;
    private Boolean verified;
}
