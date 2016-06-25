package com.banvien.fcv.mobile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Spinner;

import com.banvien.fcv.mobile.adapter.RegisterInfoAdapter;
import com.banvien.fcv.mobile.dto.RegisterHistoryDTO;
import com.banvien.fcv.mobile.utils.CustomLinearLayoutManager;
import com.banvien.fcv.mobile.utils.ELog;
import com.banvien.fcv.mobile.utils.MyLinearLayoutManager;
import com.banvien.fcv.mobile.utils.MySpeedScrollManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by Linh Nguyen on 6/25/2016.
 */
public class RegisterHistoryActivity extends BaseDrawerActivity {
    private static final String TAG = "RegisterHistoryActivity";
    private static long outletId;
    private List<RegisterHistoryDTO> data;

    @Bind(R.id.spModel)
    Spinner spOutletModel;

    @Bind(R.id.rcvModel)
    RecyclerView rcvModel;

    @Bind(R.id.rcvHistory)
    RecyclerView rcvHistory;

    RecyclerView.Adapter adapterModel;
    RecyclerView.LayoutManager layoutManagerModel;

    RecyclerView.Adapter adapterHistory;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_history);
        outletId = getIntent().getLongExtra(ScreenContants.KEY_OUTLET_ID, 0l);
        data = new ArrayList<>();

        bindViews();
        initRecyclerViewModel();
    }

    private void initRecyclerViewModel() {
        RegisterHistoryDTO registerHistoryDTO = new RegisterHistoryDTO();
        registerHistoryDTO.setName("Vị trí 1");

        //  Todo Hard cord
        data.add(registerHistoryDTO);
        data.add(registerHistoryDTO);
        data.add(registerHistoryDTO);
        data.add(registerHistoryDTO);
        data.add(registerHistoryDTO);
        data.add(registerHistoryDTO);
        data.add(registerHistoryDTO);
        data.add(registerHistoryDTO);

        rcvModel.setHasFixedSize(true);
        layoutManagerModel = new MySpeedScrollManager(
                this);
        rcvModel.setLayoutManager(layoutManagerModel);
        adapterModel = new RegisterInfoAdapter(this, data);
        rcvModel.setAdapter(adapterModel);
    }
}
