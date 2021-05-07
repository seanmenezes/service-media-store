package com.plutotv.common.model.response.v1.ratings.descriptors;

import com.plutotv.common.model.support.v1.ratings.RatingDescriptorsDatum;
import lombok.Data;

import java.util.List;

@Data
public class RatingsDescriptors {
    private List<RatingDescriptorsDatum> data;
}
