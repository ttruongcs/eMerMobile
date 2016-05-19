package com.banvien.fcv.mobile.db.dao;


import com.banvien.fcv.mobile.db.AndroidBaseDaoImpl;
import com.banvien.fcv.mobile.db.entities.OutletMerEntity;
import com.banvien.fcv.mobile.db.entities.OutletRegisteredEntity;
import com.banvien.fcv.mobile.utils.ELog;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

import java.sql.SQLException;

/**
 * Created by hieu on 8/03/2016.
 */
public class OutletRegisteredDAO extends AndroidBaseDaoImpl<OutletRegisteredEntity, String> {
    public OutletRegisteredDAO(Class<OutletRegisteredEntity> dataClass) throws SQLException {
        super(dataClass);
    }

    public OutletRegisteredDAO(ConnectionSource connectionSource, Class<OutletRegisteredEntity> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public OutletRegisteredDAO(ConnectionSource connectionSource, DatabaseTableConfig<OutletRegisteredEntity> tableConfig) throws SQLException {
        super(connectionSource, tableConfig);
    }

    public void addOutletRegisteredEntity(OutletRegisteredEntity data) {
        ELog.d("data", data.toString());
        try {
            create(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
