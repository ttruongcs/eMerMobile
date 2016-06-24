package com.banvien.fcv.mobile.db.entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

/**
 * Created by Linh Nguyen on 6/24/2016.
 */
@DatabaseTable(tableName = "ShortageProduct")
public class ShortageProductEntity {
    @DatabaseField(generatedId = true)
    private Long _id;
    @DatabaseField
    private Long outletId;
    @DatabaseField
    private Date createdDate;
    @DatabaseField
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
