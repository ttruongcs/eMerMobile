package com.banvien.fcv.mobile.db.dao;


import com.banvien.fcv.mobile.db.AndroidBaseDaoImpl;
import com.banvien.fcv.mobile.db.entities.OutletMerEntity;
import com.banvien.fcv.mobile.utils.ELog;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

import java.sql.SQLException;

/**
 * Created by hieu on 8/03/2016.
 */
public class OutletMerDAO extends AndroidBaseDaoImpl<OutletMerEntity, String> {
    public OutletMerDAO(Class<OutletMerEntity> dataClass) throws SQLException {
        super(dataClass);
    }

    public OutletMerDAO(ConnectionSource connectionSource, Class<OutletMerEntity> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public OutletMerDAO(ConnectionSource connectionSource, DatabaseTableConfig<OutletMerEntity> tableConfig) throws SQLException {
        super(connectionSource, tableConfig);
    }

    public void addOutletMerEntity(OutletMerEntity data) {
        ELog.d("data", data.toString());
        try {
            create(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
