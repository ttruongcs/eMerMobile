package com.banvien.fcv.mobile.dto.routeschedule;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Linh Nguyen
 * Date: 5/6/16
 * Time: 2:57 PM
 * To change this template use File | Settings | File Templates.
 */
public class RouteScheduleInfoDTO implements Serializable{
    private Long routeScheduleId;
    @JsonProperty("routeScheduleDetails")
    private List<MRouteScheduleDetailDTO> routeScheduleDetails = new ArrayList<>();;

    public Long getRouteScheduleId() {
        return routeScheduleId;
    }

    public void setRouteScheduleId(Long routeScheduleId) {
        this.routeScheduleId = routeScheduleId;
    }

    public List<MRouteScheduleDetailDTO> getRouteScheduleDetails() {
        return routeScheduleDetails;
    }

    public void setRouteScheduleDetails(List<MRouteScheduleDetailDTO> routeScheduleDetails) {
        this.routeScheduleDetails = routeScheduleDetails;
    }
}
