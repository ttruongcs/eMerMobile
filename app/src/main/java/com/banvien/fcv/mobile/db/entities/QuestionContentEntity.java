package com.banvien.fcv.mobile.db.entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Lam Vu
 * Date: 6/24/16
 * Time: 9:45 AM
 * To change this template use File | Settings | File Templates.
 */
@DatabaseTable(tableName = "QuestionContent")
public class QuestionContentEntity implements Serializable {
    @DatabaseField(id = true)
    private Long questionContentId;
    @DatabaseField
    private String label;
    @DatabaseField
    private String value;
    @DatabaseField
    private Integer sort;

    @DatabaseField(canBeNull = false, foreign = true, foreignAutoRefresh = false, columnName = "questionId")
    private QuestionEntity question;

    public Long getQuestionContentId() {
        return questionContentId;
    }

    public void setQuestionContentId(Long questionContentId) {
        this.questionContentId = questionContentId;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public QuestionEntity getQuestion() {
        return question;
    }

    public void setQuestion(QuestionEntity question) {
        this.question = question;
    }
}
