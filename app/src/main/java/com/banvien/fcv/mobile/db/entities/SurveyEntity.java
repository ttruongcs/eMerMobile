package com.banvien.fcv.mobile.db.entities;

import com.banvien.fcv.mobile.dto.QuestionDTO;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Lam Vu
 * Date: 6/24/16
 * Time: 9:45 AM
 * To change this template use File | Settings | File Templates.
 */
@DatabaseTable(tableName = "Survey")
public class SurveyEntity implements Serializable {
    @DatabaseField(canBeNull = false, id = true)
    private Long surveyId;
    @DatabaseField
    private String name;
    @DatabaseField
    private Date startDate;
    @DatabaseField
    private Date endDate;
    @DatabaseField
    private Boolean active;
    @DatabaseField(canBeNull = false)
    private Long outletId;

    public Long getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(Long surveyId) {
        this.surveyId = surveyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Long getOutletId() {
        return outletId;
    }

    public void setOutletId(Long outletId) {
        this.outletId = outletId;
    }
}
