package com.banvien.fcv.mobile.adapter;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.banvien.fcv.mobile.ActionActivity;
import com.banvien.fcv.mobile.InOutletHomeActivity;
import com.banvien.fcv.mobile.R;
import com.banvien.fcv.mobile.ScreenContants;
import com.banvien.fcv.mobile.beanutil.OutletUtil;
import com.banvien.fcv.mobile.core.A;
import com.banvien.fcv.mobile.db.Repo;
import com.banvien.fcv.mobile.db.entities.OutletEntity;
import com.banvien.fcv.mobile.db.entities.StatusInOutletEntity;
import com.banvien.fcv.mobile.dto.OutletDTO;
import com.banvien.fcv.mobile.fragments.BaseFragment;
import com.banvien.fcv.mobile.utils.ColorGenerator;
import com.banvien.fcv.mobile.utils.ELog;
import com.banvien.fcv.mobile.utils.TextDrawable;

import java.sql.SQLException;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Linh Nguyen on 5/20/2016.
 */
public class OutletListAdapter extends RecyclerView.Adapter<OutletListAdapter.OutletHolder> {
    private ColorGenerator mColorGenerator = ColorGenerator.MATERIAL;
    private TextDrawable.IBuilder mDrawableBuilder;
    private List<OutletDTO> mData;
    private Fragment fragment;
    private String flag;
    private Repo repo;

    public OutletListAdapter(List<OutletDTO> outletDTOs, Fragment fragment, String flag, Repo repo) {
        this.mData = outletDTOs;
        this.fragment = fragment;
        this.flag = flag;
        this.repo = repo;
    }

    @Override
    public OutletHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        OutletHolder outletHolder = new OutletHolder(v);
        mDrawableBuilder = TextDrawable.builder()
                .round();
        return outletHolder;
    }

    @Override
    public void onBindViewHolder(OutletHolder holder, int position) {
        OutletHolder outletHolder = holder;
        outletHolder.bindViews(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class OutletHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item)
        RelativeLayout item;

        @BindView(R.id.name_distributor_label)
        TextView outletName;

        @BindView(R.id.address_distributor_label)
        TextView outletCode;

        @BindView(R.id.gps_distributor_label)
        TextView outletAddress;

        public OutletHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindViews(final OutletDTO outletDTO) {
            ELog.d("outlet", outletDTO.toString());
            this.outletName.setText(outletDTO.getName());
            this.outletCode.setText(buildOutletCode(outletDTO.getCode(), outletDTO.getdCode()));
            this.outletAddress.setText(buildOutletAdress(outletDTO.getLocationNo(), outletDTO.getStreet()
                    , outletDTO.getWard(), outletDTO.getCityName()));

//            Random r = new Random();
//            TextDrawable drawable = mDrawableBuilder.build(String.valueOf(outletDTO.getName()
//                    .charAt(0)).toUpperCase(), mColorGenerator.getColor(outletDTO.getName() + r.nextInt()));
//            imageOutlet.setImageDrawable(drawable);

            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if  (outletDTO.getStatus().equals(ScreenContants.STATUS_STEP_NOTYET)
                            || outletDTO.getStatus().equals(ScreenContants.STATUS_STEP_INPROGRESS)) {
                        if(outletDTO.getStatus().equals(ScreenContants.STATUS_STEP_NOTYET)) {
                            configStatusInOutlet(outletDTO.getRouteScheduleDetailId());
                        }
                        updateStatus(outletDTO, ScreenContants.DOING);
                        Intent intent = new Intent(view.getContext(), InOutletHomeActivity.class);
                        intent.putExtra(ScreenContants.KEY_OUTLET_ID, outletDTO.getOutletId());
                        intent.putExtra(ScreenContants.KEY_ROUTESCHEDULE_DETAIL, outletDTO.getRouteScheduleDetailId());
                        view.getContext().startActivity(intent);
                    }
                }
            });
        }

        private void updateStatus(OutletDTO outletDTO, String typeStatus) {
            OutletEntity outletEntity = OutletUtil.convertToEntity(outletDTO);
            if(typeStatus.equals(ScreenContants.DOING)) {
                outletEntity.setStatus(ScreenContants.STATUS_STEP_INPROGRESS);
            }

            try {
                int rowSuccess = repo.getOutletDAO().update(outletEntity);
                if (rowSuccess == 0) {
                    ELog.d("Can't update outlet");
                }
            } catch (SQLException e) {
                ELog.d(e.getMessage(), e);
            }

        }

        private void configStatusInOutlet(Long routeScheduleDetailId) {
            try {
                List<StatusInOutletEntity> statusInOutletEntities = repo.getStatusInOutletDAO().queryForEq("routeScheduleDetailId", routeScheduleDetailId);
                if (statusInOutletEntities.size() <= 0) {
                    StatusInOutletEntity statusInOutletEntity = new StatusInOutletEntity();
                    statusInOutletEntity.setCheckIn(ScreenContants.STATUS_STEP_INPROGRESS);
                    statusInOutletEntity.setChupAnhOverview(ScreenContants.STATUS_STEP_NOTYET);
                    statusInOutletEntity.setHutHangDatHang(ScreenContants.STATUS_STEP_NOTYET);
                    statusInOutletEntity.setKhaoSatTrungBayTruoc(ScreenContants.STATUS_STEP_NOTYET);
                    statusInOutletEntity.setKhaoSatTrungBaySau(ScreenContants.STATUS_STEP_NOTYET);
                    statusInOutletEntity.setKhaoSat(ScreenContants.STATUS_STEP_NOTYET);
                    statusInOutletEntity.setHutHangDatHang(ScreenContants.STATUS_STEP_NOTYET);
                    statusInOutletEntity.setXemThongTinDangKyLichSuEIE(ScreenContants.STATUS_STEP_DONE);
                    statusInOutletEntity.setKhaoSat(ScreenContants.STATUS_STEP_NOTYET);
                    statusInOutletEntity.setDongBoCuaHang(ScreenContants.STATUS_STEP_NOTYET);
                    statusInOutletEntity.setTinhTrangCuaHang(ScreenContants.STATUS_STEP_NOTYET);
                    statusInOutletEntity.setRouteScheduleDetailId(routeScheduleDetailId);

                    repo.getStatusInOutletDAO().addStatusHome(statusInOutletEntity);
                } else {
                    ELog.d("Status outlet đã tồn tại");
                }


            } catch (SQLException e) {
                ELog.d(e.getMessage(), e);
            }
        }

        private String buildOutletCode(String outletCode, String distributorCode) {
            StringBuffer stringBuffer = new StringBuffer();

            if (outletCode != null) {
                stringBuffer.append(outletCode);
            }
            if (distributorCode != null) {
                stringBuffer.append(distributorCode);
            }

            return stringBuffer.toString();
        }

        private String buildOutletAdress(String locationNum, String street, String ward, String cityName) {
            StringBuffer stringBuffer = new StringBuffer();
            if (null != locationNum) {
                stringBuffer.append(locationNum).append(",  ");
            }
            if (null != street) {
                stringBuffer.append(street).append("   ");
            }
            if (null != ward) {
                stringBuffer.append(ward).append("   ");
            }
            if (null != cityName) {
                stringBuffer.append(cityName);
            }
            return stringBuffer.toString();
        }
    }
}
