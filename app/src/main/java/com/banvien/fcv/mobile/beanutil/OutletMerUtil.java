package com.banvien.fcv.mobile.beanutil;

import com.banvien.fcv.mobile.db.entities.CatgroupEntity;
import com.banvien.fcv.mobile.db.entities.OutletMerEntity;
import com.banvien.fcv.mobile.dto.CatgroupDTO;
import com.banvien.fcv.mobile.dto.OutletMerDTO;

/**
 * Created by Administrator on 4/4/2016.
 */
public class OutletMerUtil {

    public static OutletMerDTO convertToDTO(OutletMerEntity item) {
        OutletMerDTO result = new OutletMerDTO();
        result.set_id(item.get_id());
        result.setRouteScheduleId(item.getRouteScheduleId());
        result.setRouteScheduleDetailId(item.getRouteScheduleDetailId());
        result.setOutletId(item.getOutletId());
        result.setDataType(item.getDataType());
        result.setRegisterValue(item.getRegisterValue());
        result.setActualValue(item.getActualValue());
        result.setExhibitRegisteredId(item.getExhibitRegisteredId());
        result.setExhibitRegisteredDetailId(item.getExhibitRegisteredDetailId());
        result.setReferenceValue(item.getReferenceValue());
        return result;
    }

    public static OutletMerEntity convertToEntity(OutletMerDTO item) {
        OutletMerEntity entity = new OutletMerEntity();
        entity.set_id(item.get_id());
        entity.setRouteScheduleId(item.getRouteScheduleId());
        entity.setRouteScheduleDetailId(item.getRouteScheduleDetailId());
        entity.setOutletId(item.getOutletId());
        entity.setDataType(item.getDataType());
        entity.setRegisterValue(item.getRegisterValue());
        entity.setActualValue(item.getActualValue());
        entity.setExhibitRegisteredId(item.getExhibitRegisteredId());
        entity.setExhibitRegisteredDetailId(item.getExhibitRegisteredDetailId());
        entity.setReferenceValue(item.getReferenceValue());
        return entity;
    }
}
