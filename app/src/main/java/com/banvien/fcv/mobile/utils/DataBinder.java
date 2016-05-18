package com.banvien.fcv.mobile.utils;

import com.banvien.fcv.mobile.dto.CatgroupDTO;
import com.banvien.fcv.mobile.dto.HotzoneDTO;
import com.banvien.fcv.mobile.dto.POSMDTO;
import com.banvien.fcv.mobile.dto.ProductDTO;
import com.banvien.fcv.mobile.dto.ProductgroupDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hieu on 4/11/2016.
 */
public class DataBinder {
    public final static ObjectMapper mapper = new ObjectMapper();
    static {
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }

    public static List<POSMDTO> readPosmList(Object object) {
        List<POSMDTO> list = null;
        try {
            list = mapper.convertValue(object, new TypeReference<List<POSMDTO>>() {
            });
        }catch (Exception e) {
            ELog.e(e.getMessage(), e);
            list = new ArrayList<>();
        }
        return list;
    }

    public static List<HotzoneDTO> readHotzoneList(Object object) {
        List<HotzoneDTO> list = null;
        try {
            list = mapper.convertValue(object, new TypeReference<List<HotzoneDTO>>() {
            });
        }catch (Exception e) {
            ELog.e(e.getMessage(), e);
            list = new ArrayList<>();
        }
        return list;
    }

    public static List<CatgroupDTO> readCatgroupList(Object object) {
        List<CatgroupDTO> list = null;
        try {
            list = mapper.convertValue(object, new TypeReference<List<CatgroupDTO>>() {
            });
        }catch (Exception e) {
            ELog.e(e.getMessage(), e);
            list = new ArrayList<>();
        }
        return list;
    }

    public static List<ProductgroupDTO> readProductgroupList(Object object) {
        List<ProductgroupDTO> list = null;
        try {
            list = mapper.convertValue(object, new TypeReference<List<ProductgroupDTO>>() {
            });
        }catch (Exception e) {
            ELog.e(e.getMessage(), e);
            list = new ArrayList<>();
        }
        return list;
    }

    public static List<ProductDTO> readProductList(Object object) {
        List<ProductDTO> list = null;
        try {
            list = mapper.convertValue(object, new TypeReference<List<ProductDTO>>() {
            });
        }catch (Exception e) {
            ELog.e(e.getMessage(), e);
            list = new ArrayList<>();
        }
        return list;
    }
}
