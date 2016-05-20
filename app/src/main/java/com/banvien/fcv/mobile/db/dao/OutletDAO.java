package com.banvien.fcv.mobile.db.dao;


import com.banvien.fcv.mobile.beanutil.OutletUtil;
import com.banvien.fcv.mobile.db.AndroidBaseDaoImpl;
import com.banvien.fcv.mobile.db.entities.OutletEntity;
import com.banvien.fcv.mobile.dto.OutletDTO;
import com.banvien.fcv.mobile.utils.ELog;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import java.util.List;

public class OutletDAO extends AndroidBaseDaoImpl<OutletEntity, String> {
    public OutletDAO(Class<OutletEntity> dataClass) throws SQLException {
        super(dataClass);
    }

    public OutletDAO(ConnectionSource connectionSource, Class<OutletEntity> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public OutletDAO(ConnectionSource connectionSource, DatabaseTableConfig<OutletEntity> tableConfig) throws SQLException {
        super(connectionSource, tableConfig);
    }

    public void addOutletEntity(OutletEntity data) {
        ELog.d("data", data.toString());
        try {
            create(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public OutletEntity findById(Long outletId) {
        try {
            List<OutletEntity> results = queryBuilder().where().eq("outletId", outletId).query();
            if(results.size() > 0) {
                return results.get(0);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<OutletDTO> getOutletsWithCircumstance(String outletStatus) {
        ELog.d("data", outletStatus);
        List<OutletDTO> results = new ArrayList<>();
        try {
            List<OutletEntity> outletEntities = queryForEq("status", outletStatus);
            for(OutletEntity outletEntity : outletEntities) {
                results.add(OutletUtil.convertToDTO(outletEntity));
            }
        } catch (SQLException e) {
            ELog.e(e.getMessage());
        }
        return results;
    }
}
