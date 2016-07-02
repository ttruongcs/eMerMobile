package com.banvien.fcv.mobile.db.dao;


import com.banvien.fcv.mobile.beanutil.OutletUtil;
import com.banvien.fcv.mobile.db.AndroidBaseDaoImpl;
import com.banvien.fcv.mobile.db.entities.OutletEntity;
import com.banvien.fcv.mobile.db.entities.RouteScheduleEntity;
import com.banvien.fcv.mobile.dto.OutletDTO;
import com.banvien.fcv.mobile.utils.ELog;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class OutletDAO extends AndroidBaseDaoImpl<OutletEntity, String> {
    public OutletDAO(Class<OutletEntity> dataClass) throws SQLException {
        super(dataClass);
    }

    public OutletDAO(ConnectionSource connectionSource, Class<OutletEntity> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public OutletDAO(ConnectionSource connectionSource, DatabaseTableConfig<OutletEntity> tableConfig) throws SQLException {
        super(connectionSource, tableConfig);
    }

    public void addOutletEntity(OutletEntity data) {
        ELog.d("data", data.toString());
        try {
            create(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public OutletEntity findById(Long outletId) {
        try {
            List<OutletEntity> results = queryBuilder().where().eq("outletId", outletId).query();
            if(results.size() > 0) {
                return results.get(0);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<OutletDTO> findAll() {
        List<OutletDTO> outletResult = new ArrayList<>();
        try {
            List<OutletEntity> results = queryForAll();
            if(results.size() > 0) {
                for(OutletEntity outletEntity : results){
                    outletResult.add(OutletUtil.convertToDTO(outletEntity));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return outletResult;
    }

    public List<OutletDTO> getOutletsWithCircumstance(Integer outletStatus) {
        ELog.d("data", outletStatus.toString());
        List<OutletDTO> results = new ArrayList<>();
        try {
            List<OutletEntity> outletEntities = queryForEq("status", outletStatus);
            for(OutletEntity outletEntity : outletEntities) {
                results.add(OutletUtil.convertToDTO(outletEntity));
            }
        } catch (SQLException e) {
            ELog.e(e.getMessage());
        }
        return results;
    }

    public int countAllOutlet() {
        List<OutletEntity> result = new ArrayList<>();
        try {
            result  = queryForAll();
        } catch (SQLException e) {
            ELog.d(e.getMessage(), e);
        }

        return result.size();
    }

    public void clearData() throws SQLException {
        if(isTableExists()) {
            ELog.d("clear Data Outlet");
            deleteBuilder().delete();
        }
    }

    public List<OutletEntity> findByCode(String outletCode) {
        List<OutletEntity> entities = new ArrayList<>();

        QueryBuilder<OutletEntity, String> queryBuilder = queryBuilder();
        try {
            queryBuilder.where().like("code", outletCode);
            entities = queryBuilder.query();
        } catch (SQLException e) {
            ELog.e(e.getMessage(), e);
        }

        return entities;
    }

    public void addOrUpdate(OutletEntity entity) {
        try {
            createOrUpdate(entity);
        } catch (SQLException e) {
            ELog.d(e.getMessage(), e);
        }
    }

    public long checkExist(Long outletId) {
        long countOf = 0;
        QueryBuilder<OutletEntity, String> queryBuilder = queryBuilder();
        try {
            countOf = queryBuilder.where().eq("outletId", outletId).countOf();
        } catch (SQLException e) {
            ELog.d(e.getMessage(), e);
        }

        return countOf;
    }

    public int updateGpsOutlet(Long outletId, double lat, double log) {
        int rowSuccess = 0;
        UpdateBuilder<OutletEntity, String> updateBuilder = updateBuilder();
        try {
            updateBuilder.updateColumnValue("lat", lat);
            updateBuilder.updateColumnValue("lg", log);
            updateBuilder.where().eq("outletId", outletId);
            rowSuccess = updateBuilder.update();
        } catch (SQLException e) {
            ELog.d(e.getMessage(), e);
        }

        return rowSuccess;
    }

    public OutletEntity findByDetailId(Long routeScheduleDetailId) {
        QueryBuilder<OutletEntity, String> queryBuilder = queryBuilder();
        OutletEntity outletEntity = new OutletEntity();
        try {
            queryBuilder.where().eq("routeScheduleDetailId", routeScheduleDetailId);
            PreparedQuery<OutletEntity> preparedQuery = queryBuilder.prepare();
            outletEntity = queryForFirst(preparedQuery);
        } catch (SQLException e) {
            ELog.d(e.getMessage(), e);
        }

        return outletEntity;
    }

    public long countAll() {
        QueryBuilder<OutletEntity, String> queryBuilder = queryBuilder();

        try {
            return queryBuilder.countOf();
        } catch (SQLException e) {
            ELog.d(e.getMessage(), e);
        }

        return 0;
    }


    public long updateDeclineStatus(long routeScheduleDetailId, String detailDecline, Integer idSelected) {
        long rowSuccess = 0;
        UpdateBuilder<OutletEntity, String> updateBuilder = updateBuilder();

        try {
            updateBuilder.updateColumnValue("note", detailDecline);
            updateBuilder.updateColumnValue("activeStatus", idSelected);
            updateBuilder.where().eq("routeScheduleDetailId", routeScheduleDetailId);
            rowSuccess = updateBuilder.update();
        } catch (SQLException e) {
            ELog.d(e.getMessage(), e);
        }

        return rowSuccess;
    }
}
