package com.banvien.fcv.mobile.dto.getfromserver;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created with IntelliJ IDEA.
 * User: KhanhTran
 * Date: 3/24/16
 * Time: 11:16 AM
 * To change this template use File | Settings | File Templates.
 */
public class CatGroupDTO implements Serializable {
    private Long catGroupId;
    private String code;
    private String name;
    private CatGroupDTO parent;
    private String shortDescription;
    private String longDescription;
    private Boolean active;
    private Timestamp createdDate;
    private Timestamp modifiedDate;
    private Integer displayOrder;

    public CatGroupDTO(){};

    public Long getCatGroupId() {
        return catGroupId;
    }

    public void setCatGroupId(Long catGroupId) {
        this.catGroupId = catGroupId;
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

    public CatGroupDTO getParent() {
        return parent;
    }

    public void setParent(CatGroupDTO parent) {
        this.parent = parent;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
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

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }
}
