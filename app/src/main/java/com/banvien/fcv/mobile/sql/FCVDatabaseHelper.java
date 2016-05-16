package com.banvien.fcv.mobile.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class FCVDatabaseHelper extends SQLiteOpenHelper{
	 	private static FCVDatabaseHelper instance;

    public static synchronized FCVDatabaseHelper getDBHelper(Context context)
    {
        if (instance == null)
            instance = new FCVDatabaseHelper(context);

        return instance;
    }

	FCVDatabaseHelper(Context context) {
        super(context, Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    	db.execSQL("PRAGMA encoding = \"UTF-8\"");
    	db.execSQL("PRAGMA case_sensitive_like = false");
    	createTable4Outlet(db);
    	createTable4Check(db);
    	createDatabase4Store(db);
    	createTable4StoreCheck(db);
    }
    
    private void createTable4Outlet(SQLiteDatabase db) {
    	//table outlet
        db.execSQL("CREATE TABLE " + Constants.OUTLET_TABLE_NAME + " ("
                + FCVColumns._ID + " LONG PRIMARY KEY,"
                + FCVColumns.DISTRIBUTOR_NAME + " TEXT COLLATE UNICODE,"
                + FCVColumns.DISTRIBUTOR_NAME_NO_DIACRITIC + " TEXT COLLATE UNICODE,"
                + FCVColumns.DISTRIBUTOR_ID + " LONG,"
                + FCVColumns.DISTRIBUTOR_SAPCODE + " TEXT,"
                + FCVColumns.DMS_CODE + " TEXT,"
                + FCVColumns.ADDRESS + " TEXT,"
                + FCVColumns.ADDRESS_NO_DIACRITIC + " TEXT,"
                + FCVColumns.GPS_LATITUDE + " REAL,"
                + FCVColumns.GPS_LONGTITUDE + " REAL,"
                + FCVColumns.REGION_ID + " LONG,"
                + FCVColumns.REGION + " TEXT,"
                + FCVColumns.AUDITOR_CODE + " TEXT,"
                + FCVColumns.CHECKED + " INTEGER,"
                + FCVColumns.ACTIVE_STATUS + " INTEGER,"
                + FCVColumns.AUDIT_DATE + " TEXT,"
                + FCVColumns.SYNCED + " INTEGER,"
                + FCVColumns.NOTES + " TEXT"
                + ");");
        //table product
        db.execSQL("CREATE TABLE " + Constants.PROMOTION_PRODUCT_TABLE_NAME + " ("
                + FCVColumns._ID + " LONG PRIMARY KEY,"
                + FCVColumns.NAME + " TEXT"
                + ");");
        
      //table promotion
        db.execSQL("CREATE TABLE " + Constants.PROMOTION_TABLE_NAME + " ("
                + FCVColumns._ID + " LONG PRIMARY KEY,"
                + FCVColumns.CODE + " TEXT,"
                + FCVColumns.NAME + " TEXT,"
                + FCVColumns.PROMOTION_TYPE + " TEXT,"
                + FCVColumns.EFFECTIVE_DATE + " TEXT,"
                + FCVColumns.EXPIRE_DATE + " TEXT,"
                + FCVColumns.BUY_QUANTITY + " INTEGER,"
                + FCVColumns.BUY_UNIT + " TEXT,"
                + FCVColumns.BUY_PRODUCT + " TEXT,"
                + FCVColumns.GET_QUANTITY + " LONG,"
                + FCVColumns.GET_UNIT + " TEXT,"
                + FCVColumns.GET_PRODUCT + " TEXT,"
                + FCVColumns.OUTLET_BRAND + " TEXT,"
                + FCVColumns.OUTLET_BRAND_ID + " LONG,"
                + FCVColumns.OUTLET_BRAND_CODE + " TEXT,"
                + FCVColumns.OUTLET_BRAND_GROUP + " TEXT"
                + ");");
        
      //table Fullrangesku
        db.execSQL("CREATE TABLE " + Constants.FULL_RANGE_SKU_TABLE_NAME+ " ("
                + FCVColumns._ID + " LONG PRIMARY KEY,"
                + FCVColumns.OUTLET_BRAND + " TEXT,"
                + FCVColumns.OUTLET_BRAND_ID + " LONG,"
                + FCVColumns.OUTLET_BRAND_CODE + " TEXT,"
                + FCVColumns.OUTLET_BRAND_GROUP + " TEXT,"
                + FCVColumns.NAME + " TEXT,"
                + FCVColumns.DISPLAYORDER + " INTEGER"
                + ");");
        
      //table Iftdisplaylocation
        db.execSQL("CREATE TABLE " + Constants.IFT_DISPLAY_LOCATION_TABLE_NAME + " ("
                + FCVColumns._ID + " LONG PRIMARY KEY,"
                + FCVColumns.CODE + " TEXT,"
                + FCVColumns.NAME + " TEXT,"
                + FCVColumns.DISPLAYORDER + " INTEGER,"
                + FCVColumns.OUTLET_BRAND_ID + " LONG,"
                + FCVColumns.OUTLET_BRAND_CODE + " TEXT"
                + ");");
        
      //table Outletposm
        db.execSQL("CREATE TABLE " + Constants.OUTLET_POSM_TABLE_NAME + " ("
                + FCVColumns._ID + " LONG PRIMARY KEY,"
                + FCVColumns.CODE + " TEXT,"
                + FCVColumns.NAME + " TEXT,"
                + FCVColumns.OUTLET_BRAND + " TEXT,"
                + FCVColumns.OUTLET_BRAND_ID + " LONG,"
                + FCVColumns.OUTLET_BRAND_CODE + " TEXT,"
                + FCVColumns.OUTLET_BRAND_GROUP + " TEXT"
                + ");");
        
      //table RegionPromotions
        db.execSQL("CREATE TABLE " + Constants.REGION_PROMOTIONS_TABLE_NAME + " ("
                + FCVColumns._ID + " LONG PRIMARY KEY,"
                + FCVColumns.REGION_ID + " LONG,"
                + FCVColumns.PROMOTION_ID + " LONG"
                + ");");
        
      //table promotionProductToHandheld
        db.execSQL("CREATE TABLE " + Constants.PROMOTION_PRODUCT_TO_HANDHELD_TABLE_NAME + " ("
                + FCVColumns._ID + " LONG PRIMARY KEY,"
                + FCVColumns.PROMOTION_ID + " LONG,"
                + FCVColumns.PRODUCT_ID + " LONG"
                + ");");
        
      //table outlet_brand
        db.execSQL("CREATE TABLE " + Constants.OUTLET_BRAND_TABLE_NAME + " ("
                + FCVColumns._ID + " INTEGER PRIMARY KEY,"
                + FCVColumns.OUTLET_BRAND + " TEXT,"
                + FCVColumns.OUTLET_BRAND_CODE + " TEXT,"
                + FCVColumns.OUTLET_BRAND_GROUP + " TEXT"
                + ");");
        
      //table outlet_status
        db.execSQL("CREATE TABLE " + Constants.OUTLET_CONTENT_TYPE_STATUS_TABLE_NAME + " ("
                + FCVColumns._ID + " INTEGER PRIMARY KEY,"
                + FCVColumns.OUTLET_ID + " LONG,"
                + FCVColumns.DISTRIBUTION + " INTEGER,"
                + FCVColumns.DBB + " INTEGER,"
                + FCVColumns.FACING + " INTEGER,"
                + FCVColumns.PROMOTION + " INTEGER,"
                + FCVColumns.PICTURE + " INTEGER,"
                + FCVColumns.LASTEST_BONUS + " INTEGER"
                + ");");
        
        //table dbb outlet_status
        db.execSQL("CREATE TABLE " + Constants.OUTLET_DBB_CONTENT_TYPE_STATUS_TABLE_NAME + " ("
                + FCVColumns._ID + " INTEGER PRIMARY KEY,"
                + FCVColumns.OUTLET_ID + " LONG,"
                + FCVColumns.DBB_DISTRIBUTION + " INTEGER,"
                + FCVColumns.POSM + " INTEGER,"
                + FCVColumns.DBB_LATEST_BONUS + " INTEGER,"
                + FCVColumns.DBB_RIVAL + " INTEGER,"
                + FCVColumns.HOTZONE + " INTEGER"
                + ");");        
        
      //table distributor
        db.execSQL("CREATE TABLE " + Constants.DISTRIBUTOR_TABLE_NAME + " ("
                + FCVColumns._ID + " INTEGER PRIMARY KEY,"
                + FCVColumns.DISTRIBUTOR_ID + " LONG,"
                + FCVColumns.NAME + " TEXT"
                + ");");
        //table picture
        db.execSQL("CREATE TABLE " + Constants.OUTLET_PICTURE_TABLE_NAME + " ("
                + FCVColumns._ID + " INTEGER PRIMARY KEY,"
                + FCVColumns.OUTLET_ID + " LONG,"
                + FCVColumns.URI + " TEXT,"
                + FCVColumns.NOTES + " TEXT,"
                + FCVColumns.CREATED_DATE + " LONG"
                + ");");
      //table unit
        db.execSQL("CREATE TABLE " + Constants.UNIT_TABLE_NAME + " ("
                + FCVColumns._ID + " INTEGER PRIMARY KEY,"
                + FCVColumns.NAME + " TEXT"
                + ");");
        
        //table outlet facing register
        db.execSQL("CREATE TABLE " + Constants.OUTLET_FACING_REGISTER_TABLE_NAME + " ("
                + FCVColumns._ID + " INTEGER PRIMARY KEY,"
                + FCVColumns.OUTLET_ID + "  LONG,"
                + FCVColumns.OUTLET_BRAND_ID + " LONG,"
                + FCVColumns.FACING + " INTEGER,"
                + FCVColumns.OUTLET_LEVEL_REGISTER_ID + " LONG"
                + ");");
        
        //table outlet dbbposm register
        db.execSQL("CREATE TABLE " + Constants.OUTLET_DBB_POSM__REGISTER_TABLE_NAME + " ("
                + FCVColumns._ID + " INTEGER PRIMARY KEY,"
                + FCVColumns.OUTLET_ID + "  LONG,"
                + FCVColumns.OUTLET_BRAND_ID + " LONG,"
                + FCVColumns.OUTLET_POSM_ID + " LONG"
                + ");");        
        
        //table outlet facing register
        db.execSQL("CREATE TABLE " + Constants.OUTLET_POSM_MINIVALUE_TABLE_NAME + " ("
                + FCVColumns._ID + " INTEGER PRIMARY KEY,"
                + FCVColumns.OUTLET_ID + "  LONG,"
                + FCVColumns.OUTLET_POSM_ID + " LONG,"
                + FCVColumns.NAME + " TEXT,"
                + FCVColumns.VALUE + " INTEGER"
                + ");");        
        
      //table outlet lastest bonus
        db.execSQL("CREATE TABLE " + Constants.OUTLET_LASTEST_BONUS_TABLE_NAME + " ("
                + FCVColumns._ID + " INTEGER PRIMARY KEY,"
                + FCVColumns.OUTLET_ID + "  LONG,"
                + FCVColumns.OUTLET_BRAND_ID + " LONG,"
                + FCVColumns.OUTLET_BRAND + " TEXT,"
                + FCVColumns.OUTLET_BRAND_CODE + " TEXT,"
                + FCVColumns.OUTLET_BRAND_GROUP + " TEXT,"
                + FCVColumns.LASTEST_BONUS_DATE + " TEXT"
                + ");");
    }
    private void createTable4Check(SQLiteDatabase db) {
    	db.execSQL("CREATE TABLE " + Constants.FULL_RANGE_SKU_CHECK_TABLE_NAME + " ("
                + FCVColumns._ID + " INTEGER PRIMARY KEY,"
                + FCVColumns.OUTLET_ID + " LONG,"
                + FCVColumns.FULL_RANGE_SKU_ID + " LONG,"
                + FCVColumns.HAS + " INTEGER,"
                + FCVColumns.OUTLET_BRAND_ID + " LONG"
                + ");");
    	db.execSQL("CREATE TABLE " + Constants.DBB_POSM_FULL_RANGE_SKU_CHECK_TABLE_NAME + " ("
                + FCVColumns._ID + " INTEGER PRIMARY KEY,"
                + FCVColumns.OUTLET_ID + " LONG,"
                + FCVColumns.FULL_RANGE_SKU_ID + " LONG,"
                + FCVColumns.FULLRANGE_NAME + " TEXT,"
                + FCVColumns.OUTLET_BRAND_ID + " LONG,"
                + FCVColumns.HAS + " INTEGER,"
                + FCVColumns.OUTLET_POSM_ID + " LONG,"
                + FCVColumns.POSM_NAME + " TEXT"
                + ");");    	
    	db.execSQL("CREATE TABLE " + Constants.IFT_DISPLAY_LOCATION_CHECK_TABLE_NAME + " ("
                + FCVColumns._ID + " INTEGER PRIMARY KEY,"
                + FCVColumns.OUTLET_ID + " LONG,"
                + FCVColumns.IFT_DISPLAY_LOCATION_ID + " LONG,"
                + FCVColumns.OUTLET_BRAND_ID + " LONG,"
                + FCVColumns.HAS + " INTEGER"
                + ");");   	
    	db.execSQL("CREATE TABLE " + Constants.OUTLET_POSM_CHECK_TABLE_NAME + " ("
                + FCVColumns._ID + " INTEGER PRIMARY KEY,"
                + FCVColumns.OUTLET_ID + " LONG,"
                + FCVColumns.OUTLET_POSM_ID + " LONG,"
                + FCVColumns.HAS + " INTEGER,"
               + FCVColumns.STATUSPOSM + " TEXT"
                + ");");
    	db.execSQL("CREATE TABLE " + Constants.PROMOTION_CHECK_TABLE_NAME + " ("
                + FCVColumns._ID + " INTEGER PRIMARY KEY,"
                + FCVColumns.OUTLET_ID + " LONG,"
                + FCVColumns.PROMOTION_ID + " LONG,"
                + FCVColumns.GET_QUANTITY + " INTEGER,"
                + FCVColumns.UNIT_ID + " LONG,"
                + FCVColumns.PRODUCT_ID + " LONG,"
                + FCVColumns.KNOWN + " INTEGER,"
                + FCVColumns.CORRECT + " INTEGER"
                + ");");
    	db.execSQL("CREATE TABLE " + Constants.OUTLET_BRAND_CHECK_TABLE_NAME + " ("
                + FCVColumns._ID + " INTEGER PRIMARY KEY,"
                + FCVColumns.OUTLET_ID + " LONG,"
                + FCVColumns.OUTLET_BRAND_ID + " LONG,"
                + FCVColumns.FACING + " INTEGER"
                + ");");
    	
    	db.execSQL("CREATE TABLE " + Constants.FULLRANGE_FACING_TABLE_NAME + " ("
                + FCVColumns._ID + " INTEGER PRIMARY KEY,"
                + FCVColumns.OUTLET_ID + " LONG,"
                + FCVColumns.OUTLET_BRAND_ID + " LONG,"
                + FCVColumns.FACING + " INTEGER"
                + ");");
    	
    	db.execSQL("CREATE TABLE " + Constants.OUTLET_LOCATION_REGISTER_TABLE_NAME + " ("
                + FCVColumns._ID + " INTEGER PRIMARY KEY,"
                + FCVColumns.OUTLET_ID + " LONG,"
                + FCVColumns.OUTLET_BRAND_ID + " LONG,"
                + FCVColumns.IFT_DISPLAY_LOCATION_ID + " LONG"
                + ");");
    	
    	db.execSQL("CREATE TABLE " + Constants.OUTLET_RIVAL + " ("
                + FCVColumns._ID + " INTEGER PRIMARY KEY,"
                + FCVColumns.OUTLET_ID + " LONG,"
                + FCVColumns.HAS + " INTEGER"
                + ");");    	
    	
    }
    
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(Constants.TAG, "Upgrading database from version " + oldVersion + " to "
                + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + Constants.OUTLET_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Constants.PROMOTION_PRODUCT_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Constants.PROMOTION_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Constants.FULL_RANGE_SKU_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Constants.IFT_DISPLAY_LOCATION_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Constants.REGION_PROMOTIONS_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Constants.PROMOTION_PRODUCT_TO_HANDHELD_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Constants.OUTLET_CONTENT_TYPE_STATUS_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Constants.OUTLET_BRAND_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Constants.OUTLET_POSM_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Constants.DISTRIBUTOR_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Constants.OUTLET_PICTURE_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Constants.UNIT_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Constants.OUTLET_FACING_REGISTER_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Constants.OUTLET_LOCATION_REGISTER_TABLE_NAME);
        //check tables
        db.execSQL("DROP TABLE IF EXISTS " + Constants.FULL_RANGE_SKU_CHECK_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Constants.DBB_POSM_FULL_RANGE_SKU_CHECK_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Constants.IFT_DISPLAY_LOCATION_CHECK_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Constants.OUTLET_POSM_CHECK_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Constants.PROMOTION_CHECK_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Constants.OUTLET_BRAND_CHECK_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Constants.FULLRANGE_FACING_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Constants.OUTLET_LASTEST_BONUS_TABLE_NAME);
        
        //DBB
        db.execSQL("DROP TABLE IF EXISTS " + Constants.DBB_POSM_FULL_RANGE_SKU_CHECK_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Constants.OUTLET_RIVAL);
        db.execSQL("DROP TABLE IF EXISTS " + Constants.OUTLET_DBB_POSM__REGISTER_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Constants.OUTLET_DBB_CONTENT_TYPE_STATUS_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Constants.OUTLET_POSM_MINIVALUE_TABLE_NAME);
        
        dropStoreDatabase(db);
        
        onCreate(db);
    }
    
    private void createDatabase4Store(SQLiteDatabase db) {
    	 db.execSQL("CREATE TABLE " + Constants.S_TBL_STORE + " ("
                 + StoreColumns._ID + " LONG PRIMARY KEY,"
                 + StoreColumns.S_STORE_COL_CODE + " TEXT,"
                 + StoreColumns.S_STORE_COL_NAME + " TEXT,"
                 + StoreColumns.S_STORE_COL_NAME_NO_DIACRITIC + " TEXT,"
                 + StoreColumns.S_STORE_COL_ADDRESS + " TEXT,"
                 + StoreColumns.S_STORE_COL_ADDRESS_NO_DIACRITIC + " TEXT,"
                 + StoreColumns.S_STORE_COL_ACCOUNT + " TEXT,"
                 + StoreColumns.S_STORE_COL_ACCOUNT_ID + " LONG,"
                 + StoreColumns.S_STORE_COL_LATITUDE + " REAL,"
                 + StoreColumns.S_STORE_COL_LONGITUDE + " REAL,"
                 + StoreColumns.S_STORE_COL_REGION + " LONG,"
                 + StoreColumns.AUDITOR_CODE + " TEXT,"
                 + StoreColumns.CHECKED + " INTEGER,"
                 + StoreColumns.AUDIT_DATE + " TEXT,"
                 + StoreColumns.SYNCED + " INTEGER"
                 + ");");
         //table store product
         db.execSQL("CREATE TABLE " + Constants.S_TBL_STORE_PRODUCT + " ("
                 + StoreColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                 + StoreColumns.S_STORE_PROD_COL_PROD_ID + " LONG,"
                 + StoreColumns.S_STORE_PROD_COL_NAME + " TEXT,"
                 + StoreColumns.S_STORE_PROD_COL_BRAND + " TEXT,"
                 + StoreColumns.S_STORE_PROD_COL_GROUP + " TEXT,"
                 + StoreColumns.S_STORE_PROD_COL_SIZE + " TEXT,"
                 + StoreColumns.S_STORE_PROD_COL_STORE_ID + " LONG"
                 + ");");
         
       //table brand location
         db.execSQL("CREATE TABLE " + Constants.S_TBL_STORE_BRAND_LOC + " ("
                 + StoreColumns._ID + " LONG PRIMARY KEY,"
                 + StoreColumns.S_STORE_BRAND_LOC_COL_BRAND_GROUP + " TEXT,"
                 + StoreColumns.S_STORE_BRAND_LOC_COL_NAME + " TEXT,"
                 + StoreColumns.S_STORE_BRAND_LOC_COL_PRODUCTS + " TEXT,"
                 + StoreColumns.S_STORE_BRAND_LOC_COL_RIGHT_LOC + " TEXT"
                 + ");");
         
       //table store posm
         db.execSQL("CREATE TABLE " + Constants.S_TBL_STORE_POSM + " ("
                 + StoreColumns._ID + " LONG PRIMARY KEY,"
                 + StoreColumns.S_STORE_POSM_COL_BRAND_GROUP + " TEXT,"
                 + StoreColumns.S_STORE_POSM_COL_CODE + " LONG,"
                 + StoreColumns.S_STORE_POSM_COL_NAME + " TEXT"
                 + ");");
         
       //table sos brand
         db.execSQL("CREATE TABLE " + Constants.S_TBL_STORE_SOS_BRAND+ " ("
                 + StoreColumns._ID + " LONG PRIMARY KEY,"
                 + StoreColumns.S_STORE_SOS_BRAND_COL_BRAND_GROUP + " TEXT,"
                 + StoreColumns.S_STORE_SOS_BRAND_COL_NAME + " TEXT,"
                 + StoreColumns.S_STORE_SOS_BRAND_COL_DISPLAY_ORDER + " INTEGER"
                 + ");");
         
       //table packing group
         db.execSQL("CREATE TABLE " + Constants.S_TBL_STORE_SOS_PACKING + " ("
                 + StoreColumns._ID + " LONG PRIMARY KEY,"
                 + StoreColumns.S_STORE_PACKING_COL_NAME + " TEXT,"
                 + StoreColumns.S_STORE_PACKING_COL_BRAND_ID + " LONG,"
                 + StoreColumns.S_STORE_PACKING_COL_BRAND_GROUP + " TEXT,"
                 + StoreColumns.S_STORE_PACKING_COL_BRAND + " TEXT,"
                 + StoreColumns.S_STORE_PACKING_COL_DISPLAY_ORDER + " INTEGER"
                 + ");");
         //Table brand group
         db.execSQL("CREATE TABLE " + Constants.S_TBL_STORE_BRAND_GROUP + " ("
                 + StoreColumns._ID + " LONG PRIMARY KEY,"
                 + StoreColumns.S_STORE_BRAND_GROUP_COL_NAME + " TEXT"
                 + ");");
         
         //Table account
         db.execSQL("CREATE TABLE " + Constants.S_TBL_ACCOUNT + " ("
                 + StoreColumns._ID + " INTEGER PRIMARY KEY,"
                 + StoreColumns.S_ACCOUNT_COL_ID + " LONG,"
                 + StoreColumns.S_ACCOUNT_COL_NAME + " TEXT"
                 + ");");
         //table store promotion
         db.execSQL("CREATE TABLE " + Constants.S_TBL_STORE_PROMOTION + " ("
                 + StoreColumns._ID + " LONG PRIMARY KEY,"
                 + StoreColumns.S_STORE_PROMOTION_CODE + " TEXT,"
                 + StoreColumns.S_STORE_PROMOTION_NAME + " TEXT,"
                 + StoreColumns.S_STORE_PROMOTION_PROMOTION_TYPE + " TEXT,"
                 + StoreColumns.S_STORE_PROMOTION_EFFECTIVE_DATE + " TEXT,"
                 + StoreColumns.S_STORE_PROMOTION_EXPIRE_DATE + " TEXT,"
                 + StoreColumns.S_STORE_PROMOTION_BUY_QUANTITY + " INTEGER,"
                 + StoreColumns.S_STORE_PROMOTION_BUY_UNIT + " TEXT,"
                 + StoreColumns.S_STORE_PROMOTION_BUY_PRODUCT + " TEXT,"
                 + StoreColumns.S_STORE_PROMOTION_GET_QUANTITY + " LONG,"
                 + StoreColumns.S_STORE_PROMOTION_GET_UNIT + " TEXT,"
                 + StoreColumns.S_STORE_PROMOTION_GET_PRODUCT + " TEXT,"
                 + StoreColumns.S_STORE_PROMOTION_SOS_BRAND + " TEXT,"
                 + StoreColumns.S_STORE_PROMOTION_SOS_BRAND_ID + " LONG"
                 + ");");
       //table store product
         db.execSQL("CREATE TABLE " + Constants.S_TBL_STORE_PROMOTION_PRODUCT + " ("
                 + StoreColumns._ID + " LONG PRIMARY KEY,"
                 + StoreColumns.S_STORE_PROMOTION_PRODUCT_NAME + " TEXT"
                 + ");");
       //table AccountPromotions
         db.execSQL("CREATE TABLE " + Constants.S_TBL_ACCOUNT_PROMOTION + " ("
                 + StoreColumns._ID + " LONG PRIMARY KEY,"
                 + StoreColumns.S_ACCOUNT_COL_ID + " LONG,"
                 + StoreColumns.S_STORE_PROMOTION_ID + " LONG"
                 + ");");
         
       //table promotionProductToHandheld
         db.execSQL("CREATE TABLE " + Constants.S_TBL_STORE_PROMOTION_PRODUCT_TOHANDHELD + " ("
                 + StoreColumns._ID + " LONG PRIMARY KEY,"
                 + StoreColumns.S_STORE_PROMOTION_ID + " LONG,"
                 + StoreColumns.S_STORE_PROMOTION_PRODUCT_ID + " LONG"
                 + ");");
         db.execSQL("CREATE TABLE " + Constants.S_TBL_UNIT + " ("
                 + StoreColumns._ID + " INTEGER PRIMARY KEY,"
                 + StoreColumns.S_UNIT_NAME + " TEXT"
                 + ");");
    }
    private void createTable4StoreCheck(SQLiteDatabase db) {
    	db.execSQL("CREATE TABLE " + Constants.S_TBL_SAR_REG_PROD + " ("
                + StoreColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + StoreColumns.S_SAR_REG_PROD_STORE_ID + " LONG,"
                + StoreColumns.S_SAR_REG_PROD_PROD_ID + " LONG,"
                + StoreColumns.S_SAR_REG_PROD_HAS + " INTEGER"
                + ");");
    	db.execSQL("CREATE TABLE " + Constants.S_TBL_SAR_BRAND_LOC + " ("
                + StoreColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + StoreColumns.S_SAR_BRAND_LOC_STORE_ID + " LONG,"
                + StoreColumns.S_SAR_BRAND_LOC_BRAND_ID + " LONG,"
                + StoreColumns.S_SAR_BRAND_LOC_HAS + " INTEGER,"
                + StoreColumns.S_SAR_BRAND_LOC_RIGHT + " INTEGER"
                + ");");
    	db.execSQL("CREATE TABLE " + Constants.S_TBL_SAR_POSM + " ("
                + StoreColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + StoreColumns.S_SAR_POSM_STORE_ID + " LONG,"
                + StoreColumns.S_SAR_POSM_POSM_ID + " LONG,"
                + StoreColumns.S_SAR_POSM_HAS + " INTEGER"
                + ");");
    	db.execSQL("CREATE TABLE " + Constants.S_TBL_SAR_SOS_BRAND + " ("
                + StoreColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + StoreColumns.S_SAR_SOS_STORE_ID + " LONG,"
                + StoreColumns.S_SAR_SOS_PACKING_ID + " LONG,"
                + StoreColumns.S_SAR_SOS_QUANTITY + " INTEGER"
                + ");");
    	db.execSQL("CREATE TABLE " + Constants.S_TBL_SAR_SOD + " ("
                + StoreColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + StoreColumns.S_SAR_SOD_STORE_ID + " LONG,"
                + StoreColumns.S_SAR_SOD_BRAND + " LONG,"
                + StoreColumns.S_SAR_SOD_FCV_SS + " INTEGER,"
                + StoreColumns.S_SAR_SOD_FCV_GE + " INTEGER,"
                + StoreColumns.S_SAR_SOD_FCV_OTHER + " INTEGER,"
                + StoreColumns.S_SAR_SOD_STORE_SS + " INTEGER,"
                + StoreColumns.S_SAR_SOD_STORE_GE + " INTEGER,"
                + StoreColumns.S_SAR_SOD_STORE_OTHER + " INTEGER"
                + ");");
        db.execSQL("CREATE TABLE " + Constants.S_TBL_SAR_PICTURE + " ("
                + StoreColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + StoreColumns.S_SAR_PICTURE_COL_STORE_ID + " LONG,"
                + StoreColumns.S_SAR_PICTURE_COL_URI + " TEXT,"
                + StoreColumns.S_SAR_PICTURE_COL_NOTES + " TEXT,"
                + StoreColumns.S_SAR_PICTURE_COL_CREATED_DATE + " LONG"
                + ");");
        db.execSQL("CREATE TABLE " + Constants.S_TBL_STORE_AUDIT_STATUS + " ("
                + StoreColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + StoreColumns.S_STORE_AUDIT_STATUS_COL_STORE_ID + " LONG,"
                + StoreColumns.S_STORE_AUDIT_STATUS_COL_3S + " INTEGER,"
                + StoreColumns.S_STORE_AUDIT_STATUS_COL_VISIBILITY + " INTEGER,"
                + StoreColumns.S_STORE_AUDIT_STATUS_COL_PICTURE + " INTEGER,"
                + StoreColumns.S_STORE_AUDIT_STATUS_COL_PROMOTION + " INTEGER"
                + ");");
        db.execSQL("CREATE TABLE " + Constants.S_TBL_SAR_STORE_PROMOTION + " ("
                + StoreColumns._ID + " INTEGER PRIMARY KEY,"
                + StoreColumns.S_SAR_STORE_PROMOTION_STORE_ID + " LONG,"
                + StoreColumns.S_SAR_STORE_PROMOTION_PROMOTION_ID + " LONG,"
                + StoreColumns.S_SAR_STORE_PROMOTION_GET_QUANTITY + " INTEGER,"
                + StoreColumns.S_SAR_STORE_PROMOTION_UNIT_ID + " LONG,"
                + StoreColumns.S_SAR_STORE_PROMOTION_PRODUCT_ID + " LONG,"
                + StoreColumns.S_SAR_STORE_PROMOTION_KNOWN + " INTEGER,"
                + StoreColumns.S_SAR_STORE_PROMOTION_CORRECT + " INTEGER"
                + ");");
    }
    private void dropStoreDatabase(SQLiteDatabase db) {
    	db.execSQL("DROP TABLE IF EXISTS " + Constants.S_TBL_STORE);
        db.execSQL("DROP TABLE IF EXISTS " + Constants.S_TBL_STORE_BRAND_LOC);
        db.execSQL("DROP TABLE IF EXISTS " + Constants.S_TBL_STORE_POSM);
        db.execSQL("DROP TABLE IF EXISTS " + Constants.S_TBL_STORE_PRODUCT);
        db.execSQL("DROP TABLE IF EXISTS " + Constants.S_TBL_STORE_SOS_BRAND);
        db.execSQL("DROP TABLE IF EXISTS " + Constants.S_TBL_STORE_SOS_PACKING);
        db.execSQL("DROP TABLE IF EXISTS " + Constants.S_TBL_STORE_BRAND_GROUP);

        db.execSQL("DROP TABLE IF EXISTS " + Constants.S_TBL_SAR_REG_PROD);
        db.execSQL("DROP TABLE IF EXISTS " + Constants.S_TBL_SAR_BRAND_LOC);
        db.execSQL("DROP TABLE IF EXISTS " + Constants.S_TBL_SAR_POSM);
        db.execSQL("DROP TABLE IF EXISTS " + Constants.S_TBL_SAR_SOD);
        db.execSQL("DROP TABLE IF EXISTS " + Constants.S_TBL_SAR_SOS_BRAND);
        db.execSQL("DROP TABLE IF EXISTS " + Constants.S_TBL_SAR_PICTURE);
        db.execSQL("DROP TABLE IF EXISTS " + Constants.S_TBL_STORE_AUDIT_STATUS);
        
        db.execSQL("DROP TABLE IF EXISTS " + Constants.S_TBL_STORE_PROMOTION);
        db.execSQL("DROP TABLE IF EXISTS " + Constants.S_TBL_STORE_PROMOTION_PRODUCT);
        db.execSQL("DROP TABLE IF EXISTS " + Constants.S_TBL_STORE_PROMOTION_PRODUCT_TOHANDHELD);
        db.execSQL("DROP TABLE IF EXISTS " + Constants.S_TBL_UNIT);
        db.execSQL("DROP TABLE IF EXISTS " + Constants.S_TBL_SAR_STORE_PROMOTION);
        db.execSQL("DROP TABLE IF EXISTS " + Constants.S_TBL_ACCOUNT_PROMOTION);
    }
}
