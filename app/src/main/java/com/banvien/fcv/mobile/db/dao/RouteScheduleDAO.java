package com.banvien.fcv.mobile.db.dao;

import com.banvien.fcv.mobile.db.AndroidBaseDaoImpl;
import com.banvien.fcv.mobile.db.entities.RouteScheduleEntity;
import com.banvien.fcv.mobile.dto.routeschedule.RouteScheduleDTO;
import com.banvien.fcv.mobile.utils.ELog;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

import java.sql.SQLException;

/**
 * Created by Linh Nguyen on 6/21/2016.
 */
public class RouteScheduleDAO extends AndroidBaseDaoImpl<RouteScheduleEntity, String> {

    public RouteScheduleDAO(Class<RouteScheduleEntity> dataClass) throws SQLException {
        super(dataClass);
    }

    public RouteScheduleDAO(ConnectionSource connectionSource, Class<RouteScheduleEntity> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public RouteScheduleDAO(ConnectionSource connectionSource, DatabaseTableConfig<RouteScheduleEntity> tableConfig) throws SQLException {
        super(connectionSource, tableConfig);
    }

    public RouteScheduleEntity findRoute() {
        RouteScheduleEntity result = new RouteScheduleEntity();
        try {
            RouteScheduleEntity entity = queryBuilder().queryForFirst();

            if(entity != null) {
                result = entity;
            }
        } catch (SQLException e) {
            ELog.d(e.getMessage(), e);
        }

        return result;
    }

    public void addRoute(RouteScheduleEntity data) {
        ELog.d("data", data.toString());
        try {
            create(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void clearData() throws SQLException {
        if(isTableExists()) {
            ELog.d("clear Route Schedule");
            deleteBuilder().delete();
        }
    }
}
