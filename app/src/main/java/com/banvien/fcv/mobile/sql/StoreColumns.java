package com.banvien.fcv.mobile.sql;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Ban Vien Ltd.
 * User: Vien Nguyen (vien.nguyen@banvien.com)
 * Date: 6/23/12
 * Time: 9:48 AM
 */
public class StoreColumns implements BaseColumns {
    public static final Uri STORE_CONTENT_URI = Uri.parse("content://" + Constants.STORE_AUTHORITY + "/" + Constants.S_TBL_STORE);
    public static final Uri STORE_PRODUCT_CONTENT_URI = Uri.parse("content://" + Constants.STORE_AUTHORITY + "/" + Constants.S_TBL_STORE_PRODUCT);
    public static final Uri STORE_BRAND_LOC_CONTENT_URI = Uri.parse("content://" + Constants.STORE_AUTHORITY + "/" + Constants.S_TBL_STORE_BRAND_LOC);
    public static final Uri STORE_POSM_CONTENT_URI = Uri.parse("content://" + Constants.STORE_AUTHORITY + "/" + Constants.S_TBL_STORE_POSM);
    public static final Uri STORE_SOS_BRAND_CONTENT_URI = Uri.parse("content://" + Constants.STORE_AUTHORITY + "/" + Constants.S_TBL_STORE_SOS_BRAND);
    public static final Uri STORE_SOS_PACKING_CONTENT_URI = Uri.parse("content://" + Constants.STORE_AUTHORITY + "/" + Constants.S_TBL_STORE_SOS_PACKING);
    public static final Uri STORE_BRAND_GROUP_CONTENT_URI = Uri.parse("content://" + Constants.STORE_AUTHORITY + "/" + Constants.S_TBL_STORE_BRAND_GROUP);

    public static final Uri SAR_REG_PROD_CONTENT_URI = Uri.parse("content://" + Constants.STORE_AUTHORITY + "/" + Constants.S_TBL_SAR_REG_PROD);
    public static final Uri SAR_POSM_CONTENT_URI = Uri.parse("content://" + Constants.STORE_AUTHORITY + "/" + Constants.S_TBL_SAR_POSM);
    public static final Uri SAR_SOS_BRAND_CONTENT_URI = Uri.parse("content://" + Constants.STORE_AUTHORITY + "/" + Constants.S_TBL_SAR_SOS_BRAND);
    public static final Uri SAR_SOD_CONTENT_URI = Uri.parse("content://" + Constants.STORE_AUTHORITY + "/" + Constants.S_TBL_SAR_SOD);
    public static final Uri SAR_BRAND_LOC_CONTENT_URI = Uri.parse("content://" + Constants.STORE_AUTHORITY + "/" + Constants.S_TBL_SAR_BRAND_LOC);
    public static final Uri SAR_PICTURE_CONTENT_URI = Uri.parse("content://" + Constants.STORE_AUTHORITY + "/" + Constants.S_TBL_SAR_PICTURE);
    public static final Uri STORE_AUDIT_STATUS_CONTENT_URI = Uri.parse("content://" + Constants.STORE_AUTHORITY + "/" + Constants.S_TBL_STORE_AUDIT_STATUS);
    public static final Uri ACCOUNT_CONTENT_URI = Uri.parse("content://" + Constants.STORE_AUTHORITY + "/" + Constants.S_TBL_ACCOUNT);

    /*Promotion*/
    public static final Uri STORE_PROMOTION_CONTENT_URI = Uri.parse("content://" + Constants.STORE_AUTHORITY + "/" + Constants.S_TBL_STORE_PROMOTION);
    public static final Uri STORE_PROMOTION_PRODUCT_CONTENT_URI = Uri.parse("content://" + Constants.STORE_AUTHORITY + "/" + Constants.S_TBL_STORE_PROMOTION_PRODUCT);
    public static final Uri ACCOUNT_PROMOTION_CONTENT_URI = Uri.parse("content://" + Constants.STORE_AUTHORITY + "/" + Constants.S_TBL_ACCOUNT_PROMOTION);
    public static final Uri PROMOTION_PRODUCT_TOHANDHELD_CONTENT_URI = Uri.parse("content://" + Constants.STORE_AUTHORITY + "/" + Constants.S_TBL_STORE_PROMOTION_PRODUCT_TOHANDHELD);
    public static final Uri SAR_PROMOTION_CONTENT_URI = Uri.parse("content://" + Constants.STORE_AUTHORITY + "/" + Constants.S_TBL_SAR_STORE_PROMOTION);
    public static final Uri UNIT_CONTENT_URI = Uri.parse("content://" + Constants.STORE_AUTHORITY + "/" + Constants.S_TBL_UNIT);
    
