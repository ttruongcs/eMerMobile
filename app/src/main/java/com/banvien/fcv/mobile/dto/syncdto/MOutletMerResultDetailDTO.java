package com.banvien.fcv.mobile.dto.syncdto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Linh Nguyen
 * Date: 5/17/16
 * Time: 10:12 AM
 * To change this template use File | Settings | File Templates.
 */
public class MOutletMerResultDetailDTO implements Serializable {
    private String outletMerResultDetailId;
    private String outletMerResultId;
    private MExhibitRegisterDetailDTO exhibitRegisterDetail;
    private String type;
    private String value;
    private Long outletId;
    private Long outletModelId;
    private float score;
    private Date createdDate;
    private String createdBy;

    public String getOutletMerResultDetailId() {
        return outletMerResultDetailId;
    }

    public void setOutletMerResultDetailId(String outletMerResultDetailId) {
        this.outletMerResultDetailId = outletMerResultDetailId;
    }

    public String getOutletMerResultId() {
        return outletMerResultId;
    }

    public void setOutletMerResultId(String outletMerResultId) {
        this.outletMerResultId = outletMerResultId;
    }

    public MExhibitRegisterDetailDTO getExhibitRegisterDetail() {
        return exhibitRegisterDetail;
    }

    public void setExhibitRegisterDetail(MExhibitRegisterDetailDTO exhibitRegisterDetail) {
        this.exhibitRegisterDetail = exhibitRegisterDetail;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
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

    public Long getOutletModelId() {
        return outletModelId;
    }

    public void setOutletModelId(Long outletModelId) {
        this.outletModelId = outletModelId;
    }
}
