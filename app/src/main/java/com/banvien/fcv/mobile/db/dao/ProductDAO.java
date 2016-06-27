package com.banvien.fcv.mobile.db.dao;


import com.banvien.fcv.mobile.ScreenContants;
import com.banvien.fcv.mobile.beanutil.ProductUtil;
import com.banvien.fcv.mobile.db.AndroidBaseDaoImpl;
import com.banvien.fcv.mobile.db.entities.POSMEntity;
import com.banvien.fcv.mobile.db.entities.ProductEntity;
import com.banvien.fcv.mobile.dto.ProductDTO;
import com.banvien.fcv.mobile.dto.getfromserver.MProductDTO;
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

    public List<MProductDTO> findAll() {
        List<MProductDTO> productDTOs = new ArrayList<>();
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

    public List<MProductDTO> findByProductGroupId(Long id, String[] shortageCodes) {
        if(shortageCodes.length <= 0) {
            return new ArrayList<MProductDTO>();
        }

        List<MProductDTO> result = new ArrayList<>();
        try {
            QueryBuilder<ProductEntity, String> queryBuilder = queryBuilder();
            Where<ProductEntity, String> where = queryBuilder.where();


            if(shortageCodes.length > 0) {
                for(int i = 0; i < shortageCodes.length; i++) {
                    where.eq("code", shortageCodes[i]);
                }
                where.or(shortageCodes.length);
            }
            where.eq("productGroupId", id);
            where.and(2);
            List<ProductEntity> entities = where.query();
            for(ProductEntity entity : entities) {
                result.add(ProductUtil.convertToDTO(entity));
            }
        } catch (SQLException e) {
            ELog.d(e.getMessage(), e);
        }

        return result;
    }

    public MProductDTO findByProductId(Long productId) {
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

    public List<MProductDTO> findByCodes(String[] mhsCode) {
        List<MProductDTO> result = new ArrayList<>();
        QueryBuilder<ProductEntity, String> queryBuilder = queryBuilder();
        PreparedQuery<ProductEntity> preparedQuery = null;
        try {
            for(String code : mhsCode) {
                queryBuilder.where().eq("code", code);
                preparedQuery = queryBuilder.prepare();
                ProductEntity productEntity = queryForFirst(preparedQuery);
                result.add(ProductUtil.convertToDTO(productEntity));
            }
        } catch (SQLException e) {
            ELog.d(e.getMessage(), e);
        }


        return result;
    }

    public void clearData() throws SQLException {
        if(isTableExists()) {
            ELog.d("clear Data Product");
            deleteBuilder().delete();
        }
    }
}
