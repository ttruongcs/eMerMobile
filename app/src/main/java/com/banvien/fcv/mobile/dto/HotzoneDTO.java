package com.banvien.fcv.mobile.dto;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by hieu on 3/10/2016.
 */

public class HotzoneDTO implements Serializable {
    private long _id;
    private Long hotZoneId;
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

    public Long getHotZoneId() {
        return hotZoneId;
    }

    public void setHotZoneId(Long hotZoneId) {
        this.hotZoneId = hotZoneId;
    }
}
