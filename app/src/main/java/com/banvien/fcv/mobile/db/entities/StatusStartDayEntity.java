package com.banvien.fcv.mobile.db.entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;


@DatabaseTable(tableName = "StatusStartDay")
public class StatusStartDayEntity implements Serializable {
    @DatabaseField(generatedId = true)
    private long _id;
    @DatabaseField
    private Integer dongBoDuLieuPhanCong;
    @DatabaseField
    private Integer themCuaHangNeuMuon;
    @DatabaseField
    private Integer chupHinhDongPhuc;
    @DatabaseField
    private Integer chupHinhCongCuDungCu;
    @DatabaseField
    private Integer chupHinhCuaHangDauTien;
    @DatabaseField
    private Integer xacNhanLamViec;

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public Integer getDongBoDuLieuPhanCong() {
        return dongBoDuLieuPhanCong;
    }

    public void setDongBoDuLieuPhanCong(Integer dongBoDuLieuPhanCong) {
        this.dongBoDuLieuPhanCong = dongBoDuLieuPhanCong;
    }

    public Integer getThemCuaHangNeuMuon() {
        return themCuaHangNeuMuon;
    }

    public void setThemCuaHangNeuMuon(Integer themCuaHangNeuMuon) {
        this.themCuaHangNeuMuon = themCuaHangNeuMuon;
    }

    public Integer getChupHinhDongPhuc() {
        return chupHinhDongPhuc;
    }

    public void setChupHinhDongPhuc(Integer chupHinhDongPhuc) {
        this.chupHinhDongPhuc = chupHinhDongPhuc;
    }

    public Integer getChupHinhCongCuDungCu() {
        return chupHinhCongCuDungCu;
    }

    public void setChupHinhCongCuDungCu(Integer chupHinhCongCuDungCu) {
        this.chupHinhCongCuDungCu = chupHinhCongCuDungCu;
    }

    public Integer getChupHinhCuaHangDauTien() {
        return chupHinhCuaHangDauTien;
    }

    public void setChupHinhCuaHangDauTien(Integer chupHinhCuaHangDauTien) {
        this.chupHinhCuaHangDauTien = chupHinhCuaHangDauTien;
    }

    public Integer getXacNhanLamViec() {
        return xacNhanLamViec;
    }

    public void setXacNhanLamViec(Integer xacNhanLamViec) {
        this.xacNhanLamViec = xacNhanLamViec;
    }
}
