package com.banvien.fcv.mobile.beanutil;

import com.banvien.fcv.mobile.db.entities.QuestionEntity;
import com.banvien.fcv.mobile.db.entities.SurveyEntity;
import com.banvien.fcv.mobile.dto.QuestionDTO;
import com.banvien.fcv.mobile.dto.SurveyDTO;

/**
 * Created by Administrator on 4/4/2016.
 */
public class QuestionUtil {
    public static QuestionEntity convertToEntity(QuestionDTO dto) {
        QuestionEntity result = new QuestionEntity();
        result.setQuestionId(dto.getQuestionId());
        result.setImagePath(dto.getImagePath());
        result.setType(dto.getType());
        result.setQuestionText(dto.getQuestionText());
        result.setRequireAnswer(dto.getRequireAnswer());
        return result;
    }
}
