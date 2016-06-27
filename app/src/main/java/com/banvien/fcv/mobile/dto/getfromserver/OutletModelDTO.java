package com.banvien.fcv.mobile.dto.getfromserver;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ANHTAI
 * Date: 3/1/16
 * Time: 11:07 AM
 * To change this template use File | Settings | File Templates.
 */
public class OutletModelDTO implements Serializable {
    private Long outletModelId;
    @JsonIgnore
    private OutletModelGroupDTO outletModelGroup;
    @JsonIgnore
    private CatGroupDTO catGroup;
    private String code;
    private String name;
    @JsonBackReference
    private OutletModelDTO parent;
    private Boolean active;
    private List<OutletModelDetailDTO> outletModelDetail;
    private Timestamp createdDate;
    private Timestamp modifiedDate;

    public OutletModelDTO(){}

    public OutletModelDTO(Long outletModelId, String code, String name) {
        this.outletModelId = outletModelId;
        this.code = code;
        this.name = name ;
    }

    public Long getOutletModelId() {
        return outletModelId;
    }

    public void setOutletModelId(Long outletModelId) {
        this.outletModelId = outletModelId;
    }

    public OutletModelGroupDTO getOutletModelGroup() {
        return outletModelGroup;
    }

    public void setOutletModelGroup(OutletModelGroupDTO outletModelGroup) {
        this.outletModelGroup = outletModelGroup;
    }

    public CatGroupDTO getCatGroup() {
        return catGroup;
    }

    public void setCatGroup(CatGroupDTO catGroup) {
        this.catGroup = catGroup;
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

    public OutletModelDTO getParent() {
        return parent;
    }

    public void setParent(OutletModelDTO parent) {
        this.parent = parent;
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

    public List<OutletModelDetailDTO> getOutletModelDetail() {
        return outletModelDetail;
    }

    public void setOutletModelDetail(List<OutletModelDetailDTO> outletModelDetail) {
        this.outletModelDetail = outletModelDetail;
    }
}
