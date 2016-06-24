package com.banvien.fcv.mobile.db.dao;

import com.banvien.fcv.mobile.beanutil.ShortageProductUtil;
import com.banvien.fcv.mobile.db.AndroidBaseDaoImpl;
import com.banvien.fcv.mobile.db.entities.ShortageProductEntity;
import com.banvien.fcv.mobile.dto.ShortageProductDTO;
import com.banvien.fcv.mobile.utils.ELog;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    public ShortageProductDTO addShortageProduct(ShortageProductEntity entity) {
        ELog.d("entity", entity.toString());
        ShortageProductDTO result = new ShortageProductDTO();
        try {
            ShortageProductEntity shortageProductEntity = createIfNotExists(entity);
            result = ShortageProductUtil.convertToDTO(shortageProductEntity);
        } catch (SQLException e) {
            ELog.d("Can not insert new shortage product");
        }

        return result;
    }

    public void deleteShortageProduct(String id) {
        ELog.d("removeId", id);
        try {
            DeleteBuilder<ShortageProductEntity, String> deleteBuilder = deleteBuilder();
            deleteBuilder.where().eq("_id", id.trim());
            deleteBuilder.delete();
        } catch (SQLException e) {
            ELog.d(e.getMessage(), e);
            ELog.d("Can not delete shortage with id" + id);
        }
    }

    public List<ShortageProductDTO> findByRouteScheduleId(Long routeScheduleDetailId) {
        List<ShortageProductDTO> results = new ArrayList<>();
        try {
            List<ShortageProductEntity> shortageProductEntities = queryForEq("routeScheduleDetailId", routeScheduleDetailId);
            if(shortageProductEntities.size() > 0) {
                for(ShortageProductEntity entity : shortageProductEntities) {
                    results.add(ShortageProductUtil.convertToDTO(entity));
                }
            }

        } catch (SQLException e) {
            ELog.e(e.getMessage(), e);
        }

        return results;
    }
}
