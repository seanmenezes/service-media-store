package com.plutotv.common.model.support.v1.search.channels;

import com.plutotv.common.model.bussines.v1.search.channel.SearchChannel;
import com.plutotv.common.model.support.common.Meta;
import lombok.Data;

@Data
public class SearchChannelDatum {
    private Meta meta;
    private SearchChannel payload;
}
