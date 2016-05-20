package com.banvien.fcv.mobile.beanutil;

import com.banvien.fcv.mobile.db.entities.OutletEntity;
import com.banvien.fcv.mobile.dto.OutletDTO;

/**
 * Created by Administrator on 4/4/2016.
 */
public class OutletUtil {

    public static OutletDTO convertToDTO(OutletEntity item) {
        OutletDTO result = new OutletDTO();
        result.set_id(item.get_id());
        result.setOutletId(item.getOutletId());
        result.setCode(item.getCode());
        result.setName(item.getName());
        result.setdCode(item.getdCode());
        result.setdName(item.getdName());
        result.setTypeName(item.getTypeName());
        result.setLocationNo(item.getLocationNo());
        result.setStreet(item.getStreet());
        result.setWard(item.getWard());
        result.setDistrict(item.getDistrict());
        result.setCityName(item.getCityName());
        result.setLat(item.getLat());
        result.setLg(item.getLg());
        return result;
    }

    public static OutletEntity convertToEntity(OutletDTO item) {
        OutletEntity entity = new OutletEntity();
        entity.set_id(item.get_id());
        entity.setOutletId(item.getOutletId());
        entity.setCode(item.getCode());
        entity.setName(item.getName());
        entity.setdCode(item.getdCode());
        entity.setdName(item.getdName());
        entity.setTypeName(item.getTypeName());
        entity.setLocationNo(item.getLocationNo());
        entity.setStreet(item.getStreet());
        entity.setWard(item.getWard());
        entity.setDistrict(item.getDistrict());
        entity.setCityName(item.getCityName());
        entity.setLat(item.getLat());
        entity.setLg(item.getLg());
        return entity;
    }
}
