package com.banvien.fcv.mobile.beanutil;

import com.banvien.fcv.mobile.db.entities.ProductEntity;
import com.banvien.fcv.mobile.db.entities.ProductgroupEntity;
import com.banvien.fcv.mobile.dto.ProductDTO;
import com.banvien.fcv.mobile.dto.ProductgroupDTO;
import com.banvien.fcv.mobile.dto.getfromserver.MProductDTO;

/**
 * Created by Administrator on 4/4/2016.
 */
public class ProductUtil {

    public static MProductDTO convertToDTO(ProductEntity item) {
        MProductDTO result = new MProductDTO();
        result.setProductId(item.getProductId());
        result.setProductGroupId(item.getProductGroupId());
        result.setCode(item.getCode());
        result.setName(item.getName());
        return result;
    }

    public static ProductEntity convertToEntity(MProductDTO item) {
        ProductEntity entity = new ProductEntity();
        entity.setProductId(item.getProductId());
        entity.setProductGroupId(item.getProductGroupId());
        entity.setCode(item.getCode());
        entity.setName(item.getName());
        return entity;
    }
}
