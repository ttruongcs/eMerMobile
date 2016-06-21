package com.banvien.fcv.mobile.db.dao;

import com.banvien.fcv.mobile.beanutil.CaptureUniformUtil;
import com.banvien.fcv.mobile.db.AndroidBaseDaoImpl;
import com.banvien.fcv.mobile.db.entities.CaptureUniformEntity;
import com.banvien.fcv.mobile.db.entities.OutletMerEntity;
import com.banvien.fcv.mobile.dto.CaptureUniformDTO;
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
public class CaptureUniformDAO extends AndroidBaseDaoImpl<CaptureUniformEntity, String> {
    public CaptureUniformDAO(Class<CaptureUniformEntity> dataClass) throws SQLException {
        super(dataClass);
    }

    public CaptureUniformDAO(ConnectionSource connectionSource, Class<CaptureUniformEntity> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public CaptureUniformDAO(ConnectionSource connectionSource, DatabaseTableConfig<CaptureUniformEntity> tableConfig) throws SQLException {
        super(connectionSource, tableConfig);
    }

    public List<CaptureUniformDTO> findAll() {
        List<CaptureUniformDTO> results = new ArrayList<>();

        try {
            List<CaptureUniformEntity> entities = queryForAll();
            if(entities.size() > 0) {
                for(CaptureUniformEntity entity : entities) {
                    results.add(CaptureUniformUtil.convertToDTO(entity));
                }
            }
        } catch (SQLException e) {
            ELog.d(e.getMessage(), e);
        }
        return results;
    }

    public void deleteImageFromId(Long id) {
        try {
            DeleteBuilder<CaptureUniformEntity, String> deleteBuilder = deleteBuilder();
            deleteBuilder.where().eq("_id", id);
            deleteBuilder.delete();
        } catch (SQLException e) {
            ELog.d(e.getMessage(), e);
        }
    }
}
