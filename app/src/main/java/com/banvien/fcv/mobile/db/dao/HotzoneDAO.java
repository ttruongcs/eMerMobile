package com.banvien.fcv.mobile.db.dao;


import com.banvien.fcv.mobile.db.AndroidBaseDaoImpl;
import com.banvien.fcv.mobile.db.entities.HotzoneEntity;
import com.banvien.fcv.mobile.db.entities.OutletMerEntity;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

import java.sql.SQLException;

/**
 * Created by hieu on 8/03/2016.
 */
public class HotzoneDAO extends AndroidBaseDaoImpl<HotzoneEntity, String> {
    public HotzoneDAO(Class<HotzoneEntity> dataClass) throws SQLException {
        super(dataClass);
    }

    public HotzoneDAO(ConnectionSource connectionSource, Class<HotzoneEntity> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public HotzoneDAO(ConnectionSource connectionSource, DatabaseTableConfig<HotzoneEntity> tableConfig) throws SQLException {
        super(connectionSource, tableConfig);
    }
}
