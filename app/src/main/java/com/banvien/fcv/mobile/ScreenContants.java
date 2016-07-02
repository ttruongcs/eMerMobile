package com.banvien.fcv.mobile;

import com.j256.ormlite.stmt.query.In;

/**
 * Created by Ban Vien Ltd.
 * User: Vien Nguyen (vien.nguyen@banvien.com)
 * Date: 6/25/12
 * Time: 10:11 AM
 */
public class ScreenContants {
    public static final String OUTLET_TASKLIST_ACTION = "com.banvien.fcv.mobile.OUTLET_TASKLIST";
    public static final String POSM_LIST = "com.banvien.fcv.mobile.POSM_LIST";
    public static final String HOTZONE_LIST = "com.banvien.fcv.mobile.HOTZONE_LIST";
    public static final String PRODUCT_LIST = "com.banvien.fcv.mobile.PRODUCT_LIST";
    public static final String PRODUCTGROUP_LIST = "com.banvien.fcv.mobile.PRODUCTGROUP_LIST";
    public static final String CATGROUP_LIST = "com.banvien.fcv.mobile.CATGROUP_LIST";
    public static final String ROUTESCHEDULE_LIST = "com.banvien.fcv.mobile.ROUTESCHEDULE_LIST";
    public static final String COMPLAINTYPE_LIST = "com.banvien.fcv.mobile.COMPLAINTYPE_LIST";
    public static final Integer OUTLET_STATUS_UNFINISHED = 0;
    public static final Integer OUTLET_STATUS_DOING = 1;
    public static final Integer OUTLET_STATUS_FINISHED = 2;
    public static final String KEY_OUTLET_ID = "com.banvien.fcv.emer.outletId";
    public static final String KEY_SURVEY_ID = "com.banvien.fcv.emer.surveyId";
    public static final String KEY_ROUTESCHEDULE_DETAIL = "com.banvien.fcv.emer.routeScheduleDetailId";
    public static final String KEY_POSM_ID = "com.banvien.fcv.emer.posmid";
    public static final String KEY_GPS_UPDATE = "com.banvien.fcv.emer.gpsUpdate";
    public static final String CAPTURE_TYPE = "com.banvien.fcv.emer.capture.type";
    public static final String ROUTESCHEDULE_ID= "com.banvien.fcv.emer.routeschedule.id";
    public static final String KEY_SURVEY_TITLE = "com.banvien.fcv.emer.survey_title";
    public static final String KEY_TAKE_PICTURE_ACTION = "com.banvien.fcv.emer.take_picture_action";
    public static final Integer OUTLET_MER_ACTIVE = 1;
    public static final String INCREASE_VALUE = "INCREASE";
    public static final String DECREASE_VALUE = "DECREASE";

    // CONTANST FOR DATATYPE
    public static final String POSM_TYPE = "POSM";
    public static final String COMPLAINTYPE = "COMPLAINTYPE";
    public static final String COMPLAIN = "COMPLAIN";
    public static final String ORDER = "ORDER";
    public static final String PRODUCT_ID = "productId";
    public static final String CODE = "code";
    public static final String DATA_TYPE = "dataType";
    public static final String REFERENCE_VALUE = "referenceValue";
    public static final String OUTLETID = "outletId";

    //HOTZONE
    public static final String HOTZONE = "HOTZONE";
    public static final String HOTZONE_BEFORE = "HOTZONE_BEFORE";
    public static final String HOTZONE_AFTER = "HOTZONE_AFTER";

    //MHS
    public static final String MHS = "MHS";
    public static final String MHS_BEFORE = "MHS_BEFORE";
    public static final String MHS_AFTER = "MHS_AFTER";

    //EIE
    public static final String EIE_BEFORE = "EIE_BEFORE";
    public static final String EIE_AFTER = "EIE_AFTER";

    //FACING
    public static final String FACING = "FACING";
    public static final String FACING_BEFORE = "FACING_BEFORE";
    public static final String FACING_AFTER = "FACING_AFTER";

