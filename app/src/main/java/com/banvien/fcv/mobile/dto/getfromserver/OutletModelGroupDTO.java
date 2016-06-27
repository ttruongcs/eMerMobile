package com.banvien.fcv.mobile.dto.getfromserver;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created with IntelliJ IDEA.
 * User: Lam Vu
 * Date: 4/21/16
 * Time: 5:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class OutletModelGroupDTO implements Serializable {
    private Long outletModelGroupId;
    private String code;
    private String name;
    private String description;
    private Boolean active;
    private Timestamp createdDate;
    private Timestamp modifiedDate;

    public Long getOutletModelGroupId() {
        return outletModelGroupId;
    }

    public void setOutletModelGroupId(Long outletModelGroupId) {
        this.outletModelGroupId = outletModelGroupId;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
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
