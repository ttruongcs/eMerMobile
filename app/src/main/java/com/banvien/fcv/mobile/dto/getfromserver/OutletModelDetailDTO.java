package com.banvien.fcv.mobile.dto.getfromserver;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: TuHuynh
 * Date: 4/22/16
 * Time: 9:40 AM
 * To change this template use File | Settings | File Templates.
 */
public class OutletModelDetailDTO implements Serializable {
    private Long outletModelDetailId;
    @JsonIgnore
    private OutletModelDTO outletModel;
    private String code;
    private Boolean active;
    private String dataType;
    private String referenceValue;
    private Boolean multipleValue;
    private String threshold;
    private Double weight;
    private String rule;
    private Double weightOfTotal;
    private String expressionTotal;
    private Long outletModelID;
    private RegionDTO region;
    private RuralOpenDTO ruralOpen;

    public OutletModelDetailDTO(){
    }

    public OutletModelDetailDTO(Long outletModelDetailId, String code, Boolean active, String dataType,
        String referenceValue, Boolean multipleValue, String threshold, Double weight, String rule, Double weightOfTotal, String expressionTotal){
        this.outletModelDetailId = outletModelDetailId;
        this.code = code;
        this.active = active;
        this.dataType = dataType;
        this.referenceValue = referenceValue;
        this.multipleValue = multipleValue;
        this.threshold = threshold;
        this.weight = weight;
        this.rule = rule;
        this.weightOfTotal = weightOfTotal;
        this.expressionTotal = expressionTotal;
    }

    public Long getOutletModelDetailId(){
        return outletModelDetailId;
    }

    public void setOutletModelDetailId(Long outletModelDetailId){
        this.outletModelDetailId = outletModelDetailId;
    }

    public OutletModelDTO getOutletModel(){
        return outletModel;
    }

    public void setOutletModel(OutletModelDTO outletModel){
        this.outletModel = outletModel;
    }

    public String getCode(){
        return code;
    }

    public void setCode(String code){
        this.code = code;
    }

    public Boolean getActive(){
        return active;
    }

    public void setActive(Boolean active){
        this.active = active;
    }

    public String getDataType(){
        return dataType;
    }

    public void setDataType(String dataType){
        this.dataType = dataType;
    }

    public String getReferenceValue(){
        return referenceValue;
    }

    public void setReferenceValue(String referenceValue){
        this.referenceValue = referenceValue;
    }

    public Boolean getMultipleValue() {
        return multipleValue;
    }

    public void setMultipleValue(Boolean multipleValue) {
        this.multipleValue = multipleValue;
    }

    public String getThreshold(){
        return threshold;
    }

    public void setThreshold(String threshold){
        this.threshold = threshold;
    }

    public Double getWeight(){
        return weight;
    }

    public void setWeight(Double weight){
        this.weight = weight;
    }

    public String getRule(){
        return rule;
    }

    public void setRule(String rule){
        this.rule = rule;
    }

    public Double getWeightOfTotal() {
        return weightOfTotal;
    }

    public void setWeightOfTotal(Double weightOfTotal) {
        this.weightOfTotal = weightOfTotal;
    }

    public String getExpressionTotal() {
        return expressionTotal;
    }

    public void setExpressionTotal(String expressionTotal) {
        this.expressionTotal = expressionTotal;
    }

    public Long getOutletModelID() {
        return outletModelID;
    }

    public void setOutletModelID(Long outletModelID) {
        this.outletModelID = outletModelID;
    }

    public RegionDTO getRegion() {
        return region;
    }

    public void setRegion(RegionDTO region) {
        this.region = region;
    }

    public RuralOpenDTO getRuralOpen() {
        return ruralOpen;
    }

    public void setRuralOpen(RuralOpenDTO ruralOpen) {
        this.ruralOpen = ruralOpen;
    }
}
