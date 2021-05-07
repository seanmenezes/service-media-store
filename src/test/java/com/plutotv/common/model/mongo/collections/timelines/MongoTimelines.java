package com.plutotv.common.model.mongo.collections.timelines;

import lombok.Data;

import java.util.List;

@Data
public class MongoTimelines {
    private List<MongoTimelinesDatum> data;
}
