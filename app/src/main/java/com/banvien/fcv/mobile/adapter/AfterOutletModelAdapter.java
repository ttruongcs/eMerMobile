package com.banvien.fcv.mobile.adapter;

import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.banvien.fcv.mobile.AfterDisplayActivity;
import com.banvien.fcv.mobile.R;
import com.banvien.fcv.mobile.ScreenContants;
import com.banvien.fcv.mobile.beanutil.OutletMerUtil;
import com.banvien.fcv.mobile.db.Repo;
import com.banvien.fcv.mobile.dto.AfterDisplayDTO;
import com.banvien.fcv.mobile.dto.OutletMerDTO;
import com.banvien.fcv.mobile.dto.getfromserver.HotZoneDTO;
import com.banvien.fcv.mobile.dto.getfromserver.MProductDTO;
import com.banvien.fcv.mobile.utils.ELog;
import com.banvien.fcv.mobile.utils.UiUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Linh Nguyen on 6/16/2016.
 */
public class AfterOutletModelAdapter extends RecyclerView.Adapter {

    private List<AfterDisplayDTO> mData;
    private AfterDisplayActivity activity;
    private List<HotZoneDTO> hotzoneDTOs;
    private Long outletId;
    private Repo repo;
    private SharedPreferences preferences;

    public AfterOutletModelAdapter(AfterDisplayActivity activity, List<AfterDisplayDTO> data
            , List<HotZoneDTO> hotzoneDTOs, Repo repo, Long outletId, SharedPreferences preferences) {
        this.mData = data;
        this.activity = activity;
        this.hotzoneDTOs = hotzoneDTOs;
        this.repo = repo;
        this.outletId = outletId;
        this.preferences = preferences;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_after_display_item, parent, false);
        ItemHolder itemHolder = new ItemHolder(v);

        return itemHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemHolder itemHolder =(ItemHolder) holder;
        itemHolder.bindView(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.spinner)
        Spinner spinner;

        @BindView(R.id.lvBeforeDisplay)
        ListView listView;

        @BindView(R.id.edFacing)
        EditText edFacing;

        @BindView(R.id.edEIE)
        EditText edEIE;

        @BindView(R.id.tvOutletModelName)
        TextView tvOutletModelName;

        @BindView(R.id.tvCountChecked)
        TextView tvCountChecked;

        @BindView(R.id.tvCountTotal)
        TextView tvCountTotal;

        public ItemHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindView(AfterDisplayDTO beforeDisplayDTO) {
            tvOutletModelName.setText(beforeDisplayDTO.getOutletModelName());
            bindModelView(beforeDisplayDTO);
            initSpinner(beforeDisplayDTO);
            initRecyclerView(beforeDisplayDTO);
        }

