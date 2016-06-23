package com.banvien.fcv.mobile.library;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import com.banvien.fcv.mobile.R;
import com.banvien.fcv.mobile.ScreenContants;
import com.banvien.fcv.mobile.command.OutletMerResultCommand;
import com.banvien.fcv.mobile.db.Repo;
import com.banvien.fcv.mobile.dto.OutletDTO;
import com.banvien.fcv.mobile.dto.OutletMerDTO;
import com.banvien.fcv.mobile.dto.TypeFile;
import com.banvien.fcv.mobile.dto.syncdto.MExhibitRegisterDetailDTO;
import com.banvien.fcv.mobile.dto.syncdto.MOutletMerResultDTO;
import com.banvien.fcv.mobile.dto.syncdto.MOutletMerResultDetailDTO;
import com.banvien.fcv.mobile.rest.RestClient;
import com.banvien.fcv.mobile.utils.CheckNetworkConnection;
import com.banvien.fcv.mobile.utils.FileUtils;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SyncService {

	private Context context;
	private Repo repo;
	private Long outletId;
	private OutletDTO outlet;
	private static final String TAG = "SyncService";

	public SyncService(Context context, Long outletId) throws SQLException {
		this.context = context;
		this.outletId = outletId;
//		this.outlet = OutletUtil.convertToDTO(repo.getOutletDAO().findById(outletId));
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
			File file = new File("/storage/emulated/0/445124424/445124424-1238280458.jpg");
			Uri fileUri = Uri.fromFile(file);
			uploadFile(file);
//			OutletMerResultCommand FINAL = buildDataToSync();
//			Call<OutletMerResultCommand> call = RestClient.getInstance().getHomeService().syncDataToServer(FINAL);
//			call.enqueue(new Callback<OutletMerResultCommand>() {
//				@Override
//				public void onResponse(Call<OutletMerResultCommand> call, Response<OutletMerResultCommand> response) {
//
//				}
//
//				@Override
//				public void onFailure(Call<OutletMerResultCommand> call, Throwable t) {
//
//				}
//			});
		}catch (Exception e){
			Log.e(TAG, "error when sync data to server", e);
		}
		results.put("taskType", taskType);
		return results;
	}


	private void uploadFile(File file) {

		RequestBody requestFile =
				RequestBody.create(MediaType.parse("multipart/form-data"), file);
		// MultipartBody.Part is used to send also the actual file name
		MultipartBody.Part body =
				MultipartBody.Part.createFormData("picture", file.getName(), requestFile);

		// add another part within the multipart request
		String descriptionString = "hello, this is description speaking";
		RequestBody description =
				RequestBody.create(
						MediaType.parse("multipart/form-data"), descriptionString);

		// finally, execute the request
		Call<ResponseBody> call = RestClient.getInstance().getHomeService().upload(requestFile);
		call.enqueue(new Callback<ResponseBody>() {
			@Override
			public void onResponse(Call<ResponseBody> call,
								   Response<ResponseBody> response) {
				Log.v("Upload", "success");
			}

			@Override
			public void onFailure(Call<ResponseBody> call, Throwable t) {
				Log.e("Upload error:", t.getMessage());
			}
		});
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
