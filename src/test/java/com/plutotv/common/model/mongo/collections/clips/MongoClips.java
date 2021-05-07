package com.plutotv.common.model.mongo.collections.clips;

import lombok.Data;

import java.util.List;

@Data
public class MongoClips {
    private List<MongoClipDatum> data;
}
