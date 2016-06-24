package com.banvien.fcv.mobile.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.banvien.fcv.mobile.BeforeDisplayActivity;
import com.banvien.fcv.mobile.R;
import com.banvien.fcv.mobile.ScreenContants;
import com.banvien.fcv.mobile.db.Repo;
import com.banvien.fcv.mobile.dto.OutletMerDTO;
import com.banvien.fcv.mobile.dto.ProductDTO;
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
    private static final int MHS_KEY = 0;
    private static final int MHS_VALUE = 1;

    private BeforeDisplayActivity activity;
    private List<ProductDTO> mData;
    private Map<String, Integer> mhsCodes;
    private Repo repo;
    private LayoutInflater mInflater;
    private EditText edFacing;
    private Long outletId;
    private Long outletModelId;
    private String totalFacing;
    private SharedPreferences sharedPreferenceBefores;
    private SharedPreferences.Editor editorBefore;

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
        sharedPreferenceBefores = this.activity.getSharedPreferences(ScreenContants.BeforePREFERENCES, Context.MODE_PRIVATE);
        editorBefore = sharedPreferenceBefores.edit();
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
        if(position == 0) {
            mhsCodes = itemHolder.loadMhs();
            totalFacing = itemHolder.loadTotalFacing();
        }
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
            editorBefore.putInt(productDTO.getCode(), 0);
            editorBefore.apply();

            try {
                productName.setText(productDTO.getName());
                if(mhsCodes.get(productDTO.getCode()) != null) {
                    editMHS.setText(Integer.toString(mhsCodes.get(productDTO.getCode())));
                }
                bindEvents(productDTO);
            } catch (Exception e) {
                ELog.d("Can't get product from server", e);
            }
        }

        private Map<String, Integer> loadMhs() {
            Map<String, Integer> result = new HashMap<>();
            Map<String, Object> properties = new HashMap<>();

            try {
                properties.put("outletId", outletId);
                properties.put("outletModelId", outletModelId);
                properties.put(ScreenContants.DATA_TYPE, ScreenContants.MHS_BEFORE);

                OutletMerDTO outletMerDTO = repo.getOutletMerDAO().findFirstResultByProperties(properties);
                if(outletMerDTO != null && outletMerDTO.getActualValue() != null
                        && outletMerDTO.getActualValue() != "") {
                    String[] mhsString = outletMerDTO.getActualValue().split(",");

                    for(int i = 0; i < mhsString.length; i++) {
                        String[] mhs = mhsString[i].split(":");
                        result.put(mhs[MHS_KEY], Integer.valueOf(mhs[MHS_VALUE]));
                    }
                }

            } catch (SQLException e) {
                ELog.d(e.getMessage(), e);
            }

            return result;
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
                            } else if(quantity == 0) {
                                mhsCodes.remove(productDTO.getCode());
                            } else {

                            }
                            editorBefore.putInt(productDTO.getCode(), quantity);
                            editorBefore.apply();
                            addMhs(mhsCodes);
                            calculateFacing(mhsCodes);
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
                            } else if(quantity == 0) {
                                mhsCodes.remove(productDTO.getCode());
                            } else {

                            }
                            editorBefore.putInt(productDTO.getCode(), quantity);
                            editorBefore.apply();
                            addMhs(mhsCodes);
                            calculateFacing(mhsCodes);
                        } catch (NumberFormatException e) {

                        }

                    }
                }
            });

            editMHS.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if(s.toString().equals("")) {
                        editMHS.setText(Integer.toString(0));
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

        }

        /*Total facing equal total mhs which have quantity > 0*/
        private void calculateFacing(Map<String, Integer> mhsCodes) {
            int sum = 0;
            if(mhsCodes.size() > 0) {
                for(String key : mhsCodes.keySet()) {
                    sum += mhsCodes.get(key);

                }
            } else {
                sum = 0;
            }

            addActualValue(Integer.valueOf(Integer.toString(sum)));
            edFacing.setText(Integer.toString(sum));
        }

        /*Add mhs with actual value: (code:quantity,code2:quantity,...)*/
        private void addMhs(Map<String, Integer> mhsCodes) {
            String mhsValue = "";

            try {
                if(mhsCodes.size() > 0) {
                    for(String key : mhsCodes.keySet()) {
                        mhsValue += key + ":" + mhsCodes.get(key).toString() + ",";
                    }
                    mhsValue = mhsValue.substring(0, mhsValue.length() - 1);
                    repo.getOutletMerDAO().updateActualValueBefore(outletId, outletModelId,mhsValue);
                } else {
                    repo.getOutletMerDAO().updateActualValueBefore(outletId, outletModelId, null);
                }
            } catch (SQLException e) {
                ELog.d(e.getMessage(), e);
            }
        }

        /*Add Facing to actualValue of this outletModel */
        private void addActualValue(int facing) {
            try {
                repo.getOutletMerDAO().updateActualValue(outletId, outletModelId
                        , ScreenContants.FACING, String.valueOf(facing));
            } catch (SQLException e) {
                ELog.d(e.getMessage(), e);
            }
        }

        /*Init total facing show on screen*/
        public String loadTotalFacing() {
            ELog.d("outletId", String.valueOf(outletId));
            String result = null;
            try {
                result = repo.getOutletMerDAO().findActualValueByDataType(ScreenContants.FACING, outletId, outletModelId);
                edFacing.setText(result);
            } catch (SQLException e) {
                ELog.d(e.getMessage(), e);
            }
            return result;
        }
    }
}
