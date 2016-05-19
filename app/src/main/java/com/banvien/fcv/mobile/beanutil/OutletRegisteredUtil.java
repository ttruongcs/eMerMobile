package com.banvien.fcv.mobile.beanutil;

import com.banvien.fcv.mobile.db.entities.OutletMerEntity;
import com.banvien.fcv.mobile.db.entities.OutletRegisteredEntity;
import com.banvien.fcv.mobile.dto.OutletMerDTO;
import com.banvien.fcv.mobile.dto.OutletRegisteredDTO;

/**
 * Created by Administrator on 4/4/2016.
 */
public class OutletRegisteredUtil {

    public static OutletRegisteredDTO convertToDTO(OutletRegisteredEntity item) {
        OutletRegisteredDTO result = new OutletRegisteredDTO();
        result.set_id(item.get_id());
        result.setExhibitRegisteredId(item.getExhibitRegisteredId());
        result.setExhibitRegisteredDetailId(item.getExhibitRegisteredDetailId());
        result.setOutletId(item.getOutletId());
        result.setOutletModelId(item.getOutletId());
        result.setDataType(item.getDataType());
        result.setRegisterValue(item.getRegisterValue());
        return result;
    }

    public static OutletRegisteredEntity convertToEntity(OutletRegisteredDTO item) {
        OutletRegisteredEntity entity = new OutletRegisteredEntity();
        entity.set_id(item.get_id());
        entity.setExhibitRegisteredId(item.getExhibitRegisteredId());
        entity.setExhibitRegisteredDetailId(item.getExhibitRegisteredDetailId());
        entity.setOutletId(item.getOutletId());
        entity.setOutletModelId(item.getOutletId());
        entity.setDataType(item.getDataType());
        entity.setRegisterValue(item.getRegisterValue());
        return entity;
    }
}
