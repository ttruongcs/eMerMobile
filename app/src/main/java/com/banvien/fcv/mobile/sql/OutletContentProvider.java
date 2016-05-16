package com.banvien.fcv.mobile.sql;


import java.util.HashMap;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

/**
 * Provides access to a database of outlet. 
 */
public class OutletContentProvider extends ContentProvider {

    private static HashMap<String, String> sOuletProjectionMap;
    private static HashMap<String, String> sProductProjectionMap;
    private static HashMap<String, String> sPromotionProjectionMap;
    private static HashMap<String, String> sFullRangeSKUProjectionMap;
    private static HashMap<String, String> sIFTDisplayLocationProjectionMap;
    private static HashMap<String, String> sOutletPOSMProjectionMap;
    private static HashMap<String, String> sRegionPromotionsProjectionMap;
    private static HashMap<String, String> sPromotionProductToHandHeldProjectionMap;
    private static HashMap<String, String> sOutletStatusProjectionMap;
    private static HashMap<String, String> sOutletDbbStatusProjectionMap;
    private static HashMap<String, String> sOutletBrandProjectionMap;
    private static HashMap<String, String> sDistributorProjectionMap;
    private static HashMap<String, String> sPictureProjectionMap;
    private static HashMap<String, String> sUnitProjectionMap;
    private static HashMap<String, String> sOutletFacingProjectionMap;
    private static HashMap<String, String> sOutletLocationRegisterProjectionMap;
    private static HashMap<String, String> sOutletLastestBonusProjectionMap;
    //table check
    private static HashMap<String, String> sPowerSKUCheckProjectionMap;
    private static HashMap<String, String> sFullRangeSKUCheckProjectionMap;
    private static HashMap<String, String> sDBBPosmFullRangeSKUCheckProjectionMap;
    private static HashMap<String, String> sIFTDisplayLocationCheckProjectionMap;
    private static HashMap<String, String> sOutletPOSMCheckProjectionMap;
    private static HashMap<String, String> sPromotionCheckProjectionMap;
    private static HashMap<String, String> sOutletBrandCheckProjectionMap;
    private static HashMap<String, String> sFullrangeFacingProjectionMap;
    private static HashMap<String, String> sPosmMiniValueProjectionMap;
    private static HashMap<String, String> sDBBPosmRegisteredProjectionMap;
    private static HashMap<String, String> sOutletRivalProjectionMap;
    
    
    private static final int OUTLET = 1;
    private static final int OUTLET_ID = 2;
    
    private static final int PRODUCT = 3;
    private static final int PRODUCT_ID = 4;
    
    private static final int PROMOTION = 5;
    private static final int PROMOTION_ID = 6;
    
    private static final int OUTLET_FACING = 7;
    private static final int OUTLET_FACING_ID = 8;
    
    private static final int FULL_RANGE_SKU = 9;
    private static final int FULL_RANGE_SKU_ID = 10;
    
    private static final int IFT_DISPLAY_LOCATION = 11;
    private static final int IFT_DISPLAY_LOCATION_ID = 12;
    
    private static final int OUTLET_POSM = 13;
    private static final int OUTLET_POSM_ID = 14;
    
    private static final int REGION_PROMOTIONS = 15;
    
    private static final int PROMOTION_PRODUCT_TO_HANDHELD = 16;
    
    private static final int OUTLET_CONTENT_TYPE_STATUS = 17;
    
    private static final int OUTLET_BRAND = 18;
    private static final int OUTLET_BRAND_ID = 19;
    
    private static final int DISTRIBUTOR = 20;
    
    private static final int OUTLET_LOCATION_REGISTER= 21;
    private static final int OUTLET_LOCATION_REGISTER_ID = 22;
    
    private static final int FULL_RANGE_SKU_CHECK = 23;
    private static final int FULL_RANGE_SKU_CHECK_ID = 24;
    
    private static final int IFT_DISPLAY_LOCATION_CHECK = 25;
    private static final int IFT_DISPLAY_LOCATION_CHECK_ID = 26;
    
    private static final int OUTLET_POSM_CHECK = 27;
    private static final int OUTLET_POSM_CHECK_ID = 28;
    
    private static final int PROMOTION_CHECK = 29;
    private static final int PROMOTION_CHECK_ID = 30;
    
    private static final int OUTLET_BRAND_CHECK = 31;
    private static final int OUTLET_BRAND_CHECK_ID = 32;
    
    private static final int PICTURE = 33;
    private static final int PICTURE_ID = 34;
    
    private static final int UNIT = 35;
    private static final int UNIT_ID = 36;
    
    private static final int FULLRANGE_FACING = 37;
    private static final int FULLRANGE_FACING_ID = 38;
    
    private static final int LASTEST_BONUS = 39;
    private static final int LASTEST_BONUS_ID = 40;
    
    private static final int DBB_POSM_FULL_RANGE_SKU_CHECK = 41;
    private static final int DBB_POSM_FULL_RANGE_SKU_CHECK_ID = 42;    

    private static final int OUTLET_DBB_CONTENT_TYPE_STATUS = 43;
    
    private static final int OUTLET_POSM_MINIVALUE = 44;
    private static final int OUTLET_POSM_MINIVALUE_ID = 45;
    
    private static final int OUTLET_DBB_POSM_REGISTER= 46;
    private static final int OUTLET_DBB_POSM_REGISTER_ID = 47; 
    
    private static final int OUTLET_RIVAL= 48;
    private static final int OUTLET_RIVAL_ID = 49;     
    
    private static final UriMatcher sUriMatcher;

    private FCVDatabaseHelper mOpenHelper;

