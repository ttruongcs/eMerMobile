package com.banvien.fcv.mobile.db.dao;


import com.banvien.fcv.mobile.ScreenContants;
import com.banvien.fcv.mobile.beanutil.ProductUtil;
import com.banvien.fcv.mobile.db.AndroidBaseDaoImpl;
import com.banvien.fcv.mobile.db.entities.POSMEntity;
import com.banvien.fcv.mobile.db.entities.ProductEntity;
import com.banvien.fcv.mobile.dto.ProductDTO;
import com.banvien.fcv.mobile.utils.ELog;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    public List<ProductDTO> findAll() {
        List<ProductDTO> productDTOs = new ArrayList<>();
        try {
            List<ProductEntity> entities = queryForAll();
            for(ProductEntity entity : entities) {
                productDTOs.add(ProductUtil.convertToDTO(entity));
            }
        } catch (SQLException e) {
            ELog.d(e.getMessage(), e);
        }
        return productDTOs;
    }

    public List<ProductDTO> findByProductGroupId(Long id) {
        List<ProductDTO> result = new ArrayList<>();
        try {
            List<ProductEntity> entities = queryForEq("productGroupId", id);
            for(ProductEntity entity : entities) {
                result.add(ProductUtil.convertToDTO(entity));
            }
        } catch (SQLException e) {
            ELog.d(e.getMessage(), e);
        }

        return result;
    }

    public ProductDTO findByProductId(Long productId) {
        ProductEntity entity = new ProductEntity();
        try {
            QueryBuilder<ProductEntity, String> queryBuilder = queryBuilder();
            queryBuilder.where().eq(ScreenContants.PRODUCT_ID, productId);
            PreparedQuery<ProductEntity> preparedQuery = queryBuilder.prepare();
            entity = queryForFirst(preparedQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ProductUtil.convertToDTO(entity);
    }

    public List<ProductDTO> findByCodes(String[] mhsCode) {
        List<ProductDTO> result = new ArrayList<>();
        List<String> dataConditions = Arrays.asList(mhsCode);
        try {
            List<ProductEntity> entities = queryBuilder().where().in("code", dataConditions).query();
            if(entities.size() > 0) {
                for(ProductEntity entity : entities) {
                    result.add(ProductUtil.convertToDTO(entity));
                }
            }
        } catch (SQLException e) {
            ELog.d(e.getMessage(), e);
        }


        return result;
    }
}
