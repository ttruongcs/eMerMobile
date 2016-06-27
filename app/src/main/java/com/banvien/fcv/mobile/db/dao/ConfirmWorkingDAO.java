package com.banvien.fcv.mobile.db.dao;

import com.banvien.fcv.mobile.db.AndroidBaseDaoImpl;
import com.banvien.fcv.mobile.db.entities.ConfirmWorkingEntity;
import com.banvien.fcv.mobile.utils.ELog;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Linh Nguyen on 6/25/2016.
 */
public class ConfirmWorkingDAO extends AndroidBaseDaoImpl<ConfirmWorkingEntity, String> {
    public ConfirmWorkingDAO(Class<ConfirmWorkingEntity> dataClass) throws SQLException {
        super(dataClass);
    }

    public ConfirmWorkingDAO(ConnectionSource connectionSource, Class<ConfirmWorkingEntity> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public ConfirmWorkingDAO(ConnectionSource connectionSource, DatabaseTableConfig<ConfirmWorkingEntity> tableConfig) throws SQLException {
        super(connectionSource, tableConfig);
    }

    public void clearData() throws SQLException {
        if(isTableExists()) {
            ELog.d("clear Data confirm working");
            deleteBuilder().delete();
        }
    }

    public List<ConfirmWorkingEntity> findAll() {
        List<ConfirmWorkingEntity> entities = new ArrayList<>();
        try {
            entities = queryForAll();
        } catch (SQLException e) {
            ELog.d(e.getMessage(), e);
        }

        return entities;
    }

    public void deleteImageFromId(Long id) {
        try {
            DeleteBuilder<ConfirmWorkingEntity, String> deleteBuilder = deleteBuilder();
            deleteBuilder.where().eq("_id", id);
            deleteBuilder.delete();
        } catch (SQLException e) {
            ELog.d(e.getMessage(), e);
        }
    }
}
