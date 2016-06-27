package com.banvien.fcv.mobile.dto.getfromserver;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created with IntelliJ IDEA.
 * User: ANHTAI
 * Date: 3/1/16
 * Time: 11:07 AM
 * To change this template use File | Settings | File Templates.
 */
public class HotZoneDTO implements Serializable {
    private Long _id;
    private Long hotZoneId;
    private String note;
    private String code;
    private Timestamp createdDate;
    private Timestamp modifiedDate;
    private Boolean active;

    public HotZoneDTO() {
    }

    public HotZoneDTO(Long _id, Long hotZoneId, String note, String code, Timestamp createDate, Timestamp modifiedDate, Boolean active){
        this.hotZoneId = hotZoneId;
        this._id = _id;
        this.note = note;
        this.code = code;
        this.createdDate = createDate;
        this.modifiedDate = modifiedDate;
        this.active = active;
    }

    public Long get_id() {
        return _id;
    }

    public void set_id(Long _id) {
        this._id = _id;
    }

    public Long getHotZoneId() {
        return hotZoneId;
    }

    public void setHotZoneId(Long hotZoneId) {
        this.hotZoneId = hotZoneId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    public Timestamp getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Timestamp modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
