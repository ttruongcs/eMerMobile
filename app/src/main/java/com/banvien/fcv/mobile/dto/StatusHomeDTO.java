package com.banvien.fcv.mobile.dto;

import java.io.Serializable;

public class StatusHomeDTO implements Serializable {
    private long _id;
    private Integer chuanBiDauNgay;
    private Integer trongCuaHang;
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
