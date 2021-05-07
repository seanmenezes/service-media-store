package com.plutotv.common.model.support.v1.search.timelines;

import lombok.Data;

@Data
public class Channel {
    private String slug;
    private String name;
    private int number;
    private Logo logo;
}