    public static final String DEFAULT_SORT = _ID + " ASC";

    /**
     * Table store
     */
    public static final String S_STORE_COL_REGION = "region";
    public static final String S_STORE_COL_ADDRESS = "address";
    public static final String S_STORE_COL_ADDRESS_NO_DIACRITIC = "address_no_diacritic";
    public static final String S_STORE_COL_NAME = "name";
    public static final String S_STORE_COL_NAME_NO_DIACRITIC = "name_no_diacritic";
    public static final String S_STORE_COL_LATITUDE = "latitude";
    public static final String S_STORE_COL_LONGITUDE = "longitude";
    public static final String S_STORE_COL_ACCOUNT = "account";
    public static final String S_STORE_COL_CODE = "code";
    public static final String S_STORE_COL_ACCOUNT_ID = "account_id";

    public static final String CHECKED = "Checked";
    public static final String SYNCED = "Synced";
    public static final String AUDITOR_CODE = "AuditorCode";
    public static final String AUDIT_DATE = "AuditDate";

    /**
     * Table Store Product
     */
    public static final String S_STORE_PROD_COL_PROD_ID = "product_id";
    public static final String S_STORE_PROD_COL_NAME = "name";
    public static final String S_STORE_PROD_COL_BRAND = "brand";
    public static final String S_STORE_PROD_COL_GROUP = "prod_group";
    public static final String S_STORE_PROD_COL_SIZE = "size";
    public static final String S_STORE_PROD_COL_STORE_ID = "store_id";

    /**
     * Table Store POSM
     */
    public static final String S_STORE_POSM_COL_BRAND_GROUP = "brand_group";
    public static final String S_STORE_POSM_COL_NAME = "name";
    public static final String S_STORE_POSM_COL_CODE = "code";

    /**
     * Table Store Brand Location
     */
    public static final String S_STORE_BRAND_LOC_COL_BRAND_GROUP = "brand_group";
    public static final String S_STORE_BRAND_LOC_COL_NAME = "name";
    public static final String S_STORE_BRAND_LOC_COL_PRODUCTS = "products";
    public static final String S_STORE_BRAND_LOC_COL_RIGHT_LOC = "right_location";

    /**
     * Table store SOS Brand
     */
    public static final String S_STORE_SOS_BRAND_COL_BRAND_GROUP = "brand_group";
    public static final String S_STORE_SOS_BRAND_COL_NAME = "name";
    public static final String S_STORE_SOS_BRAND_COL_DISPLAY_ORDER = "display_order";

    /**
     * Table Store SOS Packing Group
     */
    public static final String S_STORE_PACKING_COL_NAME = "name";
    public static final String S_STORE_PACKING_COL_BRAND_ID = "brand_id";
    public static final String S_STORE_PACKING_COL_BRAND_GROUP = "brand_group";
    public static final String S_STORE_PACKING_COL_BRAND = "brand_name";
    public static final String S_STORE_PACKING_COL_DISPLAY_ORDER = "display_order";

    /**
     * Table Brand Group
     */
    public static final String  S_STORE_BRAND_GROUP_COL_NAME = "name";
    
    /**
     * Table Brand Group
     */
    public static final String  S_ACCOUNT_COL_NAME = "name";
    public static final String  S_ACCOUNT_COL_ID = "account_id";

    /**
     * Table SAR Registered Product
     */
    public static final String S_SAR_REG_PROD_STORE_ID = "store_id";
    public static final String S_SAR_REG_PROD_PROD_ID = "prod_id";
    public static final String S_SAR_REG_PROD_HAS = "has";

    /**
     * Table SAR POSM
     */
    public static final String S_SAR_POSM_STORE_ID = "store_id";
    public static final String S_SAR_POSM_POSM_ID = "posm_id";
    public static final String S_SAR_POSM_HAS = "has";

