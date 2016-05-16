package com.banvien.fcv.mobile.map;

import java.io.Serializable;

public class GPSInfo implements Serializable {
	private String name;
	private String address;
	private Double GPSLatitude;
	private Double GPSLongtitude;
	
	
	public GPSInfo(String name, String address, Double gPSLatitude,
			Double gPSLongtitude) {
		this.name = name;
		this.address = address;
		GPSLatitude = gPSLatitude;
		GPSLongtitude = gPSLongtitude;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Double getGPSLatitude() {
		return GPSLatitude;
	}
	public void setGPSLatitude(Double gPSLatitude) {
		GPSLatitude = gPSLatitude;
	}
	public Double getGPSLongtitude() {
		return GPSLongtitude;
	}
	public void setGPSLongtitude(Double gPSLongtitude) {
		GPSLongtitude = gPSLongtitude;
	}
	
}
