package com.banvien.fcv.mobile.dto.routeschedule;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Created with IntelliJ IDEA.
 * User: Linh Nguyen
 * Date: 5/16/16
 * Time: 1:27 PM
 * To change this template use File | Settings | File Templates.
 */
public class MOutletDTO implements Serializable {
    private Long outletId;
    private DistributorDTO distributor;
    private OutletTypeDTO outletType;
    private MExhibitRegisterDTO exhibitRegister;
    private String code;
    private String name;
    private String phoneNumber;
    private String owner;
    private String locationNo;
    private String street;
    private String district;
    private String ward;
    private CityDTO city;
    private String bankName;
    private String bankAccount;
    private String ownerIdentity;
    private String identityImage;
    private String source;
    private Integer status;
    private Timestamp createdDate;
    private Timestamp modifiedDate;
    private Integer primaryUsed;
    private BigDecimal lat;
    private BigDecimal lng;
    private String distributorCode;
    private String cityCode;
    private String outletTypeCode;
    private Integer maxBudget;

    public Long getOutletId() {
        return outletId;
    }

    public void setOutletId(Long outletId) {
        this.outletId = outletId;
    }

    public DistributorDTO getDistributor() {
        return distributor;
    }

    public void setDistributor(DistributorDTO distributor) {
        this.distributor = distributor;
    }

    public OutletTypeDTO getOutletType() {
        return outletType;
    }

    public void setOutletType(OutletTypeDTO outletType) {
        this.outletType = outletType;
    }

    public MExhibitRegisterDTO getExhibitRegister() {
        return exhibitRegister;
    }

    public void setExhibitRegister(MExhibitRegisterDTO exhibitRegister) {
        this.exhibitRegister = exhibitRegister;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
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

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getOwnerIdentity() {
        return ownerIdentity;
    }

    public void setOwnerIdentity(String ownerIdentity) {
        this.ownerIdentity = ownerIdentity;
    }

    public String getIdentityImage() {
        return identityImage;
    }

    public void setIdentityImage(String identityImage) {
        this.identityImage = identityImage;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public Integer getPrimaryUsed() {
        return primaryUsed;
    }

    public void setPrimaryUsed(Integer primaryUsed) {
        this.primaryUsed = primaryUsed;
    }

    public BigDecimal getLat() {
        return lat;
    }

    public void setLat(BigDecimal lat) {
        this.lat = lat;
    }

    public BigDecimal getLng() {
        return lng;
    }

    public void setLng(BigDecimal lng) {
        this.lng = lng;
    }

    public String getDistributorCode() {
        return distributorCode;
    }

    public void setDistributorCode(String distributorCode) {
        this.distributorCode = distributorCode;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getOutletTypeCode() {
        return outletTypeCode;
    }

    public void setOutletTypeCode(String outletTypeCode) {
        this.outletTypeCode = outletTypeCode;
    }

    public Integer getMaxBudget() {
        return maxBudget;
    }

    public void setMaxBudget(Integer maxBudget) {
        this.maxBudget = maxBudget;
    }
}
