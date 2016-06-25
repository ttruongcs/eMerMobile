package com.banvien.fcv.mobile.dto.getfromserver;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: TuHuynh
 * Date: 6/21/16
 * Time: 1:53 PM
 * To change this template use File | Settings | File Templates.
 */
public class EIEHistoryDTO implements Serializable {
    private double eie;
    private Integer status;

    public double getEie() {
        return eie;
    }

    public void setEie(double eie) {
        this.eie = eie;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
