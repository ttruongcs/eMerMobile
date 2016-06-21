package com.banvien.fcv.mobile.db.dao;

import com.banvien.fcv.mobile.beanutil.CaptureToolUtil;
import com.banvien.fcv.mobile.db.AndroidBaseDaoImpl;
import com.banvien.fcv.mobile.db.entities.CaptureToolEntity;
import com.banvien.fcv.mobile.dto.CaptureToolDTO;
import com.banvien.fcv.mobile.utils.ELog;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Linh Nguyen on 6/21/2016.
 */
public class CaptureToolDAO extends AndroidBaseDaoImpl<CaptureToolEntity, String> {
    public CaptureToolDAO(Class<CaptureToolEntity> dataClass) throws SQLException {
        super(dataClass);
    }

    public CaptureToolDAO(ConnectionSource connectionSource, Class<CaptureToolEntity> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public CaptureToolDAO(ConnectionSource connectionSource, DatabaseTableConfig<CaptureToolEntity> tableConfig) throws SQLException {
        super(connectionSource, tableConfig);
    }

    public List<CaptureToolDTO> findAll() {
        List<CaptureToolDTO> results = new ArrayList<>();

        try {
            List<CaptureToolEntity> entities = queryForAll();
            if(entities.size() > 0) {
                for(CaptureToolEntity entity : entities) {
                    results.add(CaptureToolUtil.convertToDTO(entity));
                }
            }
        } catch (SQLException e) {
            ELog.d(e.getMessage(), e);
        }
        return results;
    }

    public void deleteImageFromId(Long id) {
        try {
            DeleteBuilder<CaptureToolEntity, String> deleteBuilder = deleteBuilder();
            deleteBuilder.where().eq("_id", id);
            deleteBuilder.delete();
        } catch (SQLException e) {
            ELog.d(e.getMessage(), e);
        }
    }
}
