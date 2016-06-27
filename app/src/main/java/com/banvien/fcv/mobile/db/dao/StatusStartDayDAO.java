package com.banvien.fcv.mobile.db.dao;


import com.banvien.fcv.mobile.db.AndroidBaseDaoImpl;
import com.banvien.fcv.mobile.db.entities.StatusStartDayEntity;
import com.banvien.fcv.mobile.db.entities.StatusStartDayEntity;
import com.banvien.fcv.mobile.utils.ELog;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;
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

    public void clearData() throws SQLException {
        if(isTableExists()) {
            ELog.d("clear Data Status Start Day");
            deleteBuilder().delete();
        }
    }

    public boolean updateStatus(String now, String[] next) {
        boolean result = false;
        UpdateBuilder<StatusStartDayEntity, String> updateBuilder = updateBuilder();

        try {
            updateBuilder.updateColumnValue(now, 2);
            if(next != null && next.length > 0) {
                if(next.length == 1) {
                    updateBuilder.updateColumnValue(next[0], 1);
                } else {
                    for(int i=0 ; i < next.length; i++) {
                        if(i == (next.length - 1)) {
                            updateBuilder.updateColumnValue(next[i], 1);
                        } else {
                            updateBuilder.updateColumnValue(next[i], 2);
                        }

                    }
                }


            }

            long countOf = updateBuilder.update();
            if(countOf > 0) {
                result = true;
            }
        } catch (SQLException e) {
            ELog.d(e.getMessage(), e);
        }

        return result;
    }
}
