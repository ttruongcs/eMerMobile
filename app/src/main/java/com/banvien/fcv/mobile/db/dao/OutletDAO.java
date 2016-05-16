package com.banvien.fcv.mobile.db.dao;


import com.banvien.fcv.mobile.db.AndroidBaseDaoImpl;
import com.banvien.fcv.mobile.db.entities.OutletEntity;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

import java.sql.SQLException;

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
}
