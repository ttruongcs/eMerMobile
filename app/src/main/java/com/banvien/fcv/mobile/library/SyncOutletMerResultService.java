package com.banvien.fcv.mobile.library;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.util.Log;
import android.widget.TextView;

import com.banvien.fcv.mobile.ScreenContants;
import com.banvien.fcv.mobile.db.Repo;
import com.banvien.fcv.mobile.db.entities.CaptureAfterEntity;
import com.banvien.fcv.mobile.db.entities.CaptureBeforeEntity;
import com.banvien.fcv.mobile.db.entities.CaptureOverviewEntity;
import com.banvien.fcv.mobile.db.entities.DoSurveyAnswerEntity;
import com.banvien.fcv.mobile.db.entities.RouteScheduleEntity;
import com.banvien.fcv.mobile.db.entities.SurveyEntity;
import com.banvien.fcv.mobile.dto.CaptureToolDTO;
import com.banvien.fcv.mobile.dto.CaptureUniformDTO;
import com.banvien.fcv.mobile.dto.OutletDTO;
import com.banvien.fcv.mobile.dto.OutletMerDTO;
import com.banvien.fcv.mobile.dto.getfromserver.MConfirmWorkingImageCommand;
import com.banvien.fcv.mobile.dto.getfromserver.MOutletMerResultImageDTO;
import com.banvien.fcv.mobile.dto.getfromserver.MSurveyResultDTO;
import com.banvien.fcv.mobile.dto.syncdto.MOutletMerResultDTO;
import com.banvien.fcv.mobile.dto.syncdto.MOutletMerResultDetailDTO;
import com.banvien.fcv.mobile.rest.RestClient;
import com.banvien.fcv.mobile.utils.CheckNetworkConnection;
import com.banvien.fcv.mobile.utils.IteratorCallback;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

public class SyncOutletMerResultService {

	private Context context;
	private Repo repo;
	private Long outletId;
	private OutletDTO outlet;
	private static final String TAG = "SyncOutletService";

	public SyncOutletMerResultService(Context context, Long outletId) throws SQLException {
		this.context = context;
		this.outletId = outletId;
		repo = new Repo(this.context);
	}
	/**
	 *
	 * @return errors message and task type
	 */
	public Map<String, String> syncOutletResultToServer() {
		Map<String, String> results = new HashMap<String, String>();
		try{
			//check connection
			if(!CheckNetworkConnection.isConnectionAvailable(context)){

			}
		}catch (Exception e){
			Log.e(TAG, "error when sync data to server", e);
		}
		return results;
	}

	private List<OutletDTO> OutletIn() throws SQLException {
		return repo.getOutletDAO().findAll();
	}

	private List<MSurveyResultDTO> buildSurveyResult(Long outletId) throws SQLException {
		Map<String, List<MSurveyResultDTO>> map = new HashMap<>();
		List<DoSurveyAnswerEntity> doSurveyAnswerEntities = repo.getDoSurveyAnswerDAO().find(outletId);
		for (DoSurveyAnswerEntity doSurveyAnswerEntity : doSurveyAnswerEntities) {
			String key = doSurveyAnswerEntity.getSurveyId() + (doSurveyAnswerEntity.getRouteScheduleDetailId() != null? ("_" + doSurveyAnswerEntity.getRouteScheduleDetailId()) : "");
			List<MSurveyResultDTO> mSurveyResultDTOs = map.get(key);
			if (mSurveyResultDTOs == null) {
				mSurveyResultDTOs = new ArrayList<>();
				map.put(key, mSurveyResultDTOs);
			}

			MSurveyResultDTO m = new MSurveyResultDTO();
			m.setSurveyId(doSurveyAnswerEntity.getSurveyId());
			m.setRouteScheduleDetailId(doSurveyAnswerEntity.getRouteScheduleDetailId());

			mSurveyResultDTOs.add(m);

		}
		// TODO resolve sync result
		return new ArrayList<>();
	}

