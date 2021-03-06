package com.banvien.fcv.mobile.dto;

import java.io.Serializable;

public class ProductDTO implements Serializable {
    private long _id;
    private Long productId;
    private Long productGroupId;
    private String name;
    private String code;
    private Double weight;

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
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

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
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

    @Override
    public String toString() {
        return "ProductDTO{" +
                "_id=" + _id +
                ", productId=" + productId +
                ", productGroupId=" + productGroupId +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}
