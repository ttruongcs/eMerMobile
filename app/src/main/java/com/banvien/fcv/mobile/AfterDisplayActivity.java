package com.banvien.fcv.mobile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.banvien.fcv.mobile.adapter.AfterDisplayAdapter;
import com.banvien.fcv.mobile.db.Repo;
import com.banvien.fcv.mobile.db.entities.OutletMerEntity;
import com.banvien.fcv.mobile.dto.HotzoneDTO;
import com.banvien.fcv.mobile.dto.OutletMerDTO;
import com.banvien.fcv.mobile.utils.DividerItemDecoration;
import com.banvien.fcv.mobile.utils.ELog;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import butterknife.Bind;

/**
 * Created by Linh Nguyen on 5/25/2016.
 */
public class AfterDisplayActivity extends BaseDrawerActivity {
    private static final String TAG = "AfterDisplayActivity";
    private static Long outletId;

    @Bind(R.id.spinner)
    Spinner spinner;

    @Bind(R.id.swipe_refresh_after)
    SwipeRefreshLayout swipeRefreshLayout;

    @Bind(R.id.rcvAfterDisplay)
    RecyclerView recyclerView;

    private Repo repo;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<OutletMerDTO> hotzoneList;
    private List<OutletMerDTO> productList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_display);
        repo = new Repo(this);
        outletId = this.getIntent().getLongExtra(ScreenContants.KEY_OUTLET_ID, 0l);
        hotzoneList = new ArrayList<>();
        productList = new ArrayList<>();

        bindDatas();
        bindSwipeRefresh();
        initSpinner();
        initRecyclerView();
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
        adapter = new AfterDisplayAdapter(this, productList, repo);
        recyclerView.setAdapter(adapter);

    }

    private void initSpinner() {
        final List<String> spinnerName = new ArrayList<>();
        final List<Long> spinnerId = new ArrayList<>();
        int positionSelected = 0;
        try {
            spinnerName.add(getString(R.string.select_one));
            spinnerId.add(-1l);

            for (OutletMerDTO outletMerDTO : hotzoneList) {
                HotzoneDTO hotzoneDTO = this.repo.getHotZoneDAO().findByCode(outletMerDTO.getRegisterValue());
                spinnerName.add(hotzoneDTO.getName());
                spinnerId.add(hotzoneDTO.getHotZoneId());
            }

            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.simple_spinner_item, spinnerName);
            arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
            spinner.setAdapter(arrayAdapter);

            positionSelected = findHotzoneSelected(arrayAdapter);
            spinner.setSelection(positionSelected);

            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if(position != 0) {
                        addAfterHotzone(hotzoneList.get(position - 1), spinnerId.get(position));
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

    private int findHotzoneSelected(ArrayAdapter arrayAdapter) {
        int result = 0;
        try {
            OutletMerDTO outletMerDTO = this.repo.getOutletMerDAO().
                    findByDataTypeAndOutlet(ScreenContants.HOTZONE_AFTER, outletId).get(0);
            if(outletMerDTO != null) {
                HotzoneDTO hotzoneDTO = this.repo.getHotZoneDAO().findByCode(outletMerDTO.getRegisterValue());
                result = arrayAdapter.getPosition(hotzoneDTO.getName());
            }
        } catch (SQLException e) {
            ELog.d(e.getMessage(), e);
        }
        return result;
    }

    private void addAfterHotzone(OutletMerDTO dto, Long hotzoneId) {
        OutletMerDTO insertItem = bindHotzoneData(dto, hotzoneId);

        try {

            this.repo.getOutletMerDAO().addAfterHotzone(insertItem);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private OutletMerDTO bindHotzoneData(OutletMerDTO dto, Long hotzoneId) {
        OutletMerDTO insertItem = new OutletMerDTO();
        insertItem.setReferenceValue(String.valueOf(dto.get_id()));
        insertItem.setDataType(ScreenContants.HOTZONE_AFTER);
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
}
