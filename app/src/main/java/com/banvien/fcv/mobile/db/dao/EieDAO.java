package com.banvien.fcv.mobile.db.dao;

import com.banvien.fcv.mobile.db.AndroidBaseDaoImpl;
import com.banvien.fcv.mobile.db.entities.CaptureAfterEntity;
import com.banvien.fcv.mobile.db.entities.EieEntity;
import com.banvien.fcv.mobile.utils.ELog;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Linh Nguyen on 6/23/2016.
 */
public class EieDAO extends AndroidBaseDaoImpl<EieEntity, String> {
    public EieDAO(Class<EieEntity> dataClass) throws SQLException {
        super(dataClass);
    }

    public EieDAO(ConnectionSource connectionSource, Class<EieEntity> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public EieDAO(ConnectionSource connectionSource, DatabaseTableConfig<EieEntity> tableConfig) throws SQLException {
        super(connectionSource, tableConfig);
    }

    public EieEntity findOne() {
        List<EieEntity> results = new ArrayList<>();

        try {
            List<EieEntity> entities = queryForAll();
            if(entities.size() > 0) {
                return results.get(0);
            }
        } catch (SQLException e) {
            ELog.d(e.getMessage(), e);
        }
        return null;
    }

    public void addEie(EieEntity data) {
        ELog.d("data", data.toString());
        try {
            create(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
