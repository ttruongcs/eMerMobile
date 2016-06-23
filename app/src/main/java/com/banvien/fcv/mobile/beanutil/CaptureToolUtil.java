package com.banvien.fcv.mobile.beanutil;

import com.banvien.fcv.mobile.db.entities.CaptureToolEntity;
import com.banvien.fcv.mobile.dto.CaptureToolDTO;

/**
 * Created by Linh Nguyen on 6/21/2016.
 */
public class CaptureToolUtil {
    public static CaptureToolDTO convertToDTO(CaptureToolEntity entity) {
        CaptureToolDTO captureToolDTO = new CaptureToolDTO();
        captureToolDTO.set_id(entity.get_id());
        captureToolDTO.setCreatedDate(entity.getCreatedDate());
        captureToolDTO.setPathImage(entity.getPathImage());
        captureToolDTO.setRouteScheduleId(entity.getRouteScheduleId());

        return captureToolDTO;
    }

    public static CaptureToolEntity convertToEntity(CaptureToolDTO dto) {
        CaptureToolEntity captureToolEntity = new CaptureToolEntity();
        captureToolEntity.set_id(dto.get_id());
        captureToolEntity.setRouteScheduleId(dto.getRouteScheduleId());
        captureToolEntity.setPathImage(dto.getPathImage());
        captureToolEntity.setCreatedDate(dto.getCreatedDate());

        return captureToolEntity;
    }
}
