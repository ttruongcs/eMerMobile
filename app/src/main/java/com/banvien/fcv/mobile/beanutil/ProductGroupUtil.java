package com.banvien.fcv.mobile.beanutil;

import com.banvien.fcv.mobile.db.entities.ProductgroupEntity;
import com.banvien.fcv.mobile.dto.ProductgroupDTO;

/**
 * Created by Administrator on 4/4/2016.
 */
public class ProductGroupUtil {

    public static ProductgroupDTO convertToDTO(ProductgroupEntity item) {
        ProductgroupDTO result = new ProductgroupDTO();
        result.set_id(item.get_id());
        result.setCode(item.getCode());
        result.setName(item.getName());
        return result;
    }

    public static ProductgroupEntity convertToEntity(ProductgroupDTO item) {
        ProductgroupEntity entity = new ProductgroupEntity();
        entity.set_id(item.get_id());
        entity.setCode(item.getCode());
        entity.setName(item.getName());
        return entity;
    }
}
