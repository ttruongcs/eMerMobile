package com.banvien.fcv.mobile.db.dao;


import com.banvien.fcv.mobile.beanutil.OutletMerUtil;
import com.banvien.fcv.mobile.db.AndroidBaseDaoImpl;
import com.banvien.fcv.mobile.db.entities.OutletMerEntity;
import com.banvien.fcv.mobile.dto.OutletMerDTO;
import com.banvien.fcv.mobile.utils.ELog;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hieu on 8/03/2016.
 */
public class OutletMerDAO extends AndroidBaseDaoImpl<OutletMerEntity, String> {
    public OutletMerDAO(Class<OutletMerEntity> dataClass) throws SQLException {
        super(dataClass);
    }

    public OutletMerDAO(ConnectionSource connectionSource, Class<OutletMerEntity> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public OutletMerDAO(ConnectionSource connectionSource, DatabaseTableConfig<OutletMerEntity> tableConfig) throws SQLException {
        super(connectionSource, tableConfig);
    }

    public void addOutletMerEntity(OutletMerEntity data) {
        ELog.d("data", data.toString());
        try {
            create(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<OutletMerDTO> findByDataType(String dataType) {
        List<OutletMerDTO> results = new ArrayList<OutletMerDTO>();
        try {
            List<OutletMerEntity> outletMerEntityList = queryBuilder().where().eq("dataType", dataType).query();
            if(outletMerEntityList.size() > 0) {
                for(OutletMerEntity entity : outletMerEntityList){
                    results.add(OutletMerUtil.convertToDTO(entity));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return results;
    }

    public List<OutletMerDTO> findByDataTypeAndOutlet(String dataType, Long outletId) {
        List<OutletMerDTO> results = new ArrayList<OutletMerDTO>();
        try {
            Where where = queryBuilder().where();
            where.and(where.eq("dataType", dataType), where.eq("outletId", outletId));
            List<OutletMerEntity> outletMerEntityList = where.query();
            if(outletMerEntityList.size() > 0) {
                for(OutletMerEntity entity : outletMerEntityList){
                    results.add(OutletMerUtil.convertToDTO(entity));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return results;
    }


    public OutletMerDTO findByOutletMerId(long outletMerId) {
        try {
            List<OutletMerEntity> outletMerEntityList = queryBuilder().where().eq("_id", outletMerId).query();
            if(outletMerEntityList.size() > 0) {
                return OutletMerUtil.convertToDTO(outletMerEntityList.get(0));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
