package com.banvien.fcv.mobile.db.dao;

import com.banvien.fcv.mobile.db.AndroidBaseDaoImpl;
import com.banvien.fcv.mobile.db.entities.CaptureBeforeEntity;
import com.banvien.fcv.mobile.utils.ELog;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Linh Nguyen on 6/23/2016.
 */
public class CaptureBeforeDAO extends AndroidBaseDaoImpl<CaptureBeforeEntity, String> {
    public CaptureBeforeDAO(Class<CaptureBeforeEntity> dataClass) throws SQLException {
        super(dataClass);
    }

    public CaptureBeforeDAO(ConnectionSource connectionSource, Class<CaptureBeforeEntity> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public CaptureBeforeDAO(ConnectionSource connectionSource, DatabaseTableConfig<CaptureBeforeEntity> tableConfig) throws SQLException {
        super(connectionSource, tableConfig);
    }

    public List<CaptureBeforeEntity> findAll() {
        List<CaptureBeforeEntity> results = new ArrayList<>();

        try {
            List<CaptureBeforeEntity> entities = queryForAll();
            if(entities.size() > 0) {
                return results;
            }
        } catch (SQLException e) {
            ELog.d(e.getMessage(), e);
        }
        return results;
    }

    public void deleteImageFromId(Long id) {
        try {
            DeleteBuilder<CaptureBeforeEntity, String> deleteBuilder = deleteBuilder();
            deleteBuilder.where().eq("_id", id);
            deleteBuilder.delete();
        } catch (SQLException e) {
            ELog.d(e.getMessage(), e);
        }
    }

    public void clearData() throws SQLException {
        if(isTableExists()) {
            ELog.d("clear Status End Day");
            deleteBuilder().delete();
        }
    }

    public List<CaptureBeforeEntity> findByOutletId(Long outletId) {
        try {
            List<CaptureBeforeEntity> results = new ArrayList<>();
            List<CaptureBeforeEntity> result = queryBuilder().where().eq("outletId", outletId).query();
            if(result.size() > 0) {
                return result;
            }
            return results;
        } catch (SQLException e) {
            ELog.d("Error findByOutletId in CaptureBeforeEntity");
        }
        return null;
    }
}
