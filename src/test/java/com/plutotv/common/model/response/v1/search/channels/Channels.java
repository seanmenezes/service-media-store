package com.plutotv.common.model.response.v1.search.channels;

import com.plutotv.common.model.support.v1.search.channels.SearchChannelDatum;
import lombok.Data;

import java.util.List;

@Data
public class Channels {
    private List<SearchChannelDatum> data;
}
