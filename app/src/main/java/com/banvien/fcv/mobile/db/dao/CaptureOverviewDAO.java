package com.banvien.fcv.mobile.db.dao;

import com.banvien.fcv.mobile.beanutil.CaptureOverviewUtil;
import com.banvien.fcv.mobile.db.AndroidBaseDaoImpl;
import com.banvien.fcv.mobile.db.entities.CaptureOverviewEntity;
import com.banvien.fcv.mobile.dto.CaptureOverviewDTO;
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
public class CaptureOverviewDAO extends AndroidBaseDaoImpl<CaptureOverviewEntity, String> {
    public CaptureOverviewDAO(Class<CaptureOverviewEntity> dataClass) throws SQLException {
        super(dataClass);
    }

    public CaptureOverviewDAO(ConnectionSource connectionSource, Class<CaptureOverviewEntity> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public CaptureOverviewDAO(ConnectionSource connectionSource, DatabaseTableConfig<CaptureOverviewEntity> tableConfig) throws SQLException {
        super(connectionSource, tableConfig);
    }

    public List<CaptureOverviewDTO> findAll() {
        List<CaptureOverviewDTO> results = new ArrayList<>();

        try {
            List<CaptureOverviewEntity> entities = queryForAll();
            if(entities.size() > 0) {
                for(CaptureOverviewEntity entity : entities) {
                    results.add(CaptureOverviewUtil.convertToDTO(entity));
                }
            }
        } catch (SQLException e) {
            ELog.d(e.getMessage(), e);
        }
        return results;
    }

    public void deleteImageFromId(Long id) {
        try {
            DeleteBuilder<CaptureOverviewEntity, String> deleteBuilder = deleteBuilder();
            deleteBuilder.where().eq("_id", id);
            deleteBuilder.delete();
        } catch (SQLException e) {
            ELog.d(e.getMessage(), e);
        }
    }
}
