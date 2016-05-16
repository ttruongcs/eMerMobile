package com.banvien.fcv.mobile.db.dao;


import com.banvien.fcv.mobile.db.AndroidBaseDaoImpl;
import com.banvien.fcv.mobile.db.entities.ProductgroupEntity;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

import java.sql.SQLException;

/**
 * Created by hieu on 8/03/2016.
 */
public class ProductgroupDAO extends AndroidBaseDaoImpl<ProductgroupEntity, String> {
    public ProductgroupDAO(Class<ProductgroupEntity> dataClass) throws SQLException {
        super(dataClass);
    }

    public ProductgroupDAO(ConnectionSource connectionSource, Class<ProductgroupEntity> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public ProductgroupDAO(ConnectionSource connectionSource, DatabaseTableConfig<ProductgroupEntity> tableConfig) throws SQLException {
        super(connectionSource, tableConfig);
    }
}
