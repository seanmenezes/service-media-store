package com.plutotv.common.model.bussines.v1.active.regions;

import lombok.Data;

import java.util.List;

@Data
public class ActiveRegion {
    private String id;
    private String updatedAt;
    private String code;
    private String name;
    private List<String> territories;
}
