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
    private Integer khaoSatDichVuKhachHang;
    @DatabaseField
    private Integer khaoSatPOSM;
    @DatabaseField
    private Integer ghiNhanKhieuNai;

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

    public Integer getKhaoSatDichVuKhachHang() {
        return khaoSatDichVuKhachHang;
    }

    public void setKhaoSatDichVuKhachHang(Integer khaoSatDichVuKhachHang) {
        this.khaoSatDichVuKhachHang = khaoSatDichVuKhachHang;
    }

    public Integer getKhaoSatPOSM() {
        return khaoSatPOSM;
    }

    public void setKhaoSatPOSM(Integer khaoSatPOSM) {
        this.khaoSatPOSM = khaoSatPOSM;
    }

    public Integer getGhiNhanKhieuNai() {
        return ghiNhanKhieuNai;
    }

    public void setGhiNhanKhieuNai(Integer ghiNhanKhieuNai) {
        this.ghiNhanKhieuNai = ghiNhanKhieuNai;
    }
}