    //Product
    public static final String PRODUCT_AFTER = "PRODUCT_AFTER";


    // DATATYPE IMAGE
    public static final String IMAGE_OVERVIEW = "IMAGE_OVERVIEW";
    public static final String IMAGE_TOOL =  "IMAGE_TOOL";
    public static final String IMAGE_UNIFORM =  "IMAGE_UNIFORM";
    //BEFORE
    public static final String IMAGE_BEFORE =  "IMAGE_BEFORE";

    //AFTER
    public static final String IMAGE_AFTER =  "IMAGE_AFTER";

    //GPS
    public static final String GPS = "GPS";
    public static final Float GPS_DISTANCE = 50f;


    // TYPE OF NODE STEP
        // HOME
    public static final String HOME_STEP_STARTDAY = "HOME_STARTDAY";
    public static final String HOME_STEP_INOUTLET = "HOME_INOUTLET";
    public static final String HOME_STEP_ENDDAY = "HOME_ENDDAY";

            // HOME_STARTDAY
    public static final String HOME_STEP_STARTDAY_DONGBODULIEUPHANCONG = "HOME_STEP_STARTDAY_DONGBODULIEUPHANCONG";
    public static final String HOME_STEP_STARTDAY_THEMCUAHANGNEUMUON = "HOME_STEP_STARTDAY_THEMCUAHANGNEUMUON";
    public static final String HOME_STEP_STARTDAY_CHUPHINHDONGPHUC = "HOME_STEP_STARTDAY_CHUPHINHDONGPHUC";
    public static final String HOME_STEP_STARTDAY_CHUPHINHCONGCUDUNGCU = "HOME_STEP_STARTDAY_CHUPHINHCONGCUDUNGCU";
    public static final String HOME_STEP_STARTDAY_CHUPHINHCUAHANGDAUTIEN = "HOME_STEP_STARTDAY_CHUPHINHCUAHANGDAUTIEN";
    public static final String HOME_STEP_STARTDAY_XACNHANLAMVIEC = "HOME_STEP_STARTDAY_XACNHANLAMVIEC";

            // HOME_STARTDAY
    public static final String HOME_STEP_INOUTLET_CHECKIN = "HOME_STEP_INOUTLET_CHECKIN";
    public static final String HOME_STEP_INOUTLET_CHUPANHOVERVIEW = "HOME_STEP_INOUTLET_CHUPANHOVERVIEW";
    public static final String HOME_STEP_INOUTLET_XEMTHONGTINDANGKYVALICHSUEIE = "HOME_STEP_INOUTLET_XEMTHONGTINDANGKYVALICHSUEIE";
    public static final String HOME_STEP_INOUTLET_KHAOSATTRUNGBAYTRUOC = "HOME_STEP_INOUTLET_KHAOSATTRUNGBAYTRUOC";
    public static final String HOME_STEP_INOUTLET_KHAOSATTRUNGBAYSAU = "HOME_STEP_INOUTLET_KHAOSATTRUNGBAYSAU";
    public static final String HOME_STEP_INOUTLET_HUTHANGDATHANG = "HOME_STEP_INOUTLET_HUTHANGDATHANG";
    public static final String HOME_STEP_INOUTLET_KHAOSATDICHVUKHACHHANG = "HOME_STEP_INOUTLET_KHAOSATDICHVUKHACHHANG";
    public static final String HOME_STEP_INOUTLET_KHAOSATPOSM = "HOME_STEP_INOUTLET_KHAOSATPOSM";
    public static final String HOME_STEP_INOUTLET_GHINHANKHIEUNAI = "HOME_STEP_INOUTLET_GHINHANKHIEUNAI";
    public static final String HOME_STEP_INOUTLET_DONGBOCUAHANG = "HOME_STEP_INOUTLET_DONGBOCUAHANG";
    public static final String HOME_STEP_INOUTLET_TINHTRANGCUAHANG = "HOME_STEP_INOUTLET_TINHTRANGCUAHANG";

