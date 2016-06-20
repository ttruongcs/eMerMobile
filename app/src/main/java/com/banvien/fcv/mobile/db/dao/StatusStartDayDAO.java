package com.banvien.fcv.mobile.db.dao;


import com.banvien.fcv.mobile.db.AndroidBaseDaoImpl;
import com.banvien.fcv.mobile.db.entities.StatusStartDayEntity;
import com.banvien.fcv.mobile.db.entities.StatusStartDayEntity;
import com.banvien.fcv.mobile.utils.ELog;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hieu on 8/03/2016.
 */
public class StatusStartDayDAO extends AndroidBaseDaoImpl<StatusStartDayEntity, String> {
    public StatusStartDayDAO(Class<StatusStartDayEntity> dataClass) throws SQLException {
        super(dataClass);
    }

    public StatusStartDayDAO(ConnectionSource connectionSource, Class<StatusStartDayEntity> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public StatusStartDayDAO(ConnectionSource connectionSource, DatabaseTableConfig<StatusStartDayEntity> tableConfig) throws SQLException {
        super(connectionSource, tableConfig);
    }

    public void addStatusHome(StatusStartDayEntity data) {
        ELog.d("data", data.toString());
        try {
            create(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public StatusStartDayEntity getConfigStartDayHome() {
        List<StatusStartDayEntity> result = new ArrayList<>();
        try {
            result  = queryForAll();
        } catch (SQLException e) {
            ELog.d(e.getMessage(), e);
        }
        if(result.size() == 0) return null;
        return result.get(0);
    }
}
