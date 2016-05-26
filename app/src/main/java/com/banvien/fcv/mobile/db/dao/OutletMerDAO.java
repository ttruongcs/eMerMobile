package com.banvien.fcv.mobile.db.dao;


import com.banvien.fcv.mobile.ScreenContants;
import com.banvien.fcv.mobile.beanutil.OutletMerUtil;
import com.banvien.fcv.mobile.db.AndroidBaseDaoImpl;
import com.banvien.fcv.mobile.db.entities.OutletMerEntity;
import com.banvien.fcv.mobile.dto.OutletMerDTO;
import com.banvien.fcv.mobile.utils.ELog;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    public boolean checkExistByReferenceValue(String referenceValue, Long outletId) {
        try {
            Long numRows = queryBuilder().where().eq("referenceValue", referenceValue).and().eq("outletId", outletId).countOf();

            if(numRows > 0) {
                return true;
            }
        } catch (SQLException e) {
            ELog.d(e.getMessage(), e);
        }
        return false;
    }

    public void updateOutletMerEntity(OutletMerEntity outletMerEntity) {
        ELog.d("Update", outletMerEntity.toString());
        try {
            UpdateBuilder<OutletMerEntity, String> updateBuilder = updateBuilder();
            updateBuilder.where().eq("referenceValue", outletMerEntity.getReferenceValue())
                    .and().eq("outletId", outletMerEntity.getOutletId());
            updateBuilder.updateColumnValue("actualValue", outletMerEntity.getActualValue());
            updateBuilder.update();
        } catch (SQLException e) {
            ELog.d("Can't update outlet mer", e);
        }
    }

    public List<OutletMerDTO> findOrderByOutletId(String order, Long outletId) {
        ELog.d("Data", order + ", " + outletId );
        List<OutletMerDTO> results = new ArrayList<>();
        try {
            List<OutletMerEntity> entities = queryBuilder().where().eq("dataType", order)
                    .and().eq("outletId", outletId).query();

            for(OutletMerEntity outletMerEntity : entities) {
                results.add(OutletMerUtil.convertToDTO(outletMerEntity));
            }
        } catch (SQLException e) {
            ELog.d(e.getMessage(), e);
        }

        return results;
    }

    public boolean checkExistByProperties(Map<String, Object> properties) {
        boolean isExist = false;
        int count = 0;
        try {
            QueryBuilder<OutletMerEntity, String> queryBuilder = queryBuilder();
            Where where = queryBuilder.where();

            for (Map.Entry<String, Object> entry : properties.entrySet()) {
                count++;
                if(count < properties.size()) {
                    where.eq(entry.getKey(), entry.getValue()).and();
                } else {
                    where.eq(entry.getKey(), entry.getValue());
                }

            }
            long numberRows = where.countOf();
            if(numberRows > 0) {
                isExist = true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isExist;
    }

    public void addAfterHotzone(OutletMerDTO dto) {
        try {
            DeleteBuilder<OutletMerEntity, String> deleteBuilder = deleteBuilder();
            deleteBuilder.where().eq(ScreenContants.DATA_TYPE, ScreenContants.HOTZONE_AFTER)
                    .and().eq("outletId", dto.getOutletId());
            deleteBuilder.delete();

            create(OutletMerUtil.convertToEntity(dto));
            ELog.d("hotzone", "Add success");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
