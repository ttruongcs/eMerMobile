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
 * Created by Ban Vien Ltd.
 * User: Vien Nguyen (vien.nguyen@banvien.com)
 * Date: 6/22/12
 * Time: 5:28 PM
 */
public class StoreContentProvider extends ContentProvider {

    private static HashMap<String, String> sStoreProjectionMap;
    private static HashMap<String, String> sStoreProdProjectionMap;
    private static HashMap<String, String> sStoreBrandLocProjectionMap;
    private static HashMap<String, String> sStorePOSMProjectionMap;
    private static HashMap<String, String> sStoreSOSBrandProjectionMap;
    private static HashMap<String, String> sStoreSOSPackingProjectionMap;
    private static HashMap<String, String> sStoreBrandGroupProjectionMap;

    private static HashMap<String, String> sSARRegProdProjectionMap;
    private static HashMap<String, String> sSARBrandLocProjectionMap;
    private static HashMap<String, String> sSARPOSMProjectionMap;
    private static HashMap<String, String> sSARSOSProjectionMap;
    private static HashMap<String, String> sSARSODProjectionMap;
    private static HashMap<String, String> sSARPictureProjectionMap;
    private static HashMap<String, String> sStoreAuditStatusProjectionMap;
    private static HashMap<String, String> sAccountProjectionMap;

    private static HashMap<String, String> sProductProjectionMap;
    private static HashMap<String, String> sPromotionProjectionMap;
    private static HashMap<String, String> sAccountPromotionsProjectionMap;
    private static HashMap<String, String> sPromotionProductToHandHeldProjectionMap;
    private static HashMap<String, String> sSARPromotionsProjectionMap;
    private static HashMap<String, String> sUnitProjectionMap;

    private FCVDatabaseHelper fcvDatabaseHelper;

    private static final UriMatcher sUriMatcher;

    private static final int TBL_STORE = 1;
    private static final int TBL_STORE_ID = 2;

    private static final int TBL_STORE_PROD = 3;
    private static final int TBL_STORE_PROD_ID = 4;

    private static final int TBL_STORE_BRAND_LOC = 5;
    private static final int TBL_STORE_BRAND_LOC_ID = 6;

    private static final int TBL_STORE_POSM = 7;
    private static final int TBL_STORE_POSM_ID = 8;

    private static final int TBL_STORE_SOS_BRAND = 9;
    private static final int TBL_STORE_SOS_BRAND_ID = 10;

    private static final int TBL_STORE_SOS_PACKING = 11;
    private static final int TBL_STORE_SOS_PACKING_ID = 12;

    private static final int TBL_SAR_REG_PROD = 13;
    private static final int TBL_SAR_REG_PROD_ID = 14;
    private static final int TBL_SAR_BRAND_LOC = 15;
    private static final int TBL_SAR_BRAND_LOC_ID = 16;
    private static final int TBL_SAR_POSM = 17;
    private static final int TBL_SAR_POSM_ID = 18;
    private static final int TBL_SAR_SOS = 19;
    private static final int TBL_SAR_SOS_ID = 20;
    private static final int TBL_SAR_SOD = 21;
    private static final int TBL_SAR_SOD_ID = 22;

    private static final int TBL_STORE_BRAND_GROUP = 23;
    private static final int TBL_STORE_BRAND_GROUP_ID = 24;

    private static final int TBL_SAR_PICTURE = 25;
    private static final int TBL_SAR_PICTURE_ID = 26;

    private static final int TBL_STORE_AUDIT_STATUS = 27;
    private static final int TBL_STORE_AUDIT_STATUS_ID = 28;
    
    private static final int TBL_ACCOUNT = 29;
    private static final int TBL_ACCOUNT_ID = 30;

    private static final int PROMOTION = 31;
    private static final int PROMOTION_ID = 32;
    
    private static final int ACCOUNT_PROMOTIONS = 33;
    
    private static final int PROMOTION_PRODUCT_TO_HANDHELD = 34;
    
    private static final int SAR_PROMOTION = 35;
    private static final int SAR_PROMOTION_ID = 36;
    
    private static final int PRODUCT = 37;
    private static final int PRODUCT_ID = 38;
    
    private static final int UNIT = 39;
    private static final int UNIT_ID = 40;
    
