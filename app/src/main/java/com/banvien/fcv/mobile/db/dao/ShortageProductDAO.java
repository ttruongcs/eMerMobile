package com.banvien.fcv.mobile.db.dao;

import com.banvien.fcv.mobile.db.AndroidBaseDaoImpl;
import com.banvien.fcv.mobile.db.entities.ShortageProductEntity;
import com.banvien.fcv.mobile.utils.ELog;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

import java.sql.SQLException;

/**
 * Created by Linh Nguyen on 6/24/2016.
 */
public class ShortageProductDAO extends AndroidBaseDaoImpl<ShortageProductEntity, String> {

    public ShortageProductDAO(Class<ShortageProductEntity> dataClass) throws SQLException {
        super(dataClass);
    }

    public ShortageProductDAO(ConnectionSource connectionSource, Class<ShortageProductEntity> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public ShortageProductDAO(ConnectionSource connectionSource, DatabaseTableConfig<ShortageProductEntity> tableConfig) throws SQLException {
        super(connectionSource, tableConfig);
    }

    public void clearData() throws SQLException {
        if(isTableExists()) {
            ELog.d("clear Status End Day");
            deleteBuilder().delete();
        }
    }
}
