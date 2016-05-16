package com.banvien.fcv.mobile.db.dao;


import com.banvien.fcv.mobile.db.AndroidBaseDaoImpl;
import com.banvien.fcv.mobile.db.entities.POSMEntity;
import com.banvien.fcv.mobile.db.entities.ProductEntity;
import com.banvien.fcv.mobile.utils.ELog;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

import java.sql.SQLException;

/**
 * Created by hieu on 8/03/2016.
 */
public class ProductDAO extends AndroidBaseDaoImpl<ProductEntity, String> {
    public ProductDAO(Class<ProductEntity> dataClass) throws SQLException {
        super(dataClass);
    }

    public ProductDAO(ConnectionSource connectionSource, Class<ProductEntity> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public ProductDAO(ConnectionSource connectionSource, DatabaseTableConfig<ProductEntity> tableConfig) throws SQLException {
        super(connectionSource, tableConfig);
    }

    public void addProductEntity(ProductEntity data) {
        ELog.d("data", data.toString());
        try {
            create(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
