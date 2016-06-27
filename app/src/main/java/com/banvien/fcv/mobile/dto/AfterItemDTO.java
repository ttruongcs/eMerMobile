package com.banvien.fcv.mobile.dto;

import java.io.Serializable;

/**
 * Created by Linh Nguyen on 6/16/2016.
 */
public class AfterItemDTO implements Serializable {
    private Integer numberOfFace;
    private Integer yesNo;

    public Integer getNumberOfFace() {
        return numberOfFace;
    }

    public void setNumberOfFace(Integer numberOfFace) {
        this.numberOfFace = numberOfFace;
    }

    public Integer getYesNo() {
        return yesNo;
    }

    public void setYesNo(Integer yesNo) {
        this.yesNo = yesNo;
    }
}
