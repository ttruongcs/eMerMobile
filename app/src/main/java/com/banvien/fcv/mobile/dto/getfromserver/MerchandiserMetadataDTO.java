package com.banvien.fcv.mobile.dto.getfromserver;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: DEll
 * Date: 6/21/16
 * Time: 6:01 PM
 * To change this template use File | Settings | File Templates.
 */
public class MerchandiserMetadataDTO implements Serializable {
    private List<MProductDTO> products;

    private List<OutletModelDTO> outletModels;

    private List<HotZoneDTO> hotZoneDTOs;

    private List<MSurveyDTO> surveys;

    private Map<String,Map<String,EIEHistoryDTO>> eie;

    public List<MProductDTO> getProducts() {
        return products;
    }

    public void setProducts(List<MProductDTO> products) {
        this.products = products;
    }

    public List<OutletModelDTO> getOutletModels() {
        return outletModels;
    }

    public void setOutletModels(List<OutletModelDTO> outletModels) {
        this.outletModels = outletModels;
    }

    public List<HotZoneDTO> getHotZoneDTOs() {
        return hotZoneDTOs;
    }

    public void setHotZoneDTOs(List<HotZoneDTO> hotZoneDTOs) {
        this.hotZoneDTOs = hotZoneDTOs;
    }

    public List<MSurveyDTO> getSurveys() {
        return surveys;
    }

    public void setSurveys(List<MSurveyDTO> surveys) {
        this.surveys = surveys;
    }

    public Map<String, Map<String, EIEHistoryDTO>> getEie() {
        return eie;
    }

    public void setEie(Map<String, Map<String, EIEHistoryDTO>> eie) {
        this.eie = eie;
    }
}
