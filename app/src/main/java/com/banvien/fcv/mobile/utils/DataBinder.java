package com.banvien.fcv.mobile.utils;

import com.banvien.fcv.mobile.dto.CatgroupDTO;
import com.banvien.fcv.mobile.dto.ComplainTypeDTO;
import com.banvien.fcv.mobile.dto.POSMDTO;
import com.banvien.fcv.mobile.dto.ProductDTO;
import com.banvien.fcv.mobile.dto.ProductgroupDTO;
import com.banvien.fcv.mobile.dto.getfromserver.HotZoneDTO;
import com.banvien.fcv.mobile.dto.getfromserver.MAuditOutletPlanDTO;
import com.banvien.fcv.mobile.dto.getfromserver.MProductDTO;
import com.banvien.fcv.mobile.dto.routeschedule.MRouteScheduleDetailDTO;
import com.banvien.fcv.mobile.dto.routeschedule.RouteScheduleInfoDTO;
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

    public static List<HotZoneDTO> readHotzoneList(Object object) {
        List<HotZoneDTO> list = null;
        try {
            list = mapper.convertValue(object, new TypeReference<List<HotZoneDTO>>() {
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

    public static List<MProductDTO> readProductList(Object object) {
        List<MProductDTO> list = null;
        try {
            list = mapper.convertValue(object, new TypeReference<List<MProductDTO>>() {
            });
        }catch (Exception e) {
            ELog.e(e.getMessage(), e);
            list = new ArrayList<>();
        }
        return list;
    }

    public static RouteScheduleInfoDTO readRouteScheduleInfo(Object object) {
        RouteScheduleInfoDTO list = null;
        try {
            list = mapper.convertValue(object, RouteScheduleInfoDTO.class);
        }catch (Exception e) {
            ELog.e(e.getMessage(), e);
        }
        return list;
    }

    public static List<ComplainTypeDTO> readComplainTypeList(Object object) {
        List<ComplainTypeDTO> list = null;
        try {
            list = mapper.convertValue(object, new TypeReference<List<ComplainTypeDTO>>() {
            });
        }catch (Exception e) {
            ELog.e(e.getMessage(), e);
            list = new ArrayList<>();
        }
        return list;
    }


    public static List<MAuditOutletPlanDTO> readMAuditOutletPlanDTOList(Object object) {
        List<MAuditOutletPlanDTO> list = null;
        try {
            list = mapper.convertValue(object, new TypeReference<List<MAuditOutletPlanDTO>>() {
            });
        }catch (Exception e) {
            ELog.e(e.getMessage(), e);
            list = new ArrayList<>();
        }
        return list;
    }

    public static List<MRouteScheduleDetailDTO> readRouteScheduleDetail(Object listRouteOutlet) {
        List<MRouteScheduleDetailDTO> list = null;
        try {
            list = mapper.convertValue(listRouteOutlet, new TypeReference<List<MRouteScheduleDetailDTO>>() {
            });
        } catch (Exception e) {
            ELog.e(e.getMessage(), e);
            list = new ArrayList<>();
        }

        return list;
    }

    public static MAuditOutletPlanDTO readAuditOutletPlan(Object auditOutletPlan) {
        MAuditOutletPlanDTO auditOutletPlanDTO = null;
        try {
            auditOutletPlanDTO = mapper.convertValue(auditOutletPlan, MAuditOutletPlanDTO.class);
        } catch (Exception e) {
            ELog.d(e.getMessage(), e);
        }


        return auditOutletPlanDTO;
    }
}
