package com.banvien.fcv.mobile.library;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.TextView;

import com.banvien.fcv.mobile.R;
import com.banvien.fcv.mobile.ScreenContants;
import com.banvien.fcv.mobile.beanutil.HotzoneUtil;
import com.banvien.fcv.mobile.beanutil.ProductGroupUtil;
import com.banvien.fcv.mobile.beanutil.ProductUtil;
import com.banvien.fcv.mobile.beanutil.QuestionContentUtil;
import com.banvien.fcv.mobile.beanutil.QuestionUtil;
import com.banvien.fcv.mobile.beanutil.StatusHomeUtil;
import com.banvien.fcv.mobile.beanutil.SurveyUtil;
import com.banvien.fcv.mobile.core.A;
import com.banvien.fcv.mobile.db.Repo;
import com.banvien.fcv.mobile.db.entities.DeclineEntity;
import com.banvien.fcv.mobile.db.entities.HotzoneEntity;
import com.banvien.fcv.mobile.db.entities.OutletEntity;
import com.banvien.fcv.mobile.db.entities.OutletMerEntity;
import com.banvien.fcv.mobile.db.entities.ProductEntity;
import com.banvien.fcv.mobile.db.entities.ProductgroupEntity;
import com.banvien.fcv.mobile.db.entities.QuestionContentEntity;
import com.banvien.fcv.mobile.db.entities.QuestionEntity;
import com.banvien.fcv.mobile.db.entities.RouteScheduleEntity;
import com.banvien.fcv.mobile.db.entities.StatusEndDayEntity;
import com.banvien.fcv.mobile.db.entities.StatusStartDayEntity;
import com.banvien.fcv.mobile.db.entities.SurveyEntity;
import com.banvien.fcv.mobile.dto.ProductgroupDTO;
import com.banvien.fcv.mobile.dto.QuestionContentDTO;
import com.banvien.fcv.mobile.dto.QuestionDTO;
import com.banvien.fcv.mobile.dto.StatusHomeDTO;
import com.banvien.fcv.mobile.dto.SurveyDTO;
import com.banvien.fcv.mobile.dto.getfromserver.HotZoneDTO;
import com.banvien.fcv.mobile.dto.getfromserver.MAuditOutletPlanDTO;
import com.banvien.fcv.mobile.dto.getfromserver.MProductDTO;
import com.banvien.fcv.mobile.dto.getfromserver.OutletModelDTO;
import com.banvien.fcv.mobile.dto.getfromserver.OutletModelDetailDTO;
import com.banvien.fcv.mobile.rest.RestClient;
import com.banvien.fcv.mobile.utils.ChangeStatusTimeline;
import com.banvien.fcv.mobile.utils.CheckNetworkConnection;
import com.banvien.fcv.mobile.utils.DataBinder;
import com.banvien.fcv.mobile.utils.DataUtils;
import com.banvien.fcv.mobile.utils.ELog;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateService {

	private Context context;
	private Repo repo;
	private static final String TAG = "UpdateService";

	public UpdateService(Context context, Repo repo) {
		this.context = context;
		this.repo = repo;
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
			if(forceDeleteDatabase) {
//				deleteOutletAllDatabase();
			}

			DataUtils.clearData(repo, context);
			configStatusHome();
            configStatusStartDay();
			configStatusEndDay();
			Call<Map<String,Object>> calEiE =
					RestClient.getInstance().getHomeService().getEie();
//			configEiE(calEiE);
			Call<Map<String,Object>> callOutletDatas =
					RestClient.getInstance().getHomeService().getDataInNewDays(A.getPrincipal().getUserId(), new Timestamp(System.currentTimeMillis() + 1));
			results = getOutletDatas(callOutletDatas, progressDialog, textNumberOutlet);
		}catch (Exception e){
			Log.e(TAG, "error", e);
			errorMessage = context.getString(R.string.general_error);
		}
		results.put("taskType", taskType);
		return results;
	}

	private void configStatusHome() throws SQLException {
		StatusHomeDTO statusHomeDTO = new StatusHomeDTO();
		statusHomeDTO.setChuanBiDauNgay(ScreenContants.STATUS_STEP_INPROGRESS);
		statusHomeDTO.setTrongCuaHang(ScreenContants.STATUS_STEP_NOTYET);
		statusHomeDTO.setKetThucCuoiNgay(ScreenContants.STATUS_STEP_NOTYET);
		repo.getStatusHomeDAO().addStatusHome(StatusHomeUtil.convertToEntity(statusHomeDTO));
	}

	private void configStatusEndDay() throws SQLException {
		StatusEndDayEntity statusInOutletEntity = new StatusEndDayEntity();
		statusInOutletEntity.setChupAnhCuoiNgay(ScreenContants.STATUS_STEP_INPROGRESS);
		statusInOutletEntity.setDongBoCuoiNgay(ScreenContants.STATUS_STEP_NOTYET);
		statusInOutletEntity.setKetThucCuoiNgay(ScreenContants.STATUS_STEP_NOTYET);
		repo.getStatusEndDayDAO().addStatusHome(statusInOutletEntity);
	}

    private void configStatusStartDay() throws SQLException {
        StatusStartDayEntity statusStartDayEntity = new StatusStartDayEntity();
        statusStartDayEntity.setDongBoDuLieuPhanCong(ScreenContants.STATUS_STEP_INPROGRESS);
        statusStartDayEntity.setThemCuaHangNeuMuon(ScreenContants.STATUS_STEP_NOTYET);
        statusStartDayEntity.setChupHinhCongCuDungCu(ScreenContants.STATUS_STEP_NOTYET);
        statusStartDayEntity.setChupHinhCuaHangDauTien(ScreenContants.STATUS_STEP_NOTYET);
        statusStartDayEntity.setChupHinhDongPhuc(ScreenContants.STATUS_STEP_NOTYET);
        statusStartDayEntity.setXacNhanLamViec(ScreenContants.STATUS_STEP_NOTYET);
        repo.getStartDayDAO().addStatusHome(statusStartDayEntity);
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
				fillProductGroup(DataBinder.readProductgroupList(merchandiserMetadata.get("productGroups")));
				fillDecline(DataBinder.readDeclineEntitys(merchandiserMetadata.get("declineMetadata")));
				try {
					saveSurveys(DataBinder.readSurvey(merchandiserMetadata.get("surveys")));
				} catch (Exception e) {
					// ignore
					ELog.e("Error when saving surveys", e);
				}
				try {
					Integer numOutlet = buildMerPlans(DataBinder.readMAuditOutletPlanDTOList(result.get("auditOutletPlan")));
					tvNumOutlet.setText(numOutlet.toString());
                    ChangeStatusTimeline changeStatusTimeline = new ChangeStatusTimeline(repo);
                    String[] next = {ScreenContants.ADD_OUTLET, ScreenContants.CAPTURE_UNIFORM};
                    changeStatusTimeline.changeStatusToDone(ScreenContants.PREPARE_DATE_COLUMN
                            , ScreenContants.START_DATE_SYNC, next, ScreenContants.IN_OUTLET, false);
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

			private void fillProductGroup(List<ProductgroupDTO> jProducts) {
				for (ProductgroupDTO dto : jProducts) {
					try {
						ProductgroupEntity entity = ProductGroupUtil.convertToEntity(dto);
						repo.getProductGroupDAO().addProdcutGroupEntity(entity);
					} catch (SQLException e) {
						ELog.d(e.getMessage(), e);
					}
				}
			}

			private void fillDecline(List<DeclineEntity> jDelines) {
				for (DeclineEntity entity : jDelines) {
					try {
						repo.getDeclineDAO().addDecline(entity);
					} catch (SQLException e) {
						ELog.d(e.getMessage(), e);
					}
				}
			}

			private void saveSurveys(Map<Long, List<SurveyDTO>> map) throws SQLException {
				if (map !=  null) {
					for (Long outletId : map.keySet()) {
						repo.getSurveyDAO().remove(outletId);
						List<SurveyDTO> surveyDTOs = map.get(outletId);
						for (SurveyDTO surveyDTO : surveyDTOs) {
							try {
								SurveyEntity surveyEntity = SurveyUtil.convertToEntity(surveyDTO);
								surveyEntity.setOutletId(outletId);
								repo.getSurveyDAO().add(surveyEntity);
								if (surveyDTO.getQuestions() != null && surveyDTO.getQuestions().size() > 0) {
									saveQuestions(surveyDTO.getQuestions(), surveyDTO.getSurveyId());
								}
							} catch (SQLException e) {
								ELog.e(e.getMessage(), e);
							}
						}
					}
				}
			}

			private void saveQuestions(List<QuestionDTO> questionDTOs, Long surveyId) throws SQLException {
				repo.getQuestionDAO().remove(surveyId);
				for (QuestionDTO questionDTO : questionDTOs) {
					try {
						QuestionEntity questionEntity = QuestionUtil.convertToEntity(questionDTO);
						questionEntity.setSurveyId(surveyId);
						repo.getQuestionDAO().add(questionEntity);
						if (questionDTO.getQuestionContents() != null && questionDTO.getQuestionContents().size() > 0) {
							saveQuestionContents(questionDTO.getQuestionContents(), questionDTO.getQuestionId());
						}
					} catch (SQLException e) {
						ELog.e(e.getMessage(), e);
					}
				}
			}

			private void saveQuestionContents(List<QuestionContentDTO> questionContentDTOs, Long questionId) throws SQLException {
				repo.getQuestionContentDAO().remove(questionId);
				for (QuestionContentDTO questionContentDTO : questionContentDTOs) {
					try {
						QuestionContentEntity questionContentEntity = QuestionContentUtil.convertToEntity(questionContentDTO);
						QuestionEntity questionEntity = new QuestionEntity();
						questionEntity.setQuestionId(questionId);
						questionContentEntity.setQuestion(questionEntity);
						repo.getQuestionContentDAO().add(questionContentEntity);
					} catch (SQLException e) {
						ELog.e(e.getMessage(), e);
					}
				}
			}


			private Integer buildMerPlans(List<MAuditOutletPlanDTO> plans) throws SQLException {
				Integer numOutlet = 0;
				Long routeScheduleId = null;
				for(MAuditOutletPlanDTO plan : plans){
					if(plan.getOutletModel() != null && plan.getOutletModel().size() > 0){
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
				if(plan.getLatitude() != null) {
					outletEntity.setLat(Double.parseDouble(plan.getLatitude().toString()));
				}
				if(plan.getLongitude() != null) {
                    outletEntity.setLg(Double.parseDouble(plan.getLongitude().toString()));
                }
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
