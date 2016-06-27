package com.banvien.fcv.mobile.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.AppCompatCheckBox;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.banvien.fcv.mobile.AfterDisplayActivity;
import com.banvien.fcv.mobile.BeforeDisplayActivity;
import com.banvien.fcv.mobile.R;
import com.banvien.fcv.mobile.ScreenContants;
import com.banvien.fcv.mobile.db.Repo;
import com.banvien.fcv.mobile.dto.AfterItemDTO;
import com.banvien.fcv.mobile.dto.OutletMerDTO;
import com.banvien.fcv.mobile.dto.getfromserver.MProductDTO;
import com.banvien.fcv.mobile.utils.ELog;
import com.j256.ormlite.stmt.query.In;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Linh Nguyen on 5/27/2016.
 */
public class AfterDisplayAdapter extends BaseAdapter {
    private static final int MHS_KEY = 0;
    private static final int MHS_VALUE = 1;
    private static final int MHS_VALUE_YESNO = 2;

    private AfterDisplayActivity activity;
    private List<MProductDTO> mData;
    private Repo repo;
    private LayoutInflater mInflater;
    private Map<String, AfterItemDTO> mhsCodes;
    private EditText edFacing;
    private Long outletId;
    private Long outletModelId;
    private String totalFacing;
    private SharedPreferences sharedPreferenceOrders;
    private SharedPreferences.Editor editor;
    private SharedPreferences sharedPreferences;

