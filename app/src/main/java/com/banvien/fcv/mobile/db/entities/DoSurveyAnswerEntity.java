package com.banvien.fcv.mobile.db.entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by hieu on 6/29/2016.
 */
@DatabaseTable(tableName = "DoSurveyAnswer")
public class DoSurveyAnswerEntity implements Serializable {
    @DatabaseField(generatedId = true)
    private Long _id;
    @DatabaseField(canBeNull = false)
    private Long questionId;
    @DatabaseField(canBeNull = false)
    private Long surveyId;
    @DatabaseField(canBeNull = false)
    private Long outletId;
    @DatabaseField
    private String answer;
    @DatabaseField
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

    public Long get_id() {
        return _id;
    }

    public void set_id(Long _id) {
        this._id = _id;
    }

    public Long getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(Long surveyId) {
        this.surveyId = surveyId;
    }

    public Long getOutletId() {
        return outletId;
    }

    public void setOutletId(Long outletId) {
        this.outletId = outletId;
    }
}
