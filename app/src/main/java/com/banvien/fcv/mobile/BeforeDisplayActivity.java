package com.banvien.fcv.mobile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.banvien.fcv.mobile.adapter.BeforeDisplayAdapter;
import com.banvien.fcv.mobile.beanutil.OutletMerUtil;
import com.banvien.fcv.mobile.db.Repo;
import com.banvien.fcv.mobile.dto.HotzoneDTO;
import com.banvien.fcv.mobile.dto.OutletMerDTO;
import com.banvien.fcv.mobile.utils.DividerItemDecoration;
import com.banvien.fcv.mobile.utils.ELog;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;

/**
 * Created by Linh Nguyen on 5/27/2016.
 */
public class BeforeDisplayActivity extends BaseDrawerActivity {
    private static final String TAG = "BeforeDisplayActivity";
    private static Long outletId;

    @Bind(R.id.spinner)
    Spinner spinner;

    @Bind(R.id.swipe_refresh_before)
    SwipeRefreshLayout swipeRefreshLayout;

    @Bind(R.id.rcvBeforeDisplay)
    RecyclerView recyclerView;

    @Bind(R.id.edFacing)
    EditText edFacing;

    @Bind(R.id.edEIE)
    EditText edEIE;

    private Repo repo;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<OutletMerDTO> hotzoneList;
    private List<OutletMerDTO> productList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_before_display);
        repo = new Repo(this);
        outletId = this.getIntent().getLongExtra(ScreenContants.KEY_OUTLET_ID, 0l);
        hotzoneList = new ArrayList<>();
        productList = new ArrayList<>();

        bindViews();
        bindDatas();
        bindSwipeRefresh();
        initSpinner();
        initRecyclerView();
    }

    @Override
    protected void bindViews() {
        super.bindViews();
        try {
            String facingValue = this.repo.getOutletMerDAO().findActualValueByDataType(ScreenContants.FACING_BEFORE, outletId);
            String eieValue = this.repo.getOutletMerDAO().findActualValueByDataType(ScreenContants.EIE_BEFORE, outletId);;

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
                this.repo.getOutletMerDAO().addOutletMerEntity(OutletMerUtil.convertToEntity(outletMerDTO));
            } else {
                this.repo.getOutletMerDAO().updateFacingOrEIE(outletMerDTO.getDataType(),
                        OutletMerUtil.convertToEntity(outletMerDTO));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean checkDisplayExist(OutletMerDTO outletMerDTO) { //Include Facing and EIE
        boolean isExist = false;
        try {
            List<OutletMerDTO> outletMerDTOs = this.repo.getOutletMerDAO().findByDataTypeAndOutlet(outletMerDTO.getDataType() , outletMerDTO.getOutletId());
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

    private void bindSwipeRefresh() {
        swipeRefreshLayout.setColorSchemeColors(R.color.wallet_holo_blue_light);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //reloadProductData();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void initRecyclerView() {
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, null));
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new BeforeDisplayAdapter(this, productList, repo);
        recyclerView.setAdapter(adapter);

    }

    private void initSpinner() {
        final List<String> spinnerName = new ArrayList<>();
        final List<Long> spinnerId = new ArrayList<>();
        Map<String, String> mapForSearch = new HashMap<>();
        int positionSelected = 0;
        try {
            spinnerName.add(getString(R.string.select_one));
            spinnerId.add(-1l);

            for (OutletMerDTO outletMerDTO : hotzoneList) {
                HotzoneDTO hotzoneDTO = this.repo.getHotZoneDAO().findByCode(outletMerDTO.getRegisterValue());
                spinnerName.add(hotzoneDTO.getName());
                spinnerId.add(hotzoneDTO.getHotZoneId());
                mapForSearch.put(hotzoneDTO.getCode(), hotzoneDTO.getName());
            }

            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.simple_spinner_item, spinnerName);
            arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
            spinner.setAdapter(arrayAdapter);

            positionSelected = findHotzoneSelected(arrayAdapter, mapForSearch);
            spinner.setSelection(positionSelected);

            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if(position != 0) {
                        addBeforeHotzone(hotzoneList.get(position - 1), spinnerId.get(position));
                    }
                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private int findHotzoneSelected(ArrayAdapter arrayAdapter, Map<String, String> mapForSearch) {
        int result = 0;
        try {
            List<OutletMerDTO> outletMerDTOs = this.repo.getOutletMerDAO().
                    findByDataTypeAndOutlet(ScreenContants.HOTZONE_BEFORE, outletId);

            if(outletMerDTOs.size() > 0 && outletMerDTOs.get(0) != null) {
                result = arrayAdapter.getPosition(mapForSearch.get(outletMerDTOs.get(0).getRegisterValue()));
            }
        } catch (SQLException e) {
            ELog.d(e.getMessage(), e);
        }
        return result;
    }

    private void addBeforeHotzone(OutletMerDTO dto, Long hotzoneId) {
        OutletMerDTO insertItem = bindHotzoneData(dto, hotzoneId);
        Map<String, Object> properties = new HashMap<>();

        properties.put(ScreenContants.DATA_TYPE, insertItem.getDataType());
        properties.put("outletId", insertItem.getOutletId());
        properties.put(ScreenContants.REFERENCE_VALUE, insertItem.getReferenceValue());

        if(!checkHotzoneExist(properties)) {
            try {
                this.repo.getOutletMerDAO().addHotzoneDisplay(insertItem);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            ELog.d("After hotzone", "Nothing change");
        }


    }

    private OutletMerDTO bindHotzoneData(OutletMerDTO dto, Long hotzoneId) {
        OutletMerDTO insertItem = new OutletMerDTO();
        insertItem.setReferenceValue(String.valueOf(dto.get_id()));
        insertItem.setDataType(ScreenContants.HOTZONE_BEFORE);
        insertItem.setExhibitRegisteredDetailId(dto.getExhibitRegisteredDetailId());
        insertItem.setExhibitRegisteredId(dto.getExhibitRegisteredId());
        insertItem.setOutletId(dto.getOutletId());
        insertItem.setRegisterValue(dto.getRegisterValue());
        insertItem.setRouteScheduleDetailId(dto.getRouteScheduleDetailId());
        insertItem.setRouteScheduleId(dto.getRouteScheduleId());
        insertItem.setActualValue(String.valueOf(hotzoneId));

        return insertItem;
    }

    private boolean checkHotzoneExist(Map<String, Object> properties) {
        boolean isExist = false;
        try {
            isExist = this.repo.getOutletMerDAO().checkExistByProperties(properties);
            if(isExist) {
                ELog.d("ex", "Exist");
            } else {
                ELog.d("ex", "Not exist");
            }
        } catch (SQLException e) {
            ELog.d(e.getMessage(), e);
        }

        return isExist;
    }

    private void bindDatas() {
        // Get all mer result by outletId and type
        try {
            hotzoneList = this.repo.getOutletMerDAO().findByDataTypeAndOutlet(ScreenContants.HOTZONE, outletId);
            productList = this.repo.getOutletMerDAO().findByDataTypeAndOutlet(ScreenContants.MHS, outletId);
        } catch (SQLException e) {
            ELog.d("Can not get data from server", e);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(repo != null) {
            repo.release();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        edFacing.setFocusableInTouchMode(false);
        edFacing.clearFocus();
        edEIE.setFocusableInTouchMode(false);
        edEIE.clearFocus();
    }
}
