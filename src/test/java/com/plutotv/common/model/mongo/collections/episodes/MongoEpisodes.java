package com.plutotv.common.model.mongo.collections.episodes;

import lombok.Data;

import java.util.List;

@Data
public class MongoEpisodes {
    private List<MongoEpisodesDatum> data;
}
