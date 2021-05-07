package com.plutotv.common.model.mongo.collections.series;

import lombok.Data;

import java.util.List;

@Data
public class MongoSeries {
    private List<MongoSeriesDatum> data;
}
