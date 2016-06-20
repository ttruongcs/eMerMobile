package com.banvien.fcv.mobile.beanutil;

import com.banvien.fcv.mobile.db.entities.ProductEntity;
import com.banvien.fcv.mobile.db.entities.StatusHomeEntity;
import com.banvien.fcv.mobile.dto.ProductDTO;
import com.banvien.fcv.mobile.dto.StatusHomeDTO;

/**
 * Created by Administrator on 4/4/2016.
 */
public class StatusHomeUtil {

    public static StatusHomeDTO convertToDTO(StatusHomeEntity item) {
        StatusHomeDTO result = new StatusHomeDTO();
        result.set_id(item.get_id());
        result.setChuanBiDauNgay(item.getChuanBiDauNgay());
        result.setTrongCuaHang(item.getTrongCuaHang());
        result.setKetThucCuoiNgay(item.getKetThucCuoiNgay());
        return result;
    }

    public static StatusHomeEntity convertToEntity(StatusHomeDTO item) {
        StatusHomeEntity result = new StatusHomeEntity();
        result.set_id(item.get_id());
        result.setChuanBiDauNgay(item.getChuanBiDauNgay());
        result.setTrongCuaHang(item.getTrongCuaHang());
        result.setKetThucCuoiNgay(item.getKetThucCuoiNgay());
        return result;
    }
}
