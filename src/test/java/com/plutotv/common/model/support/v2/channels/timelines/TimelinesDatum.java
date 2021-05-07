package com.plutotv.common.model.support.v2.channels.timelines;

import com.plutotv.common.model.bussines.v2.channels.Timeline;
import com.plutotv.common.model.support.common.Meta;
import lombok.Data;

@Data
public class TimelinesDatum {
    private Meta meta;
    private Timeline payload;
}
