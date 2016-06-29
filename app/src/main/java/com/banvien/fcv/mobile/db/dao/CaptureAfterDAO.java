package com.banvien.fcv.mobile.db.dao;

import com.banvien.fcv.mobile.db.AndroidBaseDaoImpl;
import com.banvien.fcv.mobile.db.entities.CaptureAfterEntity;
import com.banvien.fcv.mobile.db.entities.CaptureBeforeEntity;
import com.banvien.fcv.mobile.utils.ELog;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Linh Nguyen on 6/23/2016.
 */
public class CaptureAfterDAO extends AndroidBaseDaoImpl<CaptureAfterEntity, String> {
    public CaptureAfterDAO(Class<CaptureAfterEntity> dataClass) throws SQLException {
        super(dataClass);
    }

    public CaptureAfterDAO(ConnectionSource connectionSource, Class<CaptureAfterEntity> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public CaptureAfterDAO(ConnectionSource connectionSource, DatabaseTableConfig<CaptureAfterEntity> tableConfig) throws SQLException {
        super(connectionSource, tableConfig);
    }

    public List<CaptureAfterEntity> findAll() {
        List<CaptureAfterEntity> results = new ArrayList<>();

        try {
            List<CaptureAfterEntity> entities = queryForAll();
            if(entities.size() > 0) {
                return results;
            }
        } catch (SQLException e) {
            ELog.d(e.getMessage(), e);
        }
        return results;
    }

    public List<CaptureAfterEntity> findByOutletId(Long outletId) {
        try {
            List<CaptureAfterEntity> results = new ArrayList<>();
            List<CaptureAfterEntity> result = queryBuilder().where().eq("outletId", outletId).query();
            if(result.size() > 0) {
                return result;
            }
        } catch (SQLException e) {
            ELog.d("Error findByOutletId in CaptureBeforeEntity");
        }
        return null;
    }

    public void deleteImageFromId(Long id) {
        try {
            DeleteBuilder<CaptureAfterEntity, String> deleteBuilder = deleteBuilder();
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

    public boolean checkCaptured(Long outletId) {
        boolean isCaptured = false;
        QueryBuilder<CaptureAfterEntity, String> queryBuilder = queryBuilder();

        try {
            long rowFound = queryBuilder.where().eq("outletId", outletId).countOf();
            if(rowFound > 0) {
                isCaptured = true;
            }
        } catch (SQLException e) {
            ELog.d(e.getMessage(), e);
        }

        return isCaptured;
    }
}
