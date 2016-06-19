package com.banvien.fcv.mobile.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
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
import com.banvien.fcv.mobile.sql.Constants;
import com.banvien.fcv.mobile.utils.ELog;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
    private Map<String, Integer> mhsCodes;
    private EditText edFacing;
    private Long outletId;
    private Long outletModelId;
    private String totalFacing;

    public BeforeDisplayAdapter(BeforeDisplayActivity activity, List<ProductDTO> productDTOs
            , EditText edFacing, Repo repo, Long outletId, Long outletModelId) {
        this.activity = activity;
        this.mData = productDTOs;
        this.edFacing = edFacing;
        this.repo = repo;
        this.outletId = outletId;
        this.outletModelId = outletModelId;
        mInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mhsCodes = new HashMap<>();
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

    public class ItemHolder {

        @Bind(R.id.tvMHS)
        TextView productName;

        @Bind(R.id.edMHS)
        EditText editMHS;

        public ItemHolder(View itemView) {
            ButterKnife.bind(this, itemView);
        }

        public void bindViews(final ProductDTO productDTO) {
            try {
                productName.setText(productDTO.getName());

                bindEvents(productDTO);
            } catch (Exception e) {
                ELog.d("Can't get product from server", e);
            }
        }

        private void bindEvents(final ProductDTO productDTO) {
            editMHS.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_DONE) {
                        try {
                            int quantity = Integer.parseInt(v.getText().toString());
                            if (quantity > 0) {
                                mhsCodes.put(productDTO.getCode(), + quantity);
                                ELog.d("quantity", mhsCodes.toString());
                                addMhs(mhsCodes);


//                                if (totalFacing != null && v.getText().toString() != "") {
//                                    int facing = Integer.valueOf(totalFacing)
//                                            + Integer.valueOf(v.getText().toString());
//                                    edFacing.setText(Integer.toString(facing));
//                                    addActualValue(facing);
//                                } else {
//                                    if (v.getText().toString() != "") {
//                                        edFacing.setText(v.getText().toString());
//                                        addActualValue(Integer.valueOf(v.getText().toString()));
//                                    }
//                                }
                            }
                        } catch (NumberFormatException e) {

                        }

                        return true;
                    }
                    return false;
                }
            });

            editMHS.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    EditText numberInput;
                    numberInput = (EditText) v;
                    if (!hasFocus) {
                        try {
                            int quantity = Integer.parseInt(numberInput.getText().toString());
                            if (quantity > 0) {
                                mhsCodes.put(productDTO.getCode(), + quantity);
                                ELog.d("quantity", mhsCodes.toString());
                                addMhs(mhsCodes);
//                                if (totalFacing != null && numberInput.getText().toString() != "") {
//                                    int facing = Integer.valueOf(totalFacing) + Integer.valueOf(numberInput.getText().toString());
//                                    edFacing.setText(Integer.toString(facing));
//                                    addActualValue(facing);
//                                } else {
//                                    if (numberInput.getText().toString() != "") {
//                                        edFacing.setText(numberInput.getText().toString());
//                                        addActualValue(Integer.valueOf(numberInput.getText().toString()));
//                                    }
//                                }
                            }
                        } catch (NumberFormatException e) {

                        }
                    } else if(hasFocus) {
//                        try {
//                            if (numberInput.getText().toString() != "" && Integer.valueOf(numberInput.getText().toString()) > 0) {
//                                totalFacing = repo.getOutletMerDAO().findActualValueByDataType(ScreenContants.FACING, outletId, outletModelId);
//                                int newFacing = Integer.valueOf(totalFacing) - Integer.valueOf(numberInput.getText().toString());
//                                edFacing.setText(Integer.toString(newFacing));
//                                ELog.d("Oke");
//                            }
//                        } catch (NumberFormatException e) {
//
//                        } catch (SQLException e) {
//                            ELog.d(e.getMessage(), e);
//                        }
                    }
                }
            });

            editMHS.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                    try {
//                        totalFacing = repo.getOutletMerDAO().findActualValueByDataType(ScreenContants.FACING, outletId, outletModelId);
//                        ELog.d("totalFacing", totalFacing);
//                    } catch (SQLException e) {
//                        ELog.d(e.getMessage(), e);
//                    }
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });


        }

        private void addMhs(Map<String, Integer> mhsCodes) {
            String mhsValue = "";

            try {
                if(mhsCodes.size() > 0) {
                    for(String key : mhsCodes.keySet()) {
                        mhsValue += key + ":" + mhsCodes.get(key).toString() + ",";
                    }
                    mhsValue = mhsValue.substring(0, mhsValue.length() - 1);
                    ELog.d("mhs","value: " + mhsValue);
                    repo.getOutletMerDAO().updateActualValue(outletId, outletModelId, ScreenContants.MHS, mhsValue);
                }
            } catch (SQLException e) {
                ELog.d(e.getMessage(), e);
            }
        }

        private void addActualValue(int facing) {
            try {
                repo.getOutletMerDAO().updateActualValue(outletId, outletModelId
                        , ScreenContants.FACING, String.valueOf(facing));
            } catch (SQLException e) {
                ELog.d(e.getMessage(), e);
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
