package com.banvien.fcv.mobile.dto.getfromserver;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Linh Nguyen
 * Date: 5/9/16
 * Time: 1:03 PM
 * To change this template use File | Settings | File Templates.
 */
public class MProductDTO implements Serializable {
    private Long productId;
    private Long productGroupId;
    private String code;
    private String name;
    private Double weight;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getProductGroupId() {
        return productGroupId;
    }

    public void setProductGroupId(Long productGroupId) {
        this.productGroupId = productGroupId;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }
}
