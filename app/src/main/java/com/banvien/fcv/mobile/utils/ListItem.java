package com.banvien.fcv.mobile.utils;

public class ListItem {
	private String id;
	private String name;
	private String desciption;
	private String icon;
	
	public ListItem(String id, String name, String desciption, String icon) {
		this.name = name;
		this.desciption = desciption;
		this.id = id;
		this.icon = icon;
	}
	
	public ListItem() {
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDesciption() {
		return desciption;
	}
	public void setDesciption(String desciption) {
		this.desciption = desciption;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
}
