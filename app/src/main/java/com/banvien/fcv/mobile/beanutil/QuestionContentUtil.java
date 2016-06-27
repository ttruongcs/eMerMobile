package com.banvien.fcv.mobile.beanutil;

import com.banvien.fcv.mobile.db.entities.QuestionContentEntity;
import com.banvien.fcv.mobile.db.entities.QuestionEntity;
import com.banvien.fcv.mobile.dto.QuestionContentDTO;
import com.banvien.fcv.mobile.dto.QuestionDTO;

/**
 * Created by Administrator on 4/4/2016.
 */
public class QuestionContentUtil {
    public static QuestionContentEntity convertToEntity(QuestionContentDTO dto) {
        QuestionContentEntity result = new QuestionContentEntity();
        result.setQuestionContentId(dto.getQuestionContentId());
        result.setLabel(dto.getLabel());
        result.setValue(dto.getValue());
        result.setSort(dto.getSort());
        return result;
    }
}
