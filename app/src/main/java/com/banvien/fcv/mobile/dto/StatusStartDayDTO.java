package com.banvien.fcv.mobile.dto;

import java.io.Serializable;

public class StatusStartDayDTO implements Serializable {

    private long _id;
    
    private Integer dongBoDuLieuPhanCong;
    
    private Integer themCuaHangNeuMuon;
    
    private Integer chupHinhDongPhuc;
    
    private Integer chupHinhCongCuDungCu;
    
    private Integer chupHinhCuaHangDauTien;
    
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
