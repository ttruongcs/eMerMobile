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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.banvien.fcv.mobile.BeforeDisplayActivity;
import com.banvien.fcv.mobile.R;
import com.banvien.fcv.mobile.ScreenContants;
import com.banvien.fcv.mobile.db.Repo;
import com.banvien.fcv.mobile.dto.OutletMerDTO;
import com.banvien.fcv.mobile.dto.ProductDTO;
import com.banvien.fcv.mobile.dto.getfromserver.MProductDTO;
import com.banvien.fcv.mobile.utils.EIEUtil;
import com.banvien.fcv.mobile.utils.ELog;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Linh Nguyen on 5/27/2016.
 */
public class BeforeDisplayAdapter extends BaseAdapter {
    private static final int MHS_KEY = 0;
    private static final int MHS_VALUE = 1;

    private BeforeDisplayActivity activity;
    private List<MProductDTO> mData;
    private Map<String, Integer> mhsCodes;
    private Repo repo;
    private LayoutInflater mInflater;
    private EditText edFacing;
    private TextView edCount;
    private EditText edEIE;
    private Long outletId;
    private Long outletModelId;
    private String totalFacing;
    private SharedPreferences sharedPreferenceBefores;
    private SharedPreferences.Editor editorBefore;
    private ListView listView;
    private Map<String, Integer> facingMaps = new HashMap<>();

