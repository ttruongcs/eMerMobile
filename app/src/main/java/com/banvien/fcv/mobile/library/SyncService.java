package com.banvien.fcv.mobile.library;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import com.banvien.fcv.mobile.R;
import com.banvien.fcv.mobile.ScreenContants;
import com.banvien.fcv.mobile.command.OutletMerResultCommand;
import com.banvien.fcv.mobile.db.Repo;
import com.banvien.fcv.mobile.db.entities.ConfirmWorkingEntity;
import com.banvien.fcv.mobile.db.entities.RouteScheduleEntity;
import com.banvien.fcv.mobile.dto.OutletDTO;
import com.banvien.fcv.mobile.dto.OutletMerDTO;
import com.banvien.fcv.mobile.dto.TypeFile;
import com.banvien.fcv.mobile.dto.getfromserver.MConfirmWorkingImageCommand;
import com.banvien.fcv.mobile.dto.syncdto.MExhibitRegisterDetailDTO;
import com.banvien.fcv.mobile.dto.syncdto.MOutletMerResultDTO;
import com.banvien.fcv.mobile.dto.syncdto.MOutletMerResultDetailDTO;
import com.banvien.fcv.mobile.rest.RestClient;
import com.banvien.fcv.mobile.utils.CheckNetworkConnection;
import com.banvien.fcv.mobile.utils.ELog;
import com.banvien.fcv.mobile.utils.FileUtils;

