package com.banvien.fcv.mobile.dto;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

public class OutletMerDTO implements Serializable {
    private long _id;
    private Long outletId;
    private Long routeScheduleId;
    private Long routeScheduleDetailId;
    private String exhibitRegisteredId;
    private String exhibitRegisteredDetailId;
    private String dataType;
    private String registerValue;
    private String actualValue;

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public Long getOutletId() {
        return outletId;
    }

    public void setOutletId(Long outletId) {
        this.outletId = outletId;
    }

    public Long getRouteScheduleId() {
        return routeScheduleId;
    }

    public void setRouteScheduleId(Long routeScheduleId) {
        this.routeScheduleId = routeScheduleId;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getRegisterValue() {
        return registerValue;
    }

    public void setRegisterValue(String registerValue) {
        this.registerValue = registerValue;
    }

    public String getActualValue() {
        return actualValue;
    }

    public void setActualValue(String actualValue) {
        this.actualValue = actualValue;
    }

    public Long getRouteScheduleDetailId() {
        return routeScheduleDetailId;
    }

    public void setRouteScheduleDetailId(Long routeScheduleDetailId) {
        this.routeScheduleDetailId = routeScheduleDetailId;
    }

    public String getExhibitRegisteredId() {
        return exhibitRegisteredId;
    }

    public void setExhibitRegisteredId(String exhibitRegisteredId) {
        this.exhibitRegisteredId = exhibitRegisteredId;
    }

    public String getExhibitRegisteredDetailId() {
        return exhibitRegisteredDetailId;
    }

    public void setExhibitRegisteredDetailId(String exhibitRegisteredDetailId) {
        this.exhibitRegisteredDetailId = exhibitRegisteredDetailId;
    }
}