    @Override
    public boolean onCreate() {
        fcvDatabaseHelper = FCVDatabaseHelper.getDBHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
            String sortOrder) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        switch (sUriMatcher.match(uri)) {
        case TBL_STORE:
        	qb.setTables(Constants.S_TBL_STORE);
            qb.setProjectionMap(sStoreProjectionMap);
            break;

        case TBL_STORE_ID:
        	qb.setTables(Constants.S_TBL_STORE);
            qb.setProjectionMap(sStoreProjectionMap);
            qb.appendWhere(StoreColumns._ID + "=" + uri.getPathSegments().get(1));
            break;
        case TBL_STORE_PROD:
        	qb.setTables(Constants.S_TBL_STORE_PRODUCT);
            qb.setProjectionMap(sStoreProdProjectionMap);
            break;

        case TBL_STORE_PROD_ID:
        	qb.setTables(Constants.S_TBL_STORE_PRODUCT);
            qb.setProjectionMap(sStoreProdProjectionMap);
            qb.appendWhere(StoreColumns._ID + "=" + uri.getPathSegments().get(1));
            break;

        case TBL_STORE_BRAND_LOC:
        	qb.setTables(Constants.S_TBL_STORE_BRAND_LOC);
            qb.setProjectionMap(sStoreBrandLocProjectionMap);
            break;

        case TBL_STORE_BRAND_LOC_ID:
        	qb.setTables(Constants.S_TBL_STORE_BRAND_LOC);
            qb.setProjectionMap(sStoreBrandLocProjectionMap);
            qb.appendWhere(StoreColumns._ID + "=" + uri.getPathSegments().get(1));
            break;

        case TBL_STORE_POSM:
        	qb.setTables(Constants.S_TBL_STORE_POSM);
            qb.setProjectionMap(sStorePOSMProjectionMap);
            break;

        case TBL_STORE_POSM_ID:
        	qb.setTables(Constants.S_TBL_STORE_POSM);
            qb.setProjectionMap(sStorePOSMProjectionMap);
            qb.appendWhere(StoreColumns._ID + "=" + uri.getPathSegments().get(1));
            break;

        case TBL_STORE_SOS_BRAND:
        	qb.setTables(Constants.S_TBL_STORE_SOS_BRAND);
            qb.setProjectionMap(sStoreSOSBrandProjectionMap);
            break;

        case TBL_STORE_SOS_BRAND_ID:
        	qb.setTables(Constants.S_TBL_STORE_SOS_BRAND);
            qb.setProjectionMap(sStoreSOSBrandProjectionMap);
            qb.appendWhere(StoreColumns._ID + "=" + uri.getPathSegments().get(1));
            break;

        case TBL_STORE_SOS_PACKING:
        	qb.setTables(Constants.S_TBL_STORE_SOS_PACKING);
            qb.setProjectionMap(sStoreSOSPackingProjectionMap);
            break;

        case TBL_STORE_SOS_PACKING_ID:
        	qb.setTables(Constants.S_TBL_STORE_SOS_PACKING);
            qb.setProjectionMap(sStoreSOSPackingProjectionMap);
            qb.appendWhere(StoreColumns._ID + "=" + uri.getPathSegments().get(1));
            break;

        case TBL_STORE_BRAND_GROUP:
        	qb.setTables(Constants.S_TBL_STORE_BRAND_GROUP);
            qb.setProjectionMap(sStoreBrandGroupProjectionMap);
            break;

        case TBL_STORE_BRAND_GROUP_ID:
        	qb.setTables(Constants.S_TBL_STORE_BRAND_GROUP);
            qb.setProjectionMap(sStoreBrandGroupProjectionMap);
            qb.appendWhere(StoreColumns._ID + "=" + uri.getPathSegments().get(1));
            break;

        case TBL_SAR_REG_PROD:
        	qb.setTables(Constants.S_TBL_SAR_REG_PROD);
            qb.setProjectionMap(sSARRegProdProjectionMap);
            break;

        case TBL_SAR_REG_PROD_ID:
        	qb.setTables(Constants.S_TBL_SAR_REG_PROD);
            qb.setProjectionMap(sSARRegProdProjectionMap);
            qb.appendWhere(StoreColumns._ID + "=" + uri.getPathSegments().get(1));
            break;

        case TBL_SAR_POSM:
        	qb.setTables(Constants.S_TBL_SAR_POSM);
            qb.setProjectionMap(sSARPOSMProjectionMap);
            break;

        case TBL_SAR_POSM_ID:
        	qb.setTables(Constants.S_TBL_SAR_POSM);
            qb.setProjectionMap(sSARPOSMProjectionMap);
            qb.appendWhere(StoreColumns._ID + "=" + uri.getPathSegments().get(1));
            break;

        case TBL_SAR_BRAND_LOC:
        	qb.setTables(Constants.S_TBL_SAR_BRAND_LOC);
            qb.setProjectionMap(sSARBrandLocProjectionMap);
            break;

        case TBL_SAR_BRAND_LOC_ID:
        	qb.setTables(Constants.S_TBL_SAR_BRAND_LOC);
            qb.setProjectionMap(sSARBrandLocProjectionMap);
            qb.appendWhere(StoreColumns._ID + "=" + uri.getPathSegments().get(1));
            break;

        case TBL_SAR_SOS:
        	qb.setTables(Constants.S_TBL_SAR_SOS_BRAND);
            qb.setProjectionMap(sSARSOSProjectionMap);
            break;

        case TBL_SAR_SOS_ID:
        	qb.setTables(Constants.S_TBL_SAR_SOS_BRAND);
            qb.setProjectionMap(sSARSOSProjectionMap);
            qb.appendWhere(StoreColumns._ID + "=" + uri.getPathSegments().get(1));
            break;

        case TBL_SAR_SOD:
        	qb.setTables(Constants.S_TBL_SAR_SOD);
            qb.setProjectionMap(sSARSODProjectionMap);
            break;

        case TBL_SAR_SOD_ID:
        	qb.setTables(Constants.S_TBL_SAR_SOD);
            qb.setProjectionMap(sSARSODProjectionMap);
            qb.appendWhere(StoreColumns._ID + "=" + uri.getPathSegments().get(1));
            break;

        case TBL_SAR_PICTURE:
        	qb.setTables(Constants.S_TBL_SAR_PICTURE);
            qb.setProjectionMap(sSARPictureProjectionMap);
            break;

        case TBL_SAR_PICTURE_ID:
        	qb.setTables(Constants.S_TBL_SAR_PICTURE);
            qb.setProjectionMap(sSARPictureProjectionMap);
            qb.appendWhere(StoreColumns._ID + "=" + uri.getPathSegments().get(1));
            break;

        case TBL_STORE_AUDIT_STATUS:
        	qb.setTables(Constants.S_TBL_STORE_AUDIT_STATUS);
            qb.setProjectionMap(sStoreAuditStatusProjectionMap);
            break;

        case TBL_STORE_AUDIT_STATUS_ID:
        	qb.setTables(Constants.S_TBL_STORE_AUDIT_STATUS);
            qb.setProjectionMap(sStoreAuditStatusProjectionMap);
            qb.appendWhere(StoreColumns._ID + "=" + uri.getPathSegments().get(1));
            break;
            
        case TBL_ACCOUNT:
        	qb.setTables(Constants.S_TBL_ACCOUNT);
            qb.setProjectionMap(sAccountProjectionMap);
            break;

        case TBL_ACCOUNT_ID:
        	qb.setTables(Constants.S_TBL_ACCOUNT);
            qb.setProjectionMap(sAccountProjectionMap);
            qb.appendWhere(StoreColumns._ID + "=" + uri.getPathSegments().get(1));
            break;
            
        case PRODUCT:
        	qb.setTables(Constants.S_TBL_STORE_PROMOTION_PRODUCT);
            qb.setProjectionMap(sProductProjectionMap);
            break;

        case PRODUCT_ID:
        	qb.setTables(Constants.S_TBL_STORE_PROMOTION_PRODUCT);
            qb.setProjectionMap(sProductProjectionMap);
            qb.appendWhere(StoreColumns._ID + "=" + uri.getPathSegments().get(1));
            break;
            
        case PROMOTION:
        	qb.setTables(Constants.S_TBL_STORE_PROMOTION);
            qb.setProjectionMap(sPromotionProjectionMap);
            break;
            
        case PROMOTION_ID:
        	qb.setTables(Constants.S_TBL_STORE_PROMOTION);
            qb.setProjectionMap(sPromotionProjectionMap);
            qb.appendWhere(StoreColumns._ID + "=" + uri.getPathSegments().get(1));
            break;
            
        case ACCOUNT_PROMOTIONS:
        	qb.setTables(Constants.S_TBL_ACCOUNT_PROMOTION);
            qb.setProjectionMap(sAccountPromotionsProjectionMap);
            break;
            
        case PROMOTION_PRODUCT_TO_HANDHELD:
        	qb.setTables(Constants.S_TBL_STORE_PROMOTION_PRODUCT_TOHANDHELD);
            qb.setProjectionMap(sPromotionProductToHandHeldProjectionMap);
            break;
            
        case SAR_PROMOTION:
        	qb.setTables(Constants.S_TBL_SAR_STORE_PROMOTION);
            qb.setProjectionMap(sSARPromotionsProjectionMap);
            break;
            
        case SAR_PROMOTION_ID:
        	qb.setTables(Constants.S_TBL_SAR_STORE_PROMOTION);
            qb.setProjectionMap(sSARPromotionsProjectionMap);
            qb.appendWhere(StoreColumns._ID + "=" + uri.getPathSegments().get(1));
            break;
            
        case UNIT:
        	qb.setTables(Constants.S_TBL_UNIT);
            qb.setProjectionMap(sUnitProjectionMap);
            break;
            
        default:
            throw new IllegalArgumentException("Unknown URI " + uri);
        }

        // If no sort order is specified use the default
        String orderBy;
        if (TextUtils.isEmpty(sortOrder)) {
            orderBy = StoreColumns.DEFAULT_SORT;
        } else {
            orderBy = sortOrder;
        }

        // Get the database and run the query
        SQLiteDatabase db = fcvDatabaseHelper.getReadableDatabase();
        Cursor c = qb.query(db, projection, selection, selectionArgs, null, null, orderBy);

