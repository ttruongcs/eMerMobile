package com.banvien.fcv.mobile.dto.routeschedule;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Linh Nguyen
 * Date: 5/16/16
 * Time: 1:12 PM
 * To change this template use File | Settings | File Templates.
 */
public class MExhibitRegisterDTO implements Serializable {
    private String exhibitRegisterId;
    private Long createdBy;
    private Long modifiedBy;
    private Date createdDate;
    private Date modifiedDate;
    private Short month;
    private Short year;
    private Short status;
    private String note;
    @JsonProperty("exhibitRegisterDetails")
    private List<MExhibitRegisterDetailDTO> exhibitRegisterDetails = new ArrayList<>();

    public String getExhibitRegisterId() {
        return exhibitRegisterId;
    }

    public void setExhibitRegisterId(String exhibitRegisterId) {
        this.exhibitRegisterId = exhibitRegisterId;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Long getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(Long modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Short getMonth() {
        return month;
    }

    public void setMonth(Short month) {
        this.month = month;
    }

    public Short getYear() {
        return year;
    }

    public void setYear(Short year) {
        this.year = year;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public List<MExhibitRegisterDetailDTO> getExhibitRegisterDetails() {
        return exhibitRegisterDetails;
    }

    public void setExhibitRegisterDetails(List<MExhibitRegisterDetailDTO> exhibitRegisterDetails) {
        this.exhibitRegisterDetails = exhibitRegisterDetails;
    }
}
