package com.banvien.fcv.mobile.db.entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by hieu on 3/10/2016.
 */
@DatabaseTable(tableName = "OutletRegistered")
public class OutletRegisteredEntity implements Serializable {
    @DatabaseField(generatedId = true)
    private Long _id;
    @DatabaseField
    private Long outletId;
    @DatabaseField
    private String exhibitRegisteredId;
    @DatabaseField
    private String exhibitRegisteredDetailId;
    @DatabaseField
    private Long outletModelId;
    @DatabaseField
    private String dataType;
    @DatabaseField
    private String registerValue;

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
