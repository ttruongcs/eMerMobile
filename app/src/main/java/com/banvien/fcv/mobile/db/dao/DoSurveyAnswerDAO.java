package com.banvien.fcv.mobile.db.dao;


import com.banvien.fcv.mobile.db.AndroidBaseDaoImpl;
import com.banvien.fcv.mobile.db.entities.DoSurveyAnswerEntity;
import com.banvien.fcv.mobile.utils.ELog;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

import java.sql.SQLException;

/**
 * Created by hieu on 8/03/2016.
 */
public class DoSurveyAnswerDAO extends AndroidBaseDaoImpl<DoSurveyAnswerEntity, Long> {
    public DoSurveyAnswerDAO(Class<DoSurveyAnswerEntity> dataClass) throws SQLException {
        super(dataClass);
    }

    public DoSurveyAnswerDAO(ConnectionSource connectionSource, Class<DoSurveyAnswerEntity> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public DoSurveyAnswerDAO(ConnectionSource connectionSource, DatabaseTableConfig<DoSurveyAnswerEntity> tableConfig) throws SQLException {
        super(connectionSource, tableConfig);
    }

    public void add(DoSurveyAnswerEntity data) {
        try {
            create(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void remove(Long outletId) throws SQLException {
        try {
            DeleteBuilder<DoSurveyAnswerEntity, Long> deleteBuilder = deleteBuilder();
            deleteBuilder.where().eq("outletId", outletId);
            deleteBuilder.delete();
        } catch (SQLException e) {
            ELog.e(e.getMessage(), e);
        }
    }
}
