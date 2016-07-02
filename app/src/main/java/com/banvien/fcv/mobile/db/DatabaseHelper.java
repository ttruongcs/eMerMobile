package com.banvien.fcv.mobile.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.banvien.fcv.mobile.db.dao.CaptureAfterDAO;
import com.banvien.fcv.mobile.db.dao.CaptureBeforeDAO;
import com.banvien.fcv.mobile.db.dao.CaptureOverviewDAO;
import com.banvien.fcv.mobile.db.dao.CaptureToolDAO;
import com.banvien.fcv.mobile.db.dao.CaptureUniformDAO;
import com.banvien.fcv.mobile.db.dao.CatgroupDAO;
import com.banvien.fcv.mobile.db.dao.ComplainTypeDAO;
import com.banvien.fcv.mobile.db.dao.ConfigDAO;
import com.banvien.fcv.mobile.db.dao.ConfirmWorkingDAO;
import com.banvien.fcv.mobile.db.dao.DeclineDAO;
import com.banvien.fcv.mobile.db.dao.DoSurveyAnswerDAO;
import com.banvien.fcv.mobile.db.dao.HotzoneDAO;
import com.banvien.fcv.mobile.db.dao.OutletDAO;
import com.banvien.fcv.mobile.db.dao.OutletEndDayImagesDAO;
import com.banvien.fcv.mobile.db.dao.OutletFirstImagesDAO;
import com.banvien.fcv.mobile.db.dao.OutletMerDAO;
import com.banvien.fcv.mobile.db.dao.OutletRegisteredDAO;
import com.banvien.fcv.mobile.db.dao.PosmDAO;
import com.banvien.fcv.mobile.db.dao.ProductDAO;
import com.banvien.fcv.mobile.db.dao.ProductgroupDAO;
import com.banvien.fcv.mobile.db.dao.QuestionContentDAO;
import com.banvien.fcv.mobile.db.dao.QuestionDAO;
import com.banvien.fcv.mobile.db.dao.RouteScheduleDAO;
import com.banvien.fcv.mobile.db.dao.ShortageProductDAO;
import com.banvien.fcv.mobile.db.dao.StatusEndDayDAO;
import com.banvien.fcv.mobile.db.dao.StatusHomeDAO;
import com.banvien.fcv.mobile.db.dao.StatusInOutletDAO;
import com.banvien.fcv.mobile.db.dao.StatusStartDayDAO;
import com.banvien.fcv.mobile.db.dao.SurveyDAO;
import com.banvien.fcv.mobile.db.entities.CaptureAfterEntity;
import com.banvien.fcv.mobile.db.entities.CaptureBeforeEntity;
import com.banvien.fcv.mobile.db.entities.CaptureOverviewEntity;
import com.banvien.fcv.mobile.db.entities.CaptureToolEntity;
import com.banvien.fcv.mobile.db.entities.CaptureUniformEntity;
import com.banvien.fcv.mobile.db.entities.CatgroupEntity;
import com.banvien.fcv.mobile.db.entities.ComplainTypeEntity;
import com.banvien.fcv.mobile.db.entities.ConfirmWorkingEntity;
import com.banvien.fcv.mobile.db.entities.DeclineEntity;
import com.banvien.fcv.mobile.db.entities.DoSurveyAnswerEntity;
import com.banvien.fcv.mobile.db.entities.HotzoneEntity;
import com.banvien.fcv.mobile.db.entities.OutletEndDayImagesEntity;
import com.banvien.fcv.mobile.db.entities.OutletEntity;
import com.banvien.fcv.mobile.db.entities.OutletFirstImagesEntity;
import com.banvien.fcv.mobile.db.entities.OutletMerEntity;
import com.banvien.fcv.mobile.db.entities.OutletRegisteredEntity;
import com.banvien.fcv.mobile.db.entities.POSMEntity;
import com.banvien.fcv.mobile.db.entities.ProductEntity;
import com.banvien.fcv.mobile.db.entities.ProductgroupEntity;
import com.banvien.fcv.mobile.db.entities.QuestionContentEntity;
import com.banvien.fcv.mobile.db.entities.QuestionEntity;
import com.banvien.fcv.mobile.db.entities.RouteScheduleEntity;
import com.banvien.fcv.mobile.db.entities.ShortageProductEntity;
import com.banvien.fcv.mobile.db.entities.StatusEndDayEntity;
import com.banvien.fcv.mobile.db.entities.StatusHomeEntity;
import com.banvien.fcv.mobile.db.entities.StatusInOutletEntity;
import com.banvien.fcv.mobile.db.entities.StatusStartDayEntity;
import com.banvien.fcv.mobile.db.entities.SurveyEntity;
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
	private ComplainTypeDAO complainTypeDAO = null;
	private StatusHomeDAO statusHomeDAO = null;
	private StatusStartDayDAO statusStartDayDAO = null;
	private StatusInOutletDAO statusInOutletDAO = null;
	private StatusEndDayDAO statusEndDayDAO = null;
	private CaptureUniformDAO captureUniformDAO = null;
    private RouteScheduleDAO routeScheduleDAO = null;
    private OutletFirstImagesDAO outletFirstImagesDAO = null;
	private CaptureToolDAO captureToolDAO = null;
	private OutletEndDayImagesDAO outletEndDayImagesDAO = null;
	private CaptureOverviewDAO captureOverviewDAO = null;
	private ShortageProductDAO shortageProductDAO = null;
	private ConfirmWorkingDAO confirmWorkingDAO = null;
	private SurveyDAO surveyDAO = null;
	private QuestionDAO questionDAO = null;
	private QuestionContentDAO questionContentDAO = null;
	private CaptureBeforeDAO captureBeforeDAO = null;
	private CaptureAfterDAO captureAfterDAO = null;
	private DoSurveyAnswerDAO doSurveyAnswerDAO = null;
	private DeclineDAO declineDAO = null;

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
			TableUtils.createTableIfNotExists(connectionSource, HotzoneEntity.class);
			TableUtils.createTableIfNotExists(connectionSource, OutletMerEntity.class);
			TableUtils.createTableIfNotExists(connectionSource, POSMEntity.class);
			TableUtils.createTableIfNotExists(connectionSource, ProductEntity.class);
			TableUtils.createTableIfNotExists(connectionSource, CatgroupEntity.class);
			TableUtils.createTableIfNotExists(connectionSource, ProductgroupEntity.class);
			TableUtils.createTableIfNotExists(connectionSource, OutletEntity.class);
			TableUtils.createTableIfNotExists(connectionSource, OutletRegisteredEntity.class);
			TableUtils.createTableIfNotExists(connectionSource, ComplainTypeEntity.class);
            TableUtils.createTableIfNotExists(connectionSource, CaptureUniformEntity.class);
            TableUtils.createTableIfNotExists(connectionSource, RouteScheduleEntity.class);
			TableUtils.createTableIfNotExists(connectionSource, CaptureToolEntity.class);
            TableUtils.createTableIfNotExists(connectionSource, OutletFirstImagesEntity.class);
			TableUtils.createTableIfNotExists(connectionSource, OutletEndDayImagesEntity.class);
			TableUtils.createTableIfNotExists(connectionSource, CaptureOverviewEntity.class);
			TableUtils.createTableIfNotExists(connectionSource, CaptureAfterEntity.class);
			TableUtils.createTableIfNotExists(connectionSource, CaptureBeforeEntity.class);
			TableUtils.createTableIfNotExists(connectionSource, CaptureOverviewEntity.class);
			TableUtils.createTableIfNotExists(connectionSource, ShortageProductEntity.class);
            TableUtils.createTableIfNotExists(connectionSource, ConfirmWorkingEntity.class);

			TableUtils.createTableIfNotExists(connectionSource, StatusHomeEntity.class);
			TableUtils.createTableIfNotExists(connectionSource, StatusStartDayEntity.class);
			TableUtils.createTableIfNotExists(connectionSource, StatusInOutletEntity.class);
			TableUtils.createTableIfNotExists(connectionSource, StatusEndDayEntity.class);

			TableUtils.createTableIfNotExists(connectionSource, SurveyEntity.class);
			TableUtils.createTableIfNotExists(connectionSource, DoSurveyAnswerEntity.class);
			TableUtils.createTableIfNotExists(connectionSource, QuestionEntity.class);
			TableUtils.createTableIfNotExists(connectionSource, QuestionContentEntity.class);
			TableUtils.createTableIfNotExists(connectionSource, DeclineEntity.class);

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
			TableUtils.dropTable(connectionSource, ComplainTypeEntity.class, true);
            TableUtils.dropTable(connectionSource, OutletFirstImagesEntity.class, true);
			TableUtils.dropTable(connectionSource, OutletEndDayImagesEntity.class, true);
            TableUtils.dropTable(connectionSource, StatusHomeEntity.class, true);

			TableUtils.dropTable(connectionSource, StatusStartDayEntity.class, true);
			TableUtils.dropTable(connectionSource, StatusInOutletEntity.class, true);
			TableUtils.dropTable(connectionSource, StatusEndDayEntity.class, true);
			TableUtils.dropTable(connectionSource, CaptureUniformEntity.class, true);
            TableUtils.dropTable(connectionSource, RouteScheduleEntity.class, true);
			TableUtils.dropTable(connectionSource, CaptureToolEntity.class, true);
            TableUtils.dropTable(connectionSource, ConfirmWorkingEntity.class, true);

            TableUtils.dropTable(connectionSource, CaptureOverviewEntity.class, true);
			TableUtils.dropTable(connectionSource, CaptureAfterEntity.class, true);
			TableUtils.dropTable(connectionSource, CaptureBeforeEntity.class, true);
            TableUtils.dropTable(connectionSource, ShortageProductEntity.class, true);

			TableUtils.dropTable(connectionSource, SurveyEntity.class, true);
			TableUtils.dropTable(connectionSource, DoSurveyAnswerEntity.class, true);
			TableUtils.dropTable(connectionSource, QuestionEntity.class, true);
			TableUtils.dropTable(connectionSource, QuestionContentEntity.class, true);
			TableUtils.dropTable(connectionSource, DeclineEntity.class, true);

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

	public ComplainTypeDAO getComplainTypeDAO() throws SQLException {
		if(null == complainTypeDAO) {
			complainTypeDAO = new ComplainTypeDAO(getConnectionSource(), ComplainTypeEntity.class);
		}

		return complainTypeDAO;
	}

	public StatusHomeDAO getStatusHomeDAO() throws SQLException {
		if(null == statusHomeDAO) {
			statusHomeDAO = new StatusHomeDAO(getConnectionSource(), StatusHomeEntity.class);
		}

		return statusHomeDAO;
	}

	public StatusStartDayDAO getStatusStartDayDAO() throws SQLException {
		if(null == statusStartDayDAO) {
			statusStartDayDAO = new StatusStartDayDAO(getConnectionSource(), StatusStartDayEntity.class);
		}

		return statusStartDayDAO;
	}

	public StatusInOutletDAO getStatusInOutletDAO() throws SQLException {
		if(null == statusInOutletDAO) {
			statusInOutletDAO = new StatusInOutletDAO(getConnectionSource(), StatusInOutletEntity.class);
		}

		return statusInOutletDAO;
	}

	public StatusEndDayDAO getStatusEndDayDAO() throws SQLException {
		if(null == statusEndDayDAO) {
			statusEndDayDAO = new StatusEndDayDAO(getConnectionSource(), StatusEndDayEntity.class);
		}

		return statusEndDayDAO;
	}

	public CaptureUniformDAO getCaptureUniformDAO() throws SQLException {
		if(null == captureUniformDAO) {
			captureUniformDAO = new CaptureUniformDAO(getConnectionSource(), CaptureUniformEntity.class);
		}

		return captureUniformDAO;
	}

    public RouteScheduleDAO getRouteScheduleDAO() throws SQLException {
        if(null == routeScheduleDAO) {
            routeScheduleDAO = new RouteScheduleDAO(getConnectionSource(), RouteScheduleEntity.class);
        }

        return  routeScheduleDAO;
    }

	public OutletFirstImagesDAO getOutletFirstImagesDAO() throws SQLException {
		if(null == outletFirstImagesDAO) {
			outletFirstImagesDAO = new OutletFirstImagesDAO(getConnectionSource(), OutletFirstImagesEntity.class);
		}

		return outletFirstImagesDAO;
	}

	public OutletEndDayImagesDAO getOutletEndDayImagesDAO() throws SQLException {
		if(null == outletEndDayImagesDAO) {
			outletEndDayImagesDAO = new OutletEndDayImagesDAO(getConnectionSource(), OutletEndDayImagesEntity.class);
		}

		return outletEndDayImagesDAO;
	}

	public CaptureToolDAO getCaptureToolDAO() throws SQLException {
		if(null == captureToolDAO) {
			captureToolDAO = new CaptureToolDAO(getConnectionSource(), CaptureToolEntity.class);
		}

		return captureToolDAO;
	}

    public CaptureOverviewDAO getCaptureOverviewDAO() throws SQLException {
        if(null == captureOverviewDAO) {
            captureOverviewDAO = new CaptureOverviewDAO(getConnectionSource(), CaptureOverviewEntity.class);
        }

        return captureOverviewDAO;
    }

    public ShortageProductDAO getShortageProductDAO() throws SQLException {
        if(null == shortageProductDAO) {
            shortageProductDAO = new ShortageProductDAO(getConnectionSource(), ShortageProductEntity.class);
        }

        return shortageProductDAO;
    }

    public ConfirmWorkingDAO getConfirmWorkingDAO() throws SQLException {
        if(null == confirmWorkingDAO) {
            confirmWorkingDAO = new ConfirmWorkingDAO(getConnectionSource(), ConfirmWorkingEntity.class);
        }

        return confirmWorkingDAO;
    }


	public CaptureBeforeDAO getCaptureBeforeDAO() throws SQLException {
		if(null == captureBeforeDAO) {
			captureBeforeDAO = new CaptureBeforeDAO(getConnectionSource(), CaptureBeforeEntity.class);
		}

		return captureBeforeDAO;
	}

	public CaptureAfterDAO getCaptureAfterDAO() throws SQLException {
		if(null == captureAfterDAO) {
			captureAfterDAO = new CaptureAfterDAO(getConnectionSource(), CaptureAfterEntity.class);
		}

		return captureAfterDAO;
	}


	public SurveyDAO getSurveyDAO() throws SQLException {
		if(null == surveyDAO) {
			surveyDAO = new SurveyDAO(getConnectionSource(), SurveyEntity.class);
		}

		return surveyDAO;
	}

	public QuestionDAO getQuestionDAO() throws SQLException {
		if(null == questionDAO) {
			questionDAO = new QuestionDAO(getConnectionSource(), QuestionEntity.class);
		}
		return questionDAO;
	}

	public QuestionContentDAO getQuestionContentDAO() throws SQLException {
		if(null == questionContentDAO) {
			questionContentDAO = new QuestionContentDAO(getConnectionSource(), QuestionContentEntity.class);
		}
		return questionContentDAO;
	}

	public DoSurveyAnswerDAO getDoSurveyAnswerDAO() throws SQLException {
		if(null == doSurveyAnswerDAO) {
			doSurveyAnswerDAO = new DoSurveyAnswerDAO(getConnectionSource(), DoSurveyAnswerEntity.class);
		}

		return doSurveyAnswerDAO;
	}

	public DeclineDAO getDeclineDAO() throws SQLException {
		if(null == declineDAO) {
			declineDAO = new DeclineDAO(getConnectionSource(), DeclineEntity.class);
		}

		return declineDAO;
	}



	/**
	 * Close the database connections and clear any cached DAOs.
	 */
	@Override
	public void close() {
		super.close();
	}

}