    /**
     * Table SAR Brand Location
     */
    public static final String S_SAR_BRAND_LOC_STORE_ID = "store_id";
    public static final String S_SAR_BRAND_LOC_BRAND_ID = "brand_id";
    public static final String S_SAR_BRAND_LOC_HAS = "has";
    public static final String S_SAR_BRAND_LOC_RIGHT = "right";

    /**
     * Table SAR SOS
     */
    public static final String S_SAR_SOS_STORE_ID = "store_id";
    public static final String S_SAR_SOS_PACKING_ID = "packing_id";
    public static final String S_SAR_SOS_QUANTITY = "quantity";

    /**
     * Table SAR SOD
     */
    public static final String S_SAR_SOD_STORE_ID = "store_id";
    public static final String S_SAR_SOD_BRAND = "brand_id";
    public static final String S_SAR_SOD_FCV_SS = "fcv_ss";
    public static final String S_SAR_SOD_FCV_GE = "fcv_ge";
    public static final String S_SAR_SOD_FCV_OTHER = "fcv_other";
    public static final String S_SAR_SOD_STORE_SS = "store_ss";
    public static final String S_SAR_SOD_STORE_GE = "store_ge";
    public static final String S_SAR_SOD_STORE_OTHER = "store_other";

    /**
     * Table SAR Picture
     */
    public static final String S_SAR_PICTURE_COL_STORE_ID = "store_id";
    public static final String S_SAR_PICTURE_COL_URI = "uri";
    public static final String S_SAR_PICTURE_COL_NOTES = "notes";
    public static final String S_SAR_PICTURE_COL_CREATED_DATE = "created_date";

    /**
     * Table store audit status
     */
    public static final String S_STORE_AUDIT_STATUS_COL_STORE_ID = "store_id";
    public static final String S_STORE_AUDIT_STATUS_COL_3S = "store_3s";
    public static final String S_STORE_AUDIT_STATUS_COL_VISIBILITY = "visibility";
    public static final String S_STORE_AUDIT_STATUS_COL_PROMOTION = "promotion";
    public static final String S_STORE_AUDIT_STATUS_COL_PICTURE = "picture";

    /**
     * Store Promotion
     */
    public static final String S_STORE_PROMOTION_CODE = "code";
    public static final String S_STORE_PROMOTION_NAME = "name";
    public static final String S_STORE_PROMOTION_PROMOTION_TYPE = "promotion_type";
    public static final String S_STORE_PROMOTION_EFFECTIVE_DATE = "effective_date";
    public static final String S_STORE_PROMOTION_EXPIRE_DATE = "expire_date";
    public static final String S_STORE_PROMOTION_BUY_QUANTITY = "buy_quantity";
    public static final String S_STORE_PROMOTION_BUY_UNIT = "buy_unit";
    public static final String S_STORE_PROMOTION_BUY_PRODUCT = "buy_product";
    public static final String S_STORE_PROMOTION_GET_QUANTITY = "get_quantity";
    public static final String S_STORE_PROMOTION_GET_UNIT = "get_unit";
    public static final String S_STORE_PROMOTION_GET_PRODUCT = "get_product";
    public static final String S_STORE_PROMOTION_SOS_BRAND = "sos_brand";
    public static final String S_STORE_PROMOTION_SOS_BRAND_ID = "sos_brand_id";
    
    public static final String S_STORE_PROMOTION_ID = "store_promotion_id";
    public static final String S_ACCOUNT_ID = "account_id";
    public static final String S_STORE_PROMOTION_PRODUCT_ID = "store_promotion_product_id";
    public static final String S_STORE_PROMOTION_PRODUCT_NAME = "store_promotion_product_id";
    
    public static final String S_SAR_STORE_PROMOTION_STORE_ID = "store_id";
    public static final String S_SAR_STORE_PROMOTION_PROMOTION_ID = "store_promotion_id";
    public static final String S_SAR_STORE_PROMOTION_GET_QUANTITY = "get_quantity";
    public static final String S_SAR_STORE_PROMOTION_UNIT_ID = "unit_id";
    public static final String S_SAR_STORE_PROMOTION_PRODUCT_ID = "product";
    public static final String S_SAR_STORE_PROMOTION_KNOWN = "known";
    public static final String S_SAR_STORE_PROMOTION_CORRECT = "correct";
    public static final String S_UNIT_NAME = "name";
}
