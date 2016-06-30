package com.banvien.fcv.mobile.dto;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by Linh Nguyen on 6/30/2016.
 */
public class HistoryDTO implements Serializable {
    private int month;
    private boolean status;
    private double eie;

    public HistoryDTO(int month, double eie, boolean status) {
        this.month = month;
        this.eie = eie;
        this.status = status;
    }

    public double getEie() {
        return eie;
    }

    public void setEie(double eie) {
        this.eie = eie;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }
}
