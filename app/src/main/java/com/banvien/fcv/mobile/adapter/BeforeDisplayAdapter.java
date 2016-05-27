package com.banvien.fcv.mobile.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.banvien.fcv.mobile.BeforeDisplayActivity;
import com.banvien.fcv.mobile.R;
import com.banvien.fcv.mobile.ScreenContants;
import com.banvien.fcv.mobile.beanutil.OutletMerUtil;
import com.banvien.fcv.mobile.db.Repo;
import com.banvien.fcv.mobile.dto.OutletMerDTO;
import com.banvien.fcv.mobile.dto.ProductDTO;
import com.banvien.fcv.mobile.utils.ELog;

import java.sql.SQLException;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Linh Nguyen on 5/27/2016.
 */
public class BeforeDisplayAdapter extends RecyclerView.Adapter<BeforeDisplayAdapter.ItemHolder> {
    private BeforeDisplayActivity activity;
    private List<OutletMerDTO> mData;
    private Repo repo;

    public BeforeDisplayAdapter(BeforeDisplayActivity activity, List<OutletMerDTO> outletMerDTOs, Repo repo) {
        this.activity = activity;
        this.mData = outletMerDTOs;
        this.repo = repo;
    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.after_display_item, parent, false);
        ItemHolder itemHolder = new ItemHolder(v);

        return itemHolder;
    }

    @Override
    public void onBindViewHolder(ItemHolder holder, int position) {
        ItemHolder itemHolder = holder;
        itemHolder.bindViews(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.tvMHS)
        TextView productName;

        @Bind(R.id.checkBox)
        CheckBox checkBox;

        public ItemHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindViews(final OutletMerDTO outletMerDTO) {
            ProductDTO productDTO;
            try {
                productDTO = repo.getProductDAO().findByProductId(Long.valueOf(outletMerDTO.getRegisterValue()));
                productName.setText(productDTO.getName());
                OutletMerDTO checkedObject = repo.getOutletMerDAO()
                        .findReferencedDisplay(ScreenContants.MHS_BEFORE, outletMerDTO.get_id());

                if(checkedObject.get_id() > 0 && checkedObject.getActualValue() != null) {
                    checkBox.setChecked(true);
                }

                checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        String actualValue = null;
                        if(isChecked) {
                            boolean isExist = checkProductExist(outletMerDTO);
                            if(!isExist) {
                                addProductBeforeData(outletMerDTO);
                            } else {
                                actualValue = outletMerDTO.getRegisterValue();
                                updateProductBefore(outletMerDTO, actualValue);
                            }
                        } else {
                            updateProductBefore(outletMerDTO, actualValue);
                        }
                    }
                });
            } catch (SQLException e) {
                ELog.d("Can't get product from server", e);
            }
        }

        private void updateProductBefore(OutletMerDTO outletMerDTO, String actualValue) {
            try {
                repo.getOutletMerDAO().updateOutletMer(outletMerDTO, actualValue);
            } catch (SQLException e) {
                ELog.d("Can't set actual value to null", e);
            }
        }

        private void addProductBeforeData(OutletMerDTO outletMerDTO) {
            OutletMerDTO insertItem = bindData(outletMerDTO);
            try {
                repo.getOutletMerDAO().addOutletMerEntity(OutletMerUtil.convertToEntity(insertItem));
            } catch (SQLException e) {
                ELog.d("Can't insert product before item", e);
            }

        }

        private OutletMerDTO bindData(OutletMerDTO outletMerDTO) {
            OutletMerDTO data = new OutletMerDTO();
            data.setReferenceValue(String.valueOf(outletMerDTO.get_id()));
            data.setActualValue(outletMerDTO.getRegisterValue());
            data.setDataType(ScreenContants.MHS_BEFORE);
            data.setExhibitRegisteredDetailId(outletMerDTO.getExhibitRegisteredDetailId());
            data.setExhibitRegisteredId(outletMerDTO.getExhibitRegisteredId());
            data.setOutletId(outletMerDTO.getOutletId());
            data.setRouteScheduleId(outletMerDTO.getRouteScheduleId());
            data.setRouteScheduleDetailId(outletMerDTO.getRouteScheduleDetailId());
            data.setRegisterValue(outletMerDTO.getRegisterValue());

            return data;
        }

        public boolean checkProductExist(OutletMerDTO outletMerDTO) {
            boolean isExist = false;
            try {
                isExist = repo.getOutletMerDAO().checkExistByReferenceValue(ScreenContants.MHS_BEFORE,
                        String.valueOf(outletMerDTO.get_id()), outletMerDTO.getOutletId());
            } catch (SQLException e) {
                ELog.d(e.getMessage(), e);
            }

            return isExist;
        }
    }
}
