package com.banvien.fcv.mobile.beanutil;

import com.banvien.fcv.mobile.db.entities.HotzoneEntity;
import com.banvien.fcv.mobile.db.entities.POSMEntity;
import com.banvien.fcv.mobile.dto.HotzoneDTO;
import com.banvien.fcv.mobile.dto.POSMDTO;

/**
 * Created by Administrator on 4/4/2016.
 */
public class HotzoneUtil {

    public static HotzoneDTO convertToDTO(HotzoneEntity item) {
        HotzoneDTO result = new HotzoneDTO();
        result.set_id(item.get_id());
        result.setCode(item.getCode());
        result.setName(item.getName());
        result.setHotZoneId(item.getHotZoneId());
        return result;
    }

    public static HotzoneEntity convertToEntity(HotzoneDTO item) {
        HotzoneEntity entity = new HotzoneEntity();
        entity.set_id(item.get_id());
        entity.setCode(item.getCode());
        entity.setName(item.getName());
        entity.setHotZoneId(item.getHotZoneId());
        return entity;
    }
}
