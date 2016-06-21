package com.banvien.fcv.mobile.beanutil;

import com.banvien.fcv.mobile.db.entities.CaptureUniformEntity;
import com.banvien.fcv.mobile.dto.CaptureUniformDTO;

/**
 * Created by Linh Nguyen on 6/21/2016.
 */
public class CaptureUniformUtil {
    public static CaptureUniformDTO convertToDTO(CaptureUniformEntity entity) {
        CaptureUniformDTO captureUniformDTO = new CaptureUniformDTO();
        captureUniformDTO.set_id(entity.get_id());
        captureUniformDTO.setRouteScheduleId(entity.getRouteScheduleId());
        captureUniformDTO.setCreatedDate(entity.getCreatedDate());
        captureUniformDTO.setPathImage(entity.getPathImage());

        return captureUniformDTO;
    }

    public static CaptureUniformEntity convertToEntity(CaptureUniformDTO dto) {
        CaptureUniformEntity entity = new CaptureUniformEntity();
        entity.set_id(dto.get_id());
        entity.setPathImage(dto.getPathImage());
        entity.setRouteScheduleId(dto.getRouteScheduleId());
        entity.setCreatedDate(dto.getCreatedDate());

        return entity;
    }
}