    public AfterDisplayAdapter(AfterDisplayActivity activity, List<MProductDTO> productDTOs
            , EditText edFacing, Repo repo, Long outletId, Long outletModelId, SharedPreferences preferences) {
        this.activity = activity;
        this.mData = productDTOs;
        this.edFacing = edFacing;
        this.repo = repo;
        this.outletId = outletId;
        this.outletModelId = outletModelId;
        mInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        sharedPreferences = preferences;
        sharedPreferenceOrders = this.activity.getSharedPreferences(ScreenContants.MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedPreferenceOrders.edit();
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
        View v = mInflater.inflate(R.layout.after_display_item, null);
        ItemHolder itemHolder = new ItemHolder(v);
        if(position == 0) {
            mhsCodes = itemHolder.loadMhs();
            totalFacing = itemHolder.loadTotalFacing();
        }
        itemHolder.bindViews(mData.get(position));

        return v;
    }

    public class ItemHolder {

        @Bind(R.id.tvBefore)
        TextView tvBefore;

        @Bind(R.id.tvMHS)
        TextView productName;

        @Bind(R.id.edMHS)
        EditText editMHS;

        @Bind(R.id.chHave)
        AppCompatCheckBox chHave;

        public ItemHolder(View itemView) {
            ButterKnife.bind(this, itemView);
        }

        public void bindViews(final MProductDTO productDTO) {
            ELog.d("sizePref", String.valueOf(sharedPreferences.getAll().size()));
            try {
                ELog.d("productCode", productDTO.getCode());
                productName.setText(productDTO.getName());
                if(mhsCodes.get(productDTO.getCode()) != null) {
                    editMHS.setText(Integer.toString(mhsCodes.get(productDTO.getCode()).getNumberOfFace()));
                    checkShortageExist(productDTO.getCode());
                    if(mhsCodes.get(productDTO.getCode()).getYesNo() > 0) {
                        chHave.setChecked(true);
                    } else {
                        chHave.setChecked(false);
                    }
                }
                String beforeValueMhs = sharedPreferences.getAll().get(productDTO.getCode()).toString();
                tvBefore.setText(beforeValueMhs);
                bindEvents(productDTO);
            } catch (Exception e) {
                ELog.d("Can't get product from server", e);
            }
        }

        private void checkShortageExist(String code) {
            int modelPrefId = sharedPreferences.getInt(code, -1);

            if(modelPrefId != -1) {
                if(modelPrefId == outletModelId) {
                    editor.remove(code);
                    editor.apply();
                } else {
                    editor.putInt(code, Integer.valueOf(outletModelId.toString()));
                    editor.apply();
                }

            }

        }


        private Map<String, AfterItemDTO> loadMhs() {
            Map<String, AfterItemDTO> result = new HashMap<>();
            Map<String, Object> properties = new HashMap<>();

            try {
                properties.put("outletId", outletId);
                properties.put("outletModelId", outletModelId);
                properties.put(ScreenContants.DATA_TYPE, ScreenContants.MHS_AFTER);

                OutletMerDTO outletMerDTO = repo.getOutletMerDAO().findFirstResultByProperties(properties);
                if(outletMerDTO != null && outletMerDTO.getActualValue() != null
                        && outletMerDTO.getActualValue() != "") {
                    String[] mhsString = outletMerDTO.getActualValue().split(",");

                    for(int i = 0; i < mhsString.length; i++) {
                        String[] mhs = mhsString[i].split(":");
                        AfterItemDTO afterItemDTO = new AfterItemDTO();
                        if(mhs.length > 2){
                            afterItemDTO.setNumberOfFace(Integer.valueOf(mhs[MHS_VALUE]));
                            afterItemDTO.setYesNo(Integer.valueOf(mhs[MHS_VALUE_YESNO]));;
                        }
                        else{
                            afterItemDTO.setNumberOfFace(0);
                            afterItemDTO.setYesNo(0);;
                        }
                        result.put(mhs[MHS_KEY], afterItemDTO);
                    }
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

            return result;
        }

        private void bindEvents(final MProductDTO productDTO) {
            editMHS.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_DONE) {
                        try {
                            int quantity = Integer.parseInt(v.getText().toString());
                            if (quantity > 0) {
                                AfterItemDTO afterItem = new AfterItemDTO();
                                afterItem.setYesNo(1);
                                afterItem.setNumberOfFace(quantity);
                                mhsCodes.put(productDTO.getCode(),  afterItem);
                                checkShortageExist(productDTO.getCode());
                            } else if(quantity == 0) {
                                mhsCodes.remove(productDTO.getCode());
                                editor.putInt(productDTO.getCode(), Integer.valueOf(outletModelId.toString()));
                                editor.apply();
                            } else {

                            }
                            if(quantity > 0) {
                                chHave.setChecked(Boolean.TRUE);
                            } else {
                                chHave.setChecked(Boolean.FALSE);
                            }
                            addMhs(mhsCodes, 1);
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
                                AfterItemDTO afterItem = new AfterItemDTO();
                                afterItem.setYesNo(1);
                                afterItem.setNumberOfFace(quantity);
                                mhsCodes.put(productDTO.getCode(), afterItem);
                                checkShortageExist(productDTO.getCode());
                            } else if(quantity == 0) {
                                mhsCodes.remove(productDTO.getCode());
                                editor.putInt(productDTO.getCode(), Integer.valueOf(outletModelId.toString()));
                                editor.apply();
                            } else {

                            }
                            if(quantity > 0) {
                                chHave.setChecked(Boolean.TRUE);
                            } else {
                                chHave.setChecked(Boolean.FALSE);
                            }
                            addMhs(mhsCodes, 1);
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

            chHave.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if(!b){
                        try {
                            repo.getOutletMerDAO().updateCheckYesNoAfter(outletId, outletModelId, productDTO.getCode(), 0);
                        } catch (SQLException e) {
                            ELog.d("Error when save have MHS");
                        }
                    } else{
                        try {
                            repo.getOutletMerDAO().updateCheckYesNoAfter(outletId, outletModelId, productDTO.getCode(), 1);
                        } catch (SQLException e) {
                            ELog.d("Error when save have MHS");
                        }
                    }
                }
            });

        }

        /*Total facing equal total mhs which have quantity > 0*/
        private void calculateFacing(Map<String, AfterItemDTO> mhsCodes) {
            int sum = 0;
            if(mhsCodes.size() > 0) {
                for(String key : mhsCodes.keySet()) {
                    sum += mhsCodes.get(key).getNumberOfFace();
                }
            } else {
                sum = 0;
            }

            addActualValue(Integer.valueOf(Integer.toString(sum)));
            edFacing.setText(Integer.toString(sum));
        }

        /*Add mhs with actual value: (code:quantity, code2:quantity,...)*/
        private void addMhs(Map<String, AfterItemDTO> mhsCodes, Integer yesNo) {
            String mhsValue = "";

            try {
                if(mhsCodes.size() > 0) {
                    for(String key : mhsCodes.keySet()) {
                        mhsValue += key + ":" + mhsCodes.get(key).getNumberOfFace().toString()+ ":" + yesNo.toString() + ",";
                    }
                    mhsValue = mhsValue.substring(0, mhsValue.length() - 1);
                    repo.getOutletMerDAO().updateActualValueAfter(outletId, outletModelId, mhsValue);
                } else {
                    repo.getOutletMerDAO().updateActualValueAfter(outletId, outletModelId, null);
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
