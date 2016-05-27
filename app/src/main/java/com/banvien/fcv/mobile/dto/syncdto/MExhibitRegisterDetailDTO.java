package com.banvien.fcv.mobile.dto.syncdto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Linh Nguyen
 * Date: 5/16/16
 * Time: 1:15 PM
 * To change this template use File | Settings | File Templates.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MExhibitRegisterDetailDTO implements Serializable {
    private String exhibitRegisterDetailId;
    private String exhibitRegisterId;
    private Long outletModelId;
    private String type;
    private String rule;
    private Double weight;
    private String dataType;
    private Short priority;
    private Boolean multiValue;
    private String registeredValue;

    public String getExhibitRegisterDetailId() {
        return exhibitRegisterDetailId;
    }

    public void setExhibitRegisterDetailId(String exhibitRegisterDetailId) {
        this.exhibitRegisterDetailId = exhibitRegisterDetailId;
    }

    public String getExhibitRegisterId() {
        return exhibitRegisterId;
    }

    public void setExhibitRegisterId(String exhibitRegisterId) {
        this.exhibitRegisterId = exhibitRegisterId;
    }

    public Long getOutletModelId() {
        return outletModelId;
    }

    public void setOutletModelId(Long outletModelId) {
        this.outletModelId = outletModelId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public Short getPriority() {
        return priority;
    }

    public void setPriority(Short priority) {
        this.priority = priority;
    }

    public Boolean getMultiValue() {
        return multiValue;
    }

    public void setMultiValue(Boolean multiValue) {
        this.multiValue = multiValue;
    }

    public String getRegisteredValue() {
        return registeredValue;
    }

    public void setRegisteredValue(String registeredValue) {
        this.registeredValue = registeredValue;
    }
}
