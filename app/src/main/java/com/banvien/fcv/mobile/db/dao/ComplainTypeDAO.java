package com.banvien.fcv.mobile.db.dao;

import com.banvien.fcv.mobile.db.AndroidBaseDaoImpl;
import com.banvien.fcv.mobile.db.entities.ComplainTypeEntity;
import com.banvien.fcv.mobile.utils.ELog;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

import java.sql.SQLException;

/**
 * Created by Linh Nguyen on 5/23/2016.
 */
public class ComplainTypeDAO extends AndroidBaseDaoImpl<ComplainTypeEntity, String> {
    public ComplainTypeDAO(Class<ComplainTypeEntity> dataClass) throws SQLException {
        super(dataClass);
    }

    public ComplainTypeDAO(ConnectionSource connectionSource, Class<ComplainTypeEntity> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public ComplainTypeDAO(ConnectionSource connectionSource, DatabaseTableConfig<ComplainTypeEntity> tableConfig) throws SQLException {
        super(connectionSource, tableConfig);
    }

    public void addComplainTypeEntity(ComplainTypeEntity data) {
        ELog.d("data", data.toString());
        try {
            create(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
