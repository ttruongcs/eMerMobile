package com.banvien.fcv.mobile.db.entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 * User: Lam Vu
 * Date: 6/24/16
 * Time: 9:45 AM
 * To change this template use File | Settings | File Templates.
 */
@DatabaseTable(tableName = "Question")
public class QuestionEntity implements Serializable {
    @DatabaseField(generatedId = true)
    private Long _id;
    @DatabaseField(canBeNull = false)
    private Long questionId;
    @DatabaseField
    private String questionText;
    @DatabaseField
    private Boolean requireAnswer;
    @DatabaseField
    private String imagePath;
    @DatabaseField
    private String type;
    @DatabaseField(canBeNull = false, index = true)
    private Long surveyId;


    @ForeignCollectionField(eager = true)
    private Collection<QuestionContentEntity> questionContents;

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


    public Collection<QuestionContentEntity> getQuestionContents() {
        return questionContents;
    }

    public void setQuestionContents(Collection<QuestionContentEntity> questionContents) {
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

    public Long get_id() {
        return _id;
    }

    public void set_id(Long _id) {
        this._id = _id;
    }


}
