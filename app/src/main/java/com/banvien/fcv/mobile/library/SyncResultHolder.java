package com.banvien.fcv.mobile.library;

import java.util.Timer;

public class SyncResultHolder {
	private static SyncResultHolder instance;
	private static Timer outletTimer = new Timer();
	private static Timer storeTimer = new Timer();
	private boolean isPreviousServiceCompleted = true;
	static {
		instance = new SyncResultHolder();
	}
	private SyncResultHolder() {
		
	}
	public static SyncResultHolder getInstance()  {
		return instance;
	}
	public boolean isPreviousServiceCompleted() {
		return isPreviousServiceCompleted;
	}
	public void setPreviousServiceCompleted(boolean isPreviousServiceCompleted) {
		this.isPreviousServiceCompleted = isPreviousServiceCompleted;
	}
	public static Timer getOutletTimer() {
		return outletTimer;
	}
	public static void clearOutletTimer() {
		outletTimer.cancel();
		outletTimer = new Timer();
	}
	public static Timer getStoreTimer() {
		return storeTimer;
	}
	public static void clearStoreTimer() {
		storeTimer.cancel();
		storeTimer = new Timer();
	}
}
