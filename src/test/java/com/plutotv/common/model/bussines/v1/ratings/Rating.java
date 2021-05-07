package com.plutotv.common.model.bussines.v1.ratings;

import lombok.Data;

@Data
public class Rating {
    private String id;
    private String value;
    private String region;
    private Integer weight;
}
