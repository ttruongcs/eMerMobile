package com.banvien.fcv.mobile.beanutil;

import com.banvien.fcv.mobile.db.entities.CaptureOverviewEntity;
import com.banvien.fcv.mobile.dto.CaptureOverviewDTO;

/**
 * Created by Linh Nguyen on 6/23/2016.
 */
public class CaptureOverviewUtil {
    public static CaptureOverviewDTO convertToDTO(CaptureOverviewEntity entity) {
        CaptureOverviewDTO captureOverviewDTO = new CaptureOverviewDTO();

        captureOverviewDTO.set_id(entity.get_id());
        captureOverviewDTO.setCreatedDate(entity.getCreatedDate());
        captureOverviewDTO.setPathImage(entity.getPathImage());
        captureOverviewDTO.setRouteScheduleId(entity.getRouteScheduleId());
        captureOverviewDTO.setOutletId(entity.getOutletId());

        return captureOverviewDTO;
    }

    public static CaptureOverviewEntity convertToEntity(CaptureOverviewDTO dto) {
        CaptureOverviewEntity captureOverviewEntity = new CaptureOverviewEntity();

        captureOverviewEntity.set_id(dto.get_id());
        captureOverviewEntity.setRouteScheduleId(dto.getRouteScheduleId());
        captureOverviewEntity.setPathImage(dto.getPathImage());
        captureOverviewEntity.setCreatedDate(dto.getCreatedDate());
        captureOverviewEntity.setOutletId(dto.getOutletId());

        return captureOverviewEntity;
    }
}
