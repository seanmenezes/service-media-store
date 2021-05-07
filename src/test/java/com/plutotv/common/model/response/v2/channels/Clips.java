package com.plutotv.common.model.response.v2.channels;

import com.plutotv.common.model.support.v2.channels.clips.ClipsDatum;
import lombok.Data;

import java.util.List;

@Data
public class Clips {
    private List<ClipsDatum> data;
}