        private void initRecyclerView(AfterDisplayDTO dto) {
            List<MProductDTO> productDTOs = convertToMHS(dto);

            tvCountTotal.setText(String.valueOf(productDTOs.size()));
            AfterDisplayAdapter adapter = new AfterDisplayAdapter(activity, productDTOs, edFacing
                    , repo, outletId, dto.getOutletModelId(), preferences, tvCountChecked);
            listView.setAdapter(adapter);

            listView.setOnTouchListener(new ListView.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    int action = event.getAction();
                    switch (action) {
                        case MotionEvent.ACTION_DOWN:
                            // Disallow ScrollView to intercept touch events.
                            v.getParent().requestDisallowInterceptTouchEvent(true);
                            break;

                        case MotionEvent.ACTION_UP:
                            // Allow ScrollView to intercept touch events.
                            v.getParent().requestDisallowInterceptTouchEvent(false);
                            break;
                    }

                    // Handle ListView touch events.
                    v.onTouchEvent(event);
                    return true;
                }
            });

            listView.setEnabled(false);
            UiUtils.setListViewHeightBasedOnChildren(listView, UiUtils.getHeightParamInPixel(listView));
        }

        private List<MProductDTO> convertToMHS(AfterDisplayDTO dto) {
            List<MProductDTO> results = new ArrayList<>();

            if(dto.getMhs() != null) {
                String[] mhsCode = dto.getMhs().getRegisterValue().split(",");
                try {
                    results = repo.getProductDAO().findByCodes(mhsCode);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            return results;
        }

        private void initSpinner(final AfterDisplayDTO dto) {
            final List<String> spinnerName = new ArrayList<>();
            final List<Long> spinnerId = new ArrayList<>();
            Map<String, String> mapForSearch = new HashMap<>();
            int positionSelected = 0;
            try {
                spinnerName.add(itemView.getContext().getString(R.string.select_one));
                spinnerId.add(-1l);

                for (HotZoneDTO hotzoneDTO : hotzoneDTOs) {
                    spinnerName.add(hotzoneDTO.getNote());
                    spinnerId.add(hotzoneDTO.getHotZoneId());
                    mapForSearch.put(hotzoneDTO.getCode(), hotzoneDTO.getCode());
                }

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(activity, R.layout.simple_spinner_item, spinnerName);
                arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                spinner.setAdapter(arrayAdapter);

                positionSelected = findHotzoneSelected(arrayAdapter, mapForSearch, dto.getOutletModelId());
                if(positionSelected == 0) {
                    positionSelected = findHotzoneBeforeSelected(arrayAdapter, dto.getOutletModelId());
                }
                spinner.setSelection(positionSelected);

                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if(position == 0) {
                            try {
                                repo.getOutletMerDAO().updateActualValueBefore(
                                        outletId, dto.getOutletModelId(), null, ScreenContants.HOTZONE_AFTER, ScreenContants.HOTZONE);
                            } catch (SQLException e) {
                                ELog.d(e.getMessage(), e);
                            }
                        } else if(position != 0) {
                            try {
                                repo.getOutletMerDAO().updateActualValueBefore(
                                        outletId, dto.getOutletModelId(), spinnerName.get(position), ScreenContants.HOTZONE_AFTER, ScreenContants.HOTZONE);
                            } catch (SQLException e) {
                                ELog.d(e.getMessage(), e);
                            }
                        }
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            } catch (Exception e) {
                ELog.d(e.getMessage(), e);
            }
        }

        private int findHotzoneSelected(ArrayAdapter<String> arrayAdapter, Map<String, String> mapForSearch, Long outletModelId) {
            int i = 0;
            try {
                Map<String, Object> properties = new HashMap<>();
                properties.put("outletId", outletId);
                properties.put("outletModelId", outletModelId);
                properties.put(ScreenContants.DATA_TYPE, ScreenContants.HOTZONE_AFTER);
                OutletMerDTO hotzoneDTO = repo.getOutletMerDAO().findFirstResultByProperties(properties);
                if(hotzoneDTO.getActualValue() != null) {
                    i = arrayAdapter.getPosition(hotzoneDTO.getActualValue());
                }
            } catch (SQLException e) {
                ELog.d(e.getMessage(), e);
            }

            return i;
        }

        private int findHotzoneBeforeSelected(ArrayAdapter<String> arrayAdapter, Long outletModelId) {
            int i = 0;
            try {
                Map<String, Object> properties = new HashMap<>();
                properties.put("outletId", outletId);
                properties.put("outletModelId", outletModelId);
                properties.put(ScreenContants.DATA_TYPE, ScreenContants.HOTZONE_BEFORE);
                OutletMerDTO hotzoneDTO = repo.getOutletMerDAO().findFirstResultByProperties(properties);
                if(hotzoneDTO.getActualValue() != null) {
                    i = arrayAdapter.getPosition(hotzoneDTO.getActualValue());
                }
            } catch (SQLException e) {
                ELog.d(e.getMessage(), e);
            }

            return i;
        }

        private void bindModelView(AfterDisplayDTO displayDTO) {
            try {
                String facingValue = repo.getOutletMerDAO().findActualValueByDataType(ScreenContants.FACING_BEFORE, outletId, displayDTO.getOutletModelId());
                String eieValue = repo.getOutletMerDAO().findActualValueByDataType(ScreenContants.EIE_BEFORE, outletId, displayDTO.getOutletModelId());;

                if(facingValue != null) {
                    edFacing.setText(facingValue);
                }
                if(eieValue != null) {
                    edEIE.setText(eieValue);
                }

                edFacing.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        if(actionId == EditorInfo.IME_ACTION_DONE) {
                            OutletMerDTO outletMerDTO = new OutletMerDTO();
                            outletMerDTO.setDataType(ScreenContants.FACING_BEFORE);
                            outletMerDTO.setActualValue(v.getText().toString());
                            outletMerDTO.setOutletId(outletId);
                            insertOrUpdateData(outletMerDTO);
                            return true;
                        }
                        return false;
                    }
                });

                edFacing.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        EditText numberInput;

                        if(!hasFocus) {
                            numberInput = (EditText) v;
                            if(!numberInput.getText().toString().equals("")) {
                                OutletMerDTO outletMerDTO = new OutletMerDTO();
                                outletMerDTO.setDataType(ScreenContants.FACING_BEFORE);
                                outletMerDTO.setActualValue(numberInput.getText().toString());
                                outletMerDTO.setOutletId(outletId);
                                insertOrUpdateData(outletMerDTO);
                            }
                        }
                    }
                });

                edEIE.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        if(actionId == EditorInfo.IME_ACTION_DONE) {
                            OutletMerDTO outletMerDTO = new OutletMerDTO();
                            outletMerDTO.setDataType(ScreenContants.EIE_BEFORE);
                            outletMerDTO.setActualValue(v.getText().toString());
                            outletMerDTO.setOutletId(outletId);
                            insertOrUpdateData(outletMerDTO);
                            return true;
                        }
                        return false;
                    }
                });

                edEIE.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        EditText numberInput;

                        if(!hasFocus) {
                            numberInput = (EditText) v;
                            if(!numberInput.getText().toString().equals("")) {
                                OutletMerDTO outletMerDTO = new OutletMerDTO();
                                outletMerDTO.setDataType(ScreenContants.EIE_BEFORE);
                                outletMerDTO.setActualValue(numberInput.getText().toString());
                                outletMerDTO.setOutletId(outletId);
                                insertOrUpdateData(outletMerDTO);
                            }
                        }
                    }
                });

            } catch (Exception e) {
                ELog.d(e.getMessage(), e);
            }
        }

        private void insertOrUpdateData(OutletMerDTO outletMerDTO) {
            boolean isExist = checkDisplayExist(outletMerDTO);
            try {
                if(!isExist) {
                    repo.getOutletMerDAO().addOutletMerEntity(OutletMerUtil.convertToEntity(outletMerDTO));
                } else {
                    repo.getOutletMerDAO().updateFacingOrEIE(outletMerDTO.getDataType(),
                            OutletMerUtil.convertToEntity(outletMerDTO));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        private boolean checkDisplayExist(OutletMerDTO outletMerDTO) { //Include Facing and EIE
            boolean isExist = false;
            try {
                List<OutletMerDTO> outletMerDTOs = repo.getOutletMerDAO()
                        .findByDataTypeAndOutlet(outletMerDTO.getDataType() , outletMerDTO.getOutletId());

                if(outletMerDTOs.size() > 0) {
                    isExist = true;
                } else {
                    isExist = false;
                }
            } catch (SQLException e) {
                ELog.d(e.getMessage(), e);
            }

            return isExist;
        }
    }
}
