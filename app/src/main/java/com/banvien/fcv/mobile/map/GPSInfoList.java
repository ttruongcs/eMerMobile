package com.banvien.fcv.mobile.map;

import java.io.Serializable;
import java.util.ArrayList;

public class GPSInfoList implements Serializable{
	private ArrayList<GPSInfo> infoList = new ArrayList<GPSInfo>();

	public ArrayList<GPSInfo> getInfoList() {
		return infoList;
	}

	public void setInfoList(ArrayList<GPSInfo> infoList) {
		this.infoList = infoList;
	}
	
	public void add(GPSInfo gpsInfo) {
		infoList.add(gpsInfo);
	}
}
