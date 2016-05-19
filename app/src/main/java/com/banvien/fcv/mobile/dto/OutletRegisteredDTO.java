package com.banvien.fcv.mobile.dto;

import java.io.Serializable;

/**
 * Created by hieu on 3/10/2016.
 */

public class OutletRegisteredDTO implements Serializable {
    private Long _id;
    private Long outletId;
    private String exhibitRegisteredId;
    private String exhibitRegisteredDetailId;
    private Long outletModelId;
    private String dataType;
    private String registerValue;

    public Long get_id() {
        return _id;
    }

    public void set_id(Long _id) {
        this._id = _id;
    }

    public Long getOutletId() {
        return outletId;
    }

    public void setOutletId(Long outletId) {
        this.outletId = outletId;
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

    public Long getOutletModelId() {
        return outletModelId;
    }

    public void setOutletModelId(Long outletModelId) {
        this.outletModelId = outletModelId;
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
}
