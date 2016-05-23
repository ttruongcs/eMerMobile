package com.banvien.fcv.mobile.beanutil;

import com.banvien.fcv.mobile.db.entities.POSMEntity;
import com.banvien.fcv.mobile.dto.POSMDTO;

import java.sql.Timestamp;

/**
 * Created by Administrator on 4/4/2016.
 */
public class POSMUtil {

    public static POSMDTO convertToDTO(POSMEntity item) {
        POSMDTO result = new POSMDTO();
        result.set_id(item.get_id());
        result.setCode(item.getCode());
        result.setName(item.getName());
        result.setPosmId(item.getPosmId());
        return result;
    }

    public static POSMEntity convertToEntity(POSMDTO item) {
        POSMEntity entity = new POSMEntity();
        entity.set_id(item.get_id());
        entity.setCode(item.getCode());
        entity.setName(item.getName());
        entity.setPosmId(item.getPosmId());
        return entity;
    }
}
