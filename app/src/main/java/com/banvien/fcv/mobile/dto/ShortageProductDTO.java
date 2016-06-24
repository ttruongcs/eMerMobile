package com.banvien.fcv.mobile.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Linh Nguyen on 6/24/2016.
 */
public class ShortageProductDTO implements Serializable {
    private Long _id;
    private Long outletId;
    private Date createdDate;
    private String productCode;

    public Long get_id() {
        return _id;
    }

    public void set_id(Long _id) {
        this._id = _id;
    }

    public Long getOutletId() {
        return outletId;
    }

    public void setOutletId(Long outletId) {
        this.outletId = outletId;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }
}
