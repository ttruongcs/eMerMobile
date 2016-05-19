package com.banvien.fcv.mobile.dto.routeschedule;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Linh Nguyen
 * Date: 5/6/16
 * Time: 2:57 PM
 * To change this template use File | Settings | File Templates.
 */
public class RouteScheduleInfoDTO {
    private Long routeScheduleId;
    private List<MRouteScheduleDetailDTO> routeScheduleDetails;

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
