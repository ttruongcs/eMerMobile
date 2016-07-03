package com.banvien.fcv.mobile.utils;

import android.content.Context;

import com.banvien.fcv.mobile.core.A;
import com.banvien.fcv.mobile.db.Repo;
import com.banvien.fcv.mobile.library.UpdateService;

public class UserUtils {

	public static void logOut(Context context){
		A.delc(K.PRINCIPAL_JSON);
		A.setPrincipal(null);

		Repo repo = new Repo(context);
		try {
			DataUtils.clearData(repo, context);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			repo.release();
		}

		A.prefEditor().clear().commit();


	}
	
}
