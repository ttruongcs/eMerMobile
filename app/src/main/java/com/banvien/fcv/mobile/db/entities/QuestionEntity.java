package com.banvien.fcv.mobile.db.entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Lam Vu
 * Date: 6/24/16
 * Time: 9:45 AM
 * To change this template use File | Settings | File Templates.
 */
@DatabaseTable(tableName = "Question")
public class QuestionEntity implements Serializable {
    @DatabaseField(canBeNull = false, id = true)
    private Long questionId;
    @DatabaseField
    private String questionText;
    @DatabaseField
    private Boolean requireAnswer;
    @DatabaseField
    private String imagePath;
    @DatabaseField
    private String type;
    @DatabaseField(canBeNull = false)
    private Long surveyId;

    @ForeignCollectionField(eager = true, foreignFieldName = "questionId")
    private List<QuestionContentEntity> questionContents;

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


    public List<QuestionContentEntity> getQuestionContents() {
        return questionContents;
    }

    public void setQuestionContents(List<QuestionContentEntity> questionContents) {
        this.questionContents = questionContents;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }

    public Long getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(Long surveyId) {
        this.surveyId = surveyId;
    }
}
