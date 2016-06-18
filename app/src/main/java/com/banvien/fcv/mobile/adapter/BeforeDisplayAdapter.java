package com.banvien.fcv.mobile.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
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
public class BeforeDisplayAdapter extends BaseAdapter {
    private BeforeDisplayActivity activity;
    private List<ProductDTO> mData;
    private Repo repo;
    private LayoutInflater mInflater;

    public BeforeDisplayAdapter(BeforeDisplayActivity activity, List<ProductDTO> productDTOs, Repo repo) {
        this.activity = activity;
        this.mData = productDTOs;
        this.repo = repo;
        mInflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = mInflater.inflate(R.layout.before_display_item, null);
        ItemHolder itemHolder = new ItemHolder(v);
        itemHolder.bindViews(mData.get(position));

        return v;
    }

    public class ItemHolder  {

        @Bind(R.id.tvMHS)
        TextView productName;

        @Bind(R.id.edMHS)
        EditText editText;

        public ItemHolder(View itemView) {
            ButterKnife.bind(this, itemView);
        }

        public void bindViews(final ProductDTO productDTO) {
            try {
                productName.setText(productDTO.getName());
//                OutletMerDTO checkedObject = repo.getOutletMerDAO()
//                        .findReferencedDisplay(ScreenContants.MHS_BEFORE, outletMerDTO.get_id());

//                if(checkedObject.get_id() > 0 && checkedObject.getActualValue() != null) {
//                //    activity.setTvCountChecked(ScreenContants.INCREASE_VALUE);
//                    checkBox.setChecked(true);
//                }
//
//                checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                    @Override
//                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                        String actualValue = null;
//                        if(isChecked) {
//                //            activity.setTvCountChecked(ScreenContants.INCREASE_VALUE);
//                            boolean isExist = checkProductExist(outletMerDTO);
//                            if(!isExist) {
//                                addProductBeforeData(outletMerDTO);
//                            } else {
//                                actualValue = outletMerDTO.getRegisterValue();
//                                updateProductBefore(outletMerDTO, actualValue);
//                            }
//                        } else {
//                //            activity.setTvCountChecked(ScreenContants.DECREASE_VALUE);
//                            updateProductBefore(outletMerDTO, actualValue);
//                        }
//                    }
//                });
            } catch (Exception e) {
                ELog.d("Can't get product from server", e);
            }
        }

        private void updateProductBefore(OutletMerDTO outletMerDTO, String actualValue) {
            try {
                repo.getOutletMerDAO().updateMHStMer(outletMerDTO, actualValue);
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
