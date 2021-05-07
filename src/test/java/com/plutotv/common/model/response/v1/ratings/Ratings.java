package com.plutotv.common.model.response.v1.ratings;

import com.plutotv.common.model.support.v1.ratings.RatingsDatum;
import lombok.Data;

import java.util.List;

@Data
public class Ratings {
    private List<RatingsDatum> data;
}
