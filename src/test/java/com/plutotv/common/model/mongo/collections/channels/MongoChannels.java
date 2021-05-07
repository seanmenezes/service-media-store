package com.plutotv.common.model.mongo.collections.channels;

import lombok.Data;

import java.util.List;

@Data
public class MongoChannels {
    private List<MongoChannelsDatum> data;
}
