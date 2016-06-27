package com.banvien.fcv.mobile.dto;

import java.io.Serializable;

/**
 * Created by Linh Nguyen on 6/16/2016.
 */
public class AfterDisplayDTO implements Serializable {
    private Long outletModelId;
    private String outletModelName;
    private OutletMerDTO mhs;


    public Long getOutletModelId() {
        return outletModelId;
    }

    public void setOutletModelId(Long outletModelId) {
        this.outletModelId = outletModelId;
    }

    public String getOutletModelName() {
        return outletModelName;
    }

    public void setOutletModelName(String outletModelName) {
        this.outletModelName = outletModelName;
    }

    public OutletMerDTO getMhs() {
        return mhs;
    }

    public void setMhs(OutletMerDTO mhs) {
        this.mhs = mhs;
    }

    @Override
    public String toString() {
        return "AfterDisplayDTO{" +
                "outletModelId=" + outletModelId +
                ", outletModelName='" + outletModelName + '\'' +
                ", mhs=" + mhs +
                '}';
    }
}
