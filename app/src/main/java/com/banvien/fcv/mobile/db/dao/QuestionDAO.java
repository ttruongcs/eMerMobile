package com.banvien.fcv.mobile.db.dao;


import com.banvien.fcv.mobile.db.AndroidBaseDaoImpl;
import com.banvien.fcv.mobile.db.entities.QuestionEntity;
import com.banvien.fcv.mobile.utils.ELog;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

import java.sql.SQLException;

/**
 * Created by hieu on 8/03/2016.
 */
public class QuestionDAO extends AndroidBaseDaoImpl<QuestionEntity, Long> {
    public QuestionDAO(Class<QuestionEntity> dataClass) throws SQLException {
        super(dataClass);
    }

    public QuestionDAO(ConnectionSource connectionSource, Class<QuestionEntity> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public QuestionDAO(ConnectionSource connectionSource, DatabaseTableConfig<QuestionEntity> tableConfig) throws SQLException {
        super(connectionSource, tableConfig);
    }

    public void add(QuestionEntity data) {
        try {
            create(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void remove(Long surveyId) throws SQLException {
        try {
            DeleteBuilder<QuestionEntity, Long> deleteBuilder = deleteBuilder();
            deleteBuilder.where().eq("surveyId", surveyId);
            deleteBuilder.delete();
        } catch (SQLException e) {
            ELog.e(e.getMessage(), e);
        }
    }
}
