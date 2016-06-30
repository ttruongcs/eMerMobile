package com.banvien.fcv.mobile.db.dao;


import com.banvien.fcv.mobile.db.AndroidBaseDaoImpl;
import com.banvien.fcv.mobile.db.entities.SurveyEntity;
import com.banvien.fcv.mobile.utils.ELog;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

import java.sql.SQLException;

/**
 * Created by hieu on 8/03/2016.
 */
public class SurveyDAO extends AndroidBaseDaoImpl<SurveyEntity, Long> {
    public SurveyDAO(Class<SurveyEntity> dataClass) throws SQLException {
        super(dataClass);
    }

    public SurveyDAO(ConnectionSource connectionSource, Class<SurveyEntity> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public SurveyDAO(ConnectionSource connectionSource, DatabaseTableConfig<SurveyEntity> tableConfig) throws SQLException {
        super(connectionSource, tableConfig);
    }

    public void add(SurveyEntity data) {
        try {
            create(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public long count(Long outletId) {
        try {
            return queryBuilder().where().eq("outletId", outletId).countOf();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void remove(Long outletId) throws SQLException {
        try {
            DeleteBuilder<SurveyEntity, Long> deleteBuilder = deleteBuilder();
            deleteBuilder.where().eq("outletId", outletId);
            deleteBuilder.delete();
        } catch (SQLException e) {
            ELog.e(e.getMessage(), e);
        }
    }
}
