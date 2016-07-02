package com.banvien.fcv.mobile.db.entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;


@DatabaseTable(tableName = "StatusInOutlet")
public class StatusInOutletEntity implements Serializable {
    @DatabaseField(generatedId = true)
    private long _id;
    @DatabaseField
    private Integer checkIn;
    @DatabaseField
    private Integer chupAnhOverview;
    @DatabaseField
    private Integer xemThongTinDangKyLichSuEIE;
    @DatabaseField
    private Integer khaoSatTrungBayTruoc;
    @DatabaseField
    private Integer khaoSatTrungBaySau;
    @DatabaseField
    private Integer hutHangDatHang;
    @DatabaseField
    private Long routeScheduleDetailId;
    @DatabaseField
    private Integer khaoSat;
    @DatabaseField
    private Integer dongBoCuaHang;
    @DatabaseField
    private Integer tinhTrangCuaHang;

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
