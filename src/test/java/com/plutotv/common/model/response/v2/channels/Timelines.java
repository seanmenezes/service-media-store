package com.plutotv.common.model.response.v2.channels;

import com.plutotv.common.model.support.v2.channels.timelines.TimelinesDatum;
import lombok.Data;

import java.util.List;

@Data
public class Timelines {
    private List<TimelinesDatum> data;
}
