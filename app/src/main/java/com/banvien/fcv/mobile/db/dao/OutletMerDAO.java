package com.banvien.fcv.mobile.db.dao;


import com.banvien.fcv.mobile.ScreenContants;
import com.banvien.fcv.mobile.beanutil.OutletMerUtil;
import com.banvien.fcv.mobile.db.AndroidBaseDaoImpl;
import com.banvien.fcv.mobile.db.entities.HotzoneEntity;
import com.banvien.fcv.mobile.db.entities.OutletMerEntity;
import com.banvien.fcv.mobile.dto.AfterDisplayDTO;
import com.banvien.fcv.mobile.dto.BeforeDisplayDTO;
import com.banvien.fcv.mobile.dto.OutletMerDTO;
import com.banvien.fcv.mobile.utils.ELog;
import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

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
            ELog.d(e.getMessage(), e);
        }
    }

    public void updateOutletMerEntity(OutletMerEntity data) {
        ELog.d("data", data.toString());
        try {
            update(data);
        } catch (SQLException e) {
            ELog.d(e.getMessage(), e);
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

    public boolean checkExistByReferenceValue(String dataType, String referenceValue, Long outletId) {
        try {
            Long numRows = queryBuilder().where().eq(ScreenContants.DATA_TYPE, dataType).and()
                    .eq(ScreenContants.REFERENCE_VALUE, referenceValue).and().eq("outletId", outletId).countOf();

            if(numRows > 0) {
                return true;
            }
        } catch (SQLException e) {
            ELog.d(e.getMessage(), e);
        }
        return false;
    }

    public void updateOrderMer(OutletMerEntity outletMerEntity) {
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
            ELog.d(e.getMessage(), e);
        }
        return isExist;
    }

    public OutletMerDTO findFirstResultByProperties(Map<String, Object> properties) {
        OutletMerDTO outletMerDTO = new OutletMerDTO();
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
            PreparedQuery<OutletMerEntity> preparedQuery = where.prepare();
            OutletMerEntity entity = queryForFirst(preparedQuery);
            if(entity != null) {
                outletMerDTO = OutletMerUtil.convertToDTO(entity);
            }

        } catch (SQLException e) {
            ELog.d(e.getMessage(), e);
        }
        return outletMerDTO;
    }

    public void addHotzoneDisplay(OutletMerDTO dto) {
        try {
            DeleteBuilder<OutletMerEntity, String> deleteBuilder = deleteBuilder();
            deleteBuilder.where().eq(ScreenContants.DATA_TYPE, dto.getDataType())
                    .and().eq("outletId", dto.getOutletId());
            deleteBuilder.delete();

            create(OutletMerUtil.convertToEntity(dto));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateMHStMer(OutletMerDTO outletMerDTO, String actualValue) {
        UpdateBuilder<OutletMerEntity, String> updateBuilder = updateBuilder();
        try {
            updateBuilder.where().eq("referenceValue", outletMerDTO.get_id());
            updateBuilder.updateColumnValue("actualValue", actualValue);
            updateBuilder.update();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public OutletMerDTO findReferencedDisplay(String productAfter, long id) {
        OutletMerDTO result = new OutletMerDTO();
        OutletMerEntity item = new OutletMerEntity();
        QueryBuilder<OutletMerEntity, String> queryBuilder = queryBuilder();

        try {
            queryBuilder.where().eq(ScreenContants.DATA_TYPE, productAfter).and().eq(ScreenContants.REFERENCE_VALUE, id);
            PreparedQuery<OutletMerEntity> preparedQuery = queryBuilder.prepare();
            item = queryForFirst(preparedQuery);
        } catch (SQLException e) {
            ELog.d(e.getMessage(), e);
        }

        if(item != null) {
            result = OutletMerUtil.convertToDTO(item);
        }

        return result;
    }

    public void updateFacingOrEIE(String dataType, OutletMerEntity outletMerEntity) {
        ELog.d("Update", outletMerEntity.toString());
        try {
            UpdateBuilder<OutletMerEntity, String> updateBuilder = updateBuilder();
            updateBuilder.where().eq("dataType", dataType)
                    .and().eq("outletId", outletMerEntity.getOutletId());
            updateBuilder.updateColumnValue("actualValue", outletMerEntity.getActualValue());
            updateBuilder.update();
        } catch (SQLException e) {
            ELog.d("Can't update outlet mer", e);
        }
    }

    public String findActualValueByDataType(String dataType, Long outletId, Long outletModelId) {
        String result = null;
        OutletMerEntity item = new OutletMerEntity();
        QueryBuilder<OutletMerEntity, String> queryBuilder = queryBuilder();

        try {
            queryBuilder.where().eq("dataType", dataType).and().eq("outletId", outletId).and().eq("outletModelId", outletModelId);
            PreparedQuery<OutletMerEntity> preparedQuery = queryBuilder.prepare();
            item = queryForFirst(preparedQuery);
        } catch (SQLException e) {
            ELog.d(e.getMessage(), e);
        }

        if(item != null) {
            result = item.getActualValue();
        }

        return result;
    }

    public List<OutletMerDTO> findImageByDataType(String captureType, Long outletId, Long posmId) {
        List<OutletMerDTO> result = new ArrayList<>();
        QueryBuilder<OutletMerEntity, String> queryBuilder = queryBuilder();
        Where where = queryBuilder.where();

        try {
            where.eq(ScreenContants.DATA_TYPE, captureType);
            where.and().eq(ScreenContants.OUTLETID, outletId);

            if(posmId != null) {
                where.and().eq(ScreenContants.REFERENCE_VALUE, posmId);
            }
            List<OutletMerEntity> entities = where.query();


            for(OutletMerEntity outletMerEntity : entities) {
                result.add(OutletMerUtil.convertToDTO(outletMerEntity));
            }
        } catch (SQLException e) {
            ELog.d(e.getMessage(), e);
        }

        return result;
    }

    public void deleteImageFromId(Long id) {
        try {
            DeleteBuilder<OutletMerEntity, String> deleteBuilder = deleteBuilder();
            deleteBuilder.where().eq("_id", id);
            deleteBuilder.delete();
        } catch (SQLException e) {
            ELog.d(e.getMessage(), e);
        }
    }


    public List<OutletMerDTO> findByOutletId(Long outletId) {
        try {
            List<OutletMerDTO> results = new ArrayList<>();
            List<OutletMerEntity> outletMerEntityList = queryBuilder().where().eq("outletId", outletId).query();
            if(outletMerEntityList.size() > 0) {
                for(OutletMerEntity outletMerEntity : outletMerEntityList) {
                    results.add(OutletMerUtil.convertToDTO(outletMerEntity));
                }
            }
            return results;
        } catch (SQLException e) {
            ELog.d("Error findByOutletId in OutletMerDAO");
        }
        return null;
    }

    public List<OutletMerDTO> findToSync() {
        try {
            List<OutletMerDTO> results = new ArrayList<>();
            List<OutletMerEntity> outletMerEntityList = queryForAll();
            if(outletMerEntityList.size() > 0) {
                for(OutletMerEntity outletMerEntity : outletMerEntityList) {
                    results.add(OutletMerUtil.convertToDTO(outletMerEntity));
                }
            }
            return results;
        } catch (SQLException e) {
            ELog.d("Error findByOutletId in OutletMerDAO");
        }
        return null;
    }

    public List<BeforeDisplayDTO> findOutletModelBeforeByOutletId(Long outletId) {
        List<BeforeDisplayDTO> result = new ArrayList<>();

        try {
            List<OutletMerEntity> outletModels = queryBuilder().distinct()
                    .selectColumns("outletModelId").selectColumns("outletModelName").where().eq("outletId", outletId).query();

            if(outletModels.size() > 0) {
                for(OutletMerEntity entity : outletModels) {
                    BeforeDisplayDTO beforeDisplayDTO = new BeforeDisplayDTO();
                    beforeDisplayDTO.setOutletModelId(entity.getOutletModelId());
                    beforeDisplayDTO.setOutletModelName(entity.getOutletModelName());

                    Map<String, Object> properties = new HashMap<>();
                    properties.put("outletId", outletId);
                    properties.put("outletModelId", entity.getOutletModelId());
                    properties.put(ScreenContants.DATA_TYPE, ScreenContants.MHS);
                    List<OutletMerEntity> mhsEntities = queryForFieldValues(properties);

                    ELog.d(mhsEntities.toString());

                    if(mhsEntities.size() > 0) {
                        beforeDisplayDTO.setMhs(OutletMerUtil.convertToDTO(mhsEntities.get(0)));
                    }
                    result.add(beforeDisplayDTO);

                }
            }
        } catch (SQLException e) {
            ELog.d(e.getMessage(), e);
        }

        return result;
    }



    public List<AfterDisplayDTO> findOutletModelAfterByOutletId(Long outletId) {
        List<AfterDisplayDTO> result = new ArrayList<>();

        try {
            List<OutletMerEntity> outletModels = queryBuilder().distinct()
                    .selectColumns("outletModelId").selectColumns("outletModelName").where().eq("outletId", outletId).query();

            if(outletModels.size() > 0) {
                for(OutletMerEntity entity : outletModels) {
                    AfterDisplayDTO afterDisplayDTO = new AfterDisplayDTO();
                    afterDisplayDTO.setOutletModelId(entity.getOutletModelId());
                    afterDisplayDTO.setOutletModelName(entity.getOutletModelName());

                    Map<String, Object> properties = new HashMap<>();
                    properties.put("outletId", outletId);
                    properties.put("outletModelId", entity.getOutletModelId());
                    properties.put(ScreenContants.DATA_TYPE, ScreenContants.MHS);
                    List<OutletMerEntity> mhsEntities = queryForFieldValues(properties);

                    ELog.d(mhsEntities.toString());

                    if(mhsEntities.size() > 0) {
                        afterDisplayDTO.setMhs(OutletMerUtil.convertToDTO(mhsEntities.get(0)));
                    }
                    result.add(afterDisplayDTO);
                }
            }
        } catch (SQLException e) {
            ELog.d(e.getMessage(), e);
        }
        return result;
    }



    public void updateActualValue(Long outletId, Long outletModelId,String dataType, String s) {
        UpdateBuilder<OutletMerEntity, String> updateBuilder = updateBuilder();
        try {
            updateBuilder.updateColumnValue("actualValue", s).where().eq("outletId", outletId).and()
                    .eq("outletModelId", outletModelId).and().eq(ScreenContants.DATA_TYPE, dataType);
            updateBuilder.update();
        } catch (SQLException e) {
            ELog.d(e.getMessage(), e);
        }
    }

    public void updateActualValueBefore(Long outletId, Long outletModelId, String s) {
        UpdateBuilder<OutletMerEntity, String> updateBuilder = updateBuilder();
        try {
            List<OutletMerEntity> outletMerAfterList = queryBuilder().where().eq("outletId", outletId).and()
                    .eq("outletModelId", outletModelId).and().eq(ScreenContants.DATA_TYPE, ScreenContants.MHS_BEFORE).query();

            if(outletMerAfterList.size() > 0){
                updateBuilder.updateColumnValue("actualValue", s).where().eq("outletId", outletId).and()
                        .eq("outletModelId", outletModelId).and().eq(ScreenContants.DATA_TYPE, ScreenContants.MHS_BEFORE);
                updateBuilder.update();
            } else {
                List<OutletMerEntity> outletMerList = queryBuilder().where().eq("outletId", outletId).and()
                        .eq("outletModelId", outletModelId).and().eq(ScreenContants.DATA_TYPE, ScreenContants.MHS).query();

                if(outletMerList.size() > 0){
                    OutletMerEntity entity = outletMerList.get(0);
                    OutletMerEntity outletMerEntityAfter = entity;
                    outletMerEntityAfter.setDataType(ScreenContants.MHS_BEFORE);
                    outletMerEntityAfter.setActualValue(s);
                    addOutletMerEntity(outletMerEntityAfter);
                }
            }
        } catch (SQLException e) {
            ELog.d(e.getMessage(), e);
        }
    }

    public void updateActualValueAfter(Long outletId, Long outletModelId, String s) {
        UpdateBuilder<OutletMerEntity, String> updateBuilder = updateBuilder();
        try {
            List<OutletMerEntity> outletMerAfterList = queryBuilder().where().eq("outletId", outletId).and()
                    .eq("outletModelId", outletModelId).and().eq(ScreenContants.DATA_TYPE, ScreenContants.MHS_AFTER).query();

            if(outletMerAfterList.size() > 0){
                updateBuilder.updateColumnValue("actualValue", s).where().eq("outletId", outletId).and()
                        .eq("outletModelId", outletModelId).and().eq(ScreenContants.DATA_TYPE, ScreenContants.MHS_AFTER);
                updateBuilder.update();
            } else {
                List<OutletMerEntity> outletMerList = queryBuilder().where().eq("outletId", outletId).and()
                        .eq("outletModelId", outletModelId).and().eq(ScreenContants.DATA_TYPE, ScreenContants.MHS).query();

                if(outletMerList.size() > 0){
                    OutletMerEntity entity = outletMerList.get(0);
                    OutletMerEntity outletMerEntityAfter = entity;
                    outletMerEntityAfter.setDataType(ScreenContants.MHS_AFTER);
                    outletMerEntityAfter.setActualValue(s);
                    addOutletMerEntity(outletMerEntityAfter);
                }
            }
        } catch (SQLException e) {
            ELog.d(e.getMessage(), e);
        }
    }



    public void updateCheckYesNoAfter(Long outletId, Long outletModelId, String mhsCode, Integer yesNo) {
        UpdateBuilder<OutletMerEntity, String> updateBuilder = updateBuilder();
        try {
            List<OutletMerEntity> outletMerAfterList = queryBuilder().where().eq("outletId", outletId).and()
                    .eq("outletModelId", outletModelId).and().eq(ScreenContants.DATA_TYPE, ScreenContants.MHS_AFTER).query();

            if(outletMerAfterList.size() > 0){
                OutletMerEntity outletMerValueEntity = outletMerAfterList.get(0);
                if(outletMerValueEntity.getActualValue() != null && outletMerValueEntity.getActualValue() != "")
                {
                    String[] actualValueAfter = outletMerValueEntity.getActualValue().split(",");
                    String updateValueAfter = "";
                    String finalUpdateValueAfter = "";
                    int flag = 0;
                    for(int i = 0; i < actualValueAfter.length ; i ++){
                        if(actualValueAfter[i].contains(mhsCode)){
                            updateValueAfter = actualValueAfter[i];
                            updateValueAfter = updateValueAfter.replace(updateValueAfter.split(":")[2], yesNo.toString());
                            flag = 1;
                        }else{
                            finalUpdateValueAfter = finalUpdateValueAfter + actualValueAfter[i] + ",";
                        }
                    }
                    if(flag == 0){
                        String valueYesNoAdd = mhsCode + ":" + 0 + ":" + yesNo.toString();
                        finalUpdateValueAfter = finalUpdateValueAfter + "," + valueYesNoAdd;
                    }
                    finalUpdateValueAfter = finalUpdateValueAfter + updateValueAfter;
                    updateBuilder.updateColumnValue("actualValue", finalUpdateValueAfter).where().eq("outletId", outletId).and()
                            .eq("outletModelId", outletModelId).and().eq(ScreenContants.DATA_TYPE, ScreenContants.MHS_AFTER);
                    updateBuilder.update();
                }
            } else {
                List<OutletMerEntity> outletMerList = queryBuilder().where().eq("outletId", outletId).and()
                        .eq("outletModelId", outletModelId).and().eq(ScreenContants.DATA_TYPE, ScreenContants.MHS).query();

                if(outletMerList.size() > 0){
                    OutletMerEntity entity = outletMerList.get(0);
                    OutletMerEntity outletMerEntityAfter = entity;
                    outletMerEntityAfter.setDataType(ScreenContants.MHS_AFTER);
                    outletMerEntityAfter.setActualValue(mhsCode + ":" + 0 + ":" + yesNo.toString());
                    addOutletMerEntity(outletMerEntityAfter);
                }
            }
        } catch (SQLException e) {
            ELog.d(e.getMessage(), e);
        }
    }

    public void createListMerResult(final List<OutletMerEntity> entities) {
        try {
            callBatchTasks(new Callable<Object>() {

                @Override
                public Object call() throws Exception {
                    for(OutletMerEntity entity : entities) {
                        create(entity);
                    }

                    return null;
                }
            });
        } catch (SQLException e) {
            ELog.d(e.getMessage(), e);
        }
    }



    public void clearData() throws SQLException {
        if(isTableExists()) {
            ELog.d("clear Data Outlet Mer Result");
            deleteBuilder().delete();
        }
    }

}