	private MOutletMerResultDTO createMOutletMerResultDTO(OutletDTO outlet) throws SQLException {
		MOutletMerResultDTO mOutletMerResultDTO = new MOutletMerResultDTO();

		mOutletMerResultDTO.setSubmittedDate(new Timestamp(System.currentTimeMillis()));
		mOutletMerResultDTO.setAuditDate(new Timestamp(System.currentTimeMillis()));
		mOutletMerResultDTO.setActiveStatus(ScreenContants.OUTLET_MER_ACTIVE);
		mOutletMerResultDTO.setRouteScheduleDetailId(outlet.getRouteScheduleDetailId());
		mOutletMerResultDTO.setSurveyResult(buildSurveyResult(outlet.getOutletId()));

		List<OutletMerDTO> outletMerEntityList = repo.getOutletMerDAO().findByOutletId(outlet.getOutletId());
		List<MOutletMerResultDetailDTO> outletMerResultDetailDTOs = new ArrayList<>();
		for(OutletMerDTO outletMerDTO : outletMerEntityList){
			outletMerResultDetailDTOs.add(createMOutletMerResultDTO(outletMerDTO));
		}
		mOutletMerResultDTO.setOutletMerResultDetails(outletMerResultDetailDTOs);

		return mOutletMerResultDTO;
	}

	private MOutletMerResultDetailDTO createMOutletMerResultDTO(OutletMerDTO outletMerEntity){
		MOutletMerResultDetailDTO mOutletMerResultDetail = new MOutletMerResultDetailDTO();

		mOutletMerResultDetail.setCreatedDate(new Timestamp(System.currentTimeMillis()));
		mOutletMerResultDetail.setType(outletMerEntity.getDataType());
		mOutletMerResultDetail.setOutletModelId(outletMerEntity.getOutletModelId());
		mOutletMerResultDetail.setOutletId(outletMerEntity.getOutletId());
		if(outletMerEntity.getDataType().contains(ScreenContants.HOTZONE)
				|| outletMerEntity.getDataType().contains(ScreenContants.MHS)){
			mOutletMerResultDetail.setValue(outletMerEntity.getReferenceValue());
		} else {
			if(outletMerEntity.getDataType().contains(ScreenContants.FACING)){
				if(outletMerEntity.getActualValue() != null) {
					mOutletMerResultDetail.setScore(Float.valueOf(outletMerEntity.getActualValue()));
				}
			}
		}

		return mOutletMerResultDetail;
	}

	private List<MOutletMerResultImageDTO> createListOutletImageOverview(OutletDTO outlet) throws SQLException {
		List<MOutletMerResultImageDTO> results = new ArrayList<>();
		List<CaptureOverviewEntity> captureOverviewEntityList = new ArrayList<>();
		captureOverviewEntityList = repo.getCaptureOverviewDAO().findByOutletId(outlet.getOutletId());
		if(captureOverviewEntityList.size() > 0){
			for(CaptureOverviewEntity captureOverviewEntity : captureOverviewEntityList){
				MOutletMerResultImageDTO mOutletMerResultImageDTO = new MOutletMerResultImageDTO();
				mOutletMerResultImageDTO.setType(ScreenContants.IMAGE_OVERVIEW);
				mOutletMerResultImageDTO.setRouteScheduleDetailId(outlet.getRouteScheduleDetailId());
				long date = System.currentTimeMillis();

				SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
				String dateString = sdf.format(date);
				String pathImageInServer = ScreenContants.CAPTURE_OVERVIEW
						+ outlet.getCode() + "/" + dateString + "/"
						+ captureOverviewEntity.getPathImage().split("/")[captureOverviewEntity.getPathImage().split("/").length - 1];
				mOutletMerResultImageDTO.setImageUrl(pathImageInServer);

				results.add(mOutletMerResultImageDTO);
			}
		}

		return results;
	}


