package com.banvien.fcv.mobile.dto;

import java.io.Serializable;

/**
 * Created by Linh Nguyen on 5/23/2016.
 */
public class ComplainTypeDTO implements Serializable {
    private long _id;
    private String name;
    private String code;
    private String description;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
