package com.banvien.fcv.mobile.beanutil;

import com.banvien.fcv.mobile.db.entities.HotzoneEntity;
import com.banvien.fcv.mobile.dto.getfromserver.HotZoneDTO;

/**
 * Created by Administrator on 4/4/2016.
 */
public class HotzoneUtil {

    public static HotZoneDTO convertToDTO(HotzoneEntity item) {
        HotZoneDTO result = new HotZoneDTO();
        result.setHotZoneId(item.get_id());
        result.setCode(item.getCode());
        result.setHotZoneId(item.getHotZoneId());
        result.setNote(item.getNote());
        return result;
    }

    public static HotzoneEntity convertToEntity(HotZoneDTO item) {
        HotzoneEntity entity = new HotzoneEntity();
        entity.set_id(item.getHotZoneId());
        entity.setCode(item.getCode());
        entity.setHotZoneId(item.getHotZoneId());
        return entity;
    }
}