    public BeforeDisplayAdapter(BeforeDisplayActivity activity, List<MProductDTO> productDTOs
            , EditText edFacing, EditText edEIE, Repo repo, Long outletId, Long outletModelId, TextView edCount) {
        this.activity = activity;
        this.mData = productDTOs;
        this.edFacing = edFacing;
        this.repo = repo;
        this.outletId = outletId;
        this.outletModelId = outletModelId;
        this.edCount = edCount;
        this.edEIE = edEIE;

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
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = mInflater.inflate(R.layout.before_display_item, null);
        final ItemHolder itemHolder = new ItemHolder(v);
        if(position == 0) {
            mhsCodes = itemHolder.loadMhs();
            edCount.setText(Integer.toString(mhsCodes.size()));
            totalFacing = itemHolder.loadTotalFacing();
        }
        itemHolder.bindViews(mData.get(position));
        listView = (ListView) parent;

        itemHolder.editMHS.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    if(listView != null &&
                            position <= listView.getLastVisiblePosition() &&
                            position != mData.size() - 1) {  //audit object holds the data for the adapter
                        listView.smoothScrollToPosition(position + 1);
                        listView.postDelayed(new Runnable() {
                            public void run() {
                                TextView nextField = (TextView)itemHolder.editMHS.focusSearch(View.FOCUS_DOWN);
                                if(nextField != null) {
                                    nextField.requestFocus(4);
                                }
                            }
                        }, 200);
                        return true;
                    }

                    return true;
                }
                return false;
            }
        });

        return v;
    }


    public class ItemHolder {

        @BindView(R.id.tvMHS)
        TextView productName;

        @BindView(R.id.edMHS)
        EditText editMHS;

        public ItemHolder(View itemView) {
            ButterKnife.bind(this, itemView);
        }

        public void bindViews(final MProductDTO productDTO) {
            facingMaps.put(productDTO.getCode(), productDTO.getWeight().intValue());
            try {
                productName.setText(productDTO.getName());
                if(mhsCodes.get(productDTO.getCode()) != null) {
                    editMHS.setText(Integer.toString(mhsCodes.get(productDTO.getCode())));
                    editorBefore.putInt(productDTO.getCode(), mhsCodes.get(productDTO.getCode()));
                    editorBefore.apply();
                } else {
                    editorBefore.putInt(productDTO.getCode(), 0);
                    editorBefore.apply();
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

        private void bindEvents(final MProductDTO productDTO) {
//            editMHS.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//                @Override
//                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                    if (actionId == EditorInfo.IME_ACTION_DONE) {
//                        try {
//                            int quantity = Integer.parseInt(v.getText().toString());
//                            if (quantity > 0) {
//                                mhsCodes.put(productDTO.getCode(), + quantity);
//                            } else if(quantity == 0) {
//                                mhsCodes.remove(productDTO.getCode());
//                            } else {
//
//                            }
//                            editorBefore.putInt(productDTO.getCode(), quantity);
//                            editorBefore.apply();
//                            addMhs(mhsCodes);
//                            calculateFacing(mhsCodes);
//                        } catch (NumberFormatException e) {
//
//                        }
//
//                        return true;
//                    }
//                    return false;
//                }
//            });

            editMHS.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    EditText numberInput;
                    numberInput = (EditText) v;
                    if (!hasFocus) {
                        int quantity = 0;
                        try {
                            quantity = Integer.parseInt(numberInput.getText().toString());
                            if (quantity > 0) {
                                mhsCodes.put(productDTO.getCode(), quantity);
                            } else if(quantity == 0) {
                                mhsCodes.remove(productDTO.getCode());
                            } else {

                            }

                        } catch (NumberFormatException e) {
                            mhsCodes.remove(productDTO.getCode());
                        }
                        editorBefore.putInt(productDTO.getCode(), quantity);
                        editorBefore.apply();
                        addMhs(mhsCodes, productDTO.getWeight().intValue());
                        calculateFacing();
                        checkEIE(30.0, 30.0, 40.0);

                    }
                }
            });

            editMHS.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
//                    if(s.toString().equals("")) {
//                        editMHS.setText(Integer.toString(0));
//                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

        }

        private void checkEIE(Double hotzone, Double mhs, Double facing) {
            if(EIEUtil.isPass(hotzone, mhs, facing)) {
                // Change status EIE here
            }
        }

        /*Total facing equal total mhs which have quantity > 0*/
        private void calculateFacing() {
            edCount.setText(Integer.toString(mhsCodes.size()));
            int sum = 0;
            if(mhsCodes.size() > 0) {
                for(String key : mhsCodes.keySet()) {
                    sum += (mhsCodes.get(key) * facingMaps.get(key));
                }
            } else {
                sum = 0;
            }

            addActualValue(Integer.valueOf(Integer.toString(sum)));
            edFacing.setText(Integer.toString(sum));
        }

        /*Add mhs with actual value: (code:quantity,code2:quantity,...)*/
        private void addMhs(Map<String, Integer> mhsCodes, Integer weight) {
            String mhsValue = "";

            try {
                if(mhsCodes.size() > 0) {
                    for(String key : mhsCodes.keySet()) {
                        mhsValue += key + ":" + mhsCodes.get(key).toString() + ",";
                    }
                    mhsValue = mhsValue.substring(0, mhsValue.length() - 1);
                    repo.getOutletMerDAO().updateActualValueBefore(outletId, outletModelId,mhsValue, ScreenContants.MHS_BEFORE, ScreenContants.MHS);
                } else {
                    repo.getOutletMerDAO().updateActualValueBefore(outletId, outletModelId, null, ScreenContants.MHS_BEFORE, ScreenContants.MHS);
                }
            } catch (SQLException e) {
                ELog.d(e.getMessage(), e);
            }
        }

        /*Add Facing to actualValue of this outletModel */
        private void addActualValue(int facing) {
            try {
                repo.getOutletMerDAO().updateActualValueBefore(outletId, outletModelId,
                        String.valueOf(facing), ScreenContants.FACING_BEFORE, ScreenContants.FACING);
            } catch (SQLException e) {
                ELog.d(e.getMessage(), e);
            }
        }

        /*Init total facing show on screen*/
        public String loadTotalFacing() {
            ELog.d("outletId", String.valueOf(outletId));
            String result = null;
            try {
                result = repo.getOutletMerDAO().findActualValueByDataType(ScreenContants.FACING_BEFORE, outletId, outletModelId);
                edFacing.setText(result);
            } catch (SQLException e) {
                ELog.d(e.getMessage(), e);
            }
            return result;
        }
    }
}
