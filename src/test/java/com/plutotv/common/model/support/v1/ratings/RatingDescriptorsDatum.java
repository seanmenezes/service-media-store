package com.plutotv.common.model.support.v1.ratings;

import com.plutotv.common.model.bussines.v1.ratings.RatingDescriptor;
import com.plutotv.common.model.support.common.Meta;
import lombok.Data;

@Data
public class RatingDescriptorsDatum {
    private Meta meta;
    private RatingDescriptor payload;
}
