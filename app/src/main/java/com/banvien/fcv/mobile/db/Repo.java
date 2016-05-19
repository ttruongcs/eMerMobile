package com.banvien.fcv.mobile.db;

import android.content.Context;

import com.banvien.fcv.mobile.db.dao.CatgroupDAO;
import com.banvien.fcv.mobile.db.dao.ConfigDAO;
import com.banvien.fcv.mobile.db.dao.HotzoneDAO;
import com.banvien.fcv.mobile.db.dao.OutletDAO;
import com.banvien.fcv.mobile.db.dao.OutletMerDAO;
import com.banvien.fcv.mobile.db.dao.OutletRegisteredDAO;
import com.banvien.fcv.mobile.db.dao.PosmDAO;
import com.banvien.fcv.mobile.db.dao.ProductDAO;
import com.banvien.fcv.mobile.db.dao.ProductgroupDAO;
import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.sql.SQLException;

/**
 * Created by hieu on 8/03/2016.
 */
public class Repo {
    private DatabaseHelper databaseHelper;

    public Repo(Context context) {
        databaseHelper = OpenHelperManager.getHelper(context, DatabaseHelper.class);
    }

    public DatabaseHelper getDatabaseHelper() {
        return databaseHelper;
    }

    public CatgroupDAO getCatgroupDAO() throws SQLException {
        return databaseHelper.getCatgroupDAO();
    }

    public ConfigDAO getConfigDAO() throws SQLException {
        return databaseHelper.getConfigDAO();
    }

    public HotzoneDAO getHotZoneDAO() throws SQLException {
        return databaseHelper.getHotzoneDAO();
    }

    public OutletMerDAO getOutletMerDAO() throws SQLException {
        return databaseHelper.getOutletMerDAO();
    }

    public PosmDAO getPosmDAO() throws SQLException {
        return databaseHelper.getPosmDAO();
    }

    public ProductDAO getProductDAO() throws SQLException {
        return databaseHelper.getProductDAO();
    }

    public ProductgroupDAO getProductGroupDAO() throws SQLException {
        return databaseHelper.getProductgroupDAO();
    }

    public OutletDAO getOutletDAO() throws SQLException {
        return databaseHelper.getOutletDAO();
    }

    public OutletRegisteredDAO getOutletRegisteredDAO() throws SQLException {
        return databaseHelper.getOutletRegisteredDAO();
    }

    public void release() {
        if (databaseHelper != null) {
            OpenHelperManager.releaseHelper();
        }
    }
}
