package com.banvien.fcv.mobile.dto.getfromserver;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Linh Nguyen
 * Date: 6/10/16
 * Time: 5:14 PM
 * To change this template use File | Settings | File Templates.
 */
public class RuralOpenDTO implements Serializable {
    private Long ruralOpenId;
    private String name;
    private String code;

    public Long getRuralOpenId() {
        return ruralOpenId;
    }

    public void setRuralOpenId(Long ruralOpenId) {
        this.ruralOpenId = ruralOpenId;
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
