package com.plutotv.common.model.support.v2.channels.clips;

import com.plutotv.common.model.bussines.v2.channels.Clip;
import com.plutotv.common.model.support.common.Meta;
import lombok.Data;

@Data
public class ClipsDatum {
    private Meta meta;
    private Clip payload;
}
