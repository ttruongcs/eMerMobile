package com.banvien.fcv.mobile.dto;

import java.io.Serializable;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Lam Vu
 * Date: 6/24/16
 * Time: 9:45 AM
 * To change this template use File | Settings | File Templates.
 */
public class QuestionDTO implements Serializable {

    private Long questionId;
    private String questionText;
    private Boolean requireAnswer;
    private String imagePath;
    private String type;


    private List<QuestionContentDTO> questionContents;

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public Boolean getRequireAnswer() {
        return requireAnswer;
    }

    public void setRequireAnswer(Boolean requireAnswer) {
        this.requireAnswer = requireAnswer;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }


    public List<QuestionContentDTO> getQuestionContents() {
        return questionContents;
    }

    public void setQuestionContents(List<QuestionContentDTO> questionContents) {
        this.questionContents = questionContents;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }
}
