package com.banvien.fcv.mobile.db.dao;


import com.banvien.fcv.mobile.beanutil.ProductGroupUtil;
import com.banvien.fcv.mobile.db.AndroidBaseDaoImpl;
import com.banvien.fcv.mobile.db.entities.ProductgroupEntity;
import com.banvien.fcv.mobile.dto.ProductgroupDTO;
import com.banvien.fcv.mobile.utils.ELog;
import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.dao.RawRowMapper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    public void addProdcutGroupEntity(ProductgroupEntity data) {
        ELog.d("data", data.toString());
        try {
            create(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<String> getAllName() {
        List<String> results = new ArrayList<>();
        try {
            GenericRawResults<String> rawResults = queryRaw("SELECT name FROM Productgroup",
                    new RawRowMapper<String>() {
                @Override
                public String mapRow(String[] columnNames, String[] resultColumns) throws SQLException {
                    return resultColumns[0];
                }
            });
            results = rawResults.getResults();
        } catch (SQLException e) {
            ELog.d("Find entity not working", e);
        }

        return results;
    }

    public List<ProductgroupDTO> findAll() {
        List<ProductgroupDTO> result = new ArrayList<>();
        try {
            List<ProductgroupEntity> entities = queryForAll();

            for(ProductgroupEntity entity : entities) {
                result.add(ProductGroupUtil.convertToDTO(entity));
            }
        } catch (SQLException e) {
            ELog.d(e.getMessage(), e);
        }
        return result;
    }

    public void clearData() throws SQLException {
        if(isTableExists()) {
            ELog.d("clear Data Product Group");
            deleteBuilder().delete();
        }
    }
}
