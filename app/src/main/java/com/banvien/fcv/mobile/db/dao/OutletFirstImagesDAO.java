package com.banvien.fcv.mobile.db.dao;


import com.banvien.fcv.mobile.ScreenContants;
import com.banvien.fcv.mobile.beanutil.OutletFirstImagesUtil;
import com.banvien.fcv.mobile.beanutil.OutletMerUtil;
import com.banvien.fcv.mobile.db.AndroidBaseDaoImpl;
import com.banvien.fcv.mobile.db.entities.OutletFirstImagesEntity;
import com.banvien.fcv.mobile.db.entities.OutletMerEntity;
import com.banvien.fcv.mobile.dto.BeforeDisplayDTO;
import com.banvien.fcv.mobile.dto.OutletFirstImagesDTO;
import com.banvien.fcv.mobile.dto.OutletMerDTO;
import com.banvien.fcv.mobile.utils.ELog;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.j256.ormlite.stmt.Where;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hieu on 8/03/2016.
 */
public class OutletFirstImagesDAO extends AndroidBaseDaoImpl<OutletFirstImagesEntity, String> {
    public OutletFirstImagesDAO(Class<OutletFirstImagesEntity> dataClass) throws SQLException {
        super(dataClass);
    }

    public OutletFirstImagesDAO(ConnectionSource connectionSource, Class<OutletFirstImagesEntity> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public OutletFirstImagesDAO(ConnectionSource connectionSource, DatabaseTableConfig<OutletFirstImagesEntity> tableConfig) throws SQLException {
        super(connectionSource, tableConfig);
    }

    public void addOutletFirstEntity(OutletFirstImagesEntity data) {
        ELog.d("data", data.toString());
        try {
            create(data);
        } catch (SQLException e) {
            ELog.d(e.getMessage(), e);
        }
    }

    public void updateOutletFirstEntity(OutletFirstImagesEntity data) {
        ELog.d("data", data.toString());
        try {
            update(data);
        } catch (SQLException e) {
            ELog.d(e.getMessage(), e);
        }
    }

    public List<OutletFirstImagesDTO> findByRouteScheduleDetailId(Long routeScheduleDetailId) {
        List<OutletFirstImagesDTO> results = new ArrayList<OutletFirstImagesDTO>();
        try {
            List<OutletFirstImagesEntity> outletFirstImagesList = queryBuilder().where()
                    .eq("routeScheduleDetailId", routeScheduleDetailId).query();
            if(outletFirstImagesList.size() > 0) {
                for(OutletFirstImagesEntity entity : outletFirstImagesList){
                    results.add(OutletFirstImagesUtil.convertToDTO(entity));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return results;
    }

    public void deleteImageFromId(Long id) {
        try {
            DeleteBuilder<OutletFirstImagesEntity, String> deleteBuilder = deleteBuilder();
            deleteBuilder.where().eq("_id", id);
            deleteBuilder.delete();
        } catch (SQLException e) {
            ELog.d(e.getMessage(), e);
        }
    }


}
