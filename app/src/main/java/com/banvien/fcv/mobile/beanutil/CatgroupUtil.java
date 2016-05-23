package com.banvien.fcv.mobile.beanutil;

import com.banvien.fcv.mobile.db.entities.CatgroupEntity;
import com.banvien.fcv.mobile.dto.CatgroupDTO;

/**
 * Created by Administrator on 4/4/2016.
 */
public class CatgroupUtil {

    public static CatgroupDTO convertToDTO(CatgroupEntity item) {
        CatgroupDTO result = new CatgroupDTO();
        result.set_id(item.get_id());
        result.setCode(item.getCode());
        result.setName(item.getName());
        result.setCatgroupId(item.getCatgroupId());
        return result;
    }

    public static CatgroupEntity convertToEntity(CatgroupDTO item) {
        CatgroupEntity entity = new CatgroupEntity();
        entity.set_id(item.get_id());
        entity.setCode(item.getCode());
        entity.setName(item.getName());
        entity.setCatgroupId(item.getCatgroupId());
        return entity;
    }
}
