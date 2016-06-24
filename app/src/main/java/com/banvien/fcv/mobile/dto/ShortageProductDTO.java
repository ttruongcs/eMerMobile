package com.banvien.fcv.mobile.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Linh Nguyen on 6/24/2016.
 */
public class ShortageProductDTO implements Serializable {
    private long _id;
    private Long routeSCheduleDetailId;
    private String productCode;
    private String productName;

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public Long getRouteSCheduleDetailId() {
        return routeSCheduleDetailId;
    }

    public void setRouteSCheduleDetailId(Long routeSCheduleDetailId) {
        this.routeSCheduleDetailId = routeSCheduleDetailId;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    @Override
    public String toString() {
        return "ShortageProductDTO{" +
                "_id=" + _id +
                ", routeSCheduleDetailId=" + routeSCheduleDetailId +
                ", productCode='" + productCode + '\'' +
                '}';
    }
}
