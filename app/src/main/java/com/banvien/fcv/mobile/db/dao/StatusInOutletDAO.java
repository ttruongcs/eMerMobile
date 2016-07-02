package com.banvien.fcv.mobile.db.dao;


import com.banvien.fcv.mobile.db.AndroidBaseDaoImpl;
import com.banvien.fcv.mobile.db.entities.StatusInOutletEntity;
import com.banvien.fcv.mobile.db.entities.StatusInOutletEntity;
import com.banvien.fcv.mobile.utils.ELog;
import com.j256.ormlite.stmt.PreparedQuery;
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

    public StatusInOutletEntity getConfigStatusInOutletHome(Long routeScheduleDetailId) {
        StatusInOutletEntity result = null;
        QueryBuilder<StatusInOutletEntity, String> queryBuilder = queryBuilder();
        PreparedQuery<StatusInOutletEntity> preparedQuery = null;
        try {
            queryBuilder.where().eq("routeScheduleDetailId", routeScheduleDetailId);
            preparedQuery = queryBuilder.prepare();
            result  = queryForFirst(preparedQuery);
        } catch (SQLException e) {
            ELog.d(e.getMessage(), e);
        }

        return result;
    }

    public void clearData() throws SQLException {
        if(isTableExists()) {
            ELog.d("clear Data Status In Outlet");
            deleteBuilder().delete();
        }
    }

    public boolean updateStatus(String now, String[] next, Long routeScheduleDetailId) {
        boolean result = false;
        UpdateBuilder<StatusInOutletEntity, String> updateBuilder = updateBuilder();

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
            updateBuilder.where().eq("routeScheduleDetailId", routeScheduleDetailId);
            long countOf = updateBuilder.update();
            if(countOf > 0) {
                result = true;
            }
        } catch (SQLException e) {
            ELog.d(e.getMessage(), e);
        }

        return result;
    }

    public boolean updateStatus(String now, String[] next, Long routeScheduleDetailId, boolean hidden) {
        boolean result = false;
        UpdateBuilder<StatusInOutletEntity, String> updateBuilder = updateBuilder();

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
                            if(hidden) {
                                updateBuilder.updateColumnValue(next[i], 0);
                            } else {
                                updateBuilder.updateColumnValue(next[i], 2);
                            }

                        }

                    }
                }


            }
            updateBuilder.where().eq("routeScheduleDetailId", routeScheduleDetailId);
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
