package com.banvien.fcv.mobile.dto.routeschedule;

import java.sql.Timestamp;

/**
 * Created with IntelliJ IDEA.
 * User: HLam
 * Date: 2/18/16
 * Time: 2:04 PM
 * To change this template use File | Settings | File Templates.
 */
public class OutletTypeDTO {

    private Long outletTypeId;
    private String code;
    private String name;
    private Timestamp createdDate;
    private Timestamp modifiedDate;

    public OutletTypeDTO(){}
    // OutletTypeDTO(entity.getOutletTypeId(), entity.getName(),entity.getCode(),entity.getCreatedDate(), entity.getModifiedDate());
    public OutletTypeDTO(Long outletTypeId, String name, String code, Timestamp createdDate, Timestamp modifiedTime){
        this.outletTypeId = outletTypeId;
        this.name = name;
        this.code = code;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedTime;
    }
    public Long getOutletTypeId() {
        return outletTypeId;
    }

    public void setOutletTypeId(Long outletTypeId) {
        this.outletTypeId = outletTypeId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    public Timestamp getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Timestamp modifiedDate) {
        this.modifiedDate = modifiedDate;
    }




}
