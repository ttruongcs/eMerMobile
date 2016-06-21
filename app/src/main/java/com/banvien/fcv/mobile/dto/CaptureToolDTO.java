package com.banvien.fcv.mobile.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Linh Nguyen on 6/21/2016.
 */
public class CaptureToolDTO implements Serializable {
    private long _id;
    private Long routeScheduleId;
    private String pathImage;
    private Date createdDate;

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
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
