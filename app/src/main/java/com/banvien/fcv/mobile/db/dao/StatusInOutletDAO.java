package com.banvien.fcv.mobile.db.dao;


import com.banvien.fcv.mobile.db.AndroidBaseDaoImpl;
import com.banvien.fcv.mobile.db.entities.StatusInOutletEntity;
import com.banvien.fcv.mobile.db.entities.StatusInOutletEntity;
import com.banvien.fcv.mobile.utils.ELog;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hieu on 8/03/2016.
 */
public class StatusInOutletDAO extends AndroidBaseDaoImpl<StatusInOutletEntity, String> {
    public StatusInOutletDAO(Class<StatusInOutletEntity> dataClass) throws SQLException {
        super(dataClass);
    }

    public StatusInOutletDAO(ConnectionSource connectionSource, Class<StatusInOutletEntity> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public StatusInOutletDAO(ConnectionSource connectionSource, DatabaseTableConfig<StatusInOutletEntity> tableConfig) throws SQLException {
        super(connectionSource, tableConfig);
    }

    public void addStatusHome(StatusInOutletEntity data) {
        ELog.d("data", data.toString());
        try {
            create(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public StatusInOutletEntity getConfigStatusInOutletHome() {
        List<StatusInOutletEntity> result = new ArrayList<>();
        try {
            result  = queryForAll();
        } catch (SQLException e) {
            ELog.d(e.getMessage(), e);
        }
        if(result.size() == 0) return null;
        return result.get(0);
    }

    public void clearData() throws SQLException {
        if(isTableExists()) {
            ELog.d("clear Data Status In Outlet");
            deleteBuilder().delete();
        }
    }
}
