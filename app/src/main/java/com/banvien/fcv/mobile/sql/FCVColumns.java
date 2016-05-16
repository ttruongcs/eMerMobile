package com.banvien.fcv.mobile.sql;

import android.net.Uri;
import android.provider.BaseColumns;

public class FCVColumns implements BaseColumns{
	/*
	 * outlet
	 */
	public static final Uri OUTLET_CONTENT_URI = Uri.parse("content://" + Constants.OUTLET_AUTHORITY + "/" + Constants.OUTLET_TABLE_NAME);
	public static final Uri PROMOTION_PRODUCT_CONTENT_URI = Uri.parse("content://" + Constants.OUTLET_AUTHORITY + "/" + Constants.PROMOTION_PRODUCT_TABLE_NAME);
	public static final Uri PROMOTION_CONTENT_URI = Uri.parse("content://" + Constants.OUTLET_AUTHORITY + "/" + Constants.PROMOTION_TABLE_NAME);
	public static final Uri FULL_RANGE_SKU_CONTENT_URI = Uri.parse("content://" + Constants.OUTLET_AUTHORITY + "/" + Constants.FULL_RANGE_SKU_TABLE_NAME);
	public static final Uri IFT_DISPLAY_LOCATION_CONTENT_URI = Uri.parse("content://" + Constants.OUTLET_AUTHORITY + "/" + Constants.IFT_DISPLAY_LOCATION_TABLE_NAME);
	public static final Uri OUTLET_POSM_CONTENT_URI = Uri.parse("content://" + Constants.OUTLET_AUTHORITY + "/" + Constants.OUTLET_POSM_TABLE_NAME);
	public static final Uri REGION_PROMOTIONS_CONTENT_URI = Uri.parse("content://" + Constants.OUTLET_AUTHORITY + "/" + Constants.REGION_PROMOTIONS_TABLE_NAME);
	public static final Uri PROMOTION_PRODUCT_TO_HANDHELD_CONTENT_URI = Uri.parse("content://" + Constants.OUTLET_AUTHORITY + "/" + Constants.PROMOTION_PRODUCT_TO_HANDHELD_TABLE_NAME);
	public static final Uri OUTLET_CONTENT_TYPE_STATUS_CONTENT_URI = Uri.parse("content://" + Constants.OUTLET_AUTHORITY + "/" + Constants.OUTLET_CONTENT_TYPE_STATUS_TABLE_NAME);
	public static final Uri OUTLET_DBB_CONTENT_TYPE_STATUS_CONTENT_URI = Uri.parse("content://" + Constants.OUTLET_AUTHORITY + "/" + Constants.OUTLET_DBB_CONTENT_TYPE_STATUS_TABLE_NAME);
	public static final Uri OUTLET_BRAND_CONTENT_URI = Uri.parse("content://" + Constants.OUTLET_AUTHORITY + "/" + Constants.OUTLET_BRAND_TABLE_NAME);
	public static final Uri DISTRIBUTOR_CONTENT_URI = Uri.parse("content://" + Constants.OUTLET_AUTHORITY + "/" + Constants.DISTRIBUTOR_TABLE_NAME);
	public static final Uri OUTLET_PICTURE_CONTENT_URI = Uri.parse("content://" + Constants.OUTLET_AUTHORITY + "/" + Constants.OUTLET_PICTURE_TABLE_NAME);
	public static final Uri UNIT_CONTENT_URI = Uri.parse("content://" + Constants.OUTLET_AUTHORITY + "/" + Constants.UNIT_TABLE_NAME);
	public static final Uri OUTLET_FACING_CONTENT_URI = Uri.parse("content://" + Constants.OUTLET_AUTHORITY + "/" + Constants.OUTLET_FACING_REGISTER_TABLE_NAME);
	public static final Uri OUTLET_LOCATION_REGISTER_URI = Uri.parse("content://" + Constants.OUTLET_AUTHORITY + "/" + Constants.OUTLET_LOCATION_REGISTER_TABLE_NAME);
	public static final Uri OUTLET_LASTEST_BONUS_CONTENT_URI = Uri.parse("content://" + Constants.OUTLET_AUTHORITY + "/" + Constants.OUTLET_LASTEST_BONUS_TABLE_NAME);
	public static final Uri OUTLET_POSM_MINIVALUE__URI = Uri.parse("content://" + Constants.OUTLET_AUTHORITY + "/" + Constants.OUTLET_POSM_MINIVALUE_TABLE_NAME);
	public static final Uri OUTLET_DBB_POSM_REGISTER_URI = Uri.parse("content://" + Constants.OUTLET_AUTHORITY + "/" + Constants.OUTLET_DBB_POSM__REGISTER_TABLE_NAME);
	public static final Uri OUTLET_RIVAL_URI = Uri.parse("content://" + Constants.OUTLET_AUTHORITY + "/" + Constants.OUTLET_RIVAL);
	//table check
	public static final Uri OUTLET_BRAND_CHECK_CONTENT_URI = Uri.parse("content://" + Constants.OUTLET_AUTHORITY + "/" + Constants.OUTLET_BRAND_CHECK_TABLE_NAME);
	public static final Uri PROMOTION_CHECK_CONTENT_URI = Uri.parse("content://" + Constants.OUTLET_AUTHORITY + "/" + Constants.PROMOTION_CHECK_TABLE_NAME);
	public static final Uri FULL_RANGE_SKU_CHECK_CONTENT_URI = Uri.parse("content://" + Constants.OUTLET_AUTHORITY + "/" + Constants.FULL_RANGE_SKU_CHECK_TABLE_NAME);
	public static final Uri DBB_POSM_FULL_RANGE_SKU_CHECK_CONTENT_URI = Uri.parse("content://" + Constants.OUTLET_AUTHORITY + "/" + Constants.DBB_POSM_FULL_RANGE_SKU_CHECK_TABLE_NAME);
	public static final Uri IFT_DISPLAY_LOCATION_CHECK_CONTENT_URI = Uri.parse("content://" + Constants.OUTLET_AUTHORITY + "/" + Constants.IFT_DISPLAY_LOCATION_CHECK_TABLE_NAME);
	public static final Uri OUTLET_POSM_CHECK_CONTENT_URI = Uri.parse("content://" + Constants.OUTLET_AUTHORITY + "/" + Constants.OUTLET_POSM_CHECK_TABLE_NAME);
	public static final Uri FULLRANGE_FACING_CONTENT_URI = Uri.parse("content://" + Constants.OUTLET_AUTHORITY + "/" + Constants.FULLRANGE_FACING_TABLE_NAME);
	
	
	public static final String AUDITOR_CODE = "AuditorCode";
	public static final String DISTRIBUTOR_NAME = "DistributorName";
	public static final String DISTRIBUTOR_NAME_NO_DIACRITIC = "DistributorNameNoDiacritic";
	public static final String DISTRIBUTOR_ID = "DistributorID";
    public static final String DISTRIBUTOR_SAPCODE = "DistributorSapCode";
    public static final String DMS_CODE = "DmsCode";
    public static final String ADDRESS = "Address";
    public static final String ADDRESS_NO_DIACRITIC = "AddressNoDiacritic";
    public static final String GPS_LATITUDE = "GPSLatitude";
    public static final String GPS_LONGTITUDE = "GPSLongtitude";
    public static final String CHECKED = "Checked";
    public static final String AUDIT_DATE = "AuditDate";
    public static final String SYNCED = "Synced";
    public static final String OUTLET_DEFAULT_SORT = "DistributorName ASC";
    public static final String CREATED_DATE = "CreatedDate";
    public static final String ACTIVE_STATUS = "ActiveStatus";
    public static final String DEFAULT_SORT = _ID + " ASC";
    public static final String OUTLET_LEVEL_REGISTER_ID = "OutletLevelRegisterID";
    
    
    /**
     * PowerskuDTO
     */
    public static final String NAME = "Name";
    public static final String DISPLAYORDER = "DisplayOrder";
    public static final String OUTLET_BRAND_ID = "OutletBrandID";
    public static final String OUTLET_BRAND = "OutletBrand";
    public static final String OUTLET_BRAND_CODE = "OutletBrandCode";
    public static final String OUTLET_BRAND_GROUP = "OutletBrandGroup";
    /**
     * FullrangeskuDTO is the same PowerskuDTO
     */
    
