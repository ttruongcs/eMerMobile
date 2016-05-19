package com.banvien.fcv.mobile.dto;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

public class OutletMerDTO implements Serializable {
    private long _id;
    private String outletId;
    private String routeScheduleId;
    private String routeScheduleDetailId;
    private String dataType;
    private String registerValue;
    private String actualValue;

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public String getOutletId() {
        return outletId;
    }

    public void setOutletId(String outletId) {
        this.outletId = outletId;
    }

    public String getRouteScheduleId() {
        return routeScheduleId;
    }

    public void setRouteScheduleId(String routeScheduleId) {
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

    public String getRouteScheduleDetailId() {
        return routeScheduleDetailId;
    }

    public void setRouteScheduleDetailId(String routeScheduleDetailId) {
        this.routeScheduleDetailId = routeScheduleDetailId;
    }
}