    @Override
    public boolean onCreate() {
        mOpenHelper = FCVDatabaseHelper.getDBHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
            String sortOrder) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        
        switch (sUriMatcher.match(uri)) {
        case OUTLET:
        	qb.setTables(Constants.OUTLET_TABLE_NAME);
            qb.setProjectionMap(sOuletProjectionMap);
            break;

        case OUTLET_ID:
        	qb.setTables(Constants.OUTLET_TABLE_NAME);
            qb.setProjectionMap(sOuletProjectionMap);
            qb.appendWhere(FCVColumns._ID + "=" + uri.getPathSegments().get(1));
            break;
            
        case PRODUCT:
        	qb.setTables(Constants.PROMOTION_PRODUCT_TABLE_NAME);
            qb.setProjectionMap(sProductProjectionMap);
            break;

        case PRODUCT_ID:
        	qb.setTables(Constants.PROMOTION_PRODUCT_TABLE_NAME);
            qb.setProjectionMap(sProductProjectionMap);
            qb.appendWhere(FCVColumns._ID + "=" + uri.getPathSegments().get(1));
            break;
            
        case PROMOTION:
        	qb.setTables(Constants.PROMOTION_TABLE_NAME);
            qb.setProjectionMap(sPromotionProjectionMap);
            break;
            
        case PROMOTION_ID:
        	qb.setTables(Constants.PROMOTION_TABLE_NAME);
            qb.setProjectionMap(sPromotionProjectionMap);
            qb.appendWhere(FCVColumns._ID + "=" + uri.getPathSegments().get(1));
            break;
            
        case FULL_RANGE_SKU:
        	qb.setTables(Constants.FULL_RANGE_SKU_TABLE_NAME);
            qb.setProjectionMap(sFullRangeSKUProjectionMap);
            break;
            
        case FULL_RANGE_SKU_ID:
        	qb.setTables(Constants.FULL_RANGE_SKU_TABLE_NAME);
            qb.setProjectionMap(sFullRangeSKUProjectionMap);
            qb.appendWhere(FCVColumns._ID + "=" + uri.getPathSegments().get(1));
            break;
            
        case IFT_DISPLAY_LOCATION:
        	qb.setTables(Constants.IFT_DISPLAY_LOCATION_TABLE_NAME);
            qb.setProjectionMap(sIFTDisplayLocationProjectionMap);
            break;
            
        case IFT_DISPLAY_LOCATION_ID:
        	qb.setTables(Constants.IFT_DISPLAY_LOCATION_TABLE_NAME);
            qb.setProjectionMap(sIFTDisplayLocationProjectionMap);
            qb.appendWhere(FCVColumns._ID + "=" + uri.getPathSegments().get(1));
            break;
            
        case OUTLET_POSM:
        	qb.setTables(Constants.OUTLET_POSM_TABLE_NAME);
            qb.setProjectionMap(sOutletPOSMProjectionMap);
            break;
            
        case OUTLET_POSM_ID:
        	qb.setTables(Constants.OUTLET_POSM_TABLE_NAME);
            qb.setProjectionMap(sOutletPOSMProjectionMap);
            qb.appendWhere(FCVColumns._ID + "=" + uri.getPathSegments().get(1));
            break;
            
        case REGION_PROMOTIONS:
        	qb.setTables(Constants.REGION_PROMOTIONS_TABLE_NAME);
            qb.setProjectionMap(sRegionPromotionsProjectionMap);
            break;
            
        case PROMOTION_PRODUCT_TO_HANDHELD:
        	qb.setTables(Constants.PROMOTION_PRODUCT_TO_HANDHELD_TABLE_NAME);
            qb.setProjectionMap(sPromotionProductToHandHeldProjectionMap);
            break;
            
        case OUTLET_CONTENT_TYPE_STATUS:
        	qb.setTables(Constants.OUTLET_CONTENT_TYPE_STATUS_TABLE_NAME);
            qb.setProjectionMap(sOutletStatusProjectionMap);
            break;
            
        case OUTLET_DBB_CONTENT_TYPE_STATUS:
        	qb.setTables(Constants.OUTLET_DBB_CONTENT_TYPE_STATUS_TABLE_NAME);
            qb.setProjectionMap(sOutletDbbStatusProjectionMap);
            break;            
            
        case OUTLET_BRAND:
        	qb.setTables(Constants.OUTLET_BRAND_TABLE_NAME);
            qb.setProjectionMap(sOutletBrandProjectionMap);
            break;
            
        case OUTLET_BRAND_ID:
        	qb.setTables(Constants.OUTLET_BRAND_TABLE_NAME);
            qb.setProjectionMap(sOutletBrandProjectionMap);
            qb.appendWhere(FCVColumns._ID + "=" + uri.getPathSegments().get(1));
            break;
            
        case DISTRIBUTOR:
        	qb.setTables(Constants.DISTRIBUTOR_TABLE_NAME);
            qb.setProjectionMap(sDistributorProjectionMap);
            break;
            
        case UNIT:
        	qb.setTables(Constants.UNIT_TABLE_NAME);
            qb.setProjectionMap(sUnitProjectionMap);
            break;
            
        case OUTLET_BRAND_CHECK:
        	qb.setTables(Constants.OUTLET_BRAND_CHECK_TABLE_NAME);
            qb.setProjectionMap(sOutletBrandCheckProjectionMap);
            break;
            
        case OUTLET_BRAND_CHECK_ID:
        	qb.setTables(Constants.OUTLET_BRAND_CHECK_TABLE_NAME);
            qb.setProjectionMap(sOutletBrandCheckProjectionMap);
            qb.appendWhere(FCVColumns._ID + "=" + uri.getPathSegments().get(1));
            break;
            
        case FULL_RANGE_SKU_CHECK:
        	qb.setTables(Constants.FULL_RANGE_SKU_CHECK_TABLE_NAME);
            qb.setProjectionMap(sFullRangeSKUCheckProjectionMap);
            break;
            
        case FULL_RANGE_SKU_CHECK_ID:
        	qb.setTables(Constants.FULL_RANGE_SKU_CHECK_TABLE_NAME);
            qb.setProjectionMap(sFullRangeSKUCheckProjectionMap);
            qb.appendWhere(FCVColumns._ID + "=" + uri.getPathSegments().get(1));
            break;
            
        case DBB_POSM_FULL_RANGE_SKU_CHECK:
        	qb.setTables(Constants.DBB_POSM_FULL_RANGE_SKU_CHECK_TABLE_NAME);
            qb.setProjectionMap(sDBBPosmFullRangeSKUCheckProjectionMap);
            break;
            
        case DBB_POSM_FULL_RANGE_SKU_CHECK_ID:
        	qb.setTables(Constants.DBB_POSM_FULL_RANGE_SKU_CHECK_TABLE_NAME);
            qb.setProjectionMap(sDBBPosmFullRangeSKUCheckProjectionMap);
            qb.appendWhere(FCVColumns._ID + "=" + uri.getPathSegments().get(1));
            break;            
            
        case IFT_DISPLAY_LOCATION_CHECK:
        	qb.setTables(Constants.IFT_DISPLAY_LOCATION_CHECK_TABLE_NAME);
            qb.setProjectionMap(sIFTDisplayLocationCheckProjectionMap);
            break;
            
        case IFT_DISPLAY_LOCATION_CHECK_ID:
        	qb.setTables(Constants.IFT_DISPLAY_LOCATION_CHECK_TABLE_NAME);
            qb.setProjectionMap(sIFTDisplayLocationCheckProjectionMap);
            qb.appendWhere(FCVColumns._ID + "=" + uri.getPathSegments().get(1));
            break;
            
        case OUTLET_POSM_CHECK:
        	qb.setTables(Constants.OUTLET_POSM_CHECK_TABLE_NAME);
            qb.setProjectionMap(sOutletPOSMCheckProjectionMap);
            break;
            
        case OUTLET_POSM_CHECK_ID:
        	qb.setTables(Constants.OUTLET_POSM_CHECK_TABLE_NAME);
            qb.setProjectionMap(sOutletPOSMCheckProjectionMap);
            qb.appendWhere(FCVColumns._ID + "=" + uri.getPathSegments().get(1));
            break;
            
        case PROMOTION_CHECK:
        	qb.setTables(Constants.PROMOTION_CHECK_TABLE_NAME);
            qb.setProjectionMap(sPromotionCheckProjectionMap);
            break;
            
        case PROMOTION_CHECK_ID:
        	qb.setTables(Constants.PROMOTION_CHECK_TABLE_NAME);
            qb.setProjectionMap(sPromotionCheckProjectionMap);
            qb.appendWhere(FCVColumns._ID + "=" + uri.getPathSegments().get(1));
            break;
            
        case PICTURE:
        	qb.setTables(Constants.OUTLET_PICTURE_TABLE_NAME);
            qb.setProjectionMap(sPictureProjectionMap);
            break;
            
        case PICTURE_ID:
        	qb.setTables(Constants.OUTLET_PICTURE_TABLE_NAME);
            qb.setProjectionMap(sPictureProjectionMap);
            qb.appendWhere(FCVColumns._ID + "=" + uri.getPathSegments().get(1));
            break;
            
        case FULLRANGE_FACING:
        	qb.setTables(Constants.FULLRANGE_FACING_TABLE_NAME);
            qb.setProjectionMap(sFullrangeFacingProjectionMap);
            break;
            
        case FULLRANGE_FACING_ID:
        	qb.setTables(Constants.FULLRANGE_FACING_TABLE_NAME);
            qb.setProjectionMap(sFullrangeFacingProjectionMap);
            qb.appendWhere(FCVColumns._ID + "=" + uri.getPathSegments().get(1));
            break;
            
        case OUTLET_FACING:
        	qb.setTables(Constants.OUTLET_FACING_REGISTER_TABLE_NAME);
            qb.setProjectionMap(sOutletFacingProjectionMap);
            break;                       
            
        case OUTLET_FACING_ID:
        	qb.setTables(Constants.OUTLET_FACING_REGISTER_TABLE_NAME);
            qb.setProjectionMap(sOutletFacingProjectionMap);
            qb.appendWhere(FCVColumns._ID + "=" + uri.getPathSegments().get(1));
            break;
            
        case OUTLET_POSM_MINIVALUE:
        	qb.setTables(Constants.OUTLET_POSM_MINIVALUE_TABLE_NAME);
            qb.setProjectionMap(sPosmMiniValueProjectionMap);
            break; 
            
        case OUTLET_POSM_MINIVALUE_ID:
        	qb.setTables(Constants.OUTLET_POSM_MINIVALUE_TABLE_NAME);
            qb.setProjectionMap(sPosmMiniValueProjectionMap);
            qb.appendWhere(FCVColumns._ID + "=" + uri.getPathSegments().get(1));
            break;            
            
        case OUTLET_LOCATION_REGISTER:
        	qb.setTables(Constants.OUTLET_LOCATION_REGISTER_TABLE_NAME);
            qb.setProjectionMap(sOutletLocationRegisterProjectionMap);
            break;
            
        case OUTLET_LOCATION_REGISTER_ID:
        	qb.setTables(Constants.OUTLET_LOCATION_REGISTER_TABLE_NAME);
            qb.setProjectionMap(sOutletLocationRegisterProjectionMap);
            qb.appendWhere(FCVColumns._ID + "=" + uri.getPathSegments().get(1));
            break;
            
        case OUTLET_DBB_POSM_REGISTER:
        	qb.setTables(Constants.OUTLET_DBB_POSM__REGISTER_TABLE_NAME);
            qb.setProjectionMap(sDBBPosmRegisteredProjectionMap);
            break;
            
        case OUTLET_DBB_POSM_REGISTER_ID:
        	qb.setTables(Constants.OUTLET_DBB_POSM__REGISTER_TABLE_NAME);
            qb.setProjectionMap(sDBBPosmRegisteredProjectionMap);
            qb.appendWhere(FCVColumns._ID + "=" + uri.getPathSegments().get(1));
            break;     
            
        case OUTLET_RIVAL:
        	qb.setTables(Constants.OUTLET_RIVAL);
            qb.setProjectionMap(sOutletRivalProjectionMap);
            break; 
            
        case OUTLET_RIVAL_ID:
        	qb.setTables(Constants.OUTLET_RIVAL);
            qb.setProjectionMap(sOutletRivalProjectionMap);
            qb.appendWhere(FCVColumns._ID + "=" + uri.getPathSegments().get(1));
            break;             
            
        case LASTEST_BONUS:
        	qb.setTables(Constants.OUTLET_LASTEST_BONUS_TABLE_NAME);
            qb.setProjectionMap(sOutletLastestBonusProjectionMap);
            break;
            
        case LASTEST_BONUS_ID:
        	qb.setTables(Constants.OUTLET_LASTEST_BONUS_TABLE_NAME);
            qb.setProjectionMap(sOutletLastestBonusProjectionMap);
            qb.appendWhere(FCVColumns._ID + "=" + uri.getPathSegments().get(1));
            break;
            
        default:
            throw new IllegalArgumentException("Unknown URI " + uri);
        }

        // If no sort order is specified use the default
        String orderBy;
        if (TextUtils.isEmpty(sortOrder)) {
            orderBy = FCVColumns.DEFAULT_SORT;
        } else {
            orderBy = sortOrder;
        }

        // Get the database and run the query
        SQLiteDatabase db = mOpenHelper.getReadableDatabase();
        Cursor c = qb.query(db, projection, selection, selectionArgs, null, null, orderBy);

