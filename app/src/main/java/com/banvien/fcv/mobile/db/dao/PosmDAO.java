package com.banvien.fcv.mobile.db.dao;


import com.banvien.fcv.mobile.db.AndroidBaseDaoImpl;
import com.banvien.fcv.mobile.db.entities.HotzoneEntity;
import com.banvien.fcv.mobile.db.entities.POSMEntity;
import com.banvien.fcv.mobile.utils.ELog;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

import java.sql.SQLException;
import java.util.Date;

/**
 * Created by hieu on 8/03/2016.
 */
public class PosmDAO extends AndroidBaseDaoImpl<POSMEntity, String> {
    public PosmDAO(Class<POSMEntity> dataClass) throws SQLException {
        super(dataClass);
    }

    public PosmDAO(ConnectionSource connectionSource, Class<POSMEntity> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public PosmDAO(ConnectionSource connectionSource, DatabaseTableConfig<POSMEntity> tableConfig) throws SQLException {
        super(connectionSource, tableConfig);
    }

    public void addPOSMEntity(POSMEntity data) {
        ELog.d("data", data.toString());
        try {
            create(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
