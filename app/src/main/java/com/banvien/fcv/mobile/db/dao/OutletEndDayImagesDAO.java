package com.banvien.fcv.mobile.db.dao;


import com.banvien.fcv.mobile.beanutil.OutletEndDayImagesUtil;
import com.banvien.fcv.mobile.beanutil.OutletFirstImagesUtil;
import com.banvien.fcv.mobile.db.AndroidBaseDaoImpl;
import com.banvien.fcv.mobile.db.entities.OutletEndDayImagesEntity;
import com.banvien.fcv.mobile.db.entities.OutletEntity;
import com.banvien.fcv.mobile.db.entities.OutletFirstImagesEntity;
import com.banvien.fcv.mobile.dto.OutletEndDayImagesDTO;
import com.banvien.fcv.mobile.dto.OutletFirstImagesDTO;
import com.banvien.fcv.mobile.utils.ELog;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hieu on 8/03/2016.
 */
public class OutletEndDayImagesDAO extends AndroidBaseDaoImpl<OutletEndDayImagesEntity, String> {
    public OutletEndDayImagesDAO(Class<OutletEndDayImagesEntity> dataClass) throws SQLException {
        super(dataClass);
    }

    public OutletEndDayImagesDAO(ConnectionSource connectionSource, Class<OutletEndDayImagesEntity> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public OutletEndDayImagesDAO(ConnectionSource connectionSource, DatabaseTableConfig<OutletEndDayImagesEntity> tableConfig) throws SQLException {
        super(connectionSource, tableConfig);
    }

    public void addOutletEndDayImagesEntity(OutletEndDayImagesEntity data) {
        ELog.d("data", data.toString());
        try {
            create(data);
        } catch (SQLException e) {
            ELog.d(e.getMessage(), e);
        }
    }

    public void updateOutletFirstEntity(OutletEndDayImagesEntity data) {
        ELog.d("data", data.toString());
        try {
            update(data);
        } catch (SQLException e) {
            ELog.d(e.getMessage(), e);
        }
    }

    public List<OutletEndDayImagesDTO> findByRouteScheduleDetailId(Long routeScheduleDetailId) {
        List<OutletEndDayImagesDTO> results = new ArrayList<OutletEndDayImagesDTO>();
        try {
            List<OutletEndDayImagesEntity> outletEndDayImagesEntityList = queryBuilder().where()
                    .eq("routeScheduleDetailId", routeScheduleDetailId).query();
            if(outletEndDayImagesEntityList.size() > 0) {
                for(OutletEndDayImagesEntity entity : outletEndDayImagesEntityList){
                    results.add(OutletEndDayImagesUtil.convertToDTO(entity));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return results;
    }

    public void deleteImageFromId(Long id) {
        try {
            DeleteBuilder<OutletEndDayImagesEntity, String> deleteBuilder = deleteBuilder();
            deleteBuilder.where().eq("_id", id);
            deleteBuilder.delete();
        } catch (SQLException e) {
            ELog.d(e.getMessage(), e);
        }
    }

    public void clearData() throws SQLException {
        if(isTableExists()){
            ELog.d("clear Data Picture End Day");
            deleteBuilder().delete();
        }
    }
}
