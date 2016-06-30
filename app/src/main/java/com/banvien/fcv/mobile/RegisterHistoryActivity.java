package com.banvien.fcv.mobile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.banvien.fcv.mobile.adapter.HistoryInfoAdapter;
import com.banvien.fcv.mobile.adapter.RegisterInfoAdapter;
import com.banvien.fcv.mobile.db.Repo;
import com.banvien.fcv.mobile.dto.BeforeDisplayDTO;
import com.banvien.fcv.mobile.dto.HistoryDTO;
import com.banvien.fcv.mobile.dto.RegisterInfoDTO;
import com.banvien.fcv.mobile.dto.getfromserver.OutletModelDTO;
import com.banvien.fcv.mobile.utils.ELog;
import com.banvien.fcv.mobile.utils.MySpeedScrollManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;

/**
 * Created by Linh Nguyen on 6/25/2016.
 */
public class RegisterHistoryActivity extends BaseDrawerActivity {
    private static final String TAG = "RegisterHistoryActivity";
    private static long outletId;
    private List<RegisterInfoDTO> data;
    private List<HistoryDTO> historyData;

    @Bind(R.id.spModel)
    Spinner spOutletModel;

    @Bind(R.id.rcvModel)
    RecyclerView rcvModel;

    @Bind(R.id.rcvHistory)
    RecyclerView rcvHistory;

    RecyclerView.Adapter adapterModel;
    RecyclerView.LayoutManager layoutManagerModel;

    RecyclerView.Adapter adapterHistory;
    private Repo repo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_history);
        outletId = getIntent().getLongExtra(ScreenContants.KEY_OUTLET_ID, 0l);
        data = new ArrayList<>();
        historyData = new ArrayList<>();
        repo = new Repo(this);

        bindViews();
        initSpinner();
        initRecyclerViewModel();
        initRecyclerViewHistory();
    }

    private void initRecyclerViewHistory() {
        HistoryDTO historyDTO = new HistoryDTO(1, 90, true);

        HistoryDTO historyDTO2 = new HistoryDTO(2, 90, true);

        HistoryDTO historyDTO3 = new HistoryDTO(3, 90, true);

        HistoryDTO historyDTO4 = new HistoryDTO(4, 90, true);

        HistoryDTO historyDTO5 = new HistoryDTO(5, 90, true);

        //  Todo Hard cord
        historyData.add(historyDTO);
        historyData.add(historyDTO2);
        historyData.add(historyDTO3);
        historyData.add(historyDTO4);
        historyData.add(historyDTO5);

        rcvHistory.setHasFixedSize(true);
        layoutManagerModel = new MySpeedScrollManager(
                this);
        rcvHistory.setLayoutManager(layoutManagerModel);
        adapterHistory = new HistoryInfoAdapter(this, historyData);
        rcvHistory.setAdapter(adapterHistory);
    }

    private void initSpinner() {
        final List<String> spinnerName = new ArrayList<>();
        final List<Long> spinnerId = new ArrayList<>();
        Map<String, String> mapForSearch = new HashMap<>();
        int positionSelected = 0;
        try {
            List<BeforeDisplayDTO> beforeDisplayDTOs = repo.getOutletMerDAO().findOutletModelBeforeByOutletId(outletId);
            spinnerName.add(getString(R.string.select_one));
            spinnerId.add(-1l);

            for (BeforeDisplayDTO beforeDisplayDTO : beforeDisplayDTOs) {
                spinnerName.add(beforeDisplayDTO.getOutletModelName());
                spinnerId.add(beforeDisplayDTO.getOutletModelId());
                mapForSearch.put(String.valueOf(beforeDisplayDTO.getOutletModelId()), beforeDisplayDTO.getOutletModelName());
            }

            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.simple_spinner_item, spinnerName);
            arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
            spOutletModel.setAdapter(arrayAdapter);

//            positionSelected = findHotzoneSelected(arrayAdapter, mapForSearch, dto.getOutletModelId());
//            spOutletModel.setSelection(positionSelected);

            spOutletModel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                    ((TextView) parent.getChildAt(0)).setGravity(Gravity.CENTER);
//                    if(position == 0) {
//                        try {
//                            repo.getOutletMerDAO().updateActualValueBefore(
//                                    outletId, dto.getOutletModelId(), null, ScreenContants.HOTZONE_BEFORE, ScreenContants.HOTZONE);
//                        } catch (SQLException e) {
//                            ELog.d(e.getMessage(), e);
//                        }
//                    } else if(position != 0) {
//                        try {
//                            repo.getOutletMerDAO().updateActualValueBefore(
//                                    outletId, dto.getOutletModelId(), spinnerName.get(position), ScreenContants.HOTZONE_BEFORE, ScreenContants.HOTZONE);
//                        } catch (SQLException e) {
//                            ELog.d(e.getMessage(), e);
//                        }
//                    }
                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        } catch (Exception e) {
            ELog.d(e.getMessage(), e);
        }
    }

    private void initRecyclerViewModel() {
        RegisterInfoDTO registerInfoDTO = new RegisterInfoDTO();
        registerInfoDTO.setName("Vị trí 1");

        RegisterInfoDTO registerInfoDTO2 = new RegisterInfoDTO();
        registerInfoDTO2.setName("Vị trí 2");

        RegisterInfoDTO registerInfoDTO3 = new RegisterInfoDTO();
        registerInfoDTO3.setName("Vị trí 3");

        RegisterInfoDTO registerInfoDTO4 = new RegisterInfoDTO();
        registerInfoDTO4.setName("Vị trí 4");

        RegisterInfoDTO registerInfoDTO5 = new RegisterInfoDTO();
        registerInfoDTO5.setName("Vị trí 5");

        //  Todo Hard cord
        data.add(registerInfoDTO);
        data.add(registerInfoDTO2);
        data.add(registerInfoDTO3);
        data.add(registerInfoDTO4);
        data.add(registerInfoDTO5);

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
