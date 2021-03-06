package com.banvien.fcv.mobile.dto;

import android.text.Html;
import android.text.TextUtils;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by hieu on 3/10/2016.
 */

public class OutletDTO implements Serializable {
    private long _id;
    private Long outletId;
    private String name;
    private String code;
    private String dCode;
    private String dName;
    private String typeName;
    private String locationNo;
    private String street;
    private String district;
    private String ward;
    private String cityName;
    private Double lat;
    private Double lg;
    private Integer status;
    private Long routeScheduleId;
    private Long routeScheduleDetailId;
    private String note;
    private Integer activeStatus;

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
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

    public String getdCode() {
        return dCode;
    }

    public void setdCode(String dCode) {
        this.dCode = dCode;
    }

    public String getdName() {
        return dName;
    }

    public void setdName(String dName) {
        this.dName = dName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Long getOutletId() {
        return outletId;
    }

    public void setOutletId(Long outletId) {
        this.outletId = outletId;
    }

    public String getLocationNo() {
        return locationNo;
    }

    public void setLocationNo(String locationNo) {
        this.locationNo = locationNo;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLg() {
        return lg;
    }

    public void setLg(Double lg) {
        this.lg = lg;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getRouteScheduleId() {
        return routeScheduleId;
    }

    public void setRouteScheduleId(Long routeScheduleId) {
        this.routeScheduleId = routeScheduleId;
    }

    public Long getRouteScheduleDetailId() {
        return routeScheduleDetailId;
    }

    public void setRouteScheduleDetailId(Long routeScheduleDetailId) {
        this.routeScheduleDetailId = routeScheduleDetailId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Integer getActiveStatus() {
        return activeStatus;
    }

    public void setActiveStatus(Integer activeStatus) {
        this.activeStatus = activeStatus;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        if(name != null) {
            stringBuilder.append(name);
        }
        String fullAddress = "";
        stringBuilder.append(" - ").append(code).append("\n");
        if(!(locationNo == null && street == null && ward == null && cityName == null)) {
            stringBuilder.append("Địa chỉ: ");
            if(locationNo != null && !TextUtils.isEmpty(locationNo)) {
                stringBuilder.append(locationNo).append(", ");
            }
            if(street != null && !TextUtils.isEmpty(street)) {
                stringBuilder.append(street).append(", ");
            }
            if(ward != null && !TextUtils.isEmpty(ward)) {
                stringBuilder.append(ward).append(", ");
            }
            if(cityName != null && !TextUtils.isEmpty(cityName)) {
                stringBuilder.append(cityName);
            }
            fullAddress = stringBuilder.toString();

            if(fullAddress.substring(fullAddress.length() - 1).trim().equals(",")) {
                fullAddress = fullAddress.substring(0, fullAddress.length() - 1);
            }
        }




        return fullAddress;
    }
}
