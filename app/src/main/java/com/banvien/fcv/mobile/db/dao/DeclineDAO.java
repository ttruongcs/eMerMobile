package com.banvien.fcv.mobile.db.dao;

import com.banvien.fcv.mobile.db.AndroidBaseDaoImpl;
import com.banvien.fcv.mobile.db.entities.CaptureBeforeEntity;
import com.banvien.fcv.mobile.db.entities.DeclineEntity;
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
public class DeclineDAO extends AndroidBaseDaoImpl<DeclineEntity, String> {
    public DeclineDAO(Class<DeclineEntity> dataClass) throws SQLException {
        super(dataClass);
    }

    public DeclineDAO(ConnectionSource connectionSource, Class<DeclineEntity> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public DeclineDAO(ConnectionSource connectionSource, DatabaseTableConfig<DeclineEntity> tableConfig) throws SQLException {
        super(connectionSource, tableConfig);
    }

    public List<DeclineEntity> findAll() {
        List<DeclineEntity> results = new ArrayList<>();

        try {
            List<DeclineEntity> entities = queryForAll();
            if(entities.size() > 0) {
                return results;
            }
        } catch (SQLException e) {
            ELog.d(e.getMessage(), e);
        }
        return results;
    }

    public void clearData() throws SQLException {
        if(isTableExists()) {
            ELog.d("clear Status End Day");
            deleteBuilder().delete();
        }
    }

    public DeclineEntity findByDeclineId(Long declineId) {
        try {
            List<DeclineEntity> results = new ArrayList<>();
            List<DeclineEntity> result = queryBuilder().where().eq("declineId", declineId).query();
            if(result.size() > 0) {
                return result.get(0);
            }
        } catch (SQLException e) {
            ELog.d("Error findByDeclineId");
        }
        return null;
    }

    public void addDecline(DeclineEntity data) {
        ELog.d("data", data.toString());
        try {
            create(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
