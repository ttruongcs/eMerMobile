package com.banvien.fcv.mobile.dto;

import java.io.Serializable;

public class StatusInOutletDTO implements Serializable {
    private long _id;
    
    private Integer checkIn;
    
    private Integer chupAnhOverview;
    
    private Integer xemThongTinDangKyLichSuEIE;
    
    private Integer khaoSatTrungBayTruoc;
    
    private Integer khaoSatTrungBaySau;
    
    private Integer hutHangDatHang;
    
    private Integer khaoSat;

    private Integer dongBoCuaHang;

    private Integer tinhTrangCuaHang;

    private Long routeScheduleDetailId;

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public Integer getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(Integer checkIn) {
        this.checkIn = checkIn;
    }

    public Integer getChupAnhOverview() {
        return chupAnhOverview;
    }

    public void setChupAnhOverview(Integer chupAnhOverview) {
        this.chupAnhOverview = chupAnhOverview;
    }

    public Integer getXemThongTinDangKyLichSuEIE() {
        return xemThongTinDangKyLichSuEIE;
    }

    public void setXemThongTinDangKyLichSuEIE(Integer xemThongTinDangKyLichSuEIE) {
        this.xemThongTinDangKyLichSuEIE = xemThongTinDangKyLichSuEIE;
    }

    public Integer getKhaoSatTrungBayTruoc() {
        return khaoSatTrungBayTruoc;
    }

    public void setKhaoSatTrungBayTruoc(Integer khaoSatTrungBayTruoc) {
        this.khaoSatTrungBayTruoc = khaoSatTrungBayTruoc;
    }

    public Integer getKhaoSatTrungBaySau() {
        return khaoSatTrungBaySau;
    }

    public void setKhaoSatTrungBaySau(Integer khaoSatTrungBaySau) {
        this.khaoSatTrungBaySau = khaoSatTrungBaySau;
    }

    public Integer getHutHangDatHang() {
        return hutHangDatHang;
    }

    public void setHutHangDatHang(Integer hutHangDatHang) {
        this.hutHangDatHang = hutHangDatHang;
    }

    public Integer getKhaoSat() {
        return khaoSat;
    }

    public void setKhaoSat(Integer khaoSat) {
        this.khaoSat = khaoSat;
    }

    public Long getRouteScheduleDetailId() {
        return routeScheduleDetailId;
    }

    public void setRouteScheduleDetailId(Long routeScheduleDetailId) {
        this.routeScheduleDetailId = routeScheduleDetailId;
    }

    public Integer getDongBoCuaHang() {
        return dongBoCuaHang;
    }

    public void setDongBoCuaHang(Integer dongBoCuaHang) {
        this.dongBoCuaHang = dongBoCuaHang;
    }

    public Integer getTinhTrangCuaHang() {
        return tinhTrangCuaHang;
    }

    public void setTinhTrangCuaHang(Integer tinhTrangCuaHang) {
        this.tinhTrangCuaHang = tinhTrangCuaHang;
    }
}
