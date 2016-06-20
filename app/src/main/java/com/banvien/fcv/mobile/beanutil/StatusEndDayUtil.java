package com.banvien.fcv.mobile.beanutil;

import com.banvien.fcv.mobile.db.entities.StatusEndDayEntity;
import com.banvien.fcv.mobile.db.entities.StatusInOutletEntity;
import com.banvien.fcv.mobile.dto.StatusEndDayDTO;
import com.banvien.fcv.mobile.dto.StatusInOutletDTO;

/**
 * Created by Administrator on 4/4/2016.
 */
public class StatusEndDayUtil {

    public static StatusEndDayDTO convertToDTO(StatusEndDayEntity item) {
        StatusEndDayDTO result = new StatusEndDayDTO();
        result.set_id(item.get_id());
        result.setChupAnhCuoiNgay(item.getChupAnhCuoiNgay());
        result.setDongBoCuoiNgay(item.getDongBoCuoiNgay());
        return result;
    }

    public static StatusEndDayEntity convertToEntity(StatusEndDayDTO item) {
        StatusEndDayEntity result = new StatusEndDayEntity();
        result.set_id(item.get_id());
        result.setChupAnhCuoiNgay(item.getChupAnhCuoiNgay());
        result.setDongBoCuoiNgay(item.getDongBoCuoiNgay());
        return result;
    }
}
