package com.banvien.fcv.mobile.db.entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Linh Nguyen on 6/23/2016.
 */
@DatabaseTable(tableName = "Eie")
public class EieEntity {
    @DatabaseField(generatedId = true)
    private long _id;

    @DatabaseField
    private Long eieId;

    @DatabaseField
    private Integer hzMax;

    @DatabaseField
    private Integer mhsMax;

    @DatabaseField
    private Integer facingMax;

    @DatabaseField
    private int qualifiedPoint;

    @DatabaseField
    private Integer minMhsCount;

    @DatabaseField
    private Integer minFacingCount;

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public Long getEieId() {
        return eieId;
    }

    public void setEieId(Long eieId) {
        this.eieId = eieId;
    }

    public int getHzMax() {
        return hzMax;
    }

    public void setHzMax(int hzMax) {
        this.hzMax = hzMax;
    }

    public int getMhsMax() {
        return mhsMax;
    }

    public void setMhsMax(int mhsMax) {
        this.mhsMax = mhsMax;
    }

    public int getFacingMax() {
        return facingMax;
    }

    public void setFacingMax(int facingMax) {
        this.facingMax = facingMax;
    }

    public int getQualifiedPoint() {
        return qualifiedPoint;
    }

    public void setQualifiedPoint(int qualifiedPoint) {
        this.qualifiedPoint = qualifiedPoint;
    }

    public int getMinMhsCount() {
        return minMhsCount;
    }

    public void setMinMhsCount(int minMhsCount) {
        this.minMhsCount = minMhsCount;
    }

    public int getMinFacingCount() {
        return minFacingCount;
    }

    public void setMinFacingCount(int minFacingCount) {
        this.minFacingCount = minFacingCount;
    }
}
