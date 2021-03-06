package com.banvien.fcv.mobile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.banvien.fcv.mobile.adapter.TimelineAdapter;
import com.banvien.fcv.mobile.beanutil.StatusEndDayUtil;
import com.banvien.fcv.mobile.beanutil.StatusInOutletUtil;
import com.banvien.fcv.mobile.db.Repo;
import com.banvien.fcv.mobile.db.entities.StatusEndDayEntity;
import com.banvien.fcv.mobile.db.entities.StatusInOutletEntity;
import com.banvien.fcv.mobile.dto.StatusEndDayDTO;
import com.banvien.fcv.mobile.dto.StatusInOutletDTO;
import com.banvien.fcv.mobile.dto.TimelineDTO;
import com.banvien.fcv.mobile.utils.ELog;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Linh Nguyen on 6/14/2016.
 */
public class EndDayActivity extends BaseDrawerActivity {
    private static final String TAG = "EndDayActivity";

    @BindView(R.id.rcvHomeAct)
    RecyclerView recyclerView;

    private Repo repo;

    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private StatusEndDayDTO statusEndDay;
    private List<TimelineDTO> timelineDTOs;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enddays_activity);
        repo = new Repo(this);
        timelineDTOs = new ArrayList<>();

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new TimelineAdapter(timelineDTOs, this, repo);
        recyclerView.setAdapter(adapter);
    }

    private void reloadData() {
        timelineDTOs.clear();
        try {
            StatusEndDayEntity statusEndDayEntity = repo.getStatusEndDayDAO().getConfigStatusEndDayHome();
            if(statusEndDayEntity != null){
                statusEndDay = StatusEndDayUtil.convertToDTO(statusEndDayEntity);
            } else{
                statusEndDay = null;
            }
            buildTreeStep();
        } catch (SQLException e) {
            ELog.d("Error when get CONFIG");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        reloadData();
        adapter.notifyItemRangeChanged(0, adapter.getItemCount());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (repo != null) {
            repo.release();
        }
    }
    
    private void buildTreeStep() {
        if(statusEndDay != null){
            TimelineDTO step1 = new TimelineDTO(getString(R.string.chupanhcuoingay)
                    , getString(R.string.motachupanhcuoingay), getString(R.string.stepchupanhcuoingay)
                    , ScreenContants.HOME_STEP_ENDDAY_CHUPHINHCUOINGAY, statusEndDay.getChupAnhCuoiNgay());

//            TimelineDTO step2 = new TimelineDTO(getString(R.string.dongboketqua)
//                    , getString(R.string.motadongboketqua), getString(R.string.stepdongboketqua)
//                    , ScreenContants.HOME_STEP_ENDDAY_DONGBOKETQUA, statusEndDay.getDongBoCuoiNgay());

            TimelineDTO step3 = new TimelineDTO(getString(R.string.ketthuccuoingay)
                    , getString(R.string.dongbocuoingay), getString(R.string.stepdongboketqua)
                    , ScreenContants.HOME_STEP_ENĐAY_KETTHUCCUOINGAY, statusEndDay.getKetThucCuoiNgay());

            timelineDTOs.add(step1);
//            timelineDTOs.add(step2);
            timelineDTOs.add(step3);
        } else {
            TimelineDTO step1 = new TimelineDTO(getString(R.string.chupanhcuoingay)
                    , getString(R.string.motachupanhcuoingay), getString(R.string.stepchupanhcuoingay)
                    , ScreenContants.HOME_STEP_ENDDAY_CHUPHINHCUOINGAY, ScreenContants.STATUS_STEP_INPROGRESS);

//            TimelineDTO step2 = new TimelineDTO(getString(R.string.dongboketqua)
//                    , getString(R.string.motadongboketqua), getString(R.string.stepdongboketqua)
//                    , ScreenContants.HOME_STEP_ENDDAY_DONGBOKETQUA, ScreenContants.STATUS_STEP_NOTYET);

            TimelineDTO step3 = new TimelineDTO(getString(R.string.ketthuccuoingay)
                    , getString(R.string.dongbocuoingay), getString(R.string.stepdongboketqua)
                    , ScreenContants.HOME_STEP_ENĐAY_KETTHUCCUOINGAY, ScreenContants.STATUS_STEP_NOTYET);

            timelineDTOs.add(step1);
//            timelineDTOs.add(step2);
            timelineDTOs.add(step3);
        }

        if(timelineDTOs.size() > 0) {
            timelineDTOs.get(0).setHeader(true);
            timelineDTOs.get(timelineDTOs.size() - 1).setFooter(true);
        }

    }
}
