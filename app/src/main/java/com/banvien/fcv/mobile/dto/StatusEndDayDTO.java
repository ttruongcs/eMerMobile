package com.banvien.fcv.mobile.dto;

import java.io.Serializable;

public class StatusEndDayDTO implements Serializable {
    private long _id;
    
    private Integer chupAnhCuoiNgay;
    
    private Integer dongBoCuoiNgay;

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
