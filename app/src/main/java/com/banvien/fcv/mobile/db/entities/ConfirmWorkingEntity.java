package com.banvien.fcv.mobile.db.entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

/**
 * Created by Linh Nguyen on 6/25/2016.
 */
@DatabaseTable(tableName = "ConfirmWorking")
public class ConfirmWorkingEntity {
    @DatabaseField(generatedId = true)
    private long _id;
    @DatabaseField
    private String name;
    @DatabaseField
    private String type;
    @DatabaseField
    private String pathImage;

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPathImage() {
        return pathImage;
    }

    public void setPathImage(String pathImage) {
        this.pathImage = pathImage;
    }
}