	private List<MOutletMerResultImageDTO> createListOutletImageBefore(OutletDTO outlet) throws SQLException {
		List<MOutletMerResultImageDTO> results = new ArrayList<>();
		List<CaptureBeforeEntity> captureBeforeEntityList = new ArrayList<>();
		captureBeforeEntityList = repo.getCaptureBeforeDAO().findByOutletId(outlet.getOutletId());
		if(captureBeforeEntityList.size() > 0){
			for(CaptureBeforeEntity captureOverviewEntity : captureBeforeEntityList){
				MOutletMerResultImageDTO mOutletMerResultImageDTO = new MOutletMerResultImageDTO();
				mOutletMerResultImageDTO.setType(ScreenContants.IMAGE_BEFORE);
				mOutletMerResultImageDTO.setRouteScheduleDetailId(outlet.getRouteScheduleDetailId());
				long date = System.currentTimeMillis();

				SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
				String dateString = sdf.format(date);
				String pathImageInServer = ScreenContants.CAPTURE_BEFORE_PATH
						+ outlet.getCode() + "/" + dateString + "/"
						+ captureOverviewEntity.getPathImage().split("/")[captureOverviewEntity.getPathImage().split("/").length - 1];
				mOutletMerResultImageDTO.setImageUrl(pathImageInServer);

				results.add(mOutletMerResultImageDTO);
			}
		}

		return results;
	}


	private List<MOutletMerResultImageDTO> createListOutletImageAfter(OutletDTO outlet) throws SQLException {
		List<MOutletMerResultImageDTO> results = new ArrayList<>();
		List<CaptureAfterEntity> captureAfterEntityList = new ArrayList<>();
		captureAfterEntityList = repo.getCaptureAfterDAO().findByOutletId(outlet.getOutletId());
		if(captureAfterEntityList != null && captureAfterEntityList.size() > 0){
			for(CaptureAfterEntity captureAfterEntity : captureAfterEntityList){
				MOutletMerResultImageDTO mOutletMerResultImageDTO = new MOutletMerResultImageDTO();
				mOutletMerResultImageDTO.setType(ScreenContants.IMAGE_BEFORE);
				mOutletMerResultImageDTO.setRouteScheduleDetailId(outlet.getRouteScheduleDetailId());
				long date = System.currentTimeMillis();

				SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", new Locale("vi_VN"));
				String dateString = sdf.format(date);
				String pathImageInServer = ScreenContants.CAPTURE_AFTER_PATH
						+ outlet.getCode() + "/" + dateString + "/"
						+ captureAfterEntity.getPathImage().split("/")[captureAfterEntity.getPathImage().split("/").length - 1];
				mOutletMerResultImageDTO.setImageUrl(pathImageInServer);

				results.add(mOutletMerResultImageDTO);
			}
		}

		return results;
	}


	public void syncOutletMerResultToServer(final ProgressDialog progressDialog, final TextView textNumSuccess) throws SQLException, IOException {
		List<OutletDTO> outlets = OutletIn();
		final List<MOutletMerResultDTO> mOutletMerResultDTOs = new ArrayList<>();

		for(final OutletDTO outlet : outlets){
			MOutletMerResultDTO mOutletMerResultDTO = createMOutletMerResultDTO(outlet);
			mOutletMerResultDTOs.add(mOutletMerResultDTO);
		}

		Call<Integer> callOutletMerResult = RestClient.getInstance()
				.getHomeService().submitFirstOutletResult(mOutletMerResultDTOs.get(0));

		callOutletMerResult.enqueue(new IteratorCallback<Integer>(mOutletMerResultDTOs, 0) {
			@Override
			public void onResponseArrive(Call<Integer> call, Response<Integer> response) {
				progressDialog.dismiss();
				textNumSuccess.setText(String.valueOf(mOutletMerResultDTOs.size()));
			}

			@Override
			public Call getCall(Object object) {
				return RestClient.getInstance()
						.getHomeService().submitFirstOutletResult((MOutletMerResultDTO)object);
			}

			@Override
			public void onFailure(Call<Integer> call, Throwable t) {

			}
		});
	}


