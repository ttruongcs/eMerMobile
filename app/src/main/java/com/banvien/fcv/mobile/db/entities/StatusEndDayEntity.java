package com.banvien.fcv.mobile.db.entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by hieu on 3/10/2016.
 */
@DatabaseTable(tableName = "StatusEndDay")
public class StatusEndDayEntity implements Serializable {
    @DatabaseField(generatedId = true)
    private long _id;
    @DatabaseField
    private Integer chupAnhCuoiNgay;
    @DatabaseField
    private Integer dongBoCuoiNgay;
    @DatabaseField
    private Integer ketThucCuoiNgay;

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public Integer getChupAnhCuoiNgay() {
        return chupAnhCuoiNgay;
    }

    public void setChupAnhCuoiNgay(Integer chupAnhCuoiNgay) {
        this.chupAnhCuoiNgay = chupAnhCuoiNgay;
    }

    public Integer getDongBoCuoiNgay() {
        return dongBoCuoiNgay;
    }

    public void setDongBoCuoiNgay(Integer dongBoCuoiNgay) {
        this.dongBoCuoiNgay = dongBoCuoiNgay;
    }

    public Integer getKetThucCuoiNgay() {
        return ketThucCuoiNgay;
    }

    public void setKetThucCuoiNgay(Integer ketThucCuoiNgay) {
        this.ketThucCuoiNgay = ketThucCuoiNgay;
    }
}
