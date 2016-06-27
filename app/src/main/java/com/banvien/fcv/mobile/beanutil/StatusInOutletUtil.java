package com.banvien.fcv.mobile.beanutil;

import com.banvien.fcv.mobile.db.entities.StatusInOutletEntity;
import com.banvien.fcv.mobile.db.entities.StatusStartDayEntity;
import com.banvien.fcv.mobile.dto.StatusInOutletDTO;
import com.banvien.fcv.mobile.dto.StatusStartDayDTO;

/**
 * Created by Administrator on 4/4/2016.
 */
public class StatusInOutletUtil {

    public static StatusInOutletDTO convertToDTO(StatusInOutletEntity item) {
        StatusInOutletDTO result = new StatusInOutletDTO();
        result.set_id(item.get_id());
        result.setCheckIn(item.getCheckIn());
        result.setChupAnhOverview(item.getChupAnhOverview());
        result.setHutHangDatHang(item.getHutHangDatHang());
        result.setKhaoSatTrungBaySau(item.getKhaoSatTrungBaySau());
        result.setKhaoSatTrungBayTruoc(item.getKhaoSatTrungBayTruoc());
        result.setXemThongTinDangKyLichSuEIE(item.getXemThongTinDangKyLichSuEIE());
        result.setKhaoSat(item.getKhaoSat());
        result.setRouteScheduleDetailId(item.getRouteScheduleDetailId());
        return result;
    }

    public static StatusInOutletEntity convertToEntity(StatusInOutletDTO item) {
        StatusInOutletEntity result = new StatusInOutletEntity();
        result.set_id(item.get_id());
        result.setCheckIn(item.getCheckIn());
        result.setChupAnhOverview(item.getChupAnhOverview());
        result.setHutHangDatHang(item.getHutHangDatHang());
        result.setKhaoSatTrungBaySau(item.getKhaoSatTrungBaySau());
        result.setKhaoSatTrungBayTruoc(item.getKhaoSatTrungBayTruoc());
        result.setXemThongTinDangKyLichSuEIE(item.getXemThongTinDangKyLichSuEIE());
        result.setKhaoSat(item.getKhaoSat());
        result.setRouteScheduleDetailId(item.getRouteScheduleDetailId());
        return result;
    }
}
