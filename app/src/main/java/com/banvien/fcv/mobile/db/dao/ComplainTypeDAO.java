package com.banvien.fcv.mobile.db.dao;

import com.banvien.fcv.mobile.beanutil.ComplainTypeUtil;
import com.banvien.fcv.mobile.db.AndroidBaseDaoImpl;
import com.banvien.fcv.mobile.db.entities.ComplainTypeEntity;
import com.banvien.fcv.mobile.dto.ComplainTypeDTO;
import com.banvien.fcv.mobile.utils.ELog;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Linh Nguyen on 5/23/2016.
 */
public class ComplainTypeDAO extends AndroidBaseDaoImpl<ComplainTypeEntity, String> {
    public ComplainTypeDAO(Class<ComplainTypeEntity> dataClass) throws SQLException {
        super(dataClass);
    }

    public ComplainTypeDAO(ConnectionSource connectionSource, Class<ComplainTypeEntity> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public ComplainTypeDAO(ConnectionSource connectionSource, DatabaseTableConfig<ComplainTypeEntity> tableConfig) throws SQLException {
        super(connectionSource, tableConfig);
    }

    public void addComplainTypeEntity(ComplainTypeEntity data) {
        ELog.d("data", data.toString());
        try {
            create(data);
        } catch (SQLException e) {
            ELog.e(e.getMessage(), e);
        }
    }

    public List<ComplainTypeDTO> getAllComplainType() {
        List<ComplainTypeDTO> result = new ArrayList<>();
        try {
            List<ComplainTypeEntity> entities = queryForAll();
            for(ComplainTypeEntity entity : entities) {
                result.add(ComplainTypeUtil.convertToDTO(entity));
            }
        } catch (SQLException e) {
            ELog.e(e.getMessage(), e);
        }
        return result;
    }

    public void clearData() throws SQLException {
        if(isTableExists()) {
            ELog.d("clear Data Complain Type");
            deleteBuilder().delete();
        }
    }
}
