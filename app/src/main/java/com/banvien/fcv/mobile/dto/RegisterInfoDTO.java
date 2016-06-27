package com.banvien.fcv.mobile.dto;

import java.io.Serializable;

/**
 * Created by Linh Nguyen on 6/25/2016.
 */
public class RegisterInfoDTO implements Serializable {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
