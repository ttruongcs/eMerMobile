package com.banvien.fcv.mobile.beanutil;

import com.banvien.fcv.mobile.db.entities.ShortageProductEntity;
import com.banvien.fcv.mobile.dto.ShortageProductDTO;

/**
 * Created by Linh Nguyen on 6/24/2016.
 */
public class ShortageProductUtil {
    public static ShortageProductDTO convertToDTO(ShortageProductEntity entity) {
        ShortageProductDTO shortageProductDTO = new ShortageProductDTO();
        shortageProductDTO.set_id(entity.get_id());
        shortageProductDTO.setOutletId(entity.getOutletId());
        shortageProductDTO.setProductCode(entity.getProductCode());
        shortageProductDTO.setCreatedDate(entity.getCreatedDate());

        return shortageProductDTO;
    }

    public static ShortageProductEntity convertToEntity(ShortageProductDTO dto) {
        ShortageProductEntity shortageProductEntity = new ShortageProductEntity();
        shortageProductEntity.set_id(dto.get_id());
        shortageProductEntity.setProductCode(dto.getProductCode());
        shortageProductEntity.setOutletId(dto.getOutletId());
        shortageProductEntity.setCreatedDate(dto.getCreatedDate());

        return shortageProductEntity;
    }
}
