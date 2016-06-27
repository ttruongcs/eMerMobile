package com.banvien.fcv.mobile.dto.getfromserver;

/**
 * Created with IntelliJ IDEA.
 * User: DEll
 * Date: 6/26/16
 * Time: 12:42 PM
 * To change this template use File | Settings | File Templates.
 */
public class MOutletMerResultImageDTO {
    private Long routeScheduleDetailId;
    private String type;
    private String imageUrl;
    private String mobileImagePath;
    private String nameImage;

    public Long getRouteScheduleDetailId() {
        return routeScheduleDetailId;
    }

    public void setRouteScheduleDetailId(Long routeScheduleDetailId) {
        this.routeScheduleDetailId = routeScheduleDetailId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getMobileImagePath() {
        return mobileImagePath;
    }

    public void setMobileImagePath(String mobileImagePath) {
        this.mobileImagePath = mobileImagePath;
    }

    public String getNameImage() {
        return nameImage;
    }

    public void setNameImage(String nameImage) {
        this.nameImage = nameImage;
    }
}
