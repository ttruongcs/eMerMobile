package com.banvien.fcv.mobile.library;

import java.io.File;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

import com.banvien.fcv.mobile.R;
import com.banvien.fcv.mobile.ScreenContants;
import com.banvien.fcv.mobile.beanutil.CatgroupUtil;
import com.banvien.fcv.mobile.beanutil.CatgroupUtil;
import com.banvien.fcv.mobile.beanutil.ComplainTypeUtil;
import com.banvien.fcv.mobile.beanutil.HotzoneUtil;
import com.banvien.fcv.mobile.beanutil.OutletMerUtil;
import com.banvien.fcv.mobile.beanutil.OutletUtil;
import com.banvien.fcv.mobile.beanutil.POSMUtil;
import com.banvien.fcv.mobile.beanutil.ProductGroupUtil;
import com.banvien.fcv.mobile.beanutil.ProductUtil;
import com.banvien.fcv.mobile.beanutil.StatusHomeUtil;
import com.banvien.fcv.mobile.db.DatabaseHelper;
import com.banvien.fcv.mobile.db.Repo;
import com.banvien.fcv.mobile.db.entities.CatgroupEntity;
import com.banvien.fcv.mobile.db.entities.ComplainTypeEntity;
import com.banvien.fcv.mobile.db.entities.HotzoneEntity;
import com.banvien.fcv.mobile.db.entities.OutletEntity;
import com.banvien.fcv.mobile.db.entities.OutletMerEntity;
import com.banvien.fcv.mobile.db.entities.OutletRegisteredEntity;
import com.banvien.fcv.mobile.db.entities.POSMEntity;
import com.banvien.fcv.mobile.db.entities.ProductEntity;
import com.banvien.fcv.mobile.db.entities.ProductgroupEntity;
import com.banvien.fcv.mobile.db.entities.RouteScheduleEntity;
import com.banvien.fcv.mobile.db.entities.StatusEndDayEntity;
import com.banvien.fcv.mobile.db.entities.StatusHomeEntity;
import com.banvien.fcv.mobile.db.entities.StatusInOutletEntity;
import com.banvien.fcv.mobile.dto.CatgroupDTO;
import com.banvien.fcv.mobile.dto.ComplainTypeDTO;
import com.banvien.fcv.mobile.dto.OutletDTO;
import com.banvien.fcv.mobile.dto.OutletMerDTO;
import com.banvien.fcv.mobile.dto.POSMDTO;
import com.banvien.fcv.mobile.dto.ProductDTO;
import com.banvien.fcv.mobile.dto.ProductgroupDTO;
import com.banvien.fcv.mobile.dto.StatusHomeDTO;
import com.banvien.fcv.mobile.dto.getfromserver.HotZoneDTO;
import com.banvien.fcv.mobile.dto.getfromserver.MAuditOutletPlanDTO;
import com.banvien.fcv.mobile.dto.getfromserver.MProductDTO;
import com.banvien.fcv.mobile.dto.getfromserver.MerchandiserMetadataDTO;
import com.banvien.fcv.mobile.dto.getfromserver.OutletModelDTO;
import com.banvien.fcv.mobile.dto.getfromserver.OutletModelDetailDTO;
import com.banvien.fcv.mobile.dto.routeschedule.MExhibitRegisterDTO;
import com.banvien.fcv.mobile.dto.routeschedule.MExhibitRegisterDetailDTO;
import com.banvien.fcv.mobile.dto.routeschedule.MOutletDTO;
import com.banvien.fcv.mobile.dto.routeschedule.MRouteScheduleDetailDTO;
import com.banvien.fcv.mobile.dto.routeschedule.RouteScheduleInfoDTO;
import com.banvien.fcv.mobile.rest.RestClient;
import com.banvien.fcv.mobile.utils.CheckNetworkConnection;
import com.banvien.fcv.mobile.utils.DataBinder;
import com.banvien.fcv.mobile.utils.ELog;
import com.j256.ormlite.table.TableUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateService {

	private Context context;
	private Repo repo;
	private static final String TAG = "UpdateService";

	public UpdateService(Context context) {
		this.context = context;
		repo = new Repo(this.context);
	}
	/**
	 *
	 * @return errors message and task type
	 */
	public Map<String, String> updateFromServer(boolean forceDeleteDatabase, ProgressDialog progressDialog, TextView textNumberOutlet) {
		Map<String, String> results = new HashMap<String, String>();
		String errorMessage = null;
		String taskType = "STORE";
		try{
			//check connection
			if(!CheckNetworkConnection.isConnectionAvailable(context)){
				errorMessage = context.getString(R.string.sync_error_phone_connection);
			}
			if(forceDeleteDatabase) {
//				deleteOutletAllDatabase();
			}

			clearData();
			configStatusHome();
			configStatusInOutlet();
			configStatusEndDay();
//			Call<Map<String,Object>> call =
//					RestClient.getInstance().getHomeService().getRoute(1l, 20, 5, 2016);


			Call<Map<String,Object>> callOutletDatas =
					RestClient.getInstance().getHomeService().getDataInNewDays(10l, new Timestamp(System.currentTimeMillis()));
			results = getOutletDatas(callOutletDatas, progressDialog, textNumberOutlet);
		}catch (Exception e){
			Log.e(TAG, "error", e);
			errorMessage = context.getString(R.string.general_error);
		}
		results.put("taskType", taskType);
		return results;
	}

	private void clearData() throws SQLException {
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

	private void configStatusHome() throws SQLException {
		StatusHomeDTO statusHomeDTO = new StatusHomeDTO();
		statusHomeDTO.setChuanBiDauNgay(ScreenContants.STATUS_STEP_INPROGRESS);
		statusHomeDTO.setTrongCuaHang(ScreenContants.STATUS_STEP_NOTYET);
		statusHomeDTO.setKetThucCuoiNgay(ScreenContants.STATUS_STEP_NOTYET);
		repo.getStatusHomeDAO().addStatusHome(StatusHomeUtil.convertToEntity(statusHomeDTO));
	}


	private void configStatusInOutlet() throws SQLException {
		StatusInOutletEntity statusInOutletEntity = new StatusInOutletEntity();
		statusInOutletEntity.setCheckIn(ScreenContants.STATUS_STEP_INPROGRESS);
		statusInOutletEntity.setChupAnhOverview(ScreenContants.STATUS_STEP_NOTYET);
		statusInOutletEntity.setHutHangDatHang(ScreenContants.STATUS_STEP_NOTYET);
		statusInOutletEntity.setKhaoSatTrungBayTruoc(ScreenContants.STATUS_STEP_NOTYET);
		statusInOutletEntity.setKhaoSatTrungBaySau(ScreenContants.STATUS_STEP_NOTYET);
		statusInOutletEntity.setKhaoSat(ScreenContants.STATUS_STEP_NOTYET);
		statusInOutletEntity.setHutHangDatHang(ScreenContants.STATUS_STEP_NOTYET);
		statusInOutletEntity.setXemThongTinDangKyLichSuEIE(ScreenContants.STATUS_STEP_NOTYET);
        statusInOutletEntity.setKhaoSat(ScreenContants.STATUS_STEP_NOTYET);

		repo.getStatusInOutletDAO().addStatusHome(statusInOutletEntity);
	}


	private void configStatusEndDay() throws SQLException {
		StatusEndDayEntity statusInOutletEntity = new StatusEndDayEntity();
		statusInOutletEntity.setChupAnhCuoiNgay(ScreenContants.STATUS_STEP_INPROGRESS);
		statusInOutletEntity.setDongBoCuoiNgay(ScreenContants.STATUS_STEP_NOTYET);
		repo.getStatusEndDayDAO().addStatusHome(statusInOutletEntity);
	}




	private Map<String, String> getOutletDatas(Call<Map<String,Object>> call, final ProgressDialog progressDialog,final TextView tvNumOutlet){
		final Map<String, String> mapResult = new Hashtable<>();
		call.enqueue(new Callback<Map<String, Object>>() {
			@Override
			public void onResponse(Call<Map<String, Object>> call, Response<Map<String, Object>> response) {
				Map<String, Object> result = response.body();
				HashMap<String, Object> merchandiserMetadata = (HashMap<String, Object>)result.get("metadata");
				fillProduct(DataBinder.readProductList(merchandiserMetadata.get("products")));
				fillHotzone(DataBinder.readHotzoneList(merchandiserMetadata.get("hotZoneDTOs")));
				try {
					Integer numOutlet = buildMerPlans(DataBinder.readMAuditOutletPlanDTOList(result.get("auditOutletPlan")));
					tvNumOutlet.setText(numOutlet.toString());
				} catch (SQLException e) {
					ELog.d("Error when read plans");
				}

				if (progressDialog != null && progressDialog.isShowing()) {
					progressDialog.dismiss();
				}
			}

			private void fillHotzone(List<HotZoneDTO> jHotzones) {
				for (HotZoneDTO dto : jHotzones) {
					try {
						HotzoneEntity entity = HotzoneUtil.convertToEntity(dto);
						repo.getHotZoneDAO().addHotZoneEntity(entity);
					} catch (SQLException e) {
						ELog.d(e.getMessage(), e);
					}
				}
			}

			private void fillProduct(List<MProductDTO> jProducts) {
				for (MProductDTO dto : jProducts) {
					try {
						ProductEntity entity = ProductUtil.convertToEntity(dto);
						repo.getProductDAO().addProductEntity(entity);
					} catch (SQLException e) {
						ELog.d(e.getMessage(), e);
					}
				}
			}


			private Integer buildMerPlans(List<MAuditOutletPlanDTO> plans) throws SQLException {
				Integer numOutlet = 0;
				Long routeScheduleId = null;
				for(MAuditOutletPlanDTO plan : plans){
					routeScheduleId = plan.getRouteScheduleId();
					OutletEntity outletEntity = parsePlanToOutletEntity(plan);
					repo.getOutletDAO().addOutletEntity(outletEntity);
					numOutlet = numOutlet + 1;
					List<OutletModelDTO> outletModelDTOs = plan.getOutletModel();
					for(OutletModelDTO outletModel : outletModelDTOs){
						List<OutletModelDetailDTO> outletModelDetailDTOs = outletModel.getOutletModelDetail();
						for(OutletModelDetailDTO outletModelDetail : outletModelDetailDTOs){
							OutletMerEntity outletMerEntity = new OutletMerEntity();

							outletMerEntity.setDataType(outletModelDetail.getDataType());
							outletMerEntity.setRouteScheduleDetailId(plan.getRouteScheduleDetailId());
							outletMerEntity.setRouteScheduleId(plan.getRouteScheduleId());
							outletMerEntity.setOutletId(plan.getOutletId());
							outletMerEntity.setOutletModelId(outletModel.getOutletModelId());
							outletMerEntity.setOutletModelName(outletModel.getName());
							outletMerEntity.setRegisterValue(outletModelDetail.getReferenceValue());

							repo.getOutletMerDAO().addOutletMerEntity(outletMerEntity);

						}
					}
				}
				RouteScheduleEntity routeScheduleEntity = new RouteScheduleEntity();
				routeScheduleEntity.setRouteScheduleId(routeScheduleId);
				repo.getRouteScheduleDAO().addRoute(routeScheduleEntity);
				return numOutlet;
			}

			private OutletEntity parsePlanToOutletEntity(MAuditOutletPlanDTO plan){
				OutletEntity outletEntity = new OutletEntity();
				if(plan.getCity() != null){
					outletEntity.setCityName(plan.getCity().getName());
				}
				outletEntity.setOutletId(plan.getOutletId());
				outletEntity.setCode(plan.getCode());
				outletEntity.setDistrict(plan.getDistrict());
				outletEntity.setLat(Double.parseDouble(plan.getLatitude().toString()));
				outletEntity.setLg(Double.parseDouble(plan.getLongitude().toString()));
				outletEntity.setName(plan.getName());
				outletEntity.setRouteScheduleId(plan.getRouteScheduleId());
				outletEntity.setRouteScheduleDetailId(plan.getRouteScheduleDetailId());
				outletEntity.setStatus(ScreenContants.OUTLET_STATUS_UNFINISHED);
				return outletEntity;
			}

			@Override
			public void onFailure(Call<Map<String, Object>> call, Throwable t) {
				ELog.e(t.getMessage(), t);
			}
		});
		return mapResult;
	}
}
