package com.plutotv.common.model.support.v1.vod.category_entries;

import com.plutotv.common.model.bussines.v1.vod.category_entries.VodCategoryEntry;
import com.plutotv.common.model.support.common.Meta;
import lombok.Data;

@Data
public class VodCategoryEntriesDatum {
    private Meta meta;
    private VodCategoryEntry payload;
}
