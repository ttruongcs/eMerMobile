package com.banvien.fcv.mobile.db.entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

/**
 * Created by Linh Nguyen on 6/23/2016.
 */
@DatabaseTable(tableName = "CaptureAfter")
public class CaptureAfterEntity {
    @DatabaseField(generatedId = true)
    private long _id;
    @DatabaseField
    private long outletId;
    @DatabaseField
    private Long routeScheduleId;
    @DatabaseField
    private String pathImage;
    @DatabaseField
    private Date createdDate;

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public long getOutletId() {
        return outletId;
    }

    public void setOutletId(long outletId) {
        this.outletId = outletId;
    }

    public Long getRouteScheduleId() {
        return routeScheduleId;
    }

    public void setRouteScheduleId(Long routeScheduleId) {
        this.routeScheduleId = routeScheduleId;
    }

    public String getPathImage() {
        return pathImage;
    }

    public void setPathImage(String pathImage) {
        this.pathImage = pathImage;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}
