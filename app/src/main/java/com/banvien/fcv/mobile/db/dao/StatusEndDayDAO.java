package com.banvien.fcv.mobile.db.dao;


import com.banvien.fcv.mobile.db.AndroidBaseDaoImpl;
import com.banvien.fcv.mobile.db.entities.StatusEndDayEntity;
import com.banvien.fcv.mobile.utils.ELog;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hieu on 8/03/2016.
 */
public class StatusEndDayDAO extends AndroidBaseDaoImpl<StatusEndDayEntity, String> {
    public StatusEndDayDAO(Class<StatusEndDayEntity> dataClass) throws SQLException {
        super(dataClass);
    }

    public StatusEndDayDAO(ConnectionSource connectionSource, Class<StatusEndDayEntity> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public StatusEndDayDAO(ConnectionSource connectionSource, DatabaseTableConfig<StatusEndDayEntity> tableConfig) throws SQLException {
        super(connectionSource, tableConfig);
    }

    public void addStatusHome(StatusEndDayEntity data) {
        ELog.d("data", data.toString());
        try {
            create(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public StatusEndDayEntity getConfigStatusEndDayHome() {
        List<StatusEndDayEntity> result = new ArrayList<>();
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
            ELog.d("clear Status End Day");
            deleteBuilder().delete();
        }
    }
}