import java.io.File;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
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

	public void synConfirmNewDayImformation(ProgressDialog progressDialog) throws SQLException {
		RouteScheduleEntity routeScheduleEntity = new RouteScheduleEntity();
		routeScheduleEntity = repo.getRouteScheduleDAO().findRoute();

		if(routeScheduleEntity.getConfirmWoringId() != null){

			// Dong Bo Thong Tin Hinh Anh Dau Ngay
			List<ConfirmWorkingEntity> confirmWorkingEntityList = repo.getConfirmWorkingDAO().findAll();
			if(confirmWorkingEntityList.size() > 0){
				for(ConfirmWorkingEntity confirmWorkingEntity : confirmWorkingEntityList){
					MConfirmWorkingImageCommand mConfirmWorkingImageCommand = new MConfirmWorkingImageCommand();
					mConfirmWorkingImageCommand.setName(confirmWorkingEntity.getName());
					mConfirmWorkingImageCommand.setType(ScreenContants.CONFIRM_WORKING);
					String pathImage = ScreenContants.CAPTURE_CONFIRM_WORKING + "/" + confirmWorkingEntity.getName();
					mConfirmWorkingImageCommand.setPathImage(pathImage);

					Call<Long> callBeginImformation = RestClient.getInstance()
							.getHomeService().uploadBeginDay(routeScheduleEntity.getConfirmWoringId()
									, new Timestamp(System.currentTimeMillis()), null, null, mConfirmWorkingImageCommand);

					callBeginImformation.enqueue(new Callback<Long>() {
						@Override
						public void onResponse(Call<Long> call,
											   Response<Long> response) {
							Log.v("newDayDontHaveImage", "success");
						}

						@Override
						public void onFailure(Call<Long> call, Throwable t) {
							Log.e("DontHaveImage error:", t.getMessage());
						}
					});

					// Dong Bo Thong Tin Hinh Anh Dau Ngay
					RequestBody requestFile =
							RequestBody.create(MediaType.parse("multipart/form-data")
									, new File(confirmWorkingEntity.getPathImage()));

					Call<ResponseBody> callUploadImageBegin = RestClient.getInstance()
							.getHomeService().uploadBeginImageDay(confirmWorkingEntity.getName()
									,confirmWorkingEntity.getPathImage(), requestFile);

					callUploadImageBegin.enqueue(new Callback<ResponseBody>() {
						@Override
						public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
							Log.e("SyncSevice", "Upload Image Success");
						}

						@Override
						public void onFailure(Call<ResponseBody> call, Throwable t) {
							Log.e("SyncSevice", "Upload Image Fail");
						}
					});

				}
			}
		} else {
			final Long routeScheduleId = routeScheduleEntity.getRouteScheduleId();
			Call<Long> call = RestClient.getInstance()
					.getHomeService().uploadBeginDay(routeScheduleId
							, new Timestamp(System.currentTimeMillis())
							, null, null, new MConfirmWorkingImageCommand());

			call.enqueue(new Callback<Long>() {
				@Override
				public void onResponse(Call<Long> call,
									   Response<Long> response) {
					Log.v("newDayDontHaveImage", "success");
					// Dong Bo Hinh Anh Dau Ngay
					List<ConfirmWorkingEntity> confirmWorkingEntityList = null;
					try {
						confirmWorkingEntityList = repo.getConfirmWorkingDAO().findAll();
					} catch (SQLException e) {
						ELog.d("Error when get Confirm Working");
					}
					if(confirmWorkingEntityList.size() > 0){
						for(ConfirmWorkingEntity confirmWorkingEntity : confirmWorkingEntityList){
							final MConfirmWorkingImageCommand mConfirmWorkingImageCommand = new MConfirmWorkingImageCommand();
							final String pathImgInMobile = confirmWorkingEntity.getPathImage();
							mConfirmWorkingImageCommand.setName(confirmWorkingEntity.getName());
							mConfirmWorkingImageCommand.setType(ScreenContants.CONFIRM_WORKING);
							String pathImage = ScreenContants.CAPTURE_CONFIRM_WORKING;
							mConfirmWorkingImageCommand.setPathImage(pathImage);

							Call<Long> callBeginImformation = RestClient.getInstance()
									.getHomeService().uploadBeginDay(routeScheduleId
											, new Timestamp(System.currentTimeMillis()), Long.valueOf(response.body().toString()), null, mConfirmWorkingImageCommand);
							callBeginImformation.enqueue(new Callback<Long>() {
								@Override
								public void onResponse(Call<Long> call,
													   Response<Long> response) {
									Log.v("newHaveImage", "success");

									RequestBody requestFile =
											RequestBody.create(MediaType.parse("multipart/form-data")
													, new File(pathImgInMobile));

									Call<ResponseBody> callUploadImageBegin = RestClient.getInstance()
											.getHomeService().uploadBeginImageDay(mConfirmWorkingImageCommand.getName()
													,ScreenContants.CAPTURE_CONFIRM_WORKING, requestFile);
									callUploadImageBegin.enqueue(new Callback<ResponseBody>() {
										@Override
										public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
											Log.e("SyncSevice", "Upload Image Success");
										}

										@Override
										public void onFailure(Call<ResponseBody> call, Throwable t) {
											Log.e("SyncSevice", "Upload Image Fail");
										}
									});
								}
								@Override
								public void onFailure(Call<Long> call, Throwable t) {
									Log.e("HaveImage error:", t.getMessage());
								}
							});
						}
					}
				}

				@Override
				public void onFailure(Call<Long> call, Throwable t) {
					Log.e("DontHaveImage error:", t.getMessage());
				}
			});

		}
	}


	public void synConfirmNewDayImformationDontHaveImage() throws SQLException {
		RouteScheduleEntity routeScheduleEntity = new RouteScheduleEntity();
		routeScheduleEntity = repo.getRouteScheduleDAO().findRoute();
		Call<Long> call = RestClient.getInstance()
				.getHomeService().uploadBeginDay(routeScheduleEntity.getRouteScheduleId()
						, new Timestamp(System.currentTimeMillis()), null, null, new MConfirmWorkingImageCommand());


		call.enqueue(new Callback<Long>() {
			@Override
			public void onResponse(Call<Long> call,
								   Response<Long> response) {
				Log.v("newDayDontHaveImage", "success");
			}
			@Override
			public void onFailure(Call<Long> call, Throwable t) {
				Log.e("DontHaveImage error:", t.getMessage());
			}
		});
	}

}
