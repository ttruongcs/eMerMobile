package com.banvien.fcv.mobile.utils;

/**
 * Created by Linh Nguyen on 7/3/2016.
 */
public class EIEUtil {
    public static boolean isPass(Double hotzone, Double mhs, Double facing) {
        boolean isPass = false;
        if(hotzone != null && mhs != null && facing != null) {
            if(hotzone <= 30 && mhs <= 30 && facing <= 40) {
                Double total = hotzone + mhs + facing;
                if(total <= 90) {
                    isPass = true;
                } else {
                    isPass = false;
                }
            }
        }

        return isPass;
    }
}
