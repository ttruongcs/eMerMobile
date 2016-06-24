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
    private long _id;
    @DatabaseField
    private Long routeScheduleDetailId;
    @DatabaseField
    private String productCode;

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public Long getRouteScheduleDetailId() {
        return routeScheduleDetailId;
    }

    public void setRouteScheduleDetailId(Long routeScheduleDetailId) {
        this.routeScheduleDetailId = routeScheduleDetailId;
    }
}
