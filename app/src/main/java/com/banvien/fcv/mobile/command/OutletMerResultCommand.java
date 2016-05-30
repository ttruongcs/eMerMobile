package com.banvien.fcv.mobile.command;

import com.banvien.fcv.mobile.dto.syncdto.MOutletMerResultDTO;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Linh Nguyen
 * Date: 5/17/16
 * Time: 1:31 PM
 * To change this template use File | Settings | File Templates.
 */
public class OutletMerResultCommand {
    private MOutletMerResultDTO pojo;
    private String crudaction;

    public MOutletMerResultDTO getPojo() {
        return pojo;
    }

    public void setPojo(MOutletMerResultDTO pojo) {
        this.pojo = pojo;
    }

    public String getCrudaction() {
        return crudaction;
    }

    public void setCrudaction(String crudaction) {
        this.crudaction = crudaction;
    }
}
