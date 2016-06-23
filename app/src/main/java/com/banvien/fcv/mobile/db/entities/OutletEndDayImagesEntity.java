package com.banvien.fcv.mobile.db.entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by hieu on 3/10/2016.
 */
@DatabaseTable(tableName = "OutletEndDayImages")
public class OutletEndDayImagesEntity implements Serializable {
    @DatabaseField(generatedId = true)
    private long _id;
    @DatabaseField
    private Long routeScheduleDetailId;
    @DatabaseField
    private String pathImage;

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public Long getRouteScheduleDetailId() {
        return routeScheduleDetailId;
    }

    public void setRouteScheduleDetailId(Long routeScheduleDetailId) {
        this.routeScheduleDetailId = routeScheduleDetailId;
    }

    public String getPathImage() {
        return pathImage;
    }

    public void setPathImage(String pathImage) {
        this.pathImage = pathImage;
    }
}
