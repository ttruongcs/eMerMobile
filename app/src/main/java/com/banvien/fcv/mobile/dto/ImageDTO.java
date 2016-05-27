package com.banvien.fcv.mobile.dto;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Linh Nguyen on 5/27/2016.
 */
public class ImageDTO implements Serializable {

    private Long _id;
    private Bitmap image;

    public Long get_id() {
        return _id;
    }

    public void set_id(Long _id) {
        this._id = _id;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
}
