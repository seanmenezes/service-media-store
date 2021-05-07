package com.plutotv.common.model.support.v1.vod.categories;

import com.plutotv.common.model.bussines.v1.vod.category.VodCategory;
import com.plutotv.common.model.support.common.Meta;
import lombok.Data;

@Data
public class VodCategoriesDatum {
    private Meta meta;
    private VodCategory payload;
}
