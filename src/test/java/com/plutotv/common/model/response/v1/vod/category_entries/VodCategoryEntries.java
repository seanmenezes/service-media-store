package com.plutotv.common.model.response.v1.vod.category_entries;

import com.plutotv.common.model.support.v1.vod.category_entries.VodCategoryEntriesDatum;
import lombok.Data;

import java.util.List;

@Data
public class VodCategoryEntries {
    private List<VodCategoryEntriesDatum> data;
}
