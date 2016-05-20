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
import com.banvien.fcv.mobile.beanutil.CatGroupUtil;
import com.banvien.fcv.mobile.beanutil.HotzoneUtil;
import com.banvien.fcv.mobile.beanutil.OutletMerUtil;
import com.banvien.fcv.mobile.beanutil.OutletUtil;
import com.banvien.fcv.mobile.beanutil.POSMUtil;
import com.banvien.fcv.mobile.beanutil.ProductGroupUtil;
import com.banvien.fcv.mobile.beanutil.ProductUtil;
import com.banvien.fcv.mobile.db.Repo;
import com.banvien.fcv.mobile.db.entities.CatgroupEntity;
import com.banvien.fcv.mobile.db.entities.HotzoneEntity;
import com.banvien.fcv.mobile.db.entities.OutletEntity;
import com.banvien.fcv.mobile.db.entities.OutletMerEntity;
import com.banvien.fcv.mobile.db.entities.POSMEntity;
import com.banvien.fcv.mobile.db.entities.ProductEntity;
import com.banvien.fcv.mobile.db.entities.ProductgroupEntity;
import com.banvien.fcv.mobile.dto.CatgroupDTO;
import com.banvien.fcv.mobile.dto.HotzoneDTO;
import com.banvien.fcv.mobile.dto.OutletDTO;
import com.banvien.fcv.mobile.dto.OutletMerDTO;
import com.banvien.fcv.mobile.dto.POSMDTO;
import com.banvien.fcv.mobile.dto.ProductDTO;
import com.banvien.fcv.mobile.dto.ProductgroupDTO;
import com.banvien.fcv.mobile.dto.routeschedule.MExhibitRegisterDTO;
import com.banvien.fcv.mobile.dto.routeschedule.MExhibitRegisterDetailDTO;
import com.banvien.fcv.mobile.dto.routeschedule.MOutletDTO;
import com.banvien.fcv.mobile.dto.routeschedule.MRouteScheduleDetailDTO;
import com.banvien.fcv.mobile.dto.routeschedule.RouteScheduleInfoDTO;
import com.banvien.fcv.mobile.rest.RestClient;
import com.banvien.fcv.mobile.utils.CheckNetworkConnection;
import com.banvien.fcv.mobile.utils.DataBinder;
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
		repo = new Repo(this.context);
	}
	/**
	 *
	 * @return errors message and task type
	 */
	public Map<String, String> updateFromServer(boolean forceDeleteDatabase) {
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

			JSONObject json = null;
			System.out.println("json "+ json);
			Call<Map<String,Object>> call =
					RestClient.getInstance().getHomeService().getRoute(1l, 20, 5, 2016);
			fillMetadata(call);
		}catch (Exception e){
			Log.e(TAG, "error", e);
			errorMessage = context.getString(R.string.general_error);
		}
		results.put("taskType", taskType);
		return results;
	}

	private void fillMetadata(Call<Map<String,Object>> call){
		call.enqueue(new Callback<Map<String,Object>>() {
			@Override
			public void onResponse(Call<Map<String,Object>> call, Response<Map<String,Object>> response) {
				if (response.isSuccess()) {
					// request successful (status code 200, 201)
					Map<String,Object> result = response.body();
					List<POSMDTO> jPosms = DataBinder.readPosmList(result.get(ScreenContants.POSM_LIST));
					List<HotzoneDTO> jHotzones = DataBinder.readHotzoneList(result.get(ScreenContants.HOTZONE_LIST));
					List<CatgroupDTO> jCatgroups = DataBinder.readCatgroupList(result.get(ScreenContants.CATGROUP_LIST));
					List<ProductgroupDTO> jProductGroups = DataBinder.readProductgroupList(result.get(ScreenContants.PRODUCTGROUP_LIST));
					List<ProductDTO> jProducts = DataBinder.readProductList(result.get(ScreenContants.PRODUCT_LIST));
					RouteScheduleInfoDTO routeScheduleInfo = DataBinder.readRouteScheduleInfo(result.get(ScreenContants.ROUTESCHEDULE_LIST));

					fillPOSM(jPosms);
					fillHotzone(jHotzones);
					fillCatgroups(jCatgroups);
					fillProductgroups(jProductGroups);
					fillProduct(jProducts);
					fillRouteScheduleInfo(routeScheduleInfo);
				} else {
					ELog.d("Sync error......");
				}
			}

			private void fillPOSM(List<POSMDTO> jPosms){
				for(POSMDTO dto : jPosms){
					try {
						POSMEntity entity = POSMUtil.convertToEntity(dto);
						repo.getPosmDAO().addPOSMEntity(entity);
					} catch (SQLException e) {
						ELog.d(e.getMessage(), e);
					}
				}
			}

			private void fillHotzone(List<HotzoneDTO> jHotzones){
				for(HotzoneDTO dto : jHotzones){
					try {
						HotzoneEntity entity = HotzoneUtil.convertToEntity(dto);
						repo.getHotZoneDAO().addHotZoneEntity(entity);
					} catch (SQLException e) {
						ELog.d(e.getMessage(), e);
					}
				}
			}

			private void fillCatgroups(List<CatgroupDTO> jCatgroup){
				for(CatgroupDTO dto : jCatgroup){
					try {
						CatgroupEntity entity = CatGroupUtil.convertToEntity(dto);
						repo.getCatgroupDAO().addCatgroupEntity(entity);
					} catch (SQLException e) {
						ELog.d(e.getMessage(), e);
					}
				}
			}

			private void fillProductgroups(List<ProductgroupDTO> jProductgroups){
				for(ProductgroupDTO dto : jProductgroups){
					try {
						ProductgroupEntity entity = ProductGroupUtil.convertToEntity(dto);
						repo.getProductGroupDAO().addProdcutGroupEntity(entity);
					} catch (SQLException e) {
						ELog.d(e.getMessage(), e);
					}
				}
			}

			private void fillProduct(List<ProductDTO> jProducts){
				for(ProductDTO dto : jProducts){
					try {
						ProductEntity entity = ProductUtil.convertToEntity(dto);
						repo.getProductDAO().addProductEntity(entity);
					} catch (SQLException e) {
						ELog.d(e.getMessage(), e);
					}

				}
			}

//			fill routeschedule, outlet, outletmer
			private void fillRouteScheduleInfo(RouteScheduleInfoDTO routeScheduleInfoDTO ){
					if(routeScheduleInfoDTO.getRouteScheduleDetails() != null
							&& routeScheduleInfoDTO.getRouteScheduleDetails().size() > 0) {
						List<MRouteScheduleDetailDTO> mMRouteScheduleDetails = routeScheduleInfoDTO
								.getRouteScheduleDetails();
						for(MRouteScheduleDetailDTO mRouteScheduleDetailDTO : mMRouteScheduleDetails){
							// Create and Insert Outlet to sqlLite
							MOutletDTO mOutletDTO = mRouteScheduleDetailDTO.getOutlet();
							if(mOutletDTO != null){
								OutletDTO outlet = new OutletDTO();
								outlet.setOutletId(mOutletDTO.getOutletId());
								outlet.setCode(mOutletDTO.getCode());
								outlet.setName(mOutletDTO.getName());
								outlet.setdCode(mOutletDTO.getDistributorCode());
								outlet.setdName(mOutletDTO.getDistributor().getName());
								outlet.setTypeName(mOutletDTO.getOutletTypeCode());
								outlet.setLocationNo(mOutletDTO.getLocationNo());
								outlet.setStreet(mOutletDTO.getStreet());
								outlet.setWard(mOutletDTO.getWard());
								outlet.setDistrict(mOutletDTO.getDistrict());
								if(mOutletDTO.getCity() != null){
									outlet.setCityName(mOutletDTO.getCity().getName());
								}
								outlet.setLat(mOutletDTO.getLat());
								outlet.setLg(mOutletDTO.getLng());
								try {
									OutletEntity entity = OutletUtil.convertToEntity(outlet);
									repo.getOutletDAO().addOutletEntity(entity);
								} catch (SQLException e) {
									ELog.d(e.getMessage(), e);
								}
							}
							if(mOutletDTO.getExhibitRegister() != null){
								MExhibitRegisterDTO mExhibitRegisterDTO = mOutletDTO.getExhibitRegister();
								if(mExhibitRegisterDTO.getExhibitRegisterDetails() != null
										&& mExhibitRegisterDTO.getExhibitRegisterDetails().size() > 0){
									List<MExhibitRegisterDetailDTO> mExhibitRegisterDetailDTOs
											= mExhibitRegisterDTO.getExhibitRegisterDetails();
									for(MExhibitRegisterDetailDTO mExhibitRegisterDetailDTO
											: mExhibitRegisterDetailDTOs){
										OutletMerDTO outletMerDTO = new OutletMerDTO();
										outletMerDTO.setOutletId(mOutletDTO.getOutletId());
										outletMerDTO.setRouteScheduleId(routeScheduleInfoDTO.getRouteScheduleId());
										outletMerDTO.setRouteScheduleDetailId
												(mRouteScheduleDetailDTO.getRouteScheduleDetailId());
										outletMerDTO.setDataType(mExhibitRegisterDetailDTO.getDataType());
										outletMerDTO.setRegisterValue(mExhibitRegisterDetailDTO.getRegisteredValue());
										outletMerDTO.setExhibitRegisteredId(mExhibitRegisterDTO.getExhibitRegisterId());
										outletMerDTO.setExhibitRegisteredDetailId(mExhibitRegisterDetailDTO.getExhibitRegisterDetailId());

										try {
											OutletMerEntity entity = OutletMerUtil.convertToEntity(outletMerDTO);
											repo.getOutletMerDAO().addOutletMerEntity(entity);
										} catch (SQLException e) {
											ELog.d(e.getMessage(), e);
										}
									}
								}
							}
						}
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
