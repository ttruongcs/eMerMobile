package com.banvien.fcv.mobile.beanutil;

import com.banvien.fcv.mobile.db.entities.OutletEntity;
import com.banvien.fcv.mobile.db.entities.OutletFirstImagesEntity;
import com.banvien.fcv.mobile.dto.OutletDTO;
import com.banvien.fcv.mobile.dto.OutletFirstImagesDTO;

/**
 * Created by Administrator on 4/4/2016.
 */
public class OutletFirstImagesUtil {

    public static OutletFirstImagesDTO convertToDTO(OutletFirstImagesEntity item) {
        OutletFirstImagesDTO result = new OutletFirstImagesDTO();
        result.set_id(item.get_id());
        result.setRouteScheduleDetailId(item.getRouteScheduleDetailId());
        result.setImagePath(item.getPathImage());
        return result;
    }

    public static OutletFirstImagesEntity convertToEntity(OutletFirstImagesDTO item) {
        OutletFirstImagesEntity result = new OutletFirstImagesEntity();
        result.set_id(item.get_id());
        result.setRouteScheduleDetailId(item.getRouteScheduleDetailId());
        result.setPathImage(item.getImagePath());
        return result;
    }
}
