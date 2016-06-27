package com.banvien.fcv.mobile.db.entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Linh Nguyen on 6/21/2016.
 */
@DatabaseTable(tableName = "RouteSchedule")
public class RouteScheduleEntity {
    @DatabaseField(generatedId = true)
    private Long _id;
    @DatabaseField
    private Long routeScheduleId;
    @DatabaseField
    private Long confirmWoringId;

    public Long get_id() {
        return _id;
    }

    public void set_id(Long _id) {
        this._id = _id;
    }

    public Long getRouteScheduleId() {
        return routeScheduleId;
    }

    public void setRouteScheduleId(Long routeScheduleId) {
        this.routeScheduleId = routeScheduleId;
    }

    public Long getConfirmWoringId() {
        return confirmWoringId;
    }

    public void setConfirmWoringId(Long confirmWoringId) {
        this.confirmWoringId = confirmWoringId;
    }
}
