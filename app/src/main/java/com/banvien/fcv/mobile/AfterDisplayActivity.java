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
import com.banvien.fcv.mobile.dto.OutletMerDTO;
import com.banvien.fcv.mobile.utils.DividerItemDecoration;
import com.banvien.fcv.mobile.utils.ELog;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by Linh Nguyen on 5/25/2016.
 */
public class AfterDisplayActivity extends BaseDrawerActivity {
    private static final String TAG = "TAG";
    private static Long outletId;

    @Bind(R.id.spinner)
    Spinner spinner;

    @Bind(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    @Bind(R.id.rcvAfterDisplay)
    RecyclerView recyclerView;

    private Repo repo;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<OutletMerDTO> posmList;
    private List<OutletMerDTO> productList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_display);
        repo = new Repo(this);
        outletId = this.getIntent().getLongExtra(ScreenContants.KEY_OUTLET_ID, 0l);
        posmList = new ArrayList<>();
        productList = new ArrayList<>();

        bindDatas();
        initSpinner();
        initRecyclerView();
    }

    private void initRecyclerView() {
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, null));
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new AfterDisplayAdapter(this, productList);
        recyclerView.setAdapter(adapter);

    }

    private void initSpinner() {
        final List<String> spinnerName = new ArrayList<>();
        final List<Long> spinnerId = new ArrayList<>();

        for (OutletMerDTO outletMerDTO : posmList) {
            spinnerName.add(outletMerDTO.getRegisterValue());
            spinnerId.add(outletMerDTO.get_id());
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.simple_spinner_item, spinnerName);
        arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(view.getContext(), spinnerId.get(position).toString() + ", " + spinnerName.get(position), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void bindDatas() {
        // Get all mer result by outletId and type
        try {
            posmList = this.repo.getOutletMerDAO().findByDataTypeAndOutlet(ScreenContants.POSM_TYPE, outletId);
            productList = this.repo.getOutletMerDAO().findByDataTypeAndOutlet(ScreenContants.MHS, outletId);
        } catch (SQLException e) {
            ELog.d(e.getMessage(), e);
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
