package com.banvien.fcv.mobile.dto.routeschedule;

import java.io.Serializable;

public class DistributorDTO implements Serializable {
    private Long distributorId;
    private String name;
    private String code;
    private int status;
    private RegionDTO region;
    private Integer maxBudget;

    public DistributorDTO(Long distributorId, String name, String code, Long regionId, String regionName ) {
        this.distributorId = distributorId;
        this.name = name;
        this.code = code;
        this.region = new RegionDTO(regionId, regionName);
    }

    public DistributorDTO() {
    }

    public Long getDistributorId() {
        return distributorId;
    }

    public void setDistributorId(Long distributorId) {
        this.distributorId = distributorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public RegionDTO getRegion() {
        return region;
    }

    public void setRegion(RegionDTO region) {
        this.region = region;
    }

    public Integer getMaxBudget() {
        return maxBudget;
    }

    public void setMaxBudget(Integer maxBudget) {
        this.maxBudget = maxBudget;
    }
}
