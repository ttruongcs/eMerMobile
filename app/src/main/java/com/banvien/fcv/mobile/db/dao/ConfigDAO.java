package com.banvien.fcv.mobile.db.dao;


import com.banvien.fcv.mobile.db.AndroidBaseDaoImpl;
import com.banvien.fcv.mobile.db.entities.ConfigEntity;
import com.banvien.fcv.mobile.db.entities.HotzoneEntity;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

import java.sql.SQLException;

/**
 * Created by hieu on 8/03/2016.
 */
public class ConfigDAO extends AndroidBaseDaoImpl<ConfigEntity, String> {
    public ConfigDAO(Class<ConfigEntity> dataClass) throws SQLException {
        super(dataClass);
    }

    public ConfigDAO(ConnectionSource connectionSource, Class<ConfigEntity> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public ConfigDAO(ConnectionSource connectionSource, DatabaseTableConfig<ConfigEntity> tableConfig) throws SQLException {
        super(connectionSource, tableConfig);
    }
}
