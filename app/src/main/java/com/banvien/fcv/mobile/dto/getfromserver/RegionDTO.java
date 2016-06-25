package com.banvien.fcv.mobile.dto.getfromserver;

import java.io.Serializable;

public class RegionDTO implements Serializable {
    private Long regionId;
    private String name;

    public RegionDTO(long regionId, String name) {
        this.regionId = regionId;
        this.name = name;
    }

    public RegionDTO() {
    }

    public Long getRegionId() {
        return regionId;
    }

    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
