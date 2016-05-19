package com.banvien.fcv.mobile.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.banvien.fcv.mobile.db.dao.CatgroupDAO;
import com.banvien.fcv.mobile.db.dao.ConfigDAO;
import com.banvien.fcv.mobile.db.dao.HotzoneDAO;
import com.banvien.fcv.mobile.db.dao.OutletDAO;
import com.banvien.fcv.mobile.db.dao.OutletMerDAO;
import com.banvien.fcv.mobile.db.dao.OutletRegisteredDAO;
import com.banvien.fcv.mobile.db.dao.PosmDAO;
import com.banvien.fcv.mobile.db.dao.ProductDAO;
import com.banvien.fcv.mobile.db.dao.ProductgroupDAO;
import com.banvien.fcv.mobile.db.entities.CatgroupEntity;
import com.banvien.fcv.mobile.db.entities.HotzoneEntity;
import com.banvien.fcv.mobile.db.entities.OutletEntity;
import com.banvien.fcv.mobile.db.entities.OutletMerEntity;
import com.banvien.fcv.mobile.db.entities.OutletRegisteredEntity;
import com.banvien.fcv.mobile.db.entities.POSMEntity;
import com.banvien.fcv.mobile.db.entities.ProductEntity;
import com.banvien.fcv.mobile.db.entities.ProductgroupEntity;
import com.banvien.fcv.mobile.dto.OutletRegisteredDTO;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * Created by hieu on 8/03/2016.
 *
 * Database helper class used to manage the creation and upgrading of your database. This class also usually provides
 * the DAOs used by the other classes.
 */

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
	private final static String TAG = "DatabaseHelper";

	// name of the database file for your application -- change to something appropriate for your app
	private static final String DATABASE_NAME = "fcvemer.db";
	// any time you make changes to your database objects, you may have to increase the database version
	private static final int DATABASE_VERSION = 1;

	// the DAO object we use to access the PendingQuestion table
	// the DAO object we use to access the PendingQuestion table
	private CatgroupDAO catgroupDAO = null;
	private ConfigDAO configDAO = null;
	private HotzoneDAO hotzoneDAO = null;
	private OutletMerDAO outletMerDAO = null;
	private PosmDAO posmDAO = null;
	private ProductDAO productDAO = null;
	private ProductgroupDAO productgroupDAO = null;
	private OutletDAO outletDAO = null;
	private OutletRegisteredDAO outletRegisteredDAO = null;

	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	/**
	 * This is called when the database is first created. Usually you should call createTable statements here to create
	 * the tables that will store your data.
	 */
	@Override
	public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
		try {
			Log.i(TAG, "onCreate");
			TableUtils.createTable(connectionSource, HotzoneEntity.class);
			TableUtils.createTable(connectionSource, OutletMerEntity.class);
			TableUtils.createTable(connectionSource, POSMEntity.class);
			TableUtils.createTable(connectionSource, ProductEntity.class);
			TableUtils.createTable(connectionSource, CatgroupEntity.class);
			TableUtils.createTable(connectionSource, ProductgroupEntity.class);
			TableUtils.createTable(connectionSource, OutletEntity.class);
			TableUtils.createTable(connectionSource, OutletRegisteredEntity.class);
		} catch (SQLException e) {
			Log.e(TAG, "Can't create database", e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * This is called when your application is upgraded and it has a higher version number. This allows you to adjust
	 * the various data to match the new version number.
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
		try {
			Log.i(TAG, "onUpgrade");
			TableUtils.dropTable(connectionSource, HotzoneEntity.class, true);
			TableUtils.dropTable(connectionSource, OutletMerEntity.class, true);
			TableUtils.dropTable(connectionSource, POSMEntity.class, true);
			TableUtils.dropTable(connectionSource, ProductEntity.class, true);

			TableUtils.dropTable(connectionSource, CatgroupEntity.class, true);
			TableUtils.dropTable(connectionSource, ProductgroupEntity.class, true);
			TableUtils.dropTable(connectionSource, OutletEntity.class, true);
			TableUtils.dropTable(connectionSource, OutletRegisteredEntity.class, true);
			// after we drop the old databases, we create the new ones
			onCreate(db, connectionSource);
		} catch (SQLException e) {
			Log.e(DatabaseHelper.class.getName(), "Can't drop databases", e);
			throw new RuntimeException(e);
		}
	}

	public CatgroupDAO getCatgroupDAO() throws SQLException {
		if(null == catgroupDAO) {
			catgroupDAO = new CatgroupDAO(getConnectionSource(), CatgroupEntity.class);
		}
		return catgroupDAO;
	}

	public ConfigDAO getConfigDAO() {
		return configDAO;
	}

	public HotzoneDAO getHotzoneDAO() throws SQLException {
		if(null == hotzoneDAO) {
			hotzoneDAO = new HotzoneDAO(getConnectionSource(), HotzoneEntity.class);
		}
		return hotzoneDAO;
	}

	public OutletMerDAO getOutletMerDAO() throws SQLException {
		if(null == outletMerDAO) {
			outletMerDAO = new OutletMerDAO(getConnectionSource(), OutletMerEntity.class);
		}
		return outletMerDAO;
	}

	public PosmDAO getPosmDAO() throws SQLException {
		if(null == posmDAO) {
			posmDAO = new PosmDAO(getConnectionSource(), POSMEntity.class);
		}
		return posmDAO;
	}

	public ProductDAO getProductDAO() throws SQLException {
		if(null == productDAO) {
			productDAO = new ProductDAO(getConnectionSource(), ProductEntity.class);
		}
		return productDAO;
	}

	public ProductgroupDAO getProductgroupDAO() throws SQLException {
		if(null == productgroupDAO) {
			productgroupDAO = new ProductgroupDAO(getConnectionSource(), ProductgroupEntity.class);
		}
		return productgroupDAO;
	}

	public OutletRegisteredDAO getOutletRegisteredDAO() throws SQLException {
		if(null == outletRegisteredDAO) {
			outletRegisteredDAO = new OutletRegisteredDAO(getConnectionSource(), OutletRegisteredEntity.class);
		}
		return outletRegisteredDAO;
	}

	public OutletDAO getOutletDAO() throws SQLException {
		if(null == outletDAO) {
			outletDAO = new OutletDAO(getConnectionSource(), OutletEntity.class);
		}
		return outletDAO;
	}

	/**
	 * Close the database connections and clear any cached DAOs.
	 */
	@Override
	public void close() {
		super.close();
	}

}
