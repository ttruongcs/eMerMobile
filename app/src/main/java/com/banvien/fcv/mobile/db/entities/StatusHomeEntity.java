package com.banvien.fcv.mobile.db.entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by hieu on 3/10/2016.
 */
@DatabaseTable(tableName = "StatusHome")
public class StatusHomeEntity implements Serializable {
    @DatabaseField(generatedId = true)
    private long _id;
    @DatabaseField
    private Integer chuanBiDauNgay;
    @DatabaseField
    private Integer trongCuaHang;
    @DatabaseField
    private Integer ketThucCuoiNgay;

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public Integer getChuanBiDauNgay() {
        return chuanBiDauNgay;
    }

    public void setChuanBiDauNgay(Integer chuanBiDauNgay) {
        this.chuanBiDauNgay = chuanBiDauNgay;
    }

    public Integer getTrongCuaHang() {
        return trongCuaHang;
    }

    public void setTrongCuaHang(Integer trongCuaHang) {
        this.trongCuaHang = trongCuaHang;
    }

    public Integer getKetThucCuoiNgay() {
        return ketThucCuoiNgay;
    }

    public void setKetThucCuoiNgay(Integer ketThucCuoiNgay) {
        this.ketThucCuoiNgay = ketThucCuoiNgay;
    }
}
