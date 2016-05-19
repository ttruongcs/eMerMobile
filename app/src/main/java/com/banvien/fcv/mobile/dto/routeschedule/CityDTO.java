package com.banvien.fcv.mobile.dto.routeschedule;

import java.io.Serializable;

/**
 * E-Retailer System Platform - Copyright (c) by Ban Vien Co., Ltd. All rights reserved.
 * User: viennh
 * Date: 11/30/15
 * Time: 2:15 PM
 */
public class CityDTO implements Serializable {
    private Long cityId;
    private String name;
    private RegionDTO region;

    public CityDTO() {
    }

    public CityDTO(Long cityId, String name) {
        this.cityId = cityId;
        this.name = name;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public RegionDTO getRegion(){
        return region ;
    }

    public void setRegion(RegionDTO region)
    {
        this.region = region;
    }

}
