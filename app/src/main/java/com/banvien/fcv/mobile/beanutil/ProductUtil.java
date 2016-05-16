package com.banvien.fcv.mobile.beanutil;

import com.banvien.fcv.mobile.db.entities.ProductEntity;
import com.banvien.fcv.mobile.db.entities.ProductgroupEntity;
import com.banvien.fcv.mobile.dto.ProductDTO;
import com.banvien.fcv.mobile.dto.ProductgroupDTO;

/**
 * Created by Administrator on 4/4/2016.
 */
public class ProductUtil {

    public static ProductDTO convertToDTO(ProductEntity item) {
        ProductDTO result = new ProductDTO();
        result.set_id(item.get_id());
        result.setCode(item.getCode());
        result.setName(item.getName());
        return result;
    }

    public static ProductEntity convertToEntity(ProductDTO item) {
        ProductEntity entity = new ProductEntity();
        entity.set_id(item.get_id());
        entity.setCode(item.getCode());
        entity.setName(item.getName());
        return entity;
    }
}