	public void syncOutletMerImageImfomation() throws SQLException, IOException {
		List<OutletDTO> outlets = OutletIn();
		List<MOutletMerResultImageDTO> mOutletMerResultImageDTOns = new ArrayList<>();
		for(final OutletDTO outlet : outlets){
			mOutletMerResultImageDTOns.addAll(createListOutletImageOverview(outlet));
			mOutletMerResultImageDTOns.addAll(createListOutletImageAfter(outlet));
			mOutletMerResultImageDTOns.addAll(createListOutletImageBefore(outlet));
		}

		List<List<MOutletMerResultImageDTO>> listListResult = new ArrayList<>();
		listListResult.add(mOutletMerResultImageDTOns);

		Call<Integer> callOutletMerResult = RestClient.getInstance()
				.getHomeService().submitOutletResultImageToServer(listListResult.get(0));


		callOutletMerResult.enqueue(new IteratorCallback<Integer>(listListResult, 0) {
			@Override
			public void onResponseArrive(Call<Integer> call, Response<Integer> response) {

			}

			@Override
			public Call getCall(Object object) {
				MOutletMerResultImageDTO mOutletMerResultImageDTO = (MOutletMerResultImageDTO)object;
				RequestBody requestFile =
						RequestBody.create(MediaType.parse("multipart/form-data")
								, new File(mOutletMerResultImageDTO.getImageUrl()));
				return RestClient.getInstance()
						.getHomeService().uploadBeginImageDay(mOutletMerResultImageDTO.getImageUrl()
								, mOutletMerResultImageDTO.getType(), requestFile);
			}

			@Override
			public void onFailure(Call<Integer> call, Throwable t) {

			}
		});


	}


	public void syncOutletMerImage() throws SQLException, IOException {
		List<OutletDTO> outlets = OutletIn();
		List<MOutletMerResultImageDTO> mOutletMerResultImageDTOns = new ArrayList<>();
		for(final OutletDTO outlet : outlets){
			List<CaptureOverviewEntity> captureOverviewEntityList = repo.getCaptureOverviewDAO().findByOutletId(outlet.getOutletId());
			if(captureOverviewEntityList != null && captureOverviewEntityList.size() > 0){
				for(CaptureOverviewEntity captureOverviewEntity : captureOverviewEntityList){
					MOutletMerResultImageDTO mOutletMerResultImageDTO = new MOutletMerResultImageDTO();

					long date = System.currentTimeMillis();
					SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
					String dateString = sdf.format(date);
					String pathImageInServer = ScreenContants.CAPTURE_AFTER_PATH
							+ outlet.getCode() + "/" + dateString + "/";

					mOutletMerResultImageDTO.setNameImage(captureOverviewEntity.getPathImage().split("/")
							[captureOverviewEntity.getPathImage().split("/").length - 1]);
					mOutletMerResultImageDTO.setImageUrl(pathImageInServer);
					mOutletMerResultImageDTO.setMobileImagePath(captureOverviewEntity.getPathImage());
					mOutletMerResultImageDTOns.add(mOutletMerResultImageDTO);
				}
			}


			List<CaptureBeforeEntity> captureBeforeEntityList = repo.getCaptureBeforeDAO().findByOutletId(outlet.getOutletId());
			if(captureBeforeEntityList != null && captureBeforeEntityList.size() > 0){
				for(CaptureBeforeEntity captureBeforeEntity : captureBeforeEntityList){
					MOutletMerResultImageDTO mOutletMerResultImageDTO = new MOutletMerResultImageDTO();

					long date = System.currentTimeMillis();
					SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
					String dateString = sdf.format(date);
					String pathImageInServer = ScreenContants.CAPTURE_BEFORE_PATH
							+ outlet.getCode() + "/" + dateString + "/";
					mOutletMerResultImageDTO.setNameImage(captureBeforeEntity.getPathImage().split("/")
							[captureBeforeEntity.getPathImage().split("/").length - 1]);
					mOutletMerResultImageDTO.setImageUrl(pathImageInServer);
					mOutletMerResultImageDTO.setMobileImagePath(captureBeforeEntity.getPathImage());
					mOutletMerResultImageDTOns.add(mOutletMerResultImageDTO);
				}
			}



			List<CaptureAfterEntity> captureAfterEntityList = repo.getCaptureAfterDAO().findByOutletId(outlet.getOutletId());
			if(captureAfterEntityList != null && captureAfterEntityList.size() > 0){
				for(CaptureAfterEntity captureAfterEntity : captureAfterEntityList){
					MOutletMerResultImageDTO mOutletMerResultImageDTO = new MOutletMerResultImageDTO();

					long date = System.currentTimeMillis();
					SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
					String dateString = sdf.format(date);
					String pathImageInServer = ScreenContants.CAPTURE_BEFORE_PATH
							+ outlet.getCode() + "/" + dateString + "/";
					mOutletMerResultImageDTO.setNameImage(captureAfterEntity.getPathImage().split("/")
							[captureAfterEntity.getPathImage().split("/").length - 1]);
					mOutletMerResultImageDTO.setImageUrl(pathImageInServer);
					mOutletMerResultImageDTO.setMobileImagePath(captureAfterEntity.getPathImage());
					mOutletMerResultImageDTOns.add(mOutletMerResultImageDTO);
				}
			}

		}


		if(mOutletMerResultImageDTOns.size() > 0){

			RequestBody requestFile =
					RequestBody.create(MediaType.parse("multipart/form-data")
							, new File(mOutletMerResultImageDTOns.get(0).getMobileImagePath()));

			Call<ResponseBody> callOutletMerResult = RestClient.getInstance()
					.getHomeService().uploadBeginImageDay(mOutletMerResultImageDTOns.get(0).getNameImage()
							, mOutletMerResultImageDTOns.get(0).getImageUrl(), requestFile);


			callOutletMerResult.enqueue(new IteratorCallback<ResponseBody>(mOutletMerResultImageDTOns, 0) {
				@Override
				public void onResponseArrive(Call<ResponseBody> call, Response<ResponseBody> response) {

				}

				@Override
				public Call getCall(Object object) {

					MOutletMerResultImageDTO mOutletMerResultImageDTO = (MOutletMerResultImageDTO)object;

					RequestBody requestFile =
							RequestBody.create(MediaType.parse("multipart/form-data")
									, new File(mOutletMerResultImageDTO.getMobileImagePath()));

					return RestClient.getInstance()
							.getHomeService().uploadBeginImageDay(mOutletMerResultImageDTO.getNameImage()
									, mOutletMerResultImageDTO.getImageUrl(), requestFile);
				}

				@Override
				public void onFailure(Call<ResponseBody> call, Throwable t) {

				}
			});

		}
	}


