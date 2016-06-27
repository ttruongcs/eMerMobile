package com.banvien.fcv.mobile.dto.getfromserver;

import java.io.Serializable;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: DEll
 * Date: 6/25/16
 * Time: 4:07 PM
 * To change this template use File | Settings | File Templates.
 */
public class MSurveyResultDTO implements Serializable {
    private Long surveyId;
    private Long routeScheduleDetailId;
    private List<MSurveyResponseDTO> surveyResponse;

    public Long getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(Long surveyId) {
        this.surveyId = surveyId;
    }

    public Long getRouteScheduleDetailId() {
        return routeScheduleDetailId;
    }

    public void setRouteScheduleDetailId(Long routeScheduleDetailId) {
        this.routeScheduleDetailId = routeScheduleDetailId;
    }

    public List<MSurveyResponseDTO> getSurveyResponse() {
        return surveyResponse;
    }

    public void setSurveyResponse(List<MSurveyResponseDTO> surveyResponse) {
        this.surveyResponse = surveyResponse;
    }
}
