package com.banvien.fcv.mobile.library;

import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.graphics.Paint;
import android.text.TextUtils;
import android.util.Log;

import com.banvien.fcv.mobile.R;
import com.banvien.fcv.mobile.ScreenContants;
import com.banvien.fcv.mobile.beanutil.POSMUtil;
import com.banvien.fcv.mobile.db.Repo;
import com.banvien.fcv.mobile.db.entities.POSMEntity;
import com.banvien.fcv.mobile.dto.CatgroupDTO;
import com.banvien.fcv.mobile.dto.HotzoneDTO;
import com.banvien.fcv.mobile.dto.OutletDTO;
import com.banvien.fcv.mobile.dto.OutletMerDTO;
import com.banvien.fcv.mobile.dto.POSMDTO;
import com.banvien.fcv.mobile.dto.ProductDTO;
import com.banvien.fcv.mobile.dto.ProductgroupDTO;
import com.banvien.fcv.mobile.rest.RestClient;
import com.banvien.fcv.mobile.utils.CheckNetworkConnection;
import com.banvien.fcv.mobile.utils.ELog;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateService {

	private Context context;
	private Repo repo;
	private static final String TAG = "UpdateService";
	private Map<String, List<POSMDTO>> posmMap = new HashMap<String, List<POSMDTO>>();
	private Map<String, List<HotzoneDTO>> hotZoneMap = new HashMap<String, List<HotzoneDTO>>();
	private Map<String, List<OutletDTO>> outletMap = new HashMap<String, List<OutletDTO>>();
	private Map<String, List<CatgroupDTO>> catGroupMap = new HashMap<String, List<CatgroupDTO>>();
	private Map<String, List<ProductDTO>> productMap = new HashMap<String, List<ProductDTO>>();
	private Map<String, List<ProductgroupDTO>> productGroupMap = new HashMap<String, List<ProductgroupDTO>>();
	private Map<String, List<OutletMerDTO>> outletMerDTO = new HashMap<String, List<OutletMerDTO>>();

	public UpdateService(Context context) {
		this.context = context;
	}
	/**
	 *
	 * @return errors message and task type
	 */
	public Map<String, String> updateFromServer(String auditorCode, boolean forceDeleteDatabase) {
		Map<String, String> results = new HashMap<String, String>();
		String errorMessage = null;
		String taskType = "STORE";
		try{
			//check connection
			if(!CheckNetworkConnection.isConnectionAvailable(context)){
				errorMessage = context.getString(R.string.sync_error_phone_connection);
			}
			if(forceDeleteDatabase) {
				deleteOutletAllDatabase(context);
			}

			String sync_url = context.getString(R.string.sync_url);

			JSONObject json = null;
			System.out.println("json "+ json);
			if(json != null) {
				try {
					String error = json.getString("ERROR");
					if(!TextUtils.isEmpty(error) && error.startsWith("auditorCode does not exist") ) {
						errorMessage = context.getString(R.string.sync_error_nofound_auditorcode);
					}
				}catch (JSONException e) {
					//ignore, no error found
				}
				if(errorMessage == null) {
					//// TODO: 5/10/2016
					Call<Map<String,Object>> call =
							RestClient.getInstance().getHomeService().getRoute(1l);
					fillPOSM(call);
				}
			}
		}catch (Exception e){
			Log.e(TAG, "error", e);
			errorMessage = context.getString(R.string.general_error);
		}
		results.put("taskType", taskType);
		return results;
	}

	private void fillPOSM(Call<Map<String,Object>> call){
		call.enqueue(new Callback<Map<String,Object>>() {
			@Override
			public void onResponse(Call<Map<String,Object>> call, Response<Map<String,Object>> response) {
				if (response.isSuccess()) {
					// request successful (status code 200, 201)
					Map<String,Object> result = response.body();
					List<POSMDTO> jPosms = (List<POSMDTO>)result.get(ScreenContants.POSM_LIST);
					for(POSMDTO dto : jPosms){
						try {
							POSMEntity entity = POSMUtil.convertToEntity(dto);
							repo.getPosmDAO().addPOSMEntity(entity);
						} catch (SQLException e) {
							ELog.d(e.getMessage(), e);
						}
					}
				} else {
					ELog.d("Sync POSM error......");
				}
			}

			@Override
			public void onFailure(Call<Map<String,Object>> call, Throwable t) {
				ELog.e(t.getMessage(), t);
			}
		});
	}

	public static void deleteOutletAllDatabase(Context context) {
		Log.i(TAG, "delete All Outlet Database..");
	}


}
