package com.banvien.fcv.mobile.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Environment;
import android.util.TypedValue;
import android.view.View;

import com.banvien.fcv.mobile.ScreenContants;
import com.banvien.fcv.mobile.db.Repo;

import java.io.File;
import java.sql.SQLException;

public class DataUtils {
	
	
	/**
	 * Convert Dp to Pixel
	 */
	public static void clearData(Repo repo, Context context) throws SQLException {
		SharedPreferences sharedPreferences = context.getSharedPreferences(ScreenContants.MyPREFERENCES, Context.MODE_PRIVATE);
		SharedPreferences sharedPreferenceBefores = context.getSharedPreferences(ScreenContants.BeforePREFERENCES, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		SharedPreferences.Editor editorBefores  = sharedPreferenceBefores.edit();
		editor.clear();
		editor.apply();
		editorBefores.clear();
		editorBefores.apply();
		repo.getOutletEndDayImagesDAO().clearData();
		repo.getStatusHomeDAO().clearData();
		repo.getRouteScheduleDAO().clearData();
		repo.getOutletFirstImagesDAO().clearData();
		repo.getCaptureToolDAO().clearData();
		repo.getCatgroupDAO().clearData();
		repo.getComplainTypeDAO().clearData();
		repo.getHotZoneDAO().clearData();
		repo.getCaptureUniformDAO().clearData();
		repo.getPosmDAO().clearData();
		repo.getProductDAO().clearData();
		repo.getProductGroupDAO().clearData();
		repo.getStartDayDAO().clearData();
		repo.getStatusEndDayDAO().clearData();
		repo.getStatusInOutletDAO().clearData();
		repo.getOutletMerDAO().clearData();
		repo.getOutletRegisteredDAO().clearData();
		repo.getOutletDAO().clearData();
		repo.getShortageProductDAO().clearData();
		repo.getCaptureOverviewDAO().clearData();
		repo.getConfirmWorkingDAO().clearData();
		repo.getCaptureBeforeDAO().clearData();
		repo.getDeclineDAO().clearData();
		repo.getEieDAO().clearData();
		deleteFileImage();
	}

	public static void deleteFileImage() {
		File dir = new File(Environment.getExternalStorageDirectory() + ScreenContants.CAPTURE_FCV_IMAGE);
		if (dir.isDirectory())
		{
			String[] children = dir.list();
			for (int i = 0; i < children.length; i++)
			{
				new File(dir, children[i]).delete();
			}
		}
	}
	
}
