package com.plutotv.common.model.response.v1.vod.categories;

import com.plutotv.common.model.support.v1.vod.categories.VodCategoriesDatum;
import lombok.Data;

import java.util.List;

@Data
public class VodCategories {
    private List<VodCategoriesDatum> data;
}
