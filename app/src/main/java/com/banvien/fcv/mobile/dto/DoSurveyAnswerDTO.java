package com.banvien.fcv.mobile.dto;

import java.io.Serializable;

/**
 * Created by hieu on 6/29/2016.
 */
public class DoSurveyAnswerDTO implements Serializable {
    private Long questionId;
    private String answer;
    private String extra;

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

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }
}
