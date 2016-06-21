package com.banvien.fcv.mobile.dto.routeschedule;

import java.io.Serializable;

/**
 * Created by Linh Nguyen on 6/21/2016.
 */
public class RouteScheduleDTO implements Serializable {
    private Long _id;
    private Long routeScheduleId;

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
}