	public void syncImageTool() throws SQLException, IOException {
		List<CaptureToolDTO> captureToolDTOList = repo.getCaptureToolDAO().findAll();
		RouteScheduleEntity routeScheduleEntity = repo.getRouteScheduleDAO().findRoute();
		if(routeScheduleEntity.getConfirmWoringId() != null){
			final Long confirmWorkingId = routeScheduleEntity.getConfirmWoringId();
			final Long routeScheduleId = routeScheduleEntity.getRouteScheduleId();

			List<MConfirmWorkingImageCommand> mConfirmWorkingImageCommandList = new ArrayList<>();
			for(CaptureToolDTO captureToolDTO : captureToolDTOList){
				MConfirmWorkingImageCommand mConfirmWorkingImageCommand = new MConfirmWorkingImageCommand();
				mConfirmWorkingImageCommand.setName
						(captureToolDTO.getPathImage().split("/")[captureToolDTO.getPathImage().split("/").length - 1]);

				long date = System.currentTimeMillis();
				SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
				String dateString = sdf.format(date);
				String pathInServer = ScreenContants.CAPTURE_TOOL_PATH + dateString + "/";

				mConfirmWorkingImageCommand.setPathImage(pathInServer);
				mConfirmWorkingImageCommand.setType(ScreenContants.CAPTURE_TOOL);

				mConfirmWorkingImageCommandList.add(mConfirmWorkingImageCommand);
			}

			if(mConfirmWorkingImageCommandList.size() > 0) {
				Call<Long> callBeginImformation = RestClient.getInstance()
						.getHomeService().uploadBeginDay(routeScheduleId
								, new Timestamp(System.currentTimeMillis()), confirmWorkingId, null, mConfirmWorkingImageCommandList.get(0));

				callBeginImformation.enqueue(new IteratorCallback<Long>(mConfirmWorkingImageCommandList, 0) {
					@Override
					public void onResponseArrive(Call<Long> call, Response<Long> response) {

					}
					@Override
					public Call getCall(Object object) {
						MConfirmWorkingImageCommand mConfirmWorkingImageCommand = (MConfirmWorkingImageCommand)object;
						return RestClient.getInstance()
								.getHomeService().uploadBeginDay(routeScheduleId
										, new Timestamp(System.currentTimeMillis())
										, confirmWorkingId, null, mConfirmWorkingImageCommand);
					}
					@Override
					public void onFailure(Call<Long> call, Throwable t) {

					}
				});

				List<MOutletMerResultImageDTO> mOutletMerResultImageDTOs = new ArrayList<>();
				for(CaptureToolDTO captureToolDTO : captureToolDTOList){
					MOutletMerResultImageDTO mOutletMerResultImageDTO = new MOutletMerResultImageDTO();
					mOutletMerResultImageDTO.setNameImage
							(captureToolDTO.getPathImage().split("/")[captureToolDTO.getPathImage().split("/").length - 1]);

					long date = System.currentTimeMillis();
					SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
					String dateString = sdf.format(date);
					String pathInServer = ScreenContants.CAPTURE_TOOL_PATH + dateString + "/";

					mOutletMerResultImageDTO.setImageUrl(pathInServer);
					mOutletMerResultImageDTO.setMobileImagePath(captureToolDTO.getPathImage());

					mOutletMerResultImageDTOs.add(mOutletMerResultImageDTO);
				}

				if(mOutletMerResultImageDTOs.size() > 0){

					RequestBody requestFile =
							RequestBody.create(MediaType.parse("multipart/form-data")
									, new File(mOutletMerResultImageDTOs.get(0).getMobileImagePath()));

					Call<ResponseBody> callOutletMerResult = RestClient.getInstance()
							.getHomeService().uploadBeginImageDay(mOutletMerResultImageDTOs.get(0).getNameImage()
									, mOutletMerResultImageDTOs.get(0).getImageUrl(), requestFile);


					callOutletMerResult.enqueue(new IteratorCallback<ResponseBody>(mOutletMerResultImageDTOs, 0) {
						@Override
						public void onResponseArrive(Call<ResponseBody> call, Response<ResponseBody> response) {

						}

						@Override
						public Call getCall(Object object) {

							MOutletMerResultImageDTO mOutletMerResultImageDTO = (MOutletMerResultImageDTO)object;

							RequestBody requestFile =
									RequestBody.create(MediaType.parse("multipart/form-data")
											, new File(mOutletMerResultImageDTO.getMobileImagePath()));

							return RestClient.getInstance()
									.getHomeService().uploadBeginImageDay(mOutletMerResultImageDTO.getNameImage()
											, mOutletMerResultImageDTO.getImageUrl(), requestFile);
						}

						@Override
						public void onFailure(Call<ResponseBody> call, Throwable t) {

						}
					});

				}
			}

		}
	}



