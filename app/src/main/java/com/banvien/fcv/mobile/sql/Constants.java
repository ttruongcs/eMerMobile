package com.banvien.fcv.mobile.sql;

public class Constants {
	public static final String OUTLET_AUTHORITY = "com.banvien.fcv.mobile.provider.Outlet";
	public static final String OUTLET_TABLE_NAME = "outlet";
	public static final String PROMOTION_PRODUCT_TABLE_NAME = "promotion_product";
	public static final String PROMOTION_TABLE_NAME = "promotion";
	public static final String FULL_RANGE_SKU_TABLE_NAME = "full_range_sku";
	public static final String IFT_DISPLAY_LOCATION_TABLE_NAME = "ift_display_location";
	public static final String OUTLET_POSM_TABLE_NAME = "outlet_posm";
	public static final String REGION_PROMOTIONS_TABLE_NAME = "region_promotions";
	public static final String PROMOTION_PRODUCT_TO_HANDHELD_TABLE_NAME = "promotion_product_to_handheld";
	public static final String OUTLET_BRAND_TABLE_NAME = "outlet_brand";
	public static final String OUTLET_CONTENT_TYPE_STATUS_TABLE_NAME = "outlet_status";
	public static final String OUTLET_DBB_CONTENT_TYPE_STATUS_TABLE_NAME = "outlet_dbb_status";
	public static final String DISTRIBUTOR_TABLE_NAME = "distributor";
	public static final String OUTLET_PICTURE_TABLE_NAME = "outlet_picture";
	public static final String UNIT_TABLE_NAME = "unit";
	public static final String OUTLET_FACING_REGISTER_TABLE_NAME = "outlet_facing_register";
	public static final String OUTLET_POSM_MINIVALUE_TABLE_NAME = "outlet_posm_minivalue_register";
	public static final String OUTLET_DBB_POSM__REGISTER_TABLE_NAME = "outlet_dbb_posm_register";
	
	//table 4 check data
	public static final String FULL_RANGE_SKU_CHECK_TABLE_NAME = "full_range_sku_check";
	public static final String DBB_POSM_FULL_RANGE_SKU_CHECK_TABLE_NAME = "dbb_posm_full_range_sku_check";
	public static final String IFT_DISPLAY_LOCATION_CHECK_TABLE_NAME = "ift_display_location_check";
	public static final String OUTLET_POSM_CHECK_TABLE_NAME = "outlet_posm_check";
	public static final String PROMOTION_CHECK_TABLE_NAME = "promotion_check";
	public static final String OUTLET_BRAND_CHECK_TABLE_NAME = "outlet_brand_check";
	public static final String FULLRANGE_FACING_TABLE_NAME = "fullrange_facing";
	public static final String OUTLET_LOCATION_REGISTER_TABLE_NAME = "outlet_location_register";
	public static final String OUTLET_LASTEST_BONUS_TABLE_NAME = "outlet_lastest_bonus";
	public static final String OUTLET_RIVAL = "outlet_rival";
	
	
	public static final Integer DISTRIBUTION_ID = 1;
	public static final Integer OUTLET_FACING_ID = 2;
	public static final Integer PROMOTION_ID = 3;
	public static final Integer PICTURE_ID = 4;

    //SQL Database
    public static final String TAG = "FCVDatabaseHelper";
	public static final String DATABASE_NAME = "fcv.db";
	public static final int DATABASE_VERSION = 4;

    //Table for store
    public static final String STORE_AUTHORITY = "com.banvien.fcv.mobile.provider.Store";
	public static final String S_TBL_STORE = "store";
    public static final String S_TBL_STORE_PRODUCT = "store_product";
    public static final String S_TBL_STORE_POSM = "store_posm";
    public static final String S_TBL_STORE_BRAND_LOC = "store_brand_loc";
    public static final String S_TBL_STORE_SOS_BRAND = "store_sos_brand";
    public static final String S_TBL_STORE_SOS_PACKING = "store_packing";
    public static final String S_TBL_STORE_BRAND_GROUP = "store_brand_group";
    public static final String S_TBL_ACCOUNT = "account";

    //Table for store check
    public static final String S_TBL_SAR_REG_PROD = "sar_reg_prod";
    public static final String S_TBL_SAR_POSM = "sar_posm";
    public static final String S_TBL_SAR_BRAND_LOC = "sar_brand_loc";
    public static final String S_TBL_SAR_SOS_BRAND = "sar_sos_brand";
    public static final String S_TBL_SAR_SOD = "sar_sod";
    public static final String S_TBL_SAR_PICTURE = "sar_picture";
    public static final String S_TBL_STORE_AUDIT_STATUS = "store_audit_status";
    
    public static final String S_TBL_STORE_PROMOTION = "store_promotion";
    public static final String S_TBL_ACCOUNT_PROMOTION = "account_promotion";
    public static final String S_TBL_STORE_PROMOTION_PRODUCT = "store_promotion_product";
    public static final String S_TBL_STORE_PROMOTION_PRODUCT_TOHANDHELD = "store_promotion_product_tohandheld";
    public static final String S_TBL_SAR_STORE_PROMOTION = "sar_store_promotion";
    public static final String S_TBL_UNIT = "store_unit";

    public static final String SYNC_RESULT_FILE_NAME = "fcvSyncResults";
    public static final String SETTINGS_FILE_NAME = "fcvSettings";
    
    
    public static final String SORT_ASC = "ASC";
    public static final String SORT_DESC = "DESC";

    public static final int SYNC_IN_PROGRESS = 3;
    public static final int SYNC_FAILED = 2;
    public static final int SYNC_SUCCESS = 1;
    
    public static final int DEFAULT_SYNC_INTERVAL_MINS = 5;
    
    public static final String OUTLET_BRAND_CODE_FRISO = "FRISO";
    public static final String OUTLET_BRAND_CODE_DUTCH_LADY = "DLIFT";


    /**
     * Outlet Active status
     */
    public static final int OUTLET_STATUS_ACTIVE = 1;
    public static final int OUTLET_STATUS_CLOSED = 2;
    public static final int OUTLET_STATUS_CHANGE_BUSINESS = 3;
    public static final int OUTLET_STATUS_NOT_FOUND = 4;
    public static final int OUTLET_STATUS_REFUSE_AUDIT = 5;
    public static final int OUTLET_STATUS_NO_FACING = 6;
    public static final int OUTLET_STATUS_NO_SELL = 7;
    
    /**
     * DBB
     */
    public static final String DBB_CODE = "SD_DBB"; 
}
