package com.plutotv.common.model.support.v1.ratings;

import com.plutotv.common.model.bussines.v1.ratings.Rating;
import com.plutotv.common.model.support.common.Meta;
import lombok.Data;

@Data
public class RatingsDatum {
    private Meta meta;
    private Rating payload;
}
