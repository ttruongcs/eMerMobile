package com.banvien.fcv.mobile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.widget.Spinner;

import com.banvien.fcv.mobile.adapter.RegisterInfoAdapter;
import com.banvien.fcv.mobile.dto.RegisterInfoDTO;
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
    private List<RegisterInfoDTO> data;

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
        RegisterInfoDTO registerInfoDTO = new RegisterInfoDTO();
        registerInfoDTO.setName("Vị trí 1");

        //  Todo Hard cord
        data.add(registerInfoDTO);
        data.add(registerInfoDTO);
        data.add(registerInfoDTO);
        data.add(registerInfoDTO);
        data.add(registerInfoDTO);
        data.add(registerInfoDTO);
        data.add(registerInfoDTO);
        data.add(registerInfoDTO);

//        RegisterInfoDTO historyDTO = new RegisterInfoDTO();
//        historyDTO.setName("ahihi");
//        historyDTO.setType(1);
//
//        data.add(historyDTO);
//        data.add(historyDTO);
//        data.add(historyDTO);

        rcvModel.setHasFixedSize(true);
        layoutManagerModel = new MySpeedScrollManager(
                this);
        rcvModel.setLayoutManager(layoutManagerModel);
        adapterModel = new RegisterInfoAdapter(this, data);
        rcvModel.setAdapter(adapterModel);
    }
}