        // Tell the cursor what uri to watch, so it knows when its source data changes
        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;
    }

    @Override
    public String getType(Uri uri) {
        switch (sUriMatcher.match(uri)) {
        case OUTLET:
        	break;
        case OUTLET_ID:
        	break;
        default:
            throw new IllegalArgumentException("Unknown URI " + uri);
        }
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues initialValues) {
    	
    	ContentValues values;
    	SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        if (initialValues != null) {
            values = new ContentValues(initialValues);
        } else {
            values = new ContentValues();
        }
        if (sUriMatcher.match(uri) == OUTLET) {
        	long rowId = db.insert(Constants.OUTLET_TABLE_NAME, null, values);
            if (rowId > 0) {
                Uri outletUri = ContentUris.withAppendedId(FCVColumns.OUTLET_CONTENT_URI, rowId);
                getContext().getContentResolver().notifyChange(outletUri, null);
                return outletUri;
            }
        }else if (sUriMatcher.match(uri) == PRODUCT) {
        	long rowId = db.insert(Constants.PROMOTION_PRODUCT_TABLE_NAME, null, values);
            if (rowId > 0) {
                Uri outletUri = ContentUris.withAppendedId(FCVColumns.PROMOTION_PRODUCT_CONTENT_URI, rowId);
                getContext().getContentResolver().notifyChange(outletUri, null);
                return outletUri;
            }
        }else if (sUriMatcher.match(uri) == PROMOTION) {
        	long rowId = db.insert(Constants.PROMOTION_TABLE_NAME, null, values);
            if (rowId > 0) {
                Uri outletUri = ContentUris.withAppendedId(FCVColumns.PROMOTION_CONTENT_URI, rowId);
                getContext().getContentResolver().notifyChange(outletUri, null);
                return outletUri;
            }
        }else if (sUriMatcher.match(uri) == FULL_RANGE_SKU) {
        	long rowId = db.insert(Constants.FULL_RANGE_SKU_TABLE_NAME, null, values);
            if (rowId > 0) {
                Uri outletUri = ContentUris.withAppendedId(FCVColumns.FULL_RANGE_SKU_CONTENT_URI, rowId);
                getContext().getContentResolver().notifyChange(outletUri, null);
                return outletUri;
            }
        }else if (sUriMatcher.match(uri) == IFT_DISPLAY_LOCATION) {
        	long rowId = db.insert(Constants.IFT_DISPLAY_LOCATION_TABLE_NAME, null, values);
            if (rowId > 0) {
                Uri outletUri = ContentUris.withAppendedId(FCVColumns.IFT_DISPLAY_LOCATION_CONTENT_URI, rowId);
                getContext().getContentResolver().notifyChange(outletUri, null);
                return outletUri;
            }
        }else if (sUriMatcher.match(uri) == OUTLET_POSM) {
        	long rowId = db.insert(Constants.OUTLET_POSM_TABLE_NAME, null, values);
            if (rowId > 0) {
                Uri outletUri = ContentUris.withAppendedId(FCVColumns.OUTLET_POSM_CONTENT_URI, rowId);
                getContext().getContentResolver().notifyChange(outletUri, null);
                return outletUri;
            }
            throw new SQLException("Failed to insert row into " + uri);
        }else if (sUriMatcher.match(uri) == REGION_PROMOTIONS) {
        	long rowId = db.insert(Constants.REGION_PROMOTIONS_TABLE_NAME, null, values);
            if (rowId > 0) {
                Uri outletUri = ContentUris.withAppendedId(FCVColumns.REGION_PROMOTIONS_CONTENT_URI, rowId);
                getContext().getContentResolver().notifyChange(outletUri, null);
                return outletUri;
            }
        }else if (sUriMatcher.match(uri) == PROMOTION_PRODUCT_TO_HANDHELD) {
        	long rowId = db.insert(Constants.PROMOTION_PRODUCT_TO_HANDHELD_TABLE_NAME, null, values);
            if (rowId > 0) {
                Uri outletUri = ContentUris.withAppendedId(FCVColumns.PROMOTION_PRODUCT_TO_HANDHELD_CONTENT_URI, rowId);
                getContext().getContentResolver().notifyChange(outletUri, null);
                return outletUri;
            }
        }else if (sUriMatcher.match(uri) == OUTLET_CONTENT_TYPE_STATUS) {
        	long rowId = db.insert(Constants.OUTLET_CONTENT_TYPE_STATUS_TABLE_NAME, null, values);
            if (rowId > 0) {
                Uri outletUri = ContentUris.withAppendedId(FCVColumns.OUTLET_CONTENT_TYPE_STATUS_CONTENT_URI, rowId);
                getContext().getContentResolver().notifyChange(outletUri, null);
                return outletUri;
            }
        }else if (sUriMatcher.match(uri) == OUTLET_DBB_CONTENT_TYPE_STATUS) {
        	long rowId = db.insert(Constants.OUTLET_DBB_CONTENT_TYPE_STATUS_TABLE_NAME, null, values);
            if (rowId > 0) {
                Uri outletUri = ContentUris.withAppendedId(FCVColumns.OUTLET_DBB_CONTENT_TYPE_STATUS_CONTENT_URI, rowId);
                getContext().getContentResolver().notifyChange(outletUri, null);
                return outletUri;
            }
        }        
        else if (sUriMatcher.match(uri) == OUTLET_BRAND) {
        	long rowId = db.insert(Constants.OUTLET_BRAND_TABLE_NAME, null, values);
            if (rowId > 0) {
                Uri outletUri = ContentUris.withAppendedId(FCVColumns.OUTLET_BRAND_CONTENT_URI, rowId);
                getContext().getContentResolver().notifyChange(outletUri, null);
                return outletUri;
            }
        }else if (sUriMatcher.match(uri) == DISTRIBUTOR) {
        	long rowId = db.insert(Constants.DISTRIBUTOR_TABLE_NAME, null, values);
            if (rowId > 0) {
                Uri outletUri = ContentUris.withAppendedId(FCVColumns.DISTRIBUTOR_CONTENT_URI, rowId);
                getContext().getContentResolver().notifyChange(outletUri, null);
                return outletUri;
            }
        }else if (sUriMatcher.match(uri) == FULL_RANGE_SKU_CHECK) {
        	long rowId = db.insert(Constants.FULL_RANGE_SKU_CHECK_TABLE_NAME, null, values);
            if (rowId > 0) {
                Uri outletUri = ContentUris.withAppendedId(FCVColumns.FULL_RANGE_SKU_CHECK_CONTENT_URI, rowId);
                getContext().getContentResolver().notifyChange(outletUri, null);
                return outletUri;
            }
        }
        else if (sUriMatcher.match(uri) == DBB_POSM_FULL_RANGE_SKU_CHECK) {
        	long rowId = db.insert(Constants.DBB_POSM_FULL_RANGE_SKU_CHECK_TABLE_NAME, null, values);
            if (rowId > 0) {
                Uri outletUri = ContentUris.withAppendedId(FCVColumns.DBB_POSM_FULL_RANGE_SKU_CHECK_CONTENT_URI, rowId);
                getContext().getContentResolver().notifyChange(outletUri, null);
                return outletUri;
            }
        }        
        else if (sUriMatcher.match(uri) == IFT_DISPLAY_LOCATION_CHECK) {
        	long rowId = db.insert(Constants.IFT_DISPLAY_LOCATION_CHECK_TABLE_NAME, null, values);
            if (rowId > 0) {
                Uri outletUri = ContentUris.withAppendedId(FCVColumns.IFT_DISPLAY_LOCATION_CHECK_CONTENT_URI, rowId);
                getContext().getContentResolver().notifyChange(outletUri, null);
                return outletUri;
            }
        }else if (sUriMatcher.match(uri) == OUTLET_POSM_CHECK) {
        	long rowId = db.insert(Constants.OUTLET_POSM_CHECK_TABLE_NAME, null, values);
            if (rowId > 0) {
                Uri outletUri = ContentUris.withAppendedId(FCVColumns.OUTLET_POSM_CHECK_CONTENT_URI, rowId);
                getContext().getContentResolver().notifyChange(outletUri, null);
                return outletUri;
            }
        }else if (sUriMatcher.match(uri) == PROMOTION_CHECK) {
        	long rowId = db.insert(Constants.PROMOTION_CHECK_TABLE_NAME, null, values);
            if (rowId > 0) {
                Uri outletUri = ContentUris.withAppendedId(FCVColumns.PROMOTION_CHECK_CONTENT_URI, rowId);
                getContext().getContentResolver().notifyChange(outletUri, null);
                return outletUri;
            }
        }else if (sUriMatcher.match(uri) == OUTLET_BRAND_CHECK) {
        	long rowId = db.insert(Constants.OUTLET_BRAND_CHECK_TABLE_NAME, null, values);
            if (rowId > 0) {
                Uri outletUri = ContentUris.withAppendedId(FCVColumns.OUTLET_BRAND_CHECK_CONTENT_URI, rowId);
                getContext().getContentResolver().notifyChange(outletUri, null);
                return outletUri;
            }
        }else if (sUriMatcher.match(uri) == PICTURE) {
        	long rowId = db.insert(Constants.OUTLET_PICTURE_TABLE_NAME, null, values);
            if (rowId > 0) {
                Uri outletUri = ContentUris.withAppendedId(FCVColumns.OUTLET_PICTURE_CONTENT_URI, rowId);
                getContext().getContentResolver().notifyChange(outletUri, null);
                return outletUri;
            }
        }else if (sUriMatcher.match(uri) == UNIT) {
        	long rowId = db.insert(Constants.UNIT_TABLE_NAME, null, values);
            if (rowId > 0) {
                Uri outletUri = ContentUris.withAppendedId(FCVColumns.UNIT_CONTENT_URI, rowId);
                getContext().getContentResolver().notifyChange(outletUri, null);
                return outletUri;
            }
        }else if (sUriMatcher.match(uri) == FULLRANGE_FACING) {
        	long rowId = db.insert(Constants.FULLRANGE_FACING_TABLE_NAME, null, values);
            if (rowId > 0) {
                Uri outletUri = ContentUris.withAppendedId(FCVColumns.FULLRANGE_FACING_CONTENT_URI, rowId);
                getContext().getContentResolver().notifyChange(outletUri, null);
                return outletUri;
            }
        }else if (sUriMatcher.match(uri) == OUTLET_FACING) {
        	long rowId = db.insert(Constants.OUTLET_FACING_REGISTER_TABLE_NAME, null, values);
            if (rowId > 0) {
                Uri outletUri = ContentUris.withAppendedId(FCVColumns.OUTLET_FACING_CONTENT_URI, rowId);
                getContext().getContentResolver().notifyChange(outletUri, null);
                return outletUri;
            }
        }else if (sUriMatcher.match(uri) == OUTLET_POSM_MINIVALUE) {
        	long rowId = db.insert(Constants.OUTLET_POSM_MINIVALUE_TABLE_NAME, null, values);
            if (rowId > 0) {
                Uri outletUri = ContentUris.withAppendedId(FCVColumns.OUTLET_POSM_MINIVALUE__URI, rowId);
                getContext().getContentResolver().notifyChange(outletUri, null);
                return outletUri;
            }
        }else if (sUriMatcher.match(uri) == OUTLET_LOCATION_REGISTER) {
        	long rowId = db.insert(Constants.OUTLET_LOCATION_REGISTER_TABLE_NAME, null, values);
            if (rowId > 0) {
                Uri outletUri = ContentUris.withAppendedId(FCVColumns.OUTLET_LOCATION_REGISTER_URI, rowId);
                getContext().getContentResolver().notifyChange(outletUri, null);
                return outletUri;
            }
        }else if (sUriMatcher.match(uri) == OUTLET_DBB_POSM_REGISTER) {
        	long rowId = db.insert(Constants.OUTLET_DBB_POSM__REGISTER_TABLE_NAME, null, values);
            if (rowId > 0) {
                Uri outletUri = ContentUris.withAppendedId(FCVColumns.OUTLET_DBB_POSM_REGISTER_URI, rowId);
                getContext().getContentResolver().notifyChange(outletUri, null);
                return outletUri;
            }
        }else if (sUriMatcher.match(uri) == OUTLET_RIVAL) {
        	long rowId = db.insert(Constants.OUTLET_RIVAL, null, values);
            if (rowId > 0) {
                Uri outletUri = ContentUris.withAppendedId(FCVColumns.OUTLET_RIVAL_URI, rowId);
                getContext().getContentResolver().notifyChange(outletUri, null);
                return outletUri;
            }
        }        
        else if (sUriMatcher.match(uri) == LASTEST_BONUS) {
        	long rowId = db.insert(Constants.OUTLET_LASTEST_BONUS_TABLE_NAME, null, values);
            if (rowId > 0) {
                Uri outletUri = ContentUris.withAppendedId(FCVColumns.OUTLET_LASTEST_BONUS_CONTENT_URI, rowId);
                getContext().getContentResolver().notifyChange(outletUri, null);
                return outletUri;
            }
        }else {
        	throw new IllegalArgumentException("Unknown URI " + uri);
        }
        throw new SQLException("Failed to insert row into " + uri);
    }

    @Override
    public int delete(Uri uri, String where, String[] whereArgs) {
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        int count;
        String rowId;
        switch (sUriMatcher.match(uri)) {
        case OUTLET:
            count = db.delete(Constants.OUTLET_TABLE_NAME, where, whereArgs);
            break;

        case OUTLET_ID:
            rowId = uri.getPathSegments().get(1);
            count = db.delete(Constants.OUTLET_TABLE_NAME, FCVColumns._ID + "=" + rowId
                    + (!TextUtils.isEmpty(where) ? " AND (" + where + ')' : ""), whereArgs);
            break;
            
        case PRODUCT:
        	count = db.delete(Constants.PROMOTION_PRODUCT_TABLE_NAME, where, whereArgs);
            break;

        case PRODUCT_ID:
        	rowId = uri.getPathSegments().get(1);
            count = db.delete(Constants.PROMOTION_PRODUCT_TABLE_NAME, FCVColumns._ID + "=" + rowId
                    + (!TextUtils.isEmpty(where) ? " AND (" + where + ')' : ""), whereArgs);
            break;
            
        case PROMOTION:
        	count = db.delete(Constants.PROMOTION_TABLE_NAME, where, whereArgs);
            break;
            
        case PROMOTION_ID:
        	rowId = uri.getPathSegments().get(1);
            count = db.delete(Constants.PROMOTION_TABLE_NAME, FCVColumns._ID + "=" + rowId
                    + (!TextUtils.isEmpty(where) ? " AND (" + where + ')' : ""), whereArgs);
            break;
            
        case FULL_RANGE_SKU:
        	count = db.delete(Constants.FULL_RANGE_SKU_TABLE_NAME, where, whereArgs);
            break;
            
        case FULL_RANGE_SKU_ID:
        	rowId = uri.getPathSegments().get(1);
            count = db.delete(Constants.FULL_RANGE_SKU_TABLE_NAME, FCVColumns._ID + "=" + rowId
                    + (!TextUtils.isEmpty(where) ? " AND (" + where + ')' : ""), whereArgs);
            break;
            
        case IFT_DISPLAY_LOCATION:
        	count = db.delete(Constants.IFT_DISPLAY_LOCATION_TABLE_NAME, where, whereArgs);
            break;
            
        case IFT_DISPLAY_LOCATION_ID:
        	rowId = uri.getPathSegments().get(1);
            count = db.delete(Constants.IFT_DISPLAY_LOCATION_TABLE_NAME, FCVColumns._ID + "=" + rowId
                    + (!TextUtils.isEmpty(where) ? " AND (" + where + ')' : ""), whereArgs);
            break;
            
        case OUTLET_POSM:
        	count = db.delete(Constants.OUTLET_POSM_TABLE_NAME, where, whereArgs);
            break;
            
        case OUTLET_POSM_ID:
        	rowId = uri.getPathSegments().get(1);
            count = db.delete(Constants.OUTLET_POSM_TABLE_NAME, FCVColumns._ID + "=" + rowId
                    + (!TextUtils.isEmpty(where) ? " AND (" + where + ')' : ""), whereArgs);
            break;
            
        case REGION_PROMOTIONS:
        	count = db.delete(Constants.REGION_PROMOTIONS_TABLE_NAME, where, whereArgs);
            break;
            
        case PROMOTION_PRODUCT_TO_HANDHELD:
        	count = db.delete(Constants.PROMOTION_PRODUCT_TO_HANDHELD_TABLE_NAME, where, whereArgs);
            break;
            
        case OUTLET_CONTENT_TYPE_STATUS:
        	count = db.delete(Constants.OUTLET_CONTENT_TYPE_STATUS_TABLE_NAME, where, whereArgs);
            break; 
            
        case OUTLET_DBB_CONTENT_TYPE_STATUS:
        	count = db.delete(Constants.OUTLET_DBB_CONTENT_TYPE_STATUS_TABLE_NAME, where, whereArgs);
            break;             
            
        case OUTLET_BRAND:
        	count = db.delete(Constants.OUTLET_BRAND_TABLE_NAME, where, whereArgs);
            break;
            
        case UNIT:
        	count = db.delete(Constants.UNIT_TABLE_NAME, where, whereArgs);
            break;
            
        case OUTLET_BRAND_ID:
        	rowId = uri.getPathSegments().get(1);
            count = db.delete(Constants.OUTLET_BRAND_TABLE_NAME, FCVColumns._ID + "=" + rowId
                    + (!TextUtils.isEmpty(where) ? " AND (" + where + ')' : ""), whereArgs);
            break;
            
        case DISTRIBUTOR:
        	count = db.delete(Constants.DISTRIBUTOR_TABLE_NAME, where, whereArgs);
            break;
            
        case OUTLET_BRAND_CHECK:
        	count = db.delete(Constants.OUTLET_BRAND_CHECK_TABLE_NAME, where, whereArgs);
            break;
            
        case OUTLET_BRAND_CHECK_ID:
        	rowId = uri.getPathSegments().get(1);
            count = db.delete(Constants.OUTLET_BRAND_CHECK_TABLE_NAME, FCVColumns._ID + "=" + rowId
                    + (!TextUtils.isEmpty(where) ? " AND (" + where + ')' : ""), whereArgs);
            break;
            
        case FULL_RANGE_SKU_CHECK:
        	count = db.delete(Constants.FULL_RANGE_SKU_CHECK_TABLE_NAME, where, whereArgs);
            break;
            
        case DBB_POSM_FULL_RANGE_SKU_CHECK:
        	count = db.delete(Constants.DBB_POSM_FULL_RANGE_SKU_CHECK_TABLE_NAME, where, whereArgs);
            break;            
            
        case FULL_RANGE_SKU_CHECK_ID:
        	rowId = uri.getPathSegments().get(1);
            count = db.delete(Constants.FULL_RANGE_SKU_CHECK_TABLE_NAME, FCVColumns._ID + "=" + rowId
                    + (!TextUtils.isEmpty(where) ? " AND (" + where + ')' : ""), whereArgs);
            break;
            
        case DBB_POSM_FULL_RANGE_SKU_CHECK_ID:
        	rowId = uri.getPathSegments().get(1);
            count = db.delete(Constants.DBB_POSM_FULL_RANGE_SKU_CHECK_TABLE_NAME, FCVColumns._ID + "=" + rowId
                    + (!TextUtils.isEmpty(where) ? " AND (" + where + ')' : ""), whereArgs);
            break;            
            
        case IFT_DISPLAY_LOCATION_CHECK:
        	count = db.delete(Constants.IFT_DISPLAY_LOCATION_CHECK_TABLE_NAME, where, whereArgs);
            break;
            
        case IFT_DISPLAY_LOCATION_CHECK_ID:
        	rowId = uri.getPathSegments().get(1);
            count = db.delete(Constants.IFT_DISPLAY_LOCATION_CHECK_TABLE_NAME, FCVColumns._ID + "=" + rowId
                    + (!TextUtils.isEmpty(where) ? " AND (" + where + ')' : ""), whereArgs);
            break;
            
        case OUTLET_POSM_CHECK:
        	count = db.delete(Constants.OUTLET_POSM_CHECK_TABLE_NAME, where, whereArgs);
            break;
            
        case OUTLET_POSM_CHECK_ID:
        	rowId = uri.getPathSegments().get(1);
            count = db.delete(Constants.OUTLET_POSM_CHECK_TABLE_NAME, FCVColumns._ID + "=" + rowId
                    + (!TextUtils.isEmpty(where) ? " AND (" + where + ')' : ""), whereArgs);
            break;
        case PROMOTION_CHECK:
        	count = db.delete(Constants.PROMOTION_CHECK_TABLE_NAME, where, whereArgs);
            break;
            
        case PROMOTION_CHECK_ID:
        	rowId = uri.getPathSegments().get(1);
            count = db.delete(Constants.PROMOTION_CHECK_TABLE_NAME, FCVColumns._ID + "=" + rowId
                    + (!TextUtils.isEmpty(where) ? " AND (" + where + ')' : ""), whereArgs);
            break;
            
        case PICTURE:
        	count = db.delete(Constants.OUTLET_PICTURE_TABLE_NAME, where, whereArgs);
            break;
            
        case PICTURE_ID:
        	rowId = uri.getPathSegments().get(1);
            count = db.delete(Constants.OUTLET_PICTURE_TABLE_NAME, FCVColumns._ID + "=" + rowId
                    + (!TextUtils.isEmpty(where) ? " AND (" + where + ')' : ""), whereArgs);
            break;
            
        case FULLRANGE_FACING:
        	count = db.delete(Constants.FULLRANGE_FACING_TABLE_NAME, where, whereArgs);
            break;
            
        case FULLRANGE_FACING_ID:
        	rowId = uri.getPathSegments().get(1);
            count = db.delete(Constants.FULLRANGE_FACING_TABLE_NAME, FCVColumns._ID + "=" + rowId
                    + (!TextUtils.isEmpty(where) ? " AND (" + where + ')' : ""), whereArgs);
            break;
            
        case OUTLET_FACING:
        	count = db.delete(Constants.OUTLET_FACING_REGISTER_TABLE_NAME, where, whereArgs);
            break;
            
        case OUTLET_FACING_ID:
        	rowId = uri.getPathSegments().get(1);
            count = db.delete(Constants.OUTLET_FACING_REGISTER_TABLE_NAME, FCVColumns._ID + "=" + rowId
                    + (!TextUtils.isEmpty(where) ? " AND (" + where + ')' : ""), whereArgs);
            break;
            
        case OUTLET_POSM_MINIVALUE:
        	count = db.delete(Constants.OUTLET_POSM_MINIVALUE_TABLE_NAME, where, whereArgs);
            break;
            
        case OUTLET_POSM_MINIVALUE_ID:
        	rowId = uri.getPathSegments().get(1);
            count = db.delete(Constants.OUTLET_POSM_MINIVALUE_TABLE_NAME, FCVColumns._ID + "=" + rowId
                    + (!TextUtils.isEmpty(where) ? " AND (" + where + ')' : ""), whereArgs);
            break;            
            
        case OUTLET_LOCATION_REGISTER:
        	count = db.delete(Constants.OUTLET_LOCATION_REGISTER_TABLE_NAME, where, whereArgs);
            break;
            
        case OUTLET_LOCATION_REGISTER_ID:
        	rowId = uri.getPathSegments().get(1);
            count = db.delete(Constants.OUTLET_LOCATION_REGISTER_TABLE_NAME, FCVColumns._ID + "=" + rowId
                    + (!TextUtils.isEmpty(where) ? " AND (" + where + ')' : ""), whereArgs);
            break;
            
        case OUTLET_DBB_POSM_REGISTER:
        	count = db.delete(Constants.OUTLET_DBB_POSM__REGISTER_TABLE_NAME, where, whereArgs);
            break;
            
        case OUTLET_DBB_POSM_REGISTER_ID:
        	rowId = uri.getPathSegments().get(1);
            count = db.delete(Constants.OUTLET_DBB_POSM__REGISTER_TABLE_NAME, FCVColumns._ID + "=" + rowId
                    + (!TextUtils.isEmpty(where) ? " AND (" + where + ')' : ""), whereArgs);
            break;  
            
        case OUTLET_RIVAL:
        	count = db.delete(Constants.OUTLET_RIVAL, where, whereArgs);
            break;  
            
        case OUTLET_RIVAL_ID:
        	rowId = uri.getPathSegments().get(1);
            count = db.delete(Constants.OUTLET_RIVAL, FCVColumns._ID + "=" + rowId
                    + (!TextUtils.isEmpty(where) ? " AND (" + where + ')' : ""), whereArgs);
            break;             
            
        case LASTEST_BONUS:
        	count = db.delete(Constants.OUTLET_LASTEST_BONUS_TABLE_NAME, where, whereArgs);
            break;
            
        case LASTEST_BONUS_ID:
        	rowId = uri.getPathSegments().get(1);
            count = db.delete(Constants.OUTLET_LASTEST_BONUS_TABLE_NAME, FCVColumns._ID + "=" + rowId
                    + (!TextUtils.isEmpty(where) ? " AND (" + where + ')' : ""), whereArgs);
            break;
            
        default:
            throw new IllegalArgumentException("Unknown URI " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public int update(Uri uri, ContentValues values, String where, String[] whereArgs) {
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        int count;
        String rowId;
        switch (sUriMatcher.match(uri)) {
        case OUTLET:
            count = db.update(Constants.OUTLET_TABLE_NAME, values, where, whereArgs);
            break;

        case OUTLET_ID:
            rowId = uri.getPathSegments().get(1);
            count = db.update(Constants.OUTLET_TABLE_NAME, values, FCVColumns._ID + "=" + rowId
                    + (!TextUtils.isEmpty(where) ? " AND (" + where + ')' : ""), whereArgs);
            break;
            
        case PRODUCT:
        	count = db.update(Constants.PROMOTION_PRODUCT_TABLE_NAME, values, where, whereArgs);
            break;

        case PRODUCT_ID:
        	rowId = uri.getPathSegments().get(1);
            count = db.update(Constants.PROMOTION_PRODUCT_TABLE_NAME, values, FCVColumns._ID + "=" + rowId
                    + (!TextUtils.isEmpty(where) ? " AND (" + where + ')' : ""), whereArgs);
            break;
            
        case PROMOTION:
        	count = db.update(Constants.PROMOTION_TABLE_NAME, values, where, whereArgs);
            break;
            
        case PROMOTION_ID:
        	rowId = uri.getPathSegments().get(1);
            count = db.update(Constants.PROMOTION_TABLE_NAME, values, FCVColumns._ID + "=" + rowId
                    + (!TextUtils.isEmpty(where) ? " AND (" + where + ')' : ""), whereArgs);
            break;
            
        case FULL_RANGE_SKU:
        	count = db.update(Constants.FULL_RANGE_SKU_TABLE_NAME, values, where, whereArgs);
            break;
            
        case FULL_RANGE_SKU_ID:
        	rowId = uri.getPathSegments().get(1);
            count = db.update(Constants.FULL_RANGE_SKU_TABLE_NAME, values, FCVColumns._ID + "=" + rowId
                    + (!TextUtils.isEmpty(where) ? " AND (" + where + ')' : ""), whereArgs);
            break;
            
        case IFT_DISPLAY_LOCATION:
        	count = db.update(Constants.IFT_DISPLAY_LOCATION_TABLE_NAME, values, where, whereArgs);
            break;
            
        case IFT_DISPLAY_LOCATION_ID:
        	rowId = uri.getPathSegments().get(1);
            count = db.update(Constants.IFT_DISPLAY_LOCATION_TABLE_NAME, values, FCVColumns._ID + "=" + rowId
                    + (!TextUtils.isEmpty(where) ? " AND (" + where + ')' : ""), whereArgs);
            break;
            
        case OUTLET_POSM:
        	count = db.update(Constants.OUTLET_POSM_TABLE_NAME, values, where, whereArgs);
            break;
            
        case OUTLET_POSM_ID:
        	rowId = uri.getPathSegments().get(1);
            count = db.update(Constants.OUTLET_POSM_TABLE_NAME, values, FCVColumns._ID + "=" + rowId
                    + (!TextUtils.isEmpty(where) ? " AND (" + where + ')' : ""), whereArgs);
            break;
            
        case REGION_PROMOTIONS:
        	count = db.update(Constants.REGION_PROMOTIONS_TABLE_NAME, values, where, whereArgs);
            break;
            
        case PROMOTION_PRODUCT_TO_HANDHELD:
        	count = db.update(Constants.PROMOTION_PRODUCT_TO_HANDHELD_TABLE_NAME, values, where, whereArgs);
            break;
            
        case OUTLET_CONTENT_TYPE_STATUS:
        	count = db.update(Constants.OUTLET_CONTENT_TYPE_STATUS_TABLE_NAME, values, where, whereArgs);
            break;
            
        case OUTLET_DBB_CONTENT_TYPE_STATUS:
        	count = db.update(Constants.OUTLET_DBB_CONTENT_TYPE_STATUS_TABLE_NAME, values, where, whereArgs);
            break;            
           
        case OUTLET_BRAND:
        	count = db.update(Constants.OUTLET_BRAND_TABLE_NAME, values, where, whereArgs);
            break;

        case OUTLET_BRAND_ID:
        	rowId = uri.getPathSegments().get(1);
            count = db.update(Constants.OUTLET_BRAND_TABLE_NAME, values, FCVColumns._ID + "=" + rowId
                    + (!TextUtils.isEmpty(where) ? " AND (" + where + ')' : ""), whereArgs);
            break;
            
        case OUTLET_BRAND_CHECK:
        	count = db.update(Constants.OUTLET_BRAND_CHECK_TABLE_NAME, values, where, whereArgs);
            break;

        case OUTLET_BRAND_CHECK_ID:
        	rowId = uri.getPathSegments().get(1);
            count = db.update(Constants.OUTLET_BRAND_CHECK_TABLE_NAME, values, FCVColumns._ID + "=" + rowId
                    + (!TextUtils.isEmpty(where) ? " AND (" + where + ')' : ""), whereArgs);
            break;
            
        case FULL_RANGE_SKU_CHECK:
        	count = db.update(Constants.FULL_RANGE_SKU_CHECK_TABLE_NAME, values, where, whereArgs);
            break;

        case DBB_POSM_FULL_RANGE_SKU_CHECK:
        	count = db.update(Constants.DBB_POSM_FULL_RANGE_SKU_CHECK_TABLE_NAME, values, where, whereArgs);
            break;            
            
        case FULL_RANGE_SKU_CHECK_ID:
        	rowId = uri.getPathSegments().get(1);
            count = db.update(Constants.FULL_RANGE_SKU_CHECK_TABLE_NAME, values, FCVColumns._ID + "=" + rowId
                    + (!TextUtils.isEmpty(where) ? " AND (" + where + ')' : ""), whereArgs);
            break;
            
        case DBB_POSM_FULL_RANGE_SKU_CHECK_ID:
        	rowId = uri.getPathSegments().get(1);
            count = db.update(Constants.DBB_POSM_FULL_RANGE_SKU_CHECK_TABLE_NAME, values, FCVColumns._ID + "=" + rowId
                    + (!TextUtils.isEmpty(where) ? " AND (" + where + ')' : ""), whereArgs);
            break;            
            
        case IFT_DISPLAY_LOCATION_CHECK:
        	count = db.update(Constants.IFT_DISPLAY_LOCATION_CHECK_TABLE_NAME, values, where, whereArgs);
            break;

        case IFT_DISPLAY_LOCATION_CHECK_ID:
        	rowId = uri.getPathSegments().get(1);
            count = db.update(Constants.IFT_DISPLAY_LOCATION_CHECK_TABLE_NAME, values, FCVColumns._ID + "=" + rowId
                    + (!TextUtils.isEmpty(where) ? " AND (" + where + ')' : ""), whereArgs);
            break;
            
        case OUTLET_POSM_CHECK:
        	count = db.update(Constants.OUTLET_POSM_CHECK_TABLE_NAME, values, where, whereArgs);
            break;

        case OUTLET_POSM_CHECK_ID:
        	rowId = uri.getPathSegments().get(1);
            count = db.update(Constants.OUTLET_POSM_CHECK_TABLE_NAME, values, FCVColumns._ID + "=" + rowId
                    + (!TextUtils.isEmpty(where) ? " AND (" + where + ')' : ""), whereArgs);
            break;
            
        case PROMOTION_CHECK:
        	count = db.update(Constants.PROMOTION_CHECK_TABLE_NAME, values, where, whereArgs);
            break;

        case PROMOTION_CHECK_ID:
        	rowId = uri.getPathSegments().get(1);
            count = db.update(Constants.PROMOTION_CHECK_TABLE_NAME, values, FCVColumns._ID + "=" + rowId
                    + (!TextUtils.isEmpty(where) ? " AND (" + where + ')' : ""), whereArgs);
            break;
            
        case PICTURE:
        	count = db.update(Constants.OUTLET_PICTURE_TABLE_NAME, values, where, whereArgs);
            break;

        case PICTURE_ID:
        	rowId = uri.getPathSegments().get(1);
            count = db.update(Constants.OUTLET_PICTURE_TABLE_NAME, values, FCVColumns._ID + "=" + rowId
                    + (!TextUtils.isEmpty(where) ? " AND (" + where + ')' : ""), whereArgs);
            break;
            
        case FULLRANGE_FACING:
        	count = db.update(Constants.FULLRANGE_FACING_TABLE_NAME, values, where, whereArgs);
            break;

        case FULLRANGE_FACING_ID:
        	rowId = uri.getPathSegments().get(1);
            count = db.update(Constants.FULLRANGE_FACING_TABLE_NAME, values, FCVColumns._ID + "=" + rowId
                    + (!TextUtils.isEmpty(where) ? " AND (" + where + ')' : ""), whereArgs);
            break;
            
        case OUTLET_FACING:
        	count = db.update(Constants.OUTLET_FACING_REGISTER_TABLE_NAME, values, where, whereArgs);
            break;

        case OUTLET_FACING_ID:
        	rowId = uri.getPathSegments().get(1);
            count = db.update(Constants.OUTLET_FACING_REGISTER_TABLE_NAME, values, FCVColumns._ID + "=" + rowId
                    + (!TextUtils.isEmpty(where) ? " AND (" + where + ')' : ""), whereArgs);
            break;
            
        case OUTLET_POSM_MINIVALUE:
        	count = db.update(Constants.OUTLET_POSM_MINIVALUE_TABLE_NAME, values, where, whereArgs);
            break;

        case OUTLET_POSM_MINIVALUE_ID:
        	rowId = uri.getPathSegments().get(1);
            count = db.update(Constants.OUTLET_POSM_MINIVALUE_TABLE_NAME, values, FCVColumns._ID + "=" + rowId
                    + (!TextUtils.isEmpty(where) ? " AND (" + where + ')' : ""), whereArgs);
            break;           
            
        case OUTLET_LOCATION_REGISTER:
        	count = db.update(Constants.OUTLET_LOCATION_REGISTER_TABLE_NAME, values, where, whereArgs);
            break;

        case OUTLET_LOCATION_REGISTER_ID:
        	rowId = uri.getPathSegments().get(1);
            count = db.update(Constants.OUTLET_LOCATION_REGISTER_TABLE_NAME, values, FCVColumns._ID + "=" + rowId
                    + (!TextUtils.isEmpty(where) ? " AND (" + where + ')' : ""), whereArgs);
            break;
            
        case OUTLET_DBB_POSM_REGISTER:
        	count = db.update(Constants.OUTLET_DBB_POSM__REGISTER_TABLE_NAME, values, where, whereArgs);
            break;

        case OUTLET_DBB_POSM_REGISTER_ID:
        	rowId = uri.getPathSegments().get(1);
            count = db.update(Constants.OUTLET_DBB_POSM__REGISTER_TABLE_NAME, values, FCVColumns._ID + "=" + rowId
                    + (!TextUtils.isEmpty(where) ? " AND (" + where + ')' : ""), whereArgs);
            break;  
            
        case OUTLET_RIVAL:
        	count = db.update(Constants.OUTLET_RIVAL, values, where, whereArgs);
            break;

        case OUTLET_RIVAL_ID:
        	rowId = uri.getPathSegments().get(1);
            count = db.update(Constants.OUTLET_RIVAL, values, FCVColumns._ID + "=" + rowId
                    + (!TextUtils.isEmpty(where) ? " AND (" + where + ')' : ""), whereArgs);
            break;            
            
        case LASTEST_BONUS:
        	count = db.update(Constants.OUTLET_LASTEST_BONUS_TABLE_NAME, values, where, whereArgs);
            break;

        case LASTEST_BONUS_ID:
        	rowId = uri.getPathSegments().get(1);
            count = db.update(Constants.OUTLET_LASTEST_BONUS_TABLE_NAME, values, FCVColumns._ID + "=" + rowId
                    + (!TextUtils.isEmpty(where) ? " AND (" + where + ')' : ""), whereArgs);
            break;
            
        default:
            throw new IllegalArgumentException("Unknown URI " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    static {
        sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        sUriMatcher.addURI(Constants.OUTLET_AUTHORITY, Constants.OUTLET_TABLE_NAME, OUTLET);
        sUriMatcher.addURI(Constants.OUTLET_AUTHORITY, Constants.OUTLET_TABLE_NAME + "/#", OUTLET_ID);
        
        sUriMatcher.addURI(Constants.OUTLET_AUTHORITY, Constants.PROMOTION_PRODUCT_TABLE_NAME, PRODUCT);
        sUriMatcher.addURI(Constants.OUTLET_AUTHORITY, Constants.PROMOTION_PRODUCT_TABLE_NAME + "/#", PRODUCT_ID);
        
        sUriMatcher.addURI(Constants.OUTLET_AUTHORITY, Constants.PROMOTION_TABLE_NAME, PROMOTION);
        sUriMatcher.addURI(Constants.OUTLET_AUTHORITY, Constants.PROMOTION_TABLE_NAME + "/#", PROMOTION_ID);
        
        sUriMatcher.addURI(Constants.OUTLET_AUTHORITY, Constants.FULL_RANGE_SKU_TABLE_NAME, FULL_RANGE_SKU);
        sUriMatcher.addURI(Constants.OUTLET_AUTHORITY, Constants.FULL_RANGE_SKU_TABLE_NAME + "/#", FULL_RANGE_SKU_ID);
        
        sUriMatcher.addURI(Constants.OUTLET_AUTHORITY, Constants.IFT_DISPLAY_LOCATION_TABLE_NAME, IFT_DISPLAY_LOCATION);
        sUriMatcher.addURI(Constants.OUTLET_AUTHORITY, Constants.IFT_DISPLAY_LOCATION_TABLE_NAME + "/#", IFT_DISPLAY_LOCATION_ID);
        
        sUriMatcher.addURI(Constants.OUTLET_AUTHORITY, Constants.OUTLET_POSM_TABLE_NAME, OUTLET_POSM);
        sUriMatcher.addURI(Constants.OUTLET_AUTHORITY, Constants.OUTLET_POSM_TABLE_NAME + "/#", OUTLET_POSM_ID);
        
        sUriMatcher.addURI(Constants.OUTLET_AUTHORITY, Constants.REGION_PROMOTIONS_TABLE_NAME, REGION_PROMOTIONS);
        
        sUriMatcher.addURI(Constants.OUTLET_AUTHORITY, Constants.PROMOTION_PRODUCT_TO_HANDHELD_TABLE_NAME, PROMOTION_PRODUCT_TO_HANDHELD);
        
        sUriMatcher.addURI(Constants.OUTLET_AUTHORITY, Constants.OUTLET_CONTENT_TYPE_STATUS_TABLE_NAME, OUTLET_CONTENT_TYPE_STATUS);
        
        sUriMatcher.addURI(Constants.OUTLET_AUTHORITY, Constants.OUTLET_DBB_CONTENT_TYPE_STATUS_TABLE_NAME, OUTLET_DBB_CONTENT_TYPE_STATUS);
        
        sUriMatcher.addURI(Constants.OUTLET_AUTHORITY, Constants.OUTLET_BRAND_TABLE_NAME, OUTLET_BRAND);
        sUriMatcher.addURI(Constants.OUTLET_AUTHORITY, Constants.OUTLET_BRAND_TABLE_NAME + "/#", OUTLET_BRAND_ID);
        
        sUriMatcher.addURI(Constants.OUTLET_AUTHORITY, Constants.DISTRIBUTOR_TABLE_NAME, DISTRIBUTOR);
        
        sUriMatcher.addURI(Constants.OUTLET_AUTHORITY, Constants.FULL_RANGE_SKU_CHECK_TABLE_NAME, FULL_RANGE_SKU_CHECK);
        sUriMatcher.addURI(Constants.OUTLET_AUTHORITY, Constants.FULL_RANGE_SKU_CHECK_TABLE_NAME + "/#", FULL_RANGE_SKU_CHECK_ID);
        
        sUriMatcher.addURI(Constants.OUTLET_AUTHORITY, Constants.DBB_POSM_FULL_RANGE_SKU_CHECK_TABLE_NAME, DBB_POSM_FULL_RANGE_SKU_CHECK);
        sUriMatcher.addURI(Constants.OUTLET_AUTHORITY, Constants.DBB_POSM_FULL_RANGE_SKU_CHECK_TABLE_NAME + "/#", DBB_POSM_FULL_RANGE_SKU_CHECK_ID);        
        
        sUriMatcher.addURI(Constants.OUTLET_AUTHORITY, Constants.IFT_DISPLAY_LOCATION_CHECK_TABLE_NAME, IFT_DISPLAY_LOCATION_CHECK);
        sUriMatcher.addURI(Constants.OUTLET_AUTHORITY, Constants.IFT_DISPLAY_LOCATION_CHECK_TABLE_NAME + "/#", IFT_DISPLAY_LOCATION_CHECK_ID);
        
        sUriMatcher.addURI(Constants.OUTLET_AUTHORITY, Constants.OUTLET_POSM_CHECK_TABLE_NAME, OUTLET_POSM_CHECK);
        sUriMatcher.addURI(Constants.OUTLET_AUTHORITY, Constants.OUTLET_POSM_CHECK_TABLE_NAME + "/#", OUTLET_POSM_CHECK_ID);
        
        sUriMatcher.addURI(Constants.OUTLET_AUTHORITY, Constants.PROMOTION_CHECK_TABLE_NAME, PROMOTION_CHECK);
        sUriMatcher.addURI(Constants.OUTLET_AUTHORITY, Constants.PROMOTION_CHECK_TABLE_NAME + "/#", PROMOTION_CHECK_ID);
        
        sUriMatcher.addURI(Constants.OUTLET_AUTHORITY, Constants.OUTLET_BRAND_CHECK_TABLE_NAME, OUTLET_BRAND_CHECK);
        sUriMatcher.addURI(Constants.OUTLET_AUTHORITY, Constants.OUTLET_BRAND_CHECK_TABLE_NAME + "/#", OUTLET_BRAND_CHECK_ID);
        
        sUriMatcher.addURI(Constants.OUTLET_AUTHORITY, Constants.OUTLET_PICTURE_TABLE_NAME, PICTURE);
        sUriMatcher.addURI(Constants.OUTLET_AUTHORITY, Constants.OUTLET_PICTURE_TABLE_NAME + "/#", PICTURE_ID);
        
        sUriMatcher.addURI(Constants.OUTLET_AUTHORITY, Constants.UNIT_TABLE_NAME, UNIT);
        sUriMatcher.addURI(Constants.OUTLET_AUTHORITY, Constants.UNIT_TABLE_NAME + "/#", UNIT_ID);
        
        sUriMatcher.addURI(Constants.OUTLET_AUTHORITY, Constants.FULLRANGE_FACING_TABLE_NAME, FULLRANGE_FACING);
        sUriMatcher.addURI(Constants.OUTLET_AUTHORITY, Constants.FULLRANGE_FACING_TABLE_NAME + "/#", FULLRANGE_FACING_ID);
        
        sUriMatcher.addURI(Constants.OUTLET_AUTHORITY, Constants.OUTLET_FACING_REGISTER_TABLE_NAME, OUTLET_FACING);
        sUriMatcher.addURI(Constants.OUTLET_AUTHORITY, Constants.OUTLET_FACING_REGISTER_TABLE_NAME + "/#", OUTLET_FACING_ID);
        
        sUriMatcher.addURI(Constants.OUTLET_AUTHORITY, Constants.OUTLET_POSM_MINIVALUE_TABLE_NAME, OUTLET_POSM_MINIVALUE);
        sUriMatcher.addURI(Constants.OUTLET_AUTHORITY, Constants.OUTLET_POSM_MINIVALUE_TABLE_NAME + "/#", OUTLET_POSM_MINIVALUE_ID);        
        
        sUriMatcher.addURI(Constants.OUTLET_AUTHORITY, Constants.OUTLET_LOCATION_REGISTER_TABLE_NAME, OUTLET_LOCATION_REGISTER);
        sUriMatcher.addURI(Constants.OUTLET_AUTHORITY, Constants.OUTLET_LOCATION_REGISTER_TABLE_NAME + "/#", OUTLET_LOCATION_REGISTER_ID);
        
        sUriMatcher.addURI(Constants.OUTLET_AUTHORITY, Constants.OUTLET_DBB_POSM__REGISTER_TABLE_NAME, OUTLET_DBB_POSM_REGISTER);
        sUriMatcher.addURI(Constants.OUTLET_AUTHORITY, Constants.OUTLET_DBB_POSM__REGISTER_TABLE_NAME + "/#", OUTLET_DBB_POSM_REGISTER_ID);  
        
        sUriMatcher.addURI(Constants.OUTLET_AUTHORITY, Constants.OUTLET_RIVAL, OUTLET_RIVAL);
        sUriMatcher.addURI(Constants.OUTLET_AUTHORITY, Constants.OUTLET_RIVAL + "/#", OUTLET_RIVAL_ID);          
        
        sUriMatcher.addURI(Constants.OUTLET_AUTHORITY, Constants.OUTLET_LASTEST_BONUS_TABLE_NAME, LASTEST_BONUS);
        sUriMatcher.addURI(Constants.OUTLET_AUTHORITY, Constants.OUTLET_LASTEST_BONUS_TABLE_NAME + "/#", LASTEST_BONUS_ID);

        sOuletProjectionMap = new HashMap<String, String>();
        sOuletProjectionMap.put(FCVColumns._ID, FCVColumns._ID);
        sOuletProjectionMap.put(FCVColumns.DISTRIBUTOR_NAME, FCVColumns.DISTRIBUTOR_NAME);
        sOuletProjectionMap.put(FCVColumns.DISTRIBUTOR_NAME_NO_DIACRITIC, FCVColumns.DISTRIBUTOR_NAME_NO_DIACRITIC);
        sOuletProjectionMap.put(FCVColumns.DISTRIBUTOR_ID, FCVColumns.DISTRIBUTOR_ID);
        sOuletProjectionMap.put(FCVColumns.DISTRIBUTOR_SAPCODE, FCVColumns.DISTRIBUTOR_SAPCODE);
        sOuletProjectionMap.put(FCVColumns.DMS_CODE, FCVColumns.DMS_CODE);
        sOuletProjectionMap.put(FCVColumns.ADDRESS, FCVColumns.ADDRESS);
        sOuletProjectionMap.put(FCVColumns.ADDRESS_NO_DIACRITIC, FCVColumns.ADDRESS_NO_DIACRITIC);
        sOuletProjectionMap.put(FCVColumns.REGION_ID, FCVColumns.REGION_ID);
        sOuletProjectionMap.put(FCVColumns.REGION, FCVColumns.REGION);
        sOuletProjectionMap.put(FCVColumns.GPS_LATITUDE, FCVColumns.GPS_LATITUDE);
        sOuletProjectionMap.put(FCVColumns.GPS_LONGTITUDE, FCVColumns.GPS_LONGTITUDE);
        sOuletProjectionMap.put(FCVColumns.AUDITOR_CODE, FCVColumns.AUDITOR_CODE);
        sOuletProjectionMap.put(FCVColumns.CHECKED, FCVColumns.CHECKED);
        sOuletProjectionMap.put(FCVColumns.ACTIVE_STATUS, FCVColumns.ACTIVE_STATUS);
        sOuletProjectionMap.put(FCVColumns.AUDIT_DATE, FCVColumns.AUDIT_DATE);
        sOuletProjectionMap.put(FCVColumns.SYNCED, FCVColumns.SYNCED);
        sOuletProjectionMap.put(FCVColumns.NOTES, FCVColumns.NOTES);
        
        sProductProjectionMap = new HashMap<String, String>();
        sProductProjectionMap.put(FCVColumns._ID, FCVColumns._ID);
        sProductProjectionMap.put(FCVColumns.NAME, FCVColumns.NAME);
        
        sPromotionProjectionMap = new HashMap<String, String>();
        sPromotionProjectionMap.put(FCVColumns._ID, FCVColumns._ID);
        sPromotionProjectionMap.put(FCVColumns.NAME, FCVColumns.NAME);
        sPromotionProjectionMap.put(FCVColumns.CODE, FCVColumns.CODE);
        sPromotionProjectionMap.put(FCVColumns.EFFECTIVE_DATE, FCVColumns.EFFECTIVE_DATE);
        sPromotionProjectionMap.put(FCVColumns.EXPIRE_DATE, FCVColumns.EXPIRE_DATE);
        sPromotionProjectionMap.put(FCVColumns.BUY_QUANTITY, FCVColumns.BUY_QUANTITY);
        sPromotionProjectionMap.put(FCVColumns.BUY_UNIT, FCVColumns.BUY_UNIT);
        sPromotionProjectionMap.put(FCVColumns.BUY_PRODUCT, FCVColumns.BUY_PRODUCT);
        sPromotionProjectionMap.put(FCVColumns.GET_QUANTITY, FCVColumns.GET_QUANTITY);
        sPromotionProjectionMap.put(FCVColumns.GET_UNIT, FCVColumns.GET_UNIT);
        sPromotionProjectionMap.put(FCVColumns.OUTLET_BRAND, FCVColumns.OUTLET_BRAND);
        sPromotionProjectionMap.put(FCVColumns.OUTLET_BRAND_ID, FCVColumns.OUTLET_BRAND_ID);
        sPromotionProjectionMap.put(FCVColumns.OUTLET_BRAND_CODE, FCVColumns.OUTLET_BRAND_CODE);
        sPromotionProjectionMap.put(FCVColumns.OUTLET_BRAND_GROUP, FCVColumns.OUTLET_BRAND_GROUP);
        
        sFullRangeSKUProjectionMap = new HashMap<String, String>();
        sFullRangeSKUProjectionMap.put(FCVColumns._ID, FCVColumns._ID);
        sFullRangeSKUProjectionMap.put(FCVColumns.OUTLET_BRAND, FCVColumns.OUTLET_BRAND);
        sFullRangeSKUProjectionMap.put(FCVColumns.OUTLET_BRAND_ID, FCVColumns.OUTLET_BRAND_ID);
        sFullRangeSKUProjectionMap.put(FCVColumns.OUTLET_BRAND_CODE, FCVColumns.OUTLET_BRAND_CODE);
        sFullRangeSKUProjectionMap.put(FCVColumns.OUTLET_BRAND_GROUP, FCVColumns.OUTLET_BRAND_GROUP);
        sFullRangeSKUProjectionMap.put(FCVColumns.NAME, FCVColumns.NAME);
        sFullRangeSKUProjectionMap.put(FCVColumns.DISPLAYORDER, FCVColumns.DISPLAYORDER);
        
        sIFTDisplayLocationProjectionMap = new HashMap<String, String>();
        sIFTDisplayLocationProjectionMap.put(FCVColumns._ID, FCVColumns._ID);
        sIFTDisplayLocationProjectionMap.put(FCVColumns.CODE, FCVColumns.CODE);
        sIFTDisplayLocationProjectionMap.put(FCVColumns.NAME, FCVColumns.NAME);
        sIFTDisplayLocationProjectionMap.put(FCVColumns.DISPLAYORDER, FCVColumns.DISPLAYORDER);
        sIFTDisplayLocationProjectionMap.put(FCVColumns.OUTLET_BRAND_ID, FCVColumns.OUTLET_BRAND_ID);
        sIFTDisplayLocationProjectionMap.put(FCVColumns.OUTLET_BRAND_CODE, FCVColumns.OUTLET_BRAND_CODE);
        
        sOutletPOSMProjectionMap = new HashMap<String, String>();
        sOutletPOSMProjectionMap.put(FCVColumns._ID, FCVColumns._ID);
        sOutletPOSMProjectionMap.put(FCVColumns.CODE, FCVColumns.CODE);
        sOutletPOSMProjectionMap.put(FCVColumns.NAME, FCVColumns.NAME);
        sOutletPOSMProjectionMap.put(FCVColumns.ACTIVE, FCVColumns.ACTIVE);
        sOutletPOSMProjectionMap.put(FCVColumns.OUTLET_BRAND, FCVColumns.OUTLET_BRAND);
        sOutletPOSMProjectionMap.put(FCVColumns.OUTLET_BRAND_ID, FCVColumns.OUTLET_BRAND_ID);
        sOutletPOSMProjectionMap.put(FCVColumns.OUTLET_BRAND_CODE, FCVColumns.OUTLET_BRAND_CODE);
        sOutletPOSMProjectionMap.put(FCVColumns.OUTLET_BRAND_GROUP, FCVColumns.OUTLET_BRAND_GROUP);
        
        sOutletRivalProjectionMap = new HashMap<String, String>();
        sOutletRivalProjectionMap.put(FCVColumns._ID, FCVColumns._ID);
        sOutletRivalProjectionMap.put(FCVColumns.OUTLET_ID, FCVColumns.OUTLET_ID);
        sOutletRivalProjectionMap.put(FCVColumns.HAS, FCVColumns.HAS);    
        
        sRegionPromotionsProjectionMap = new HashMap<String, String>();
        sRegionPromotionsProjectionMap.put(FCVColumns._ID, FCVColumns._ID);
        sRegionPromotionsProjectionMap.put(FCVColumns.PROMOTION_ID, FCVColumns.PROMOTION_ID);
        sRegionPromotionsProjectionMap.put(FCVColumns.REGION_ID, FCVColumns.REGION_ID);
        
        sPromotionProductToHandHeldProjectionMap = new HashMap<String, String>();
        sPromotionProductToHandHeldProjectionMap.put(FCVColumns._ID, FCVColumns._ID);
        sPromotionProductToHandHeldProjectionMap.put(FCVColumns.PRODUCT_ID, FCVColumns.PRODUCT_ID);
        sPromotionProductToHandHeldProjectionMap.put(FCVColumns.PROMOTION_ID, FCVColumns.PROMOTION_ID);
        
        sOutletStatusProjectionMap = new HashMap<String, String>();
        sOutletStatusProjectionMap.put(FCVColumns._ID, FCVColumns._ID);
        sOutletStatusProjectionMap.put(FCVColumns.DISTRIBUTION, FCVColumns.DISTRIBUTION);
        sOutletStatusProjectionMap.put(FCVColumns.FACING, FCVColumns.FACING);
        sOutletStatusProjectionMap.put(FCVColumns.DBB, FCVColumns.DBB);
        sOutletStatusProjectionMap.put(FCVColumns.PROMOTION, FCVColumns.PROMOTION);
        sOutletStatusProjectionMap.put(FCVColumns.PICTURE, FCVColumns.PICTURE);
        sOutletStatusProjectionMap.put(FCVColumns.LASTEST_BONUS, FCVColumns.LASTEST_BONUS);
        
        sOutletDbbStatusProjectionMap = new HashMap<String, String>();
        sOutletDbbStatusProjectionMap.put(FCVColumns._ID, FCVColumns._ID);
        sOutletDbbStatusProjectionMap.put(FCVColumns.DBB_DISTRIBUTION, FCVColumns.DBB_DISTRIBUTION);
        sOutletDbbStatusProjectionMap.put(FCVColumns.POSM, FCVColumns.POSM);
        sOutletDbbStatusProjectionMap.put(FCVColumns.HOTZONE, FCVColumns.HOTZONE);
        sOutletDbbStatusProjectionMap.put(FCVColumns.DBB_LATEST_BONUS, FCVColumns.DBB_LATEST_BONUS);
        sOutletDbbStatusProjectionMap.put(FCVColumns.DBB_RIVAL, FCVColumns.DBB_RIVAL);
                       
        sOutletBrandProjectionMap = new HashMap<String, String>();
        sOutletBrandProjectionMap.put(FCVColumns._ID, FCVColumns._ID);
        sOutletBrandProjectionMap.put(FCVColumns.OUTLET_BRAND, FCVColumns.OUTLET_BRAND);
        sOutletBrandProjectionMap.put(FCVColumns.OUTLET_BRAND_CODE, FCVColumns.OUTLET_BRAND_CODE);
        sOutletBrandProjectionMap.put(FCVColumns.OUTLET_BRAND_GROUP, FCVColumns.OUTLET_BRAND_GROUP);
        
        sDistributorProjectionMap = new HashMap<String, String>();
        sDistributorProjectionMap.put(FCVColumns._ID, FCVColumns._ID);
        sDistributorProjectionMap.put(FCVColumns.DISTRIBUTOR_ID, FCVColumns.DISTRIBUTOR_ID);
        sDistributorProjectionMap.put(FCVColumns.NAME, FCVColumns.NAME);
        
        sPictureProjectionMap = new HashMap<String, String>();
        sPictureProjectionMap.put(FCVColumns._ID, FCVColumns._ID);
        sPictureProjectionMap.put(FCVColumns.OUTLET_ID, FCVColumns.OUTLET_ID);
        sPictureProjectionMap.put(FCVColumns.URI, FCVColumns.URI);
        sPictureProjectionMap.put(FCVColumns.NOTES, FCVColumns.NOTES);
        
        sUnitProjectionMap = new HashMap<String, String>();
        sUnitProjectionMap.put(FCVColumns._ID, FCVColumns._ID);
        sUnitProjectionMap.put(FCVColumns.NAME, FCVColumns.NAME);
        
        sOutletFacingProjectionMap = new HashMap<String, String>();
        sOutletFacingProjectionMap.put(FCVColumns._ID, FCVColumns._ID);
        sOutletFacingProjectionMap.put(FCVColumns.OUTLET_ID, FCVColumns.OUTLET_ID);
        sOutletFacingProjectionMap.put(FCVColumns.OUTLET_BRAND_ID, FCVColumns.OUTLET_BRAND_ID);
        sOutletFacingProjectionMap.put(FCVColumns.FACING, FCVColumns.FACING);
        sOutletFacingProjectionMap.put(FCVColumns.OUTLET_LEVEL_REGISTER_ID, FCVColumns.OUTLET_LEVEL_REGISTER_ID);
        
        sOutletLocationRegisterProjectionMap = new HashMap<String, String>();
        sOutletLocationRegisterProjectionMap.put(FCVColumns._ID, FCVColumns._ID);
        sOutletLocationRegisterProjectionMap.put(FCVColumns.OUTLET_ID, FCVColumns.OUTLET_ID);
        sOutletLocationRegisterProjectionMap.put(FCVColumns.OUTLET_BRAND_ID, FCVColumns.OUTLET_BRAND_ID);
        sOutletLocationRegisterProjectionMap.put(FCVColumns.IFT_DISPLAY_LOCATION_ID, FCVColumns.IFT_DISPLAY_LOCATION_ID);
        
        sOutletLastestBonusProjectionMap = new HashMap<String, String>();
        sOutletLastestBonusProjectionMap.put(FCVColumns._ID, FCVColumns._ID);
        sOutletLastestBonusProjectionMap.put(FCVColumns.OUTLET_ID, FCVColumns.OUTLET_ID);
        sOutletLastestBonusProjectionMap.put(FCVColumns.OUTLET_BRAND_ID, FCVColumns.OUTLET_BRAND_ID);
        sOutletLastestBonusProjectionMap.put(FCVColumns.OUTLET_BRAND, FCVColumns.OUTLET_BRAND);
        sOutletLastestBonusProjectionMap.put(FCVColumns.OUTLET_BRAND_CODE, FCVColumns.OUTLET_BRAND_CODE);
        sOutletLastestBonusProjectionMap.put(FCVColumns.OUTLET_BRAND_GROUP, FCVColumns.OUTLET_BRAND_GROUP);
        sOutletLastestBonusProjectionMap.put(FCVColumns.LASTEST_BONUS_DATE, FCVColumns.LASTEST_BONUS_DATE);
        
        sDBBPosmRegisteredProjectionMap = new HashMap<String, String>();
        sDBBPosmRegisteredProjectionMap.put(FCVColumns._ID, FCVColumns._ID);
        sDBBPosmRegisteredProjectionMap.put(FCVColumns.OUTLET_ID, FCVColumns.OUTLET_ID);
        sDBBPosmRegisteredProjectionMap.put(FCVColumns.OUTLET_BRAND_ID, FCVColumns.OUTLET_BRAND_ID);
        sDBBPosmRegisteredProjectionMap.put(FCVColumns.OUTLET_POSM_ID, FCVColumns.OUTLET_POSM_ID);        
        
        //table check
        sPowerSKUCheckProjectionMap = new HashMap<String, String>();
        sPowerSKUCheckProjectionMap.put(FCVColumns._ID, FCVColumns._ID);
        sPowerSKUCheckProjectionMap.put(FCVColumns.OUTLET_ID, FCVColumns.OUTLET_ID);
        sPowerSKUCheckProjectionMap.put(FCVColumns.POWER_SKU_ID, FCVColumns.POWER_SKU_ID);
        sPowerSKUCheckProjectionMap.put(FCVColumns.HAS, FCVColumns.HAS);
        
        sFullRangeSKUCheckProjectionMap = new HashMap<String, String>();
        sFullRangeSKUCheckProjectionMap.put(FCVColumns._ID, FCVColumns._ID);
        sFullRangeSKUCheckProjectionMap.put(FCVColumns.OUTLET_ID, FCVColumns.OUTLET_ID);
        sFullRangeSKUCheckProjectionMap.put(FCVColumns.FULL_RANGE_SKU_ID, FCVColumns.FULL_RANGE_SKU_ID);
        sFullRangeSKUCheckProjectionMap.put(FCVColumns.HAS, FCVColumns.HAS);
        sFullRangeSKUCheckProjectionMap.put(FCVColumns.OUTLET_BRAND_ID, FCVColumns.OUTLET_BRAND_ID);
        
        sDBBPosmFullRangeSKUCheckProjectionMap = new HashMap<String, String>();
        sDBBPosmFullRangeSKUCheckProjectionMap.put(FCVColumns._ID, FCVColumns._ID);
        sDBBPosmFullRangeSKUCheckProjectionMap.put(FCVColumns.OUTLET_ID, FCVColumns.OUTLET_ID);
        sDBBPosmFullRangeSKUCheckProjectionMap.put(FCVColumns.FULL_RANGE_SKU_ID, FCVColumns.FULL_RANGE_SKU_ID);
        sDBBPosmFullRangeSKUCheckProjectionMap.put(FCVColumns.FULLRANGE_NAME, FCVColumns.FULLRANGE_NAME); 
        sDBBPosmFullRangeSKUCheckProjectionMap.put(FCVColumns.OUTLET_BRAND_ID, FCVColumns.OUTLET_BRAND_ID); 
        sDBBPosmFullRangeSKUCheckProjectionMap.put(FCVColumns.HAS, FCVColumns.HAS);
        sDBBPosmFullRangeSKUCheckProjectionMap.put(FCVColumns.OUTLET_POSM_ID, FCVColumns.OUTLET_POSM_ID); 
        sDBBPosmFullRangeSKUCheckProjectionMap.put(FCVColumns.POSM_NAME, FCVColumns.POSM_NAME); 
        
        sIFTDisplayLocationCheckProjectionMap = new HashMap<String, String>();
        sIFTDisplayLocationCheckProjectionMap.put(FCVColumns._ID, FCVColumns._ID);
        sIFTDisplayLocationCheckProjectionMap.put(FCVColumns.OUTLET_ID, FCVColumns.OUTLET_ID);
        sIFTDisplayLocationCheckProjectionMap.put(FCVColumns.IFT_DISPLAY_LOCATION_ID, FCVColumns.IFT_DISPLAY_LOCATION_ID);
        sIFTDisplayLocationCheckProjectionMap.put(FCVColumns.OUTLET_BRAND_ID, FCVColumns.OUTLET_BRAND_ID);
        sIFTDisplayLocationCheckProjectionMap.put(FCVColumns.HAS, FCVColumns.HAS);
        
        sOutletPOSMCheckProjectionMap = new HashMap<String, String>();
        sOutletPOSMCheckProjectionMap.put(FCVColumns._ID, FCVColumns._ID);
        sOutletPOSMCheckProjectionMap.put(FCVColumns.OUTLET_ID, FCVColumns.OUTLET_ID);
        sOutletPOSMCheckProjectionMap.put(FCVColumns.OUTLET_POSM_ID, FCVColumns.OUTLET_POSM_ID);
        sOutletPOSMCheckProjectionMap.put(FCVColumns.HAS, FCVColumns.HAS);
        sOutletPOSMCheckProjectionMap.put(FCVColumns.STATUSPOSM, FCVColumns.STATUSPOSM);
        
        sPromotionCheckProjectionMap = new HashMap<String, String>();
        sPromotionCheckProjectionMap.put(FCVColumns._ID, FCVColumns._ID);
        sPromotionCheckProjectionMap.put(FCVColumns.OUTLET_ID, FCVColumns.OUTLET_ID);
        sPromotionCheckProjectionMap.put(FCVColumns.PROMOTION_ID, FCVColumns.PROMOTION_ID);
        sPromotionCheckProjectionMap.put(FCVColumns.PRODUCT_ID, FCVColumns.PRODUCT_ID);
        sPromotionCheckProjectionMap.put(FCVColumns.UNIT_ID, FCVColumns.UNIT_ID);
        sPromotionCheckProjectionMap.put(FCVColumns.GET_QUANTITY, FCVColumns.GET_QUANTITY);
        sPromotionCheckProjectionMap.put(FCVColumns.KNOWN, FCVColumns.KNOWN);
        sPromotionCheckProjectionMap.put(FCVColumns.CORRECT, FCVColumns.CORRECT);
        
        sOutletBrandCheckProjectionMap = new HashMap<String, String>();
        sOutletBrandCheckProjectionMap.put(FCVColumns._ID, FCVColumns._ID);
        sOutletBrandCheckProjectionMap.put(FCVColumns.OUTLET_ID, FCVColumns.OUTLET_ID);
        sOutletBrandCheckProjectionMap.put(FCVColumns.OUTLET_BRAND_ID, FCVColumns.OUTLET_BRAND_ID);
        sOutletBrandCheckProjectionMap.put(FCVColumns.FACING, FCVColumns.FACING);
        
        sFullrangeFacingProjectionMap = new HashMap<String, String>();
        sFullrangeFacingProjectionMap.put(FCVColumns._ID, FCVColumns._ID);
        sFullrangeFacingProjectionMap.put(FCVColumns.OUTLET_ID, FCVColumns.OUTLET_ID);
        sFullrangeFacingProjectionMap.put(FCVColumns.OUTLET_BRAND_ID, FCVColumns.OUTLET_BRAND_ID);
        sFullrangeFacingProjectionMap.put(FCVColumns.FACING, FCVColumns.FACING);
        
        sPosmMiniValueProjectionMap = new HashMap<String, String>();
        sPosmMiniValueProjectionMap.put(FCVColumns._ID, FCVColumns._ID);
        sPosmMiniValueProjectionMap.put(FCVColumns.OUTLET_ID, FCVColumns.OUTLET_ID);
        sPosmMiniValueProjectionMap.put(FCVColumns.OUTLET_POSM_ID, FCVColumns.OUTLET_POSM_ID);
        sPosmMiniValueProjectionMap.put(FCVColumns.NAME, FCVColumns.NAME);
        sPosmMiniValueProjectionMap.put(FCVColumns.VALUE, FCVColumns.VALUE);
    }
}
