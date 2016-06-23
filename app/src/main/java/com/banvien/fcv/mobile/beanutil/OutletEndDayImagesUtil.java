package com.banvien.fcv.mobile.beanutil;

import com.banvien.fcv.mobile.db.entities.OutletEndDayImagesEntity;
import com.banvien.fcv.mobile.db.entities.OutletFirstImagesEntity;
import com.banvien.fcv.mobile.dto.OutletEndDayImagesDTO;
import com.banvien.fcv.mobile.dto.OutletFirstImagesDTO;

/**
 * Created by Administrator on 4/4/2016.
 */
public class OutletEndDayImagesUtil {

    public static OutletEndDayImagesDTO convertToDTO(OutletEndDayImagesEntity item) {
        OutletEndDayImagesDTO result = new OutletEndDayImagesDTO();
        result.set_id(item.get_id());
        result.setRouteScheduleDetailId(item.getRouteScheduleDetailId());
        result.setImagePath(item.getPathImage());
        return result;
    }

    public static OutletEndDayImagesEntity convertToEntity(OutletEndDayImagesDTO item) {
        OutletEndDayImagesEntity result = new OutletEndDayImagesEntity();
        result.set_id(item.get_id());
        result.setRouteScheduleDetailId(item.getRouteScheduleDetailId());
        result.setPathImage(item.getImagePath());
        return result;
    }
}
