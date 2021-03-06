package com.banvien.fcv.mobile.db.dao;


import com.banvien.fcv.mobile.beanutil.ProductGroupUtil;
import com.banvien.fcv.mobile.db.AndroidBaseDaoImpl;
import com.banvien.fcv.mobile.db.entities.ProductgroupEntity;
import com.banvien.fcv.mobile.db.entities.StatusHomeEntity;
import com.banvien.fcv.mobile.dto.ProductgroupDTO;
import com.banvien.fcv.mobile.utils.ELog;
import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.dao.RawRowMapper;
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
public class StatusHomeDAO extends AndroidBaseDaoImpl<StatusHomeEntity, String> {
    public StatusHomeDAO(Class<StatusHomeEntity> dataClass) throws SQLException {
        super(dataClass);
    }

    public StatusHomeDAO(ConnectionSource connectionSource, Class<StatusHomeEntity> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public StatusHomeDAO(ConnectionSource connectionSource, DatabaseTableConfig<StatusHomeEntity> tableConfig) throws SQLException {
        super(connectionSource, tableConfig);
    }

    public void addStatusHome(StatusHomeEntity data) {
        ELog.d("data", data.toString());
        try {
            create(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public StatusHomeEntity getConfigStatusHome() {
        List<StatusHomeEntity> result = new ArrayList<>();
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
            ELog.d("clear Data Status Home");
            deleteBuilder().delete();
        }
    }

    public boolean updateStatus(String now, String next) {
        boolean result = false;
        QueryBuilder<StatusHomeEntity, String> queryBuilder = queryBuilder();
        UpdateBuilder<StatusHomeEntity, String> updateBuilder = updateBuilder();

        try {
            if (queryBuilder.where().eq(now, 2).countOf() <= 0) {
                updateBuilder.updateColumnValue(now, 2);
                if(next != null) {
                    updateBuilder.updateColumnValue(next, 1);
                }

                long countOf = updateBuilder.update();
                if(countOf > 0) {
                    result = true;
                }
            }
        } catch (SQLException e) {
            ELog.d(e.getMessage(), e);
        }

        return result;
    }

    public boolean updateStatus(String now, String[] next) {
        boolean result = false;
        UpdateBuilder<StatusHomeEntity, String> updateBuilder = updateBuilder();

        try {
            updateBuilder.updateColumnValue(now, 2);
            if(next != null && next.length > 0) {
                for(int i = 0; i < next.length; i++) {
                    updateBuilder.updateColumnValue(next[i], 1);
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
