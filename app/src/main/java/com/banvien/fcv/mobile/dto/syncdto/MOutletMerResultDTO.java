package com.banvien.fcv.mobile.dto.syncdto;

import com.banvien.fcv.mobile.dto.getfromserver.MSurveyResultDTO;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Linh Nguyen
 * Date: 5/17/16
 * Time: 10:10 AM
 * To change this template use File | Settings | File Templates.
 */
public class MOutletMerResultDTO implements Serializable {
    private String outletMerResultId;
    private Long routeScheduleDetailId;
    private Date auditDate;
    private Date submittedDate;
    private Integer activeStatus;
    private String note;
    private List<MOutletMerResultDetailDTO> outletMerResultDetails;
    private List<MSurveyResultDTO> surveyResult;

    public String getOutletMerResultId() {
        return outletMerResultId;
    }

    public void setOutletMerResultId(String outletMerResultId) {
        this.outletMerResultId = outletMerResultId;
    }

    public Long getRouteScheduleDetailId() {
        return routeScheduleDetailId;
    }

    public void setRouteScheduleDetailId(Long routeScheduleDetailId) {
        this.routeScheduleDetailId = routeScheduleDetailId;
    }

    public Date getAuditDate() {
        return auditDate;
    }

    public void setAuditDate(Date auditDate) {
        this.auditDate = auditDate;
    }

    public Date getSubmittedDate() {
        return submittedDate;
    }

    public void setSubmittedDate(Date submittedDate) {
        this.submittedDate = submittedDate;
    }

    public Integer getActiveStatus() {
        return activeStatus;
    }

    public void setActiveStatus(Integer activeStatus) {
        this.activeStatus = activeStatus;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public List<MOutletMerResultDetailDTO> getOutletMerResultDetails() {
        return outletMerResultDetails;
    }

    public void setOutletMerResultDetails(List<MOutletMerResultDetailDTO> outletMerResultDetails) {
        this.outletMerResultDetails = outletMerResultDetails;
    }

    public List<MSurveyResultDTO> getSurveyResult() {
        return surveyResult;
    }

    public void setSurveyResult(List<MSurveyResultDTO> surveyResult) {
        this.surveyResult = surveyResult;
    }
}