    /**
     * PromotionDTO
     */
    public static final String CODE = "Code";
    public static final String PROMOTION_TYPE = "PromotionType";
    public static final String EFFECTIVE_DATE = "EffectiveDate";
    public static final String EXPIRE_DATE = "ExpireDate";
    public static final String BUY_QUANTITY = "BuyQuantity";
    public static final String BUY_UNIT = "BuyUnit";
    public static final String BUY_PRODUCT = "BuyProduct";
    public static final String GET_QUANTITY = "GetQuantity";
    public static final String GET_UNIT = "GetUnit";
    public static final String GET_PRODUCT = "GetProduct";
    
    /**
     * ProductDTO
     */
    public static final String SIZE = "Size";
    public static final String PRODUCT_GROUP = "ProductGroup";
    public static final String BRAND = "Brand";
    
    /**
     * promotionProductToHandheld
     */
    public static final String PROMOTION_ID = "PromotionID";
    public static final String PRODUCT_ID = "ProductID";
    
    /**
     * regionPromotions
     */
    public static final String REGION_ID = "RegionID";
    public static final String REGION = "Region";
    
    /**
     * OutletposmDTO
     */
    
    public static final String ACTIVE = "Active";
    
    /**
     * outlet status
     * 
     */
    public static final String OUTLET_ID = "OutletID";
    public static final String STATUS = "Status";
    public static final String DISTRIBUTION = "Distribution";
    public static final String DBB = "DBB";    
    public static final String PROMOTION = "Promotion";
    public static final String PICTURE = "Picture";
    public static final String LASTEST_BONUS = "LastestBonus";
    
    /**
     * outlet dbb status
     * 
     */
    
    public static final String DBB_DISTRIBUTION = "DBB_Distribution";    
    public static final String POSM = "POSM";
    public static final String HOTZONE = "Hotzone";
    public static final String DBB_LATEST_BONUS = "DbbLatestBonus";
    public static final String DBB_RIVAL = "Rival";
    
    
    /**
     * OutletBrand
     */
    
    public static final String VALUE = "Value";
    /**
     * Picture
     */
    public static final String NOTES = "Notes";
    public static final String URI = "Uri";
    /**
     * 4 check data
     */
    public static final String POWER_SKU_ID = "PowerSKUID";
    public static final String FULL_RANGE_SKU_ID = "FullRangeSKUID";
    public static final String IFT_DISPLAY_LOCATION_ID = "IFTDisplayLocationID";
    public static final String FACING = "Facing";
    public static final String KNOWN = "Known";
    public static final String CORRECT = "Correct";
    public static final String HAS = "Has";
    public static final String STATUSPOSM = "StatusPosm";
    public static final String OUTLET_POSM_ID = "OutletPosmID";
    public static final String UNIT_ID = "UnitID";
    public static final String LASTEST_BONUS_DATE = "LastestBonusDate";
    public static final String FULLRANGE_NAME = "NameFullRange";
    public static final String POSM_NAME = "NamePosm";
}
