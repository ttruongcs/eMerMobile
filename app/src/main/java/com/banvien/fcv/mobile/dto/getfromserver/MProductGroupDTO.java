package com.banvien.fcv.mobile.dto.getfromserver;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Linh Nguyen
 * Date: 5/9/16
 * Time: 1:03 PM
 * To change this template use File | Settings | File Templates.
 */
public class MProductGroupDTO implements Serializable {
    private Long productGroupId;
    @JsonIgnore
    private Long catGroupId;
    private String code;
    private String name;

    public Long getProductGroupId() {
        return productGroupId;
    }

    public void setProductGroupId(Long productGroupId) {
        this.productGroupId = productGroupId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCatGroupId() {
        return catGroupId;
    }

    public void setCatGroupId(Long catGroupId) {
        this.catGroupId = catGroupId;
    }
}
