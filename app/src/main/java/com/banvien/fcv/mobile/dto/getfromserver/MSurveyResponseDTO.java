package com.banvien.fcv.mobile.dto.getfromserver;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: DEll
 * Date: 6/26/16
 * Time: 4:34 PM
 * To change this template use File | Settings | File Templates.
 */
public class MSurveyResponseDTO implements Serializable {
    private Long questionId;
    private String answer;

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
