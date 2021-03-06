package com.banvien.fcv.mobile.db.dao;


import com.banvien.fcv.mobile.db.AndroidBaseDaoImpl;
import com.banvien.fcv.mobile.db.entities.CatgroupEntity;
import com.banvien.fcv.mobile.db.entities.ProductEntity;
import com.banvien.fcv.mobile.utils.ELog;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

import java.sql.SQLException;

/**
 * Created by hieu on 8/03/2016.
 */
public class CatgroupDAO extends AndroidBaseDaoImpl<CatgroupEntity, String> {
    public CatgroupDAO(Class<CatgroupEntity> dataClass) throws SQLException {
        super(dataClass);
    }

    public CatgroupDAO(ConnectionSource connectionSource, Class<CatgroupEntity> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public CatgroupDAO(ConnectionSource connectionSource, DatabaseTableConfig<CatgroupEntity> tableConfig) throws SQLException {
        super(connectionSource, tableConfig);
    }

    public void addCatgroupEntity(CatgroupEntity data) {
        ELog.d("data", data.toString());
        try {
            create(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void clearData() throws SQLException {
        if(isTableExists()) {
            ELog.d("clear Data CatGroup");
            deleteBuilder().delete();
        }
    }
}
