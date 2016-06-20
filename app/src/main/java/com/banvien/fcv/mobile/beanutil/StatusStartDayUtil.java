package com.banvien.fcv.mobile.beanutil;

import com.banvien.fcv.mobile.db.entities.StatusHomeEntity;
import com.banvien.fcv.mobile.db.entities.StatusStartDayEntity;
import com.banvien.fcv.mobile.dto.StatusHomeDTO;
import com.banvien.fcv.mobile.dto.StatusStartDayDTO;

/**
 * Created by Administrator on 4/4/2016.
 */
public class StatusStartDayUtil {

    public static StatusStartDayDTO convertToDTO(StatusStartDayEntity item) {
        StatusStartDayDTO result = new StatusStartDayDTO();
        result.set_id(item.get_id());
        result.setChupHinhCongCuDungCu(item.getChupHinhCongCuDungCu());
        result.setChupHinhCuaHangDauTien(item.getChupHinhCuaHangDauTien());
        result.setChupHinhDongPhuc(item.getChupHinhDongPhuc());
        result.setDongBoDuLieuPhanCong(item.getDongBoDuLieuPhanCong());
        result.setThemCuaHangNeuMuon(item.getThemCuaHangNeuMuon());
        result.setXacNhanLamViec(item.getXacNhanLamViec());
        return result;
    }

    public static StatusStartDayEntity convertToEntity(StatusStartDayDTO item) {
        StatusStartDayEntity result = new StatusStartDayEntity();
        result.set_id(item.get_id());
        result.setChupHinhCongCuDungCu(item.getChupHinhCongCuDungCu());
        result.setChupHinhCuaHangDauTien(item.getChupHinhCuaHangDauTien());
        result.setChupHinhDongPhuc(item.getChupHinhDongPhuc());
        result.setDongBoDuLieuPhanCong(item.getDongBoDuLieuPhanCong());
        result.setThemCuaHangNeuMuon(item.getThemCuaHangNeuMuon());
        result.setXacNhanLamViec(item.getXacNhanLamViec());
        return result;
    }
}
