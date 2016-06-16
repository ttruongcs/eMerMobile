package com.banvien.fcv.mobile.db.entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by hieu on 3/10/2016.
 */
@DatabaseTable(tableName = "OutletMer")
public class OutletMerEntity implements Serializable {
    @DatabaseField(generatedId = true)
    private long _id;
    @DatabaseField
    private Long outletId;
    @DatabaseField
    private Long routeScheduleId;
    @DatabaseField
    private Long outletModelId;
    @DatabaseField
    private String outletModelName;
    @DatabaseField
    private Long routeScheduleDetailId;
    @DatabaseField
    private String exhibitRegisteredId;
    @DatabaseField
    private String exhibitRegisteredDetailId;
    @DatabaseField
    private String dataType;
    @DatabaseField
    private String registerValue;
    @DatabaseField
    private String actualValue;
    @DatabaseField
    private String referenceValue;

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
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

    public String getReferenceValue() {
        return referenceValue;
    }

    public void setReferenceValue(String referenceValue) {
        this.referenceValue = referenceValue;
    }

    public Long getOutletModelId() {
        return outletModelId;
    }

    public void setOutletModelId(Long outletModelId) {
        this.outletModelId = outletModelId;
    }

    public String getOutletModelName() {
        return outletModelName;
    }

    public void setOutletModelName(String outletModelName) {
        this.outletModelName = outletModelName;
    }
}
