package com.banvien.fcv.mobile.library;

import android.content.Context;
import android.util.Log;

import com.banvien.fcv.mobile.R;
import com.banvien.fcv.mobile.ScreenContants;
import com.banvien.fcv.mobile.command.OutletMerResultCommand;
import com.banvien.fcv.mobile.db.Repo;
import com.banvien.fcv.mobile.dto.HotzoneDTO;
import com.banvien.fcv.mobile.dto.OutletMerDTO;
import com.banvien.fcv.mobile.dto.syncdto.MExhibitRegisterDetailDTO;
import com.banvien.fcv.mobile.dto.syncdto.MOutletMerResultDTO;
import com.banvien.fcv.mobile.dto.syncdto.MOutletMerResultDetailDTO;
import com.banvien.fcv.mobile.rest.RestClient;
import com.banvien.fcv.mobile.utils.CheckNetworkConnection;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SyncService {

	private Context context;
	private Repo repo;
	private Long outletId;
	private static final String TAG = "SyncService";

	public SyncService(Context context, Long outletId) {
		this.context = context;
		this.outletId = outletId;
		repo = new Repo(this.context);
	}
	/**
	 *
	 * @return errors message and task type
	 */
	public Map<String, String> syncToServer(boolean forceDeleteDatabase) {
		Map<String, String> results = new HashMap<String, String>();
		String errorMessage = null;
		String taskType = "STORE";
		try{
			//check connection
			if(!CheckNetworkConnection.isConnectionAvailable(context)){
				errorMessage = context.getString(R.string.sync_error_phone_connection);
			}
			OutletMerResultCommand FINAL = buildDataToSync();
			Call<OutletMerResultCommand> call = RestClient.getInstance().getHomeService().syncDataToServer(FINAL);
			call.enqueue(new Callback<OutletMerResultCommand>() {
				@Override
				public void onResponse(Call<OutletMerResultCommand> call, Response<OutletMerResultCommand> response) {

				}

				@Override
				public void onFailure(Call<OutletMerResultCommand> call, Throwable t) {

				}
			});
		}catch (Exception e){
			Log.e(TAG, "error when sync data to server", e);
		}
		results.put("taskType", taskType);
		return results;
	}

	private OutletMerResultCommand buildDataToSync() throws SQLException {
		OutletMerResultCommand FINAL = new OutletMerResultCommand();
		MOutletMerResultDTO finalOutletMerResultDTO = new MOutletMerResultDTO();

		// Setup OutletMerResult for final
		finalOutletMerResultDTO.setActiveStatus(ScreenContants.OUTLET_MER_ACTIVE);
		finalOutletMerResultDTO.setAuditDate(new Date());
		finalOutletMerResultDTO.setSubmittedDate(new Date());
		finalOutletMerResultDTO.setNote("");
		finalOutletMerResultDTO.setOutletMerResultId("");

		// Setup List MOutletMerResultDetailDTO
		List<OutletMerDTO> outletMerDTOs = repo.getOutletMerDAO().findToSync();
		List<MOutletMerResultDetailDTO> outletMerResultDetailDTOs = new ArrayList<>();
		for(OutletMerDTO outletMerDTO : outletMerDTOs){
			if(outletMerDTO.getRouteScheduleDetailId() != null){
				finalOutletMerResultDTO.setRouteScheduleDetailId(outletMerDTO.getRouteScheduleDetailId());
			}

			MOutletMerResultDetailDTO mOutletMerResultDetailDTO = new MOutletMerResultDetailDTO();

			MExhibitRegisterDetailDTO exhibitRegisterDetailDTO = new MExhibitRegisterDetailDTO();
			exhibitRegisterDetailDTO.setExhibitRegisterId(outletMerDTO.getExhibitRegisteredDetailId());

			mOutletMerResultDetailDTO.setExhibitRegisterDetail(exhibitRegisterDetailDTO);
			mOutletMerResultDetailDTO.setType(outletMerDTO.getDataType());
			mOutletMerResultDetailDTO.setCreatedDate(new Date());
			if(outletMerDTO.getDataType() != null &&
					(outletMerDTO.getDataType().equals(ScreenContants.FACING_AFTER)
							|| outletMerDTO.getDataType().equals(ScreenContants.FACING_BEFORE)
							|| outletMerDTO.getDataType().equals(ScreenContants.EIE_AFTER)
							|| outletMerDTO.getDataType().equals(ScreenContants.EIE_BEFORE))){
				mOutletMerResultDetailDTO.setScore(Float.valueOf(outletMerDTO.getActualValue()));
			} else {
				mOutletMerResultDetailDTO.setValue(outletMerDTO.getActualValue());
			}

			outletMerResultDetailDTOs.add(mOutletMerResultDetailDTO);
		}
		finalOutletMerResultDTO.setOutletMerResultDetails(outletMerResultDetailDTOs);
		FINAL.setPojo(finalOutletMerResultDTO);
		return FINAL;
	}
}
