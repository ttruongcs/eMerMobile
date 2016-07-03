package com.banvien.fcv.mobile.dto.getfromserver;

/**
 * Created with IntelliJ IDEA.
 * User: DEll
 * Date: 6/24/16
 * Time: 7:51 PM
 * To change this template use File | Settings | File Templates.
 */
public class MConfirmWorkingImageCommand  {
    private String name;
    private String type;
    private String pathImage;
    private String mobilePath;

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

    public String getMobilePath() {
        return mobilePath;
    }

    public void setMobilePath(String mobilePath) {
        this.mobilePath = mobilePath;
    }
}
