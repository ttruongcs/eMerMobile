package com.banvien.fcv.mobile.dto.routeschedule;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created with IntelliJ IDEA.
 * User: Linh Nguyen
 * Date: 5/11/16
 * Time: 3:12 PM
 * To change this template use File | Settings | File Templates.
 */
public class MRouteScheduleDetailDTO implements Serializable {
    private Long routeScheduleDetailId;
    private MOutletDTO outlet;
    private Timestamp date;
    private Integer weekDays;
    private Integer week;
    private Integer month;
    private Integer year;
    private Integer active;


    public Long getRouteScheduleDetailId() {
        return routeScheduleDetailId;
    }

    public void setRouteScheduleDetailId(Long routeScheduleDetailId) {
        this.routeScheduleDetailId = routeScheduleDetailId;
    }

    public MOutletDTO getOutlet() {
        return outlet;
    }

    public void setOutlet(MOutletDTO outlet) {
        this.outlet = outlet;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public Integer getWeekDays() {
        return weekDays;
    }

    public void setWeekDays(Integer weekDays) {
        this.weekDays = weekDays;
    }

    public Integer getWeek() {
        return week;
    }

    public void setWeek(Integer week) {
        this.week = week;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }
}
