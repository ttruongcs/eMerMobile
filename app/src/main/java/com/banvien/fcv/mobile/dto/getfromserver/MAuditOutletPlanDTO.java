package com.banvien.fcv.mobile.dto.getfromserver;

import com.banvien.fcv.mobile.dto.routeschedule.CityDTO;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: DEll
 * Date: 6/21/16
 * Time: 6:07 PM
 * To change this template use File | Settings | File Templates.
 */
public class MAuditOutletPlanDTO implements Serializable {
    private Long outletId;
    private String code;
    private String name;
    private String locationNo;
    private String street;
    private String district;
    private String ward;
    private Long routeScheduleId;
    private Long routeScheduleDetailId;
    private CityDTO city;

    private BigDecimal latitude;
    private BigDecimal longitude;

    private Boolean isAuditedToday;
    private List<OutletModelDTO> outletModel;
    private List<String> productCode;

    public Long getOutletId() {
        return outletId;
    }

    public void setOutletId(Long outletId) {
        this.outletId = outletId;
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

    public String getLocationNo() {
        return locationNo;
    }

    public void setLocationNo(String locationNo) {
        this.locationNo = locationNo;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    public CityDTO getCity() {
        return city;
    }

    public void setCity(CityDTO city) {
        this.city = city;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public Boolean getAuditedToday() {
        return isAuditedToday;
    }

    public void setAuditedToday(Boolean auditedToday) {
        isAuditedToday = auditedToday;
    }

    public List<OutletModelDTO> getOutletModel() {
        return outletModel;
    }

    public void setOutletModel(List<OutletModelDTO> outletModel) {
        this.outletModel = outletModel;
    }

    public List<String> getProductCode() {
        return productCode;
    }

    public void setProductCode(List<String> productCode) {
        this.productCode = productCode;
    }

    public Long getRouteScheduleId() {
        return routeScheduleId;
    }

    public void setRouteScheduleId(Long routeScheduleId) {
        this.routeScheduleId = routeScheduleId;
    }

    public Long getRouteScheduleDetailId() {
        return routeScheduleDetailId;
    }

    public void setRouteScheduleDetailId(Long routeScheduleDetailId) {
        this.routeScheduleDetailId = routeScheduleDetailId;
    }
}
