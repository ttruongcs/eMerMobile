package com.banvien.fcv.mobile.library;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.banvien.fcv.mobile.ScreenContants;

public class SyncBroadcastReceiver extends BroadcastReceiver {
	public SyncBroadcastReceiver() {
		super();
	}

	@Override
	public void onReceive(Context arg0, Intent arg1) {
		SyncResultHolder.getInstance().setPreviousServiceCompleted(true);
		boolean isOutlet = (Boolean)arg1.getExtras().get("isOutlet");
		if(isOutlet) {
			Intent mainIntent = new Intent(ScreenContants.OUTLET_TASKLIST_ACTION);
			mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			arg0.startActivity(mainIntent);
		}
	}
	
}
