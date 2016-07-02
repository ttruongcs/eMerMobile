package com.banvien.fcv.mobile.utils;

import android.content.Intent;
import android.content.res.Resources;
import android.util.TypedValue;
import android.view.View;

import com.banvien.fcv.mobile.LoginActivity;
import com.banvien.fcv.mobile.core.A;

public class UserUtils {
	
	
	/**
	 * Convert Dp to Pixel
	 */
	public static void logOut(View v){
		A.delc(K.PRINCIPAL_JSON);
		A.setPrincipal(null);

		Intent intent = new Intent(v.getContext(), LoginActivity.class);
		v.getContext().startActivity(intent);
	}
	
}
