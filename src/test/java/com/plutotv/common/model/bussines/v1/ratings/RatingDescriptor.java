package com.plutotv.common.model.bussines.v1.ratings;

import lombok.Data;

@Data
public class RatingDescriptor {
    private String id;
    private String displayName;
    private String label;
    private String region;
    private String slug;
}
