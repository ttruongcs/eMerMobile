package com.banvien.fcv.mobile.dto;

import java.io.Serializable;


public class ConfigDTO implements Serializable {
    private long _id;
    private String name;
    private String code;

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
}