            // ENDAY
    public static final String HOME_STEP_ENDDAY_CHUPHINHCUOINGAY = "HOME_STEP_ENDDAY_CHUPHINHCUOINGAY";
    public static final String HOME_STEP_ENDDAY_DONGBOKETQUA = "HOME_STEP_ENDDAY_DONGBOKETQUA";
    public static final String HOME_STEP_ENĐAY_KETTHUCCUOINGAY = "HOME_STEP_ENDAY_KETTHUCCUOINGAY";


            //HOME_STARTDAY
//    public static final String STARTDAY_STEP_STARTDAY = "HOME_STARTDAY";
//    public static final String STARTDAY_STEP_INOUTLET = "HOME_INOUTLET";
//    public static final String STARTDAY_STEP_ENDDAY = "HOME_ENDDAY";

    // STATUS OF TREE STEP
    public static final Integer STATUS_STEP_DONE = 2;
    public static final Integer STATUS_STEP_INPROGRESS = 1;
    public static final Integer STATUS_STEP_NOTYET = 0;

    //Image path
    public static final String CAPTURE_FCV_IMAGE = "fcvImage/";
    public static final String CAPTURE_UNIFORM_PATH = "fcvImage/uniform/";
    public static final String CAPTURE_TOOL_PATH = "fcvImage/tool/";
    public static final String CAPTURE_FIRST_OUTLET = "fcvImage/firstOutlet/";
    public static final String CAPTURE_ENDDAY = "fcvImage/endday/";
    public static final String CAPTURE_OVERVIEW = "fcvImage/overview/";
    public static final String CAPTURE_CONFIRM_WORKING = "fcvImage/working/";
    public static final String CAPTURE_BEFORE_PATH = "fcvImage/before/";
    public static final String CAPTURE_AFTER_PATH = "fcvImage/after/";

    public static final String UNFINISH = "UNFINISH";
    public static final String DOING = "DOING";
    public static final String FINISHED = "FINISHED";

    //Shared Preferences
    public static final String MyPREFERENCES = "shortagePrefs";
    public static final String BeforePREFERENCES = "beforePrefs";

    public static final String INSERT = "INSERT";
    public static final String REMOVE = "REMOVE";

    // Confirm working
    public static final String CONFIRM_WORKING_PATH = "confirm_";
    public static final String CONFIRM_WORKING = "CONFIRM_WORKING";

    //Column parent name
    public static final String PREPARE_DATE_COLUMN = "chuanBiDauNgay";
    public static final String IN_OUTLET = "trongCuaHang";
    public static final String END_DATE_COLUMN = "ketThucCuoiNgay";
    //Column child name

    public static final String START_DATE_SYNC = "dongBoDuLieuPhanCong";
    public static final String ADD_OUTLET = "themCuaHangNeuMuon";
    public static final String CAPTURE_UNIFORM = "chupHinhDongPhuc";
    public static final String CAPTURE_TOOL = "chupHinhCongCuDungCu";
    public static final String CONFIRM_WORKING_COLUMN = "xacNhanLamViec";
    public static final String CAPTURE_FIRST_OUTLET_COLUMN = "chupHinhCuaHangDauTien";
    public static final String CHECK_IN_COLUMN = "checkIn";
    public static final String CAPTURE_OVERVIEW_COLUMN = "chupAnhOverview";
    public static final String REGISTER_HISTORY_COLUMN = "xemThongTinDangKyLichSuEIE";
    public static final String BEFORE_DISPLAY_COLUMN = "khaoSatTrungBayTruoc";
    public static final String AFTER_DISPLAY_COLUMN = "khaoSatTrungBaySau";
    public static final String SHORTAGE_PRODUCT_COLUMN = "hutHangDatHang";
    public static final String SURVEY_COLUMN = "khaoSat";
    public static final String CONFIRM_END_COLUMN = "ketThucCuoiNgay";
    public static final String CHECK_OUTLET_COLUMN = "tinhTrangCuaHang";



}
