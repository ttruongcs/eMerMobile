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

    public RouteScheduleDTO findRoute() {
        RouteScheduleDTO result = new RouteScheduleDTO();
        try {
            RouteScheduleEntity entity = queryBuilder().queryForFirst();

            if(entity != null) {
                result.set_id(entity.get_id());
                result.setRouteScheduleId(entity.getRouteScheduleId());
            }
        } catch (SQLException e) {
            ELog.d(e.getMessage(), e);
        }

        return result;
    }
}
