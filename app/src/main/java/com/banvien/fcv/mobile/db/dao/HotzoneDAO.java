package com.banvien.fcv.mobile.db.dao;


import com.banvien.fcv.mobile.ScreenContants;
import com.banvien.fcv.mobile.beanutil.HotzoneUtil;
import com.banvien.fcv.mobile.db.AndroidBaseDaoImpl;
import com.banvien.fcv.mobile.db.entities.HotzoneEntity;
import com.banvien.fcv.mobile.dto.HotzoneDTO;
import com.banvien.fcv.mobile.utils.ELog;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

import java.sql.SQLException;

public class HotzoneDAO extends AndroidBaseDaoImpl<HotzoneEntity, String> {
    public HotzoneDAO(Class<HotzoneEntity> dataClass) throws SQLException {
        super(dataClass);
    }

    public HotzoneDAO(ConnectionSource connectionSource, Class<HotzoneEntity> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public HotzoneDAO(ConnectionSource connectionSource, DatabaseTableConfig<HotzoneEntity> tableConfig) throws SQLException {
        super(connectionSource, tableConfig);
    }

    public void addHotZoneEntity(HotzoneEntity data) {
        ELog.d("data", data.toString());
        try {
            create(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public HotzoneDTO findByCode(String registerValue) {
        HotzoneEntity entity = new HotzoneEntity();
        try {
            QueryBuilder<HotzoneEntity, String> queryBuilder = queryBuilder();
            queryBuilder.where().eq(ScreenContants.CODE, registerValue);
            PreparedQuery<HotzoneEntity> preparedQuery = queryBuilder.prepare();
            entity = queryForFirst(preparedQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return HotzoneUtil.convertToDTO(entity);
    }
}
