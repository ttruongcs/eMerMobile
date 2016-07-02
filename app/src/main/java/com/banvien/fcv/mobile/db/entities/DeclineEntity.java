package com.banvien.fcv.mobile.db.entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

/**
 * Created by Linh Nguyen on 6/23/2016.
 */
@DatabaseTable(tableName = "Decline")
public class DeclineEntity {
    @DatabaseField(generatedId = true)
    private long _id;

    @DatabaseField
    private Long declineMetadataId;

    @DatabaseField
    private String code;

    @DatabaseField
    private String name;

    @DatabaseField
    private Integer sortOrder;

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public Long getDeclineMetadataId() {
        return declineMetadataId;
    }

    public void setDeclineMetadataId(Long declineMetadataId) {
        this.declineMetadataId = declineMetadataId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    @Override
    public String toString() {
        return name;
    }
}
