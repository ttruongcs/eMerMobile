package com.banvien.fcv.mobile.beanutil;

import com.banvien.fcv.mobile.db.entities.SurveyEntity;
import com.banvien.fcv.mobile.dto.SurveyDTO;

/**
 * Created by Administrator on 4/4/2016.
 */
public class SurveyUtil {
    public static SurveyEntity convertToEntity(SurveyDTO dto) {
        SurveyEntity result = new SurveyEntity();
        result.setSurveyId(dto.getSurveyId());
        result.setActive(dto.getActive());
        result.setEndDate(dto.getEndDate());
        result.setStartDate(dto.getStartDate());
        result.setName(dto.getName());
        return result;
    }
}
