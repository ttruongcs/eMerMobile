package com.banvien.fcv.mobile.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.banvien.fcv.mobile.R;
import com.banvien.fcv.mobile.ScreenContants;
import com.banvien.fcv.mobile.StartDayActivity;
import com.banvien.fcv.mobile.db.Repo;
import com.banvien.fcv.mobile.db.entities.OutletEntity;
import com.banvien.fcv.mobile.db.entities.OutletMerEntity;
import com.banvien.fcv.mobile.dto.getfromserver.MAuditOutletPlanDTO;
import com.banvien.fcv.mobile.dto.getfromserver.OutletModelDTO;
import com.banvien.fcv.mobile.dto.getfromserver.OutletModelDetailDTO;
import com.banvien.fcv.mobile.dto.routeschedule.MRouteScheduleDetailDTO;
import com.banvien.fcv.mobile.library.SyncService;
import com.banvien.fcv.mobile.rest.RestClient;
import com.banvien.fcv.mobile.utils.ChangeStatusTimeline;
import com.banvien.fcv.mobile.utils.DataBinder;
import com.banvien.fcv.mobile.utils.ELog;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Linh Nguyen on 6/24/2016.
 */
public class AddOutletAdapter extends RecyclerView.Adapter {
    private static final int DELETE_CURRENT_TASK = 1;
    private static final int NOT_DELETE_CURRENT_TASK = -1;
    private List<MRouteScheduleDetailDTO> mData;
    private Activity activity;
    private Repo repo;

    public AddOutletAdapter(Activity activity, List<MRouteScheduleDetailDTO> entities, Repo repo) {
        this.activity = activity;
        this.mData = entities;
        this.repo = repo;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_outlet_item, parent, false);
        ItemHolder itemHolder = new ItemHolder(v);

        return itemHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemHolder itemHolder = (ItemHolder) holder;
        itemHolder.bindViews(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.cbAddOutlet)
        CheckBox cbAddOutlet;

        @BindView(R.id.tvAddOutlet)
        TextView tvAddOutlet;

        public ItemHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindViews(final MRouteScheduleDetailDTO routeScheduleDetailDTO) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String time = sdf.format(routeScheduleDetailDTO.getDate());
            String outletInfo = routeScheduleDetailDTO.getOutlet().getName() + " " + "(" + time + ")";
            tvAddOutlet.setText(outletInfo);
            cbAddOutlet.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked) {
                       showDialog(routeScheduleDetailDTO);
                    }
                }
            });
        }

        private void showDialog(final MRouteScheduleDetailDTO routeScheduleDetailDTO) {
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);

            builder.setTitle(activity.getString(R.string.dialog_delete_outlet_title));
            builder.setMessage(activity.getString(R.string.dialog_delete_outlet));

            String positiveText = activity.getString(R.string.accept);
            builder.setPositiveButton(positiveText, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ELog.d("hello", "a");
                    addToServer(routeScheduleDetailDTO, DELETE_CURRENT_TASK);

                }
            });

            String negativeText = activity.getString(R.string.cancel);
            builder.setNegativeButton(negativeText, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    addToServer(routeScheduleDetailDTO, NOT_DELETE_CURRENT_TASK);
                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();

        }

        private void addToServer(MRouteScheduleDetailDTO routeScheduleDetailDTO, int deleteCurrentTask) {
            Call<Map<String, Object>> call = RestClient.getInstance().getOutletService()
                    .addRoute(routeScheduleDetailDTO.getRouteScheduleDetailId(), deleteCurrentTask);
            call.enqueue(new Callback<Map<String, Object>>() {
                @Override
                public void onResponse(Call<Map<String, Object>> call, Response<Map<String, Object>> response) {
                    Map<String, Object> result = response.body();
                    MAuditOutletPlanDTO auditOutletPlanDTO = DataBinder.readAuditOutletPlan(result.get("auditOutletPlan"));
                    if(auditOutletPlanDTO != null) {
                        addMerResult(auditOutletPlanDTO);
                        Toast.makeText(itemView.getContext(), activity.getString(R.string.add_success), Toast.LENGTH_SHORT).show();
                        activity.finish();
                    }
                }

                @Override
                public void onFailure(Call<Map<String, Object>> call, Throwable t) {
                    Toast.makeText(itemView.getContext(), activity.getString(R.string.add_failed), Toast.LENGTH_SHORT).show();
                }
            });
        }

        private void addMerResult(MAuditOutletPlanDTO auditOutletPlanDTO) {
            OutletEntity entity = new OutletEntity();
            entity.setOutletId(auditOutletPlanDTO.getOutletId());
            entity.setName(auditOutletPlanDTO.getName());
            entity.setCode(auditOutletPlanDTO.getCode());
            entity.setLocationNo(auditOutletPlanDTO.getLocationNo());
            entity.setStreet(auditOutletPlanDTO.getStreet());
            entity.setDistrict(auditOutletPlanDTO.getDistrict());
            entity.setStatus(0);
            entity.setWard(auditOutletPlanDTO.getWard());
            entity.setRouteScheduleId(auditOutletPlanDTO.getRouteScheduleId());
            entity.setRouteScheduleDetailId(auditOutletPlanDTO.getRouteScheduleDetailId());
            if(auditOutletPlanDTO.getCity() != null && auditOutletPlanDTO.getCity().getName() != null) {
                entity.setCityName(auditOutletPlanDTO.getCity().getName());
            }
            entity.setLat(auditOutletPlanDTO.getLatitude());
            entity.setLg(auditOutletPlanDTO.getLongitude());
            entity.setAuditedToday(auditOutletPlanDTO.getAuditedToday());
            try {
                repo.getOutletDAO().addOrUpdate(entity);
            } catch (SQLException e) {
                ELog.d(e.getMessage(), e);
            }

            List<OutletMerEntity> outletMerEntities = new ArrayList<>();
            for(OutletModelDTO outletModelDTO : auditOutletPlanDTO.getOutletModel()) {
                for(OutletModelDetailDTO outletModelDetailDTO : outletModelDTO.getOutletModelDetail()) {
                    OutletMerEntity outletMerEntity = new OutletMerEntity();
                    outletMerEntity.setOutletId(auditOutletPlanDTO.getOutletId());
                    outletMerEntity.setRouteScheduleId(auditOutletPlanDTO.getRouteScheduleId());
                    outletMerEntity.setOutletModelId(outletModelDTO.getOutletModelId());
                    outletMerEntity.setOutletModelName(outletModelDTO.getName());
                    outletMerEntity.setRouteScheduleDetailId(auditOutletPlanDTO.getRouteScheduleDetailId());
                    outletMerEntity.setDataType(outletModelDetailDTO.getDataType());
                    outletMerEntity.setRegisterValue(outletModelDetailDTO.getReferenceValue());
                    outletMerEntities.add(outletMerEntity);
                }
            }
            try {
                repo.getOutletMerDAO().createListMerResult(outletMerEntities);
            } catch (SQLException e) {
                ELog.d(e.getMessage(), e);
            }
        }
    }
}