        // Tell the cursor what uri to watch, so it knows when its source data changes
        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;
    }

    @Override
    public String getType(Uri uri) {
        switch (sUriMatcher.match(uri)) {
        case TBL_STORE:
        	break;
        case TBL_STORE_ID:
        	break;
        default:
            throw new IllegalArgumentException("Unknown URI " + uri);
        }
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues initialValues) {
        ContentValues values;
    	SQLiteDatabase db = fcvDatabaseHelper.getWritableDatabase();
        if (initialValues != null) {
            values = new ContentValues(initialValues);
        } else {
            values = new ContentValues();
        }
        if (sUriMatcher.match(uri) == TBL_STORE) {
        	long rowId = db.insert(Constants.S_TBL_STORE, null, values);
            if (rowId > 0) {
                Uri storeUri = ContentUris.withAppendedId(StoreColumns.STORE_CONTENT_URI, rowId);
                getContext().getContentResolver().notifyChange(storeUri, null);
                return storeUri;
            }
            throw new SQLException("Failed to insert row into " + uri);
        }else if (sUriMatcher.match(uri) == TBL_STORE_BRAND_LOC) {
        	long rowId = db.insert(Constants.S_TBL_STORE_BRAND_LOC, null, values);
            if (rowId > 0) {
                Uri storeUri = ContentUris.withAppendedId(StoreColumns.STORE_BRAND_LOC_CONTENT_URI, rowId);
                getContext().getContentResolver().notifyChange(storeUri, null);
                return storeUri;
            }
            throw new SQLException("Failed to insert row into " + uri);
        }else if (sUriMatcher.match(uri) == TBL_STORE_POSM) {
        	long rowId = db.insert(Constants.S_TBL_STORE_POSM, null, values);
            if (rowId > 0) {
                Uri storeUri = ContentUris.withAppendedId(StoreColumns.STORE_POSM_CONTENT_URI, rowId);
                getContext().getContentResolver().notifyChange(storeUri, null);
                return storeUri;
            }
            throw new SQLException("Failed to insert row into " + uri);
        }else if (sUriMatcher.match(uri) == TBL_STORE_PROD) {
        	long rowId = db.insert(Constants.S_TBL_STORE_PRODUCT, null, values);
            if (rowId > 0) {
                Uri storeUri = ContentUris.withAppendedId(StoreColumns.STORE_PRODUCT_CONTENT_URI, rowId);
                getContext().getContentResolver().notifyChange(storeUri, null);
                return storeUri;
            }
            throw new SQLException("Failed to insert row into " + uri);
        }else if (sUriMatcher.match(uri) == TBL_STORE_SOS_BRAND) {
        	long rowId = db.insert(Constants.S_TBL_STORE_SOS_BRAND, null, values);
            if (rowId > 0) {
                Uri storeUri = ContentUris.withAppendedId(StoreColumns.STORE_SOS_BRAND_CONTENT_URI, rowId);
                getContext().getContentResolver().notifyChange(storeUri, null);
                return storeUri;
            }
            throw new SQLException("Failed to insert row into " + uri);
        }else if (sUriMatcher.match(uri) == TBL_STORE_SOS_PACKING) {
        	long rowId = db.insert(Constants.S_TBL_STORE_SOS_PACKING, null, values);
            if (rowId > 0) {
                Uri storeUri = ContentUris.withAppendedId(StoreColumns.STORE_SOS_PACKING_CONTENT_URI, rowId);
                getContext().getContentResolver().notifyChange(storeUri, null);
                return storeUri;
            }
            throw new SQLException("Failed to insert row into " + uri);
        }else if (sUriMatcher.match(uri) == TBL_STORE_BRAND_GROUP) {
        	long rowId = db.insert(Constants.S_TBL_STORE_BRAND_GROUP, null, values);
            if (rowId > 0) {
                Uri storeUri = ContentUris.withAppendedId(StoreColumns.STORE_BRAND_GROUP_CONTENT_URI, rowId);
                getContext().getContentResolver().notifyChange(storeUri, null);
                return storeUri;
            }
            throw new SQLException("Failed to insert row into " + uri);
        }else if (sUriMatcher.match(uri) == TBL_SAR_REG_PROD) {
        	long rowId = db.insert(Constants.S_TBL_SAR_REG_PROD, null, values);
            if (rowId > 0) {
                Uri storeUri = ContentUris.withAppendedId(StoreColumns.SAR_REG_PROD_CONTENT_URI, rowId);
                getContext().getContentResolver().notifyChange(storeUri, null);
                return storeUri;
            }
            throw new SQLException("Failed to insert row into " + uri);
        }else if (sUriMatcher.match(uri) == TBL_SAR_BRAND_LOC) {
        	long rowId = db.insert(Constants.S_TBL_SAR_BRAND_LOC, null, values);
            if (rowId > 0) {
                Uri storeUri = ContentUris.withAppendedId(StoreColumns.SAR_BRAND_LOC_CONTENT_URI, rowId);
                getContext().getContentResolver().notifyChange(storeUri, null);
                return storeUri;
            }
            throw new SQLException("Failed to insert row into " + uri);
        }else if (sUriMatcher.match(uri) == TBL_SAR_POSM) {
        	long rowId = db.insert(Constants.S_TBL_SAR_POSM, null, values);
            if (rowId > 0) {
                Uri storeUri = ContentUris.withAppendedId(StoreColumns.SAR_POSM_CONTENT_URI, rowId);
                getContext().getContentResolver().notifyChange(storeUri, null);
                return storeUri;
            }
            throw new SQLException("Failed to insert row into " + uri);
        }else if (sUriMatcher.match(uri) == TBL_SAR_SOS) {
        	long rowId = db.insert(Constants.S_TBL_SAR_SOS_BRAND, null, values);
            if (rowId > 0) {
                Uri storeUri = ContentUris.withAppendedId(StoreColumns.SAR_SOS_BRAND_CONTENT_URI, rowId);
                getContext().getContentResolver().notifyChange(storeUri, null);
                return storeUri;
            }
            throw new SQLException("Failed to insert row into " + uri);
        }else if (sUriMatcher.match(uri) == TBL_SAR_SOD) {
        	long rowId = db.insert(Constants.S_TBL_SAR_SOD, null, values);
            if (rowId > 0) {
                Uri storeUri = ContentUris.withAppendedId(StoreColumns.SAR_SOD_CONTENT_URI, rowId);
                getContext().getContentResolver().notifyChange(storeUri, null);
                return storeUri;
            }
        }else if (sUriMatcher.match(uri) == TBL_SAR_PICTURE) {
        	long rowId = db.insert(Constants.S_TBL_SAR_PICTURE, null, values);
            if (rowId > 0) {
                Uri storeUri = ContentUris.withAppendedId(StoreColumns.SAR_PICTURE_CONTENT_URI, rowId);
                getContext().getContentResolver().notifyChange(storeUri, null);
                return storeUri;
            }
        }else if (sUriMatcher.match(uri) == TBL_STORE_AUDIT_STATUS) {
        	long rowId = db.insert(Constants.S_TBL_STORE_AUDIT_STATUS, null, values);
            if (rowId > 0) {
                Uri storeUri = ContentUris.withAppendedId(StoreColumns.STORE_AUDIT_STATUS_CONTENT_URI, rowId);
                getContext().getContentResolver().notifyChange(storeUri, null);
                return storeUri;
            }
        }else if (sUriMatcher.match(uri) == TBL_ACCOUNT) {
        	long rowId = db.insert(Constants.S_TBL_ACCOUNT, null, values);
            if (rowId > 0) {
                Uri storeUri = ContentUris.withAppendedId(StoreColumns.ACCOUNT_CONTENT_URI, rowId);
                getContext().getContentResolver().notifyChange(storeUri, null);
                return storeUri;
            }
        }else if (sUriMatcher.match(uri) == PRODUCT) {
        	long rowId = db.insert(Constants.S_TBL_STORE_PROMOTION_PRODUCT, null, values);
            if (rowId > 0) {
                Uri storeUri = ContentUris.withAppendedId(StoreColumns.STORE_PROMOTION_PRODUCT_CONTENT_URI, rowId);
                getContext().getContentResolver().notifyChange(storeUri, null);
                return storeUri;
            }
        }else if (sUriMatcher.match(uri) == PROMOTION) {
        	long rowId = db.insert(Constants.S_TBL_STORE_PROMOTION, null, values);
            if (rowId > 0) {
                Uri storeUri = ContentUris.withAppendedId(StoreColumns.STORE_PROMOTION_CONTENT_URI, rowId);
                getContext().getContentResolver().notifyChange(storeUri, null);
                return storeUri;
            }
        }else if (sUriMatcher.match(uri) == ACCOUNT_PROMOTIONS) {
        	long rowId = db.insert(Constants.S_TBL_ACCOUNT_PROMOTION, null, values);
            if (rowId > 0) {
                Uri storeUri = ContentUris.withAppendedId(StoreColumns.ACCOUNT_PROMOTION_CONTENT_URI, rowId);
                getContext().getContentResolver().notifyChange(storeUri, null);
                return storeUri;
            }
        }else if (sUriMatcher.match(uri) == PROMOTION_PRODUCT_TO_HANDHELD) {
        	long rowId = db.insert(Constants.S_TBL_STORE_PROMOTION_PRODUCT_TOHANDHELD, null, values);
            if (rowId > 0) {
                Uri storeUri = ContentUris.withAppendedId(StoreColumns.PROMOTION_PRODUCT_TOHANDHELD_CONTENT_URI, rowId);
                getContext().getContentResolver().notifyChange(storeUri, null);
                return storeUri;
            }
        }else if (sUriMatcher.match(uri) == SAR_PROMOTION) {
        	long rowId = db.insert(Constants.S_TBL_SAR_STORE_PROMOTION, null, values);
            if (rowId > 0) {
                Uri storeUri = ContentUris.withAppendedId(StoreColumns.SAR_PROMOTION_CONTENT_URI, rowId);
                getContext().getContentResolver().notifyChange(storeUri, null);
                return storeUri;
            }
        }else if (sUriMatcher.match(uri) == UNIT) {
        	long rowId = db.insert(Constants.S_TBL_UNIT, null, values);
            if (rowId > 0) {
                Uri storeUri = ContentUris.withAppendedId(StoreColumns.UNIT_CONTENT_URI, rowId);
                getContext().getContentResolver().notifyChange(storeUri, null);
                return storeUri;
            }
        }else {
        	throw new IllegalArgumentException("Unknown URI " + uri);
        }
        throw new SQLException("Failed to insert row into " + uri);
    }

    @Override
    public int delete(Uri uri, String where, String[] whereArgs) {
        SQLiteDatabase db = fcvDatabaseHelper.getWritableDatabase();
        int count;
        String rowId;
        switch (sUriMatcher.match(uri)) {
        case TBL_STORE:
            count = db.delete(Constants.S_TBL_STORE, where, whereArgs);
            break;

        case TBL_STORE_ID:
            rowId = uri.getPathSegments().get(1);
            count = db.delete(Constants.S_TBL_STORE, StoreColumns._ID + "=" + rowId
                    + (!TextUtils.isEmpty(where) ? " AND (" + where + ')' : ""), whereArgs);
            break;

        case TBL_STORE_PROD:
        	count = db.delete(Constants.S_TBL_STORE_PRODUCT, where, whereArgs);
            break;

        case TBL_STORE_PROD_ID:
        	rowId = uri.getPathSegments().get(1);
            count = db.delete(Constants.S_TBL_STORE_PRODUCT, StoreColumns._ID + "=" + rowId
                    + (!TextUtils.isEmpty(where) ? " AND (" + where + ')' : ""), whereArgs);
            break;

        case TBL_STORE_POSM:
        	count = db.delete(Constants.S_TBL_STORE_POSM, where, whereArgs);
            break;

        case TBL_STORE_POSM_ID:
        	rowId = uri.getPathSegments().get(1);
            count = db.delete(Constants.S_TBL_STORE_POSM, StoreColumns._ID + "=" + rowId
                    + (!TextUtils.isEmpty(where) ? " AND (" + where + ')' : ""), whereArgs);
            break;

        case TBL_STORE_BRAND_LOC:
        	count = db.delete(Constants.S_TBL_STORE_BRAND_LOC, where, whereArgs);
            break;

        case TBL_STORE_BRAND_LOC_ID:
        	rowId = uri.getPathSegments().get(1);
            count = db.delete(Constants.S_TBL_STORE_BRAND_LOC, StoreColumns._ID + "=" + rowId
                    + (!TextUtils.isEmpty(where) ? " AND (" + where + ')' : ""), whereArgs);
            break;

        case TBL_STORE_SOS_PACKING:
        	count = db.delete(Constants.S_TBL_STORE_SOS_PACKING, where, whereArgs);
            break;

        case TBL_STORE_SOS_PACKING_ID:
        	rowId = uri.getPathSegments().get(1);
            count = db.delete(Constants.S_TBL_STORE_SOS_PACKING, StoreColumns._ID + "=" + rowId
                    + (!TextUtils.isEmpty(where) ? " AND (" + where + ')' : ""), whereArgs);
            break;

        case TBL_STORE_BRAND_GROUP:
        	count = db.delete(Constants.S_TBL_STORE_BRAND_GROUP, where, whereArgs);
            break;

        case TBL_STORE_BRAND_GROUP_ID:
        	rowId = uri.getPathSegments().get(1);
            count = db.delete(Constants.S_TBL_STORE_BRAND_GROUP, StoreColumns._ID + "=" + rowId
                    + (!TextUtils.isEmpty(where) ? " AND (" + where + ')' : ""), whereArgs);
            break;

        case TBL_STORE_SOS_BRAND:
        	count = db.delete(Constants.S_TBL_STORE_SOS_BRAND, where, whereArgs);
            break;

        case TBL_STORE_SOS_BRAND_ID:
        	rowId = uri.getPathSegments().get(1);
            count = db.delete(Constants.S_TBL_STORE_SOS_BRAND, StoreColumns._ID + "=" + rowId
                    + (!TextUtils.isEmpty(where) ? " AND (" + where + ')' : ""), whereArgs);
            break;

        case TBL_SAR_REG_PROD:
        	count = db.delete(Constants.S_TBL_SAR_REG_PROD, where, whereArgs);
            break;

        case TBL_SAR_REG_PROD_ID:
        	rowId = uri.getPathSegments().get(1);
            count = db.delete(Constants.S_TBL_SAR_REG_PROD, StoreColumns._ID + "=" + rowId
                    + (!TextUtils.isEmpty(where) ? " AND (" + where + ')' : ""), whereArgs);
            break;

        case TBL_SAR_POSM:
        	count = db.delete(Constants.S_TBL_SAR_POSM, where, whereArgs);
            break;

        case TBL_SAR_POSM_ID:
            rowId = uri.getPathSegments().get(1);
        	count = db.delete(Constants.S_TBL_SAR_POSM, where, whereArgs);
            break;

        case TBL_SAR_BRAND_LOC:
        	count = db.delete(Constants.S_TBL_SAR_BRAND_LOC, where, whereArgs);
            break;

        case TBL_SAR_BRAND_LOC_ID:
            rowId = uri.getPathSegments().get(1);
        	count = db.delete(Constants.S_TBL_SAR_BRAND_LOC, StoreColumns._ID + "=" + rowId
                    + (!TextUtils.isEmpty(where) ? " AND (" + where + ')' : ""), whereArgs);
            break;

        case TBL_SAR_SOS:
        	count = db.delete(Constants.S_TBL_SAR_SOS_BRAND, where, whereArgs);
            break;

        case TBL_SAR_SOS_ID:
            rowId = uri.getPathSegments().get(1);
        	count = db.delete(Constants.S_TBL_SAR_SOS_BRAND, StoreColumns._ID + "=" + rowId
                    + (!TextUtils.isEmpty(where) ? " AND (" + where + ')' : ""), whereArgs);
            break;
        case TBL_SAR_SOD:
        	count = db.delete(Constants.S_TBL_SAR_SOD, where, whereArgs);
            break;

        case TBL_SAR_SOD_ID:
            rowId = uri.getPathSegments().get(1);
        	count = db.delete(Constants.S_TBL_SAR_SOD, StoreColumns._ID + "=" + rowId
                    + (!TextUtils.isEmpty(where) ? " AND (" + where + ')' : ""), whereArgs);
            break;
        case TBL_SAR_PICTURE:
        	count = db.delete(Constants.S_TBL_SAR_PICTURE, where, whereArgs);
            break;

        case TBL_SAR_PICTURE_ID:
            rowId = uri.getPathSegments().get(1);
        	count = db.delete(Constants.S_TBL_SAR_PICTURE, StoreColumns._ID + "=" + rowId
                    + (!TextUtils.isEmpty(where) ? " AND (" + where + ')' : ""), whereArgs);
            break;
        case TBL_STORE_AUDIT_STATUS:
        	count = db.delete(Constants.S_TBL_STORE_AUDIT_STATUS, where, whereArgs);
            break;

        case TBL_STORE_AUDIT_STATUS_ID:
            rowId = uri.getPathSegments().get(1);
        	count = db.delete(Constants.S_TBL_STORE_AUDIT_STATUS, StoreColumns._ID + "=" + rowId
                    + (!TextUtils.isEmpty(where) ? " AND (" + where + ')' : ""), whereArgs);
            break;
            
        case TBL_ACCOUNT:
        	count = db.delete(Constants.S_TBL_ACCOUNT, where, whereArgs);
            break;
        case PROMOTION:
        	count = db.delete(Constants.S_TBL_STORE_PROMOTION, where, whereArgs);
            break;
        case PRODUCT:
        	count = db.delete(Constants.S_TBL_STORE_PROMOTION_PRODUCT, where, whereArgs);
            break;
        case PROMOTION_PRODUCT_TO_HANDHELD:
        	count = db.delete(Constants.S_TBL_STORE_PROMOTION_PRODUCT_TOHANDHELD, where, whereArgs);
            break;
        case ACCOUNT_PROMOTIONS:
        	count = db.delete(Constants.S_TBL_ACCOUNT_PROMOTION, where, whereArgs);
            break;
        case SAR_PROMOTION:
        	count = db.delete(Constants.S_TBL_SAR_STORE_PROMOTION, where, whereArgs);
            break;
        case UNIT:
        	count = db.delete(Constants.S_TBL_UNIT, where, whereArgs);
            break;
        default:
            throw new IllegalArgumentException("Unknown URI " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public int update(Uri uri, ContentValues values, String where, String[] whereArgs) {
        SQLiteDatabase db = fcvDatabaseHelper.getWritableDatabase();
        int count;
        String rowId;
        switch (sUriMatcher.match(uri)) {
        case TBL_STORE:
            count = db.update(Constants.S_TBL_STORE, values, where, whereArgs);
            break;

        case TBL_STORE_ID:
            rowId = uri.getPathSegments().get(1);
            count = db.update(Constants.S_TBL_STORE, values, StoreColumns._ID + "=" + rowId
                    + (!TextUtils.isEmpty(where) ? " AND (" + where + ')' : ""), whereArgs);
            break;

        case TBL_STORE_PROD:
        	count = db.update(Constants.S_TBL_STORE_PRODUCT, values, where, whereArgs);
            break;

        case TBL_STORE_PROD_ID:
        	rowId = uri.getPathSegments().get(1);
            count = db.update(Constants.S_TBL_STORE_PRODUCT, values, StoreColumns._ID + "=" + rowId
                    + (!TextUtils.isEmpty(where) ? " AND (" + where + ')' : ""), whereArgs);
            break;

        case TBL_STORE_BRAND_LOC:
        	count = db.update(Constants.S_TBL_STORE_BRAND_LOC, values, where, whereArgs);
            break;

        case TBL_STORE_BRAND_LOC_ID:
        	rowId = uri.getPathSegments().get(1);
            count = db.update(Constants.S_TBL_STORE_BRAND_LOC, values, StoreColumns._ID + "=" + rowId
                    + (!TextUtils.isEmpty(where) ? " AND (" + where + ')' : ""), whereArgs);
            break;
        case TBL_STORE_SOS_BRAND:
        	count = db.update(Constants.S_TBL_STORE_SOS_BRAND, values, where, whereArgs);
            break;

        case TBL_STORE_SOS_BRAND_ID:
        	rowId = uri.getPathSegments().get(1);
            count = db.update(Constants.S_TBL_STORE_SOS_BRAND, values, StoreColumns._ID + "=" + rowId
                    + (!TextUtils.isEmpty(where) ? " AND (" + where + ')' : ""), whereArgs);
            break;
        case TBL_STORE_SOS_PACKING:
        	count = db.update(Constants.S_TBL_STORE_SOS_PACKING, values, where, whereArgs);
            break;

        case TBL_STORE_SOS_PACKING_ID:
        	rowId = uri.getPathSegments().get(1);
            count = db.update(Constants.S_TBL_STORE_SOS_PACKING, values, StoreColumns._ID + "=" + rowId
                    + (!TextUtils.isEmpty(where) ? " AND (" + where + ')' : ""), whereArgs);
            break;
        case TBL_STORE_BRAND_GROUP:
        	count = db.update(Constants.S_TBL_STORE_BRAND_GROUP, values, where, whereArgs);
            break;

        case TBL_STORE_BRAND_GROUP_ID:
        	rowId = uri.getPathSegments().get(1);
            count = db.update(Constants.S_TBL_STORE_BRAND_GROUP, values, StoreColumns._ID + "=" + rowId
                    + (!TextUtils.isEmpty(where) ? " AND (" + where + ')' : ""), whereArgs);
            break;
        case TBL_SAR_REG_PROD:
        	count = db.update(Constants.S_TBL_SAR_REG_PROD, values, where, whereArgs);
            break;

        case TBL_SAR_REG_PROD_ID:
        	rowId = uri.getPathSegments().get(1);
            count = db.update(Constants.S_TBL_SAR_REG_PROD, values, StoreColumns._ID + "=" + rowId
                    + (!TextUtils.isEmpty(where) ? " AND (" + where + ')' : ""), whereArgs);
            break;
        case TBL_SAR_POSM:
        	count = db.update(Constants.S_TBL_SAR_POSM, values, where, whereArgs);
            break;

        case TBL_SAR_POSM_ID:
        	rowId = uri.getPathSegments().get(1);
            count = db.update(Constants.S_TBL_SAR_POSM, values, StoreColumns._ID + "=" + rowId
                    + (!TextUtils.isEmpty(where) ? " AND (" + where + ')' : ""), whereArgs);
            break;
        case TBL_SAR_BRAND_LOC:
        	count = db.update(Constants.S_TBL_SAR_BRAND_LOC, values, where, whereArgs);
            break;

        case TBL_SAR_BRAND_LOC_ID:
        	rowId = uri.getPathSegments().get(1);
            count = db.update(Constants.S_TBL_SAR_BRAND_LOC, values, StoreColumns._ID + "=" + rowId
                    + (!TextUtils.isEmpty(where) ? " AND (" + where + ')' : ""), whereArgs);
            break;
        case TBL_SAR_SOS:
        	count = db.update(Constants.S_TBL_SAR_SOS_BRAND, values, where, whereArgs);
            break;

        case TBL_SAR_SOS_ID:
        	rowId = uri.getPathSegments().get(1);
            count = db.update(Constants.S_TBL_SAR_SOS_BRAND, values, StoreColumns._ID + "=" + rowId
                    + (!TextUtils.isEmpty(where) ? " AND (" + where + ')' : ""), whereArgs);
            break;
        case TBL_SAR_SOD:
        	count = db.update(Constants.S_TBL_SAR_SOD, values, where, whereArgs);
            break;

        case TBL_SAR_SOD_ID:
        	rowId = uri.getPathSegments().get(1);
            count = db.update(Constants.S_TBL_SAR_SOD, values, StoreColumns._ID + "=" + rowId
                    + (!TextUtils.isEmpty(where) ? " AND (" + where + ')' : ""), whereArgs);
            break;
        case TBL_SAR_PICTURE:
        	count = db.update(Constants.S_TBL_SAR_PICTURE, values, where, whereArgs);
            break;

        case TBL_SAR_PICTURE_ID:
        	rowId = uri.getPathSegments().get(1);
            count = db.update(Constants.S_TBL_SAR_PICTURE, values, StoreColumns._ID + "=" + rowId
                    + (!TextUtils.isEmpty(where) ? " AND (" + where + ')' : ""), whereArgs);
            break;
        case TBL_STORE_AUDIT_STATUS:
        	count = db.update(Constants.S_TBL_STORE_AUDIT_STATUS, values, where, whereArgs);
            break;

        case TBL_STORE_AUDIT_STATUS_ID:
        	rowId = uri.getPathSegments().get(1);
            count = db.update(Constants.S_TBL_STORE_AUDIT_STATUS, values, StoreColumns._ID + "=" + rowId
                    + (!TextUtils.isEmpty(where) ? " AND (" + where + ')' : ""), whereArgs);
            break;
            
        case SAR_PROMOTION:
        	count = db.update(Constants.S_TBL_SAR_STORE_PROMOTION, values, where, whereArgs);
            break;

        case SAR_PROMOTION_ID:
        	rowId = uri.getPathSegments().get(1);
            count = db.update(Constants.S_TBL_SAR_STORE_PROMOTION, values, StoreColumns._ID + "=" + rowId
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
        sUriMatcher.addURI(Constants.STORE_AUTHORITY, Constants.S_TBL_STORE, TBL_STORE);
        sUriMatcher.addURI(Constants.STORE_AUTHORITY, Constants.S_TBL_STORE + "/#", TBL_STORE_ID);

        sUriMatcher.addURI(Constants.STORE_AUTHORITY, Constants.S_TBL_STORE_BRAND_LOC, TBL_STORE_BRAND_LOC);
        sUriMatcher.addURI(Constants.STORE_AUTHORITY, Constants.S_TBL_STORE_BRAND_LOC + "/#", TBL_STORE_BRAND_LOC_ID);

        sUriMatcher.addURI(Constants.STORE_AUTHORITY, Constants.S_TBL_STORE_POSM, TBL_STORE_POSM);
        sUriMatcher.addURI(Constants.STORE_AUTHORITY, Constants.S_TBL_STORE_POSM + "/#", TBL_STORE_POSM_ID);

        sUriMatcher.addURI(Constants.STORE_AUTHORITY, Constants.S_TBL_STORE_PRODUCT, TBL_STORE_PROD);
        sUriMatcher.addURI(Constants.STORE_AUTHORITY, Constants.S_TBL_STORE_PRODUCT + "/#", TBL_STORE_PROD_ID);

        sUriMatcher.addURI(Constants.STORE_AUTHORITY, Constants.S_TBL_STORE_SOS_BRAND, TBL_STORE_SOS_BRAND);
        sUriMatcher.addURI(Constants.STORE_AUTHORITY, Constants.S_TBL_STORE_SOS_BRAND + "/#", TBL_STORE_SOS_BRAND_ID);

        sUriMatcher.addURI(Constants.STORE_AUTHORITY, Constants.S_TBL_STORE_SOS_PACKING, TBL_STORE_SOS_PACKING);
        sUriMatcher.addURI(Constants.STORE_AUTHORITY, Constants.S_TBL_STORE_SOS_PACKING + "/#", TBL_STORE_SOS_PACKING_ID);

        sUriMatcher.addURI(Constants.STORE_AUTHORITY, Constants.S_TBL_STORE_BRAND_GROUP, TBL_STORE_BRAND_GROUP);
        sUriMatcher.addURI(Constants.STORE_AUTHORITY, Constants.S_TBL_STORE_BRAND_GROUP + "/#", TBL_STORE_BRAND_GROUP_ID);

        sUriMatcher.addURI(Constants.STORE_AUTHORITY, Constants.S_TBL_SAR_REG_PROD, TBL_SAR_REG_PROD);
        sUriMatcher.addURI(Constants.STORE_AUTHORITY, Constants.S_TBL_SAR_REG_PROD + "/#", TBL_SAR_REG_PROD_ID);

        sUriMatcher.addURI(Constants.STORE_AUTHORITY, Constants.S_TBL_SAR_BRAND_LOC, TBL_SAR_BRAND_LOC);
        sUriMatcher.addURI(Constants.STORE_AUTHORITY, Constants.S_TBL_SAR_BRAND_LOC + "/#", TBL_SAR_BRAND_LOC_ID);

        sUriMatcher.addURI(Constants.STORE_AUTHORITY, Constants.S_TBL_SAR_POSM, TBL_SAR_POSM);
        sUriMatcher.addURI(Constants.STORE_AUTHORITY, Constants.S_TBL_SAR_POSM + "/#", TBL_SAR_POSM_ID);

        sUriMatcher.addURI(Constants.STORE_AUTHORITY, Constants.S_TBL_SAR_SOS_BRAND, TBL_SAR_SOS);
        sUriMatcher.addURI(Constants.STORE_AUTHORITY, Constants.S_TBL_SAR_SOS_BRAND + "/#", TBL_SAR_SOS_ID);

        sUriMatcher.addURI(Constants.STORE_AUTHORITY, Constants.S_TBL_SAR_SOD, TBL_SAR_SOD);
        sUriMatcher.addURI(Constants.STORE_AUTHORITY, Constants.S_TBL_SAR_SOD + "/#", TBL_SAR_SOD_ID);

        sUriMatcher.addURI(Constants.STORE_AUTHORITY, Constants.S_TBL_SAR_PICTURE, TBL_SAR_PICTURE);
        sUriMatcher.addURI(Constants.STORE_AUTHORITY, Constants.S_TBL_SAR_PICTURE + "/#", TBL_SAR_PICTURE_ID);

        sUriMatcher.addURI(Constants.STORE_AUTHORITY, Constants.S_TBL_STORE_AUDIT_STATUS, TBL_STORE_AUDIT_STATUS);
        sUriMatcher.addURI(Constants.STORE_AUTHORITY, Constants.S_TBL_STORE_AUDIT_STATUS + "/#", TBL_STORE_AUDIT_STATUS_ID);
        
        sUriMatcher.addURI(Constants.STORE_AUTHORITY, Constants.S_TBL_ACCOUNT, TBL_ACCOUNT);
        sUriMatcher.addURI(Constants.STORE_AUTHORITY, Constants.S_TBL_ACCOUNT + "/#", TBL_ACCOUNT_ID);
        
        /*Promotion*/
        sUriMatcher.addURI(Constants.STORE_AUTHORITY, Constants.S_TBL_STORE_PROMOTION_PRODUCT, PRODUCT);
        sUriMatcher.addURI(Constants.STORE_AUTHORITY, Constants.S_TBL_STORE_PROMOTION_PRODUCT + "/#", PRODUCT_ID);
        
        sUriMatcher.addURI(Constants.STORE_AUTHORITY, Constants.S_TBL_STORE_PROMOTION, PROMOTION);
        sUriMatcher.addURI(Constants.STORE_AUTHORITY, Constants.S_TBL_STORE_PROMOTION + "/#", PROMOTION_ID);
        
        sUriMatcher.addURI(Constants.STORE_AUTHORITY, Constants.S_TBL_ACCOUNT_PROMOTION, ACCOUNT_PROMOTIONS);
        
        sUriMatcher.addURI(Constants.STORE_AUTHORITY, Constants.S_TBL_STORE_PROMOTION_PRODUCT_TOHANDHELD, PROMOTION_PRODUCT_TO_HANDHELD);
        
        sUriMatcher.addURI(Constants.STORE_AUTHORITY, Constants.S_TBL_SAR_STORE_PROMOTION, SAR_PROMOTION);
        sUriMatcher.addURI(Constants.STORE_AUTHORITY, Constants.S_TBL_SAR_STORE_PROMOTION + "/#", SAR_PROMOTION_ID);
        
        sUriMatcher.addURI(Constants.STORE_AUTHORITY, Constants.S_TBL_UNIT, UNIT);
        sUriMatcher.addURI(Constants.STORE_AUTHORITY, Constants.S_TBL_UNIT + "/#", UNIT_ID);

        
        //Mapping columns
        sStoreProjectionMap = new HashMap<String, String>();
        sStoreProjectionMap.put(StoreColumns._ID, StoreColumns._ID);
        sStoreProjectionMap.put(StoreColumns.S_STORE_COL_CODE, StoreColumns.S_STORE_COL_CODE);
        sStoreProjectionMap.put(StoreColumns.S_STORE_COL_NAME, StoreColumns.S_STORE_COL_NAME);
        sStoreProjectionMap.put(StoreColumns.S_STORE_COL_ADDRESS, StoreColumns.S_STORE_COL_ADDRESS);
        sStoreProjectionMap.put(StoreColumns.S_STORE_COL_ADDRESS_NO_DIACRITIC, StoreColumns.S_STORE_COL_ADDRESS_NO_DIACRITIC);
        sStoreProjectionMap.put(StoreColumns.S_STORE_COL_ACCOUNT, StoreColumns.S_STORE_COL_ACCOUNT);
        sStoreProjectionMap.put(StoreColumns.S_STORE_COL_NAME_NO_DIACRITIC, StoreColumns.S_STORE_COL_NAME_NO_DIACRITIC);
        sStoreProjectionMap.put(StoreColumns.S_STORE_COL_LATITUDE, StoreColumns.S_STORE_COL_LATITUDE);
        sStoreProjectionMap.put(StoreColumns.S_STORE_COL_LONGITUDE, StoreColumns.S_STORE_COL_LONGITUDE);
        sStoreProjectionMap.put(StoreColumns.S_STORE_COL_REGION, StoreColumns.S_STORE_COL_REGION);
        sStoreProjectionMap.put(StoreColumns.S_STORE_COL_ACCOUNT_ID, StoreColumns.S_STORE_COL_ACCOUNT_ID);
        sStoreProjectionMap.put(StoreColumns.AUDITOR_CODE, StoreColumns.AUDITOR_CODE);
        sStoreProjectionMap.put(StoreColumns.CHECKED, StoreColumns.CHECKED);
        sStoreProjectionMap.put(StoreColumns.AUDIT_DATE, StoreColumns.AUDIT_DATE);
        sStoreProjectionMap.put(StoreColumns.SYNCED, StoreColumns.SYNCED);


        sStoreProdProjectionMap = new HashMap<String, String>();
        sStoreProdProjectionMap.put(StoreColumns._ID, StoreColumns._ID);
        sStoreProdProjectionMap.put(StoreColumns.S_STORE_PROD_COL_PROD_ID, StoreColumns.S_STORE_PROD_COL_PROD_ID);
        sStoreProdProjectionMap.put(StoreColumns.S_STORE_PROD_COL_BRAND, StoreColumns.S_STORE_PROD_COL_BRAND);
        sStoreProdProjectionMap.put(StoreColumns.S_STORE_PROD_COL_NAME, StoreColumns.S_STORE_PROD_COL_NAME);
        sStoreProdProjectionMap.put(StoreColumns.S_STORE_PROD_COL_GROUP, StoreColumns.S_STORE_PROD_COL_GROUP);
        sStoreProdProjectionMap.put(StoreColumns.S_STORE_PROD_COL_SIZE, StoreColumns.S_STORE_PROD_COL_SIZE);
        sStoreProdProjectionMap.put(StoreColumns.S_STORE_PROD_COL_STORE_ID, StoreColumns.S_STORE_PROD_COL_STORE_ID);


        sStoreBrandLocProjectionMap = new HashMap<String, String>();
        sStoreBrandLocProjectionMap.put(StoreColumns._ID, StoreColumns._ID);
        sStoreBrandLocProjectionMap.put(StoreColumns.S_STORE_BRAND_LOC_COL_BRAND_GROUP, StoreColumns.S_STORE_BRAND_LOC_COL_BRAND_GROUP);
        sStoreBrandLocProjectionMap.put(StoreColumns.S_STORE_BRAND_LOC_COL_NAME, StoreColumns.S_STORE_BRAND_LOC_COL_NAME);
        sStoreBrandLocProjectionMap.put(StoreColumns.S_STORE_BRAND_LOC_COL_PRODUCTS, StoreColumns.S_STORE_BRAND_LOC_COL_PRODUCTS);
        sStoreBrandLocProjectionMap.put(StoreColumns.S_STORE_BRAND_LOC_COL_RIGHT_LOC, StoreColumns.S_STORE_BRAND_LOC_COL_RIGHT_LOC);

        sStorePOSMProjectionMap = new HashMap<String, String>();
        sStorePOSMProjectionMap.put(StoreColumns._ID, StoreColumns._ID);
        sStorePOSMProjectionMap.put(StoreColumns.S_STORE_POSM_COL_BRAND_GROUP, StoreColumns.S_STORE_POSM_COL_BRAND_GROUP);
        sStorePOSMProjectionMap.put(StoreColumns.S_STORE_POSM_COL_CODE, StoreColumns.S_STORE_POSM_COL_CODE);
        sStorePOSMProjectionMap.put(StoreColumns.S_STORE_POSM_COL_NAME, StoreColumns.S_STORE_POSM_COL_NAME);

        sStoreSOSBrandProjectionMap = new HashMap<String, String>();
        sStoreSOSBrandProjectionMap.put(StoreColumns._ID, StoreColumns._ID);
        sStoreSOSBrandProjectionMap.put(StoreColumns.S_STORE_SOS_BRAND_COL_BRAND_GROUP, StoreColumns.S_STORE_SOS_BRAND_COL_BRAND_GROUP);
        sStoreSOSBrandProjectionMap.put(StoreColumns.S_STORE_SOS_BRAND_COL_NAME, StoreColumns.S_STORE_SOS_BRAND_COL_NAME);
        sStoreSOSBrandProjectionMap.put(StoreColumns.S_STORE_SOS_BRAND_COL_DISPLAY_ORDER, StoreColumns.S_STORE_SOS_BRAND_COL_DISPLAY_ORDER);


        sStoreSOSPackingProjectionMap = new HashMap<String, String>();
        sStoreSOSPackingProjectionMap.put(StoreColumns._ID, StoreColumns._ID);
        sStoreSOSPackingProjectionMap.put(StoreColumns.S_STORE_PACKING_COL_NAME, StoreColumns.S_STORE_PACKING_COL_NAME);
        sStoreSOSPackingProjectionMap.put(StoreColumns.S_STORE_PACKING_COL_BRAND_ID, StoreColumns.S_STORE_PACKING_COL_BRAND_ID);
        sStoreSOSPackingProjectionMap.put(StoreColumns.S_STORE_PACKING_COL_BRAND_GROUP, StoreColumns.S_STORE_PACKING_COL_BRAND_GROUP);
        sStoreSOSPackingProjectionMap.put(StoreColumns.S_STORE_PACKING_COL_BRAND, StoreColumns.S_STORE_PACKING_COL_BRAND);
        sStoreSOSPackingProjectionMap.put(StoreColumns.S_STORE_PACKING_COL_DISPLAY_ORDER, StoreColumns.S_STORE_PACKING_COL_DISPLAY_ORDER);

        sStoreBrandGroupProjectionMap = new HashMap<String, String>();
        sStoreBrandGroupProjectionMap.put(StoreColumns._ID, StoreColumns._ID);
        sStoreBrandGroupProjectionMap.put(StoreColumns.S_STORE_BRAND_GROUP_COL_NAME, StoreColumns.S_STORE_BRAND_GROUP_COL_NAME);

        sSARRegProdProjectionMap = new HashMap<String, String>();
        sSARRegProdProjectionMap.put(StoreColumns._ID, StoreColumns._ID);
        sSARRegProdProjectionMap.put(StoreColumns.S_SAR_REG_PROD_PROD_ID, StoreColumns.S_SAR_REG_PROD_PROD_ID);
        sSARRegProdProjectionMap.put(StoreColumns.S_SAR_REG_PROD_STORE_ID, StoreColumns.S_SAR_REG_PROD_STORE_ID);
        sSARRegProdProjectionMap.put(StoreColumns.S_SAR_REG_PROD_HAS, StoreColumns.S_SAR_REG_PROD_HAS);

        sSARPOSMProjectionMap = new HashMap<String, String>();
        sSARPOSMProjectionMap.put(StoreColumns._ID, StoreColumns._ID);
        sSARPOSMProjectionMap.put(StoreColumns.S_SAR_POSM_POSM_ID, StoreColumns.S_SAR_POSM_POSM_ID);
        sSARPOSMProjectionMap.put(StoreColumns.S_SAR_POSM_STORE_ID, StoreColumns.S_SAR_POSM_STORE_ID);
        sSARPOSMProjectionMap.put(StoreColumns.S_SAR_POSM_HAS, StoreColumns.S_SAR_POSM_HAS);

        sSARBrandLocProjectionMap = new HashMap<String, String>();
        sSARBrandLocProjectionMap.put(StoreColumns._ID, StoreColumns._ID);
        sSARBrandLocProjectionMap.put(StoreColumns.S_SAR_BRAND_LOC_BRAND_ID, StoreColumns.S_SAR_BRAND_LOC_BRAND_ID);
        sSARBrandLocProjectionMap.put(StoreColumns.S_SAR_BRAND_LOC_STORE_ID, StoreColumns.S_SAR_BRAND_LOC_STORE_ID);
        sSARBrandLocProjectionMap.put(StoreColumns.S_SAR_BRAND_LOC_RIGHT, StoreColumns.S_SAR_BRAND_LOC_RIGHT);
        sSARBrandLocProjectionMap.put(StoreColumns.S_SAR_BRAND_LOC_HAS, StoreColumns.S_SAR_BRAND_LOC_HAS);

        sSARSOSProjectionMap = new HashMap<String, String>();
        sSARSOSProjectionMap.put(StoreColumns._ID, StoreColumns._ID);
        sSARSOSProjectionMap.put(StoreColumns.S_SAR_SOS_PACKING_ID, StoreColumns.S_SAR_SOS_PACKING_ID);
        sSARSOSProjectionMap.put(StoreColumns.S_SAR_SOS_STORE_ID, StoreColumns.S_SAR_SOS_STORE_ID);
        sSARSOSProjectionMap.put(StoreColumns.S_SAR_SOS_QUANTITY, StoreColumns.S_SAR_SOS_QUANTITY);

        sSARSODProjectionMap = new HashMap<String, String>();
        sSARSODProjectionMap.put(StoreColumns._ID, StoreColumns._ID);
        sSARSODProjectionMap.put(StoreColumns.S_SAR_SOD_BRAND, StoreColumns.S_SAR_SOD_BRAND);
        sSARSODProjectionMap.put(StoreColumns.S_SAR_SOD_STORE_ID, StoreColumns.S_SAR_SOD_STORE_ID);
        sSARSODProjectionMap.put(StoreColumns.S_SAR_SOD_FCV_GE, StoreColumns.S_SAR_SOD_FCV_GE);
        sSARSODProjectionMap.put(StoreColumns.S_SAR_SOD_FCV_SS, StoreColumns.S_SAR_SOD_FCV_SS);
        sSARSODProjectionMap.put(StoreColumns.S_SAR_SOD_FCV_OTHER, StoreColumns.S_SAR_SOD_FCV_OTHER);
        sSARSODProjectionMap.put(StoreColumns.S_SAR_SOD_STORE_GE, StoreColumns.S_SAR_SOD_STORE_GE);
        sSARSODProjectionMap.put(StoreColumns.S_SAR_SOD_STORE_SS, StoreColumns.S_SAR_SOD_STORE_SS);
        sSARSODProjectionMap.put(StoreColumns.S_SAR_SOD_STORE_OTHER, StoreColumns.S_SAR_SOD_STORE_OTHER);

        sSARPictureProjectionMap = new HashMap<String, String>();
        sSARPictureProjectionMap.put(StoreColumns._ID, StoreColumns._ID);
        sSARPictureProjectionMap.put(StoreColumns.S_SAR_PICTURE_COL_STORE_ID, StoreColumns.S_SAR_PICTURE_COL_STORE_ID);
        sSARPictureProjectionMap.put(StoreColumns.S_SAR_PICTURE_COL_URI, StoreColumns.S_SAR_PICTURE_COL_URI);
        sSARPictureProjectionMap.put(StoreColumns.S_SAR_PICTURE_COL_NOTES, StoreColumns.S_SAR_PICTURE_COL_NOTES);
        sSARPictureProjectionMap.put(StoreColumns.S_SAR_PICTURE_COL_CREATED_DATE, StoreColumns.S_SAR_PICTURE_COL_CREATED_DATE);

        sStoreAuditStatusProjectionMap = new HashMap<String, String>();
        sStoreAuditStatusProjectionMap.put(StoreColumns._ID, StoreColumns._ID);
        sStoreAuditStatusProjectionMap.put(StoreColumns.S_STORE_AUDIT_STATUS_COL_STORE_ID, StoreColumns.S_STORE_AUDIT_STATUS_COL_STORE_ID);
        sStoreAuditStatusProjectionMap.put(StoreColumns.S_STORE_AUDIT_STATUS_COL_3S, StoreColumns.S_STORE_AUDIT_STATUS_COL_3S);
        sStoreAuditStatusProjectionMap.put(StoreColumns.S_STORE_AUDIT_STATUS_COL_VISIBILITY, StoreColumns.S_STORE_AUDIT_STATUS_COL_VISIBILITY);
        sStoreAuditStatusProjectionMap.put(StoreColumns.S_STORE_AUDIT_STATUS_COL_PICTURE, StoreColumns.S_STORE_AUDIT_STATUS_COL_PICTURE);
        sStoreAuditStatusProjectionMap.put(StoreColumns.S_STORE_AUDIT_STATUS_COL_PROMOTION, StoreColumns.S_STORE_AUDIT_STATUS_COL_PROMOTION);
        
        sAccountProjectionMap = new HashMap<String, String>();
        sAccountProjectionMap.put(StoreColumns._ID, StoreColumns._ID);
        sAccountProjectionMap.put(StoreColumns.S_ACCOUNT_COL_ID, StoreColumns.S_ACCOUNT_COL_ID);
        sAccountProjectionMap.put(StoreColumns.S_ACCOUNT_COL_NAME, StoreColumns.S_ACCOUNT_COL_NAME);
        
        /*Promotion*/
        sProductProjectionMap = new HashMap<String, String>();
        sProductProjectionMap.put(StoreColumns._ID, StoreColumns._ID);
        sProductProjectionMap.put(StoreColumns.S_STORE_PROMOTION_PRODUCT_NAME, StoreColumns.S_STORE_PROMOTION_PRODUCT_NAME);
        
        sPromotionProjectionMap = new HashMap<String, String>();
        sPromotionProjectionMap.put(StoreColumns._ID, StoreColumns._ID);
        sPromotionProjectionMap.put(StoreColumns.S_STORE_PROMOTION_NAME, StoreColumns.S_STORE_PROMOTION_NAME);
        sPromotionProjectionMap.put(StoreColumns.S_STORE_PROMOTION_CODE, StoreColumns.S_STORE_PROMOTION_CODE);
        sPromotionProjectionMap.put(StoreColumns.S_STORE_PROMOTION_PROMOTION_TYPE, StoreColumns.S_STORE_PROMOTION_PROMOTION_TYPE);
        sPromotionProjectionMap.put(StoreColumns.S_STORE_PROMOTION_EFFECTIVE_DATE, StoreColumns.S_STORE_PROMOTION_EFFECTIVE_DATE);
        sPromotionProjectionMap.put(StoreColumns.S_STORE_PROMOTION_EXPIRE_DATE, StoreColumns.S_STORE_PROMOTION_EXPIRE_DATE);
        sPromotionProjectionMap.put(StoreColumns.S_STORE_PROMOTION_BUY_QUANTITY, StoreColumns.S_STORE_PROMOTION_BUY_QUANTITY);
        sPromotionProjectionMap.put(StoreColumns.S_STORE_PROMOTION_BUY_UNIT, StoreColumns.S_STORE_PROMOTION_BUY_UNIT);
        sPromotionProjectionMap.put(StoreColumns.S_STORE_PROMOTION_BUY_PRODUCT, StoreColumns.S_STORE_PROMOTION_BUY_PRODUCT);
        sPromotionProjectionMap.put(StoreColumns.S_STORE_PROMOTION_GET_QUANTITY, StoreColumns.S_STORE_PROMOTION_GET_QUANTITY);
        sPromotionProjectionMap.put(StoreColumns.S_STORE_PROMOTION_GET_UNIT, StoreColumns.S_STORE_PROMOTION_GET_UNIT);
        sPromotionProjectionMap.put(StoreColumns.S_STORE_PROMOTION_GET_PRODUCT, StoreColumns.S_STORE_PROMOTION_GET_PRODUCT);
        sPromotionProjectionMap.put(StoreColumns.S_STORE_PROMOTION_SOS_BRAND, StoreColumns.S_STORE_PROMOTION_SOS_BRAND);
        sPromotionProjectionMap.put(StoreColumns.S_STORE_PROMOTION_SOS_BRAND_ID, StoreColumns.S_STORE_PROMOTION_SOS_BRAND_ID);
        
        sAccountPromotionsProjectionMap = new HashMap<String, String>();
        sAccountPromotionsProjectionMap.put(StoreColumns._ID, StoreColumns._ID);
        sAccountPromotionsProjectionMap.put(StoreColumns.S_ACCOUNT_COL_ID, StoreColumns.S_ACCOUNT_COL_ID);
        sAccountPromotionsProjectionMap.put(StoreColumns.S_STORE_PROMOTION_ID, StoreColumns.S_STORE_PROMOTION_ID);
        
        sPromotionProductToHandHeldProjectionMap = new HashMap<String, String>();
        sPromotionProductToHandHeldProjectionMap.put(StoreColumns._ID, StoreColumns._ID);
        sPromotionProductToHandHeldProjectionMap.put(StoreColumns.S_STORE_PROMOTION_ID, StoreColumns.S_STORE_PROMOTION_ID);
        sPromotionProductToHandHeldProjectionMap.put(StoreColumns.S_STORE_PROMOTION_PRODUCT_ID, StoreColumns.S_STORE_PROMOTION_PRODUCT_ID);
        
        sSARPromotionsProjectionMap = new HashMap<String, String>();
        sSARPromotionsProjectionMap.put(StoreColumns._ID, StoreColumns._ID);
        sSARPromotionsProjectionMap.put(StoreColumns.S_SAR_STORE_PROMOTION_STORE_ID, StoreColumns.S_SAR_STORE_PROMOTION_STORE_ID);
        sSARPromotionsProjectionMap.put(StoreColumns.S_SAR_STORE_PROMOTION_PROMOTION_ID, StoreColumns.S_SAR_STORE_PROMOTION_PROMOTION_ID);
        sSARPromotionsProjectionMap.put(StoreColumns.S_SAR_STORE_PROMOTION_GET_QUANTITY, StoreColumns.S_SAR_STORE_PROMOTION_GET_QUANTITY);
        sSARPromotionsProjectionMap.put(StoreColumns.S_SAR_STORE_PROMOTION_UNIT_ID, StoreColumns.S_SAR_STORE_PROMOTION_UNIT_ID);
        sSARPromotionsProjectionMap.put(StoreColumns.S_SAR_STORE_PROMOTION_PRODUCT_ID, StoreColumns.S_SAR_STORE_PROMOTION_PRODUCT_ID);
        sSARPromotionsProjectionMap.put(StoreColumns.S_SAR_STORE_PROMOTION_KNOWN, StoreColumns.S_SAR_STORE_PROMOTION_KNOWN);
        sSARPromotionsProjectionMap.put(StoreColumns.S_SAR_STORE_PROMOTION_CORRECT, StoreColumns.S_SAR_STORE_PROMOTION_CORRECT);
        
        sUnitProjectionMap = new HashMap<String, String>();
        sUnitProjectionMap.put(StoreColumns._ID, StoreColumns._ID);
        sUnitProjectionMap.put(StoreColumns.S_UNIT_NAME, StoreColumns.S_UNIT_NAME);
    }
}
