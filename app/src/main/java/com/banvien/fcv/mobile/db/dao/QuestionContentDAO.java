package com.banvien.fcv.mobile.db.dao;


import com.banvien.fcv.mobile.db.AndroidBaseDaoImpl;
import com.banvien.fcv.mobile.db.entities.QuestionContentEntity;
import com.banvien.fcv.mobile.utils.ELog;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

import java.sql.SQLException;

/**
 * Created by hieu on 8/03/2016.
 */
public class QuestionContentDAO extends AndroidBaseDaoImpl<QuestionContentEntity, Long> {
    public QuestionContentDAO(Class<QuestionContentEntity> dataClass) throws SQLException {
        super(dataClass);
    }

    public QuestionContentDAO(ConnectionSource connectionSource, Class<QuestionContentEntity> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public QuestionContentDAO(ConnectionSource connectionSource, DatabaseTableConfig<QuestionContentEntity> tableConfig) throws SQLException {
        super(connectionSource, tableConfig);
    }

    public void add(QuestionContentEntity data) {
        try {
            create(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void remove(Long questionId) throws SQLException {
        try {
            DeleteBuilder<QuestionContentEntity, Long> deleteBuilder = deleteBuilder();
            deleteBuilder.where().eq("questionId", questionId);
            deleteBuilder.delete();
        } catch (SQLException e) {
            ELog.e(e.getMessage(), e);
        }
    }
}
