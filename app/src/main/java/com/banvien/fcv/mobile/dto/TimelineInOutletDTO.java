package com.banvien.fcv.mobile.dto;

/**
 * Created by Linh Nguyen on 6/14/2016.
 */
public class TimelineInOutletDTO {
    private String title;
    private String detail;
    private String order;
    private String type;
    private Long outletId;
    private Long routeScheduleDetailId;
    private int isDone;    //0: Not done, 1: Continue, 2: Done
    private boolean isHeader;
    private boolean isFooter;

    public TimelineInOutletDTO() {};

    public TimelineInOutletDTO(String title, String detail,
                               String order, String type, int isDone, Long outletId, Long routeScheduleDetailId) {
        this.title = title;
        this.detail = detail;
        this.order = order;
        this.type = type;
        this.isDone = isDone;
        this.outletId = outletId;
        this.routeScheduleDetailId = routeScheduleDetailId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public int getIsDone() {
        return isDone;
    }

    public void setIsDone(int isDone) {
        this.isDone = isDone;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getOutletId() {
        return outletId;
    }

    public void setOutletId(Long outletId) {
        this.outletId = outletId;
    }

    public boolean isHeader() {
        return isHeader;
    }

    public void setHeader(boolean header) {
        isHeader = header;
    }

    public boolean isFooter() {
        return isFooter;
    }

    public void setFooter(boolean footer) {
        isFooter = footer;
    }

    public Long getRouteScheduleDetailId() {
        return routeScheduleDetailId;
    }

    public void setRouteScheduleDetailId(Long routeScheduleDetailId) {
        this.routeScheduleDetailId = routeScheduleDetailId;
    }
}
