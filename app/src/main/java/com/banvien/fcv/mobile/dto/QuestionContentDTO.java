package com.banvien.fcv.mobile.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Lam Vu
 * Date: 6/24/16
 * Time: 9:45 AM
 * To change this template use File | Settings | File Templates.
 */
public class QuestionContentDTO implements Serializable {
    private Long questionContentId;
    private String label;
    private String value;
    private Integer sort;

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
}
