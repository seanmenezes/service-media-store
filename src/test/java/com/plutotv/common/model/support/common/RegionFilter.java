package com.plutotv.common.model.support.common;

import lombok.Data;

import java.util.List;

@Data
public class RegionFilter {
    private List<String> include;
    private List<String> exclude;
}