	public void syncImageUniform() throws SQLException, IOException {
		List<CaptureUniformDTO> captureUniformDTOList = repo.getCaptureUniformDAO().findAll();
		RouteScheduleEntity routeScheduleEntity = repo.getRouteScheduleDAO().findRoute();
		if(routeScheduleEntity.getConfirmWoringId() != null){
			final Long confirmWorkingId = routeScheduleEntity.getConfirmWoringId();
			final Long routeScheduleId = routeScheduleEntity.getRouteScheduleId();

			List<MConfirmWorkingImageCommand> mConfirmWorkingImageCommandList = new ArrayList<>();
			for(CaptureUniformDTO captureToolDTO : captureUniformDTOList){
				MConfirmWorkingImageCommand mConfirmWorkingImageCommand = new MConfirmWorkingImageCommand();
				mConfirmWorkingImageCommand.setName
						(captureToolDTO.getPathImage().split("/")[captureToolDTO.getPathImage().split("/").length - 1]);

				long date = System.currentTimeMillis();
				SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
				String dateString = sdf.format(date);
				String pathInServer = ScreenContants.CAPTURE_UNIFORM_PATH + dateString + "/";

				mConfirmWorkingImageCommand.setPathImage(pathInServer);
				mConfirmWorkingImageCommand.setType(ScreenContants.CAPTURE_UNIFORM);

				mConfirmWorkingImageCommandList.add(mConfirmWorkingImageCommand);
			}

			if(mConfirmWorkingImageCommandList.size() > 0) {
				Call<Long> callBeginImformation = RestClient.getInstance()
						.getHomeService().uploadBeginDay(routeScheduleId
								, new Timestamp(System.currentTimeMillis()), confirmWorkingId, null, mConfirmWorkingImageCommandList.get(0));

				callBeginImformation.enqueue(new IteratorCallback<Long>(mConfirmWorkingImageCommandList, 0) {
					@Override
					public void onResponseArrive(Call<Long> call, Response<Long> response) {

					}
					@Override
					public Call getCall(Object object) {
						MConfirmWorkingImageCommand mConfirmWorkingImageCommand = (MConfirmWorkingImageCommand)object;
						return RestClient.getInstance()
								.getHomeService().uploadBeginDay(routeScheduleId
										, new Timestamp(System.currentTimeMillis())
										, confirmWorkingId, null, mConfirmWorkingImageCommand);
					}
					@Override
					public void onFailure(Call<Long> call, Throwable t) {

					}
				});

				List<MOutletMerResultImageDTO> mOutletMerResultImageDTOs = new ArrayList<>();
				for(CaptureUniformDTO captureUniformDTO : captureUniformDTOList){
					MOutletMerResultImageDTO mOutletMerResultImageDTO = new MOutletMerResultImageDTO();
					mOutletMerResultImageDTO.setNameImage
							(captureUniformDTO.getPathImage().split("/")[captureUniformDTO.getPathImage().split("/").length - 1]);

					long date = System.currentTimeMillis();
					SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
					String dateString = sdf.format(date);
					String pathInServer = ScreenContants.CAPTURE_UNIFORM_PATH + dateString + "/";

					mOutletMerResultImageDTO.setImageUrl(pathInServer);
					mOutletMerResultImageDTO.setMobileImagePath(captureUniformDTO.getPathImage());

					mOutletMerResultImageDTOs.add(mOutletMerResultImageDTO);
				}

				if(mOutletMerResultImageDTOs.size() > 0){

					RequestBody requestFile =
							RequestBody.create(MediaType.parse("multipart/form-data")
									, new File(mOutletMerResultImageDTOs.get(0).getMobileImagePath()));

					Call<ResponseBody> callOutletMerResult = RestClient.getInstance()
							.getHomeService().uploadBeginImageDay(mOutletMerResultImageDTOs.get(0).getNameImage()
									, mOutletMerResultImageDTOs.get(0).getImageUrl(), requestFile);


					callOutletMerResult.enqueue(new IteratorCallback<ResponseBody>(mOutletMerResultImageDTOs, 0) {
						@Override
						public void onResponseArrive(Call<ResponseBody> call, Response<ResponseBody> response) {

						}

						@Override
						public Call getCall(Object object) {

							MOutletMerResultImageDTO mOutletMerResultImageDTO = (MOutletMerResultImageDTO)object;

							RequestBody requestFile =
									RequestBody.create(MediaType.parse("multipart/form-data")
											, new File(mOutletMerResultImageDTO.getMobileImagePath()));

							return RestClient.getInstance()
									.getHomeService().uploadBeginImageDay(mOutletMerResultImageDTO.getNameImage()
											, mOutletMerResultImageDTO.getImageUrl(), requestFile);
						}

						@Override
						public void onFailure(Call<ResponseBody> call, Throwable t) {

						}
					});

				}

			}
		}
	}

	public void clearData() throws SQLException {
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
		deleteFileImage();
	}


	private void deleteFileImage() {
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
