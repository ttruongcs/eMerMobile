package com.banvien.fcv.mobile.beanutil;

import com.banvien.fcv.mobile.db.entities.ComplainTypeEntity;
import com.banvien.fcv.mobile.dto.ComplainTypeDTO;

/**
 * Created by Linh Nguyen on 5/23/2016.
 */
public class ComplainTypeUtil {
    public static ComplainTypeDTO convertToDTO(ComplainTypeEntity entity) {
        ComplainTypeDTO result = new ComplainTypeDTO();
        result.set_id(entity.get_id());
        result.setName(entity.getName());
        result.setCode(entity.getCode());
        result.setDescription(entity.getDescription());

        return result;
    }

    public static ComplainTypeEntity convertToEntity(ComplainTypeDTO dto) {
        ComplainTypeEntity result = new ComplainTypeEntity();
        result.set_id(dto.get_id());
        result.setName(dto.getName());
        result.setCode(dto.getCode());
        result.setDescription(dto.getDescription());

        return result;
    }
}
