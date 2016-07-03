package com.banvien.fcv.mobile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.banvien.fcv.mobile.adapter.TimelineAdapter;
import com.banvien.fcv.mobile.beanutil.StatusHomeUtil;
import com.banvien.fcv.mobile.db.Repo;
import com.banvien.fcv.mobile.db.entities.StatusHomeEntity;
import com.banvien.fcv.mobile.dto.StatusHomeDTO;
import com.banvien.fcv.mobile.dto.TimelineDTO;
import com.banvien.fcv.mobile.utils.ELog;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Linh Nguyen on 6/14/2016.
 */
public class HomeActivity extends BaseDrawerActivity {
    private static final String TAG = "HomeActivity";

    @BindView(R.id.rcvHomeAct)
    RecyclerView recyclerView;

    private Repo repo;
    private StatusHomeDTO statusHome;

    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<TimelineDTO> timelineDTOs;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        repo = new Repo(this);
        repo.getDatabaseHelper().getWritableDatabase(); //Create database and table
        timelineDTOs = new ArrayList<>();
        getSupportActionBar().setTitle(R.string.home);

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new TimelineAdapter(timelineDTOs, this, repo);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (repo != null) {
            repo.release();
        }
    }

    private void reloadData() {
        try {
            timelineDTOs.clear();
            StatusHomeEntity statusHomeEntity = repo.getStatusHomeDAO().getConfigStatusHome();
            if(statusHomeEntity != null){
                statusHome = StatusHomeUtil.convertToDTO(statusHomeEntity);
            } else{
                statusHome = null;
            }
            buildTreeStep();
        } catch (SQLException e) {
            ELog.d("Error when get CONFIG!!");
        }
    }

    private void buildTreeStep() {
        if(statusHome != null){
            TimelineDTO startDay = new TimelineDTO(getString(R.string.chuanbidaungay)
                    , getString(R.string.motachuanbidaungay), getString(R.string.stepchuabidaungay)
                    , ScreenContants.HOME_STEP_STARTDAY, statusHome.getChuanBiDauNgay());
            TimelineDTO inOutlet = new TimelineDTO(getString(R.string.chamdientrungbaytaicuahang)
                    , getString(R.string.motachamdiemtrungbaytaicuahang), getString(R.string.stepchamdiemtrungbay)
                    , ScreenContants.HOME_STEP_INOUTLET, statusHome.getTrongCuaHang());
            TimelineDTO endDay = new TimelineDTO(getString(R.string.ketthuccuoingay)
                    , getString(R.string.motaketthuccuoingay), getString(R.string.stepketthuccuoingay)
                    , ScreenContants.HOME_STEP_ENDDAY, statusHome.getKetThucCuoiNgay());
            timelineDTOs.add(startDay);
            timelineDTOs.add(inOutlet);
            timelineDTOs.add(endDay);
        } else {
            TimelineDTO startDay = new TimelineDTO(getString(R.string.chuanbidaungay)
                    , getString(R.string.motachuanbidaungay), getString(R.string.stepchuabidaungay)
                    , ScreenContants.HOME_STEP_STARTDAY, ScreenContants.STATUS_STEP_INPROGRESS);
            TimelineDTO inOutlet = new TimelineDTO(getString(R.string.chamdientrungbaytaicuahang)
                    , getString(R.string.motachamdiemtrungbaytaicuahang), getString(R.string.stepchamdiemtrungbay)
                    , ScreenContants.HOME_STEP_INOUTLET, ScreenContants.STATUS_STEP_NOTYET);
            TimelineDTO endDay = new TimelineDTO(getString(R.string.ketthuccuoingay)
                    , getString(R.string.motaketthuccuoingay), getString(R.string.stepketthuccuoingay)
                    , ScreenContants.HOME_STEP_ENDDAY, ScreenContants.STATUS_STEP_NOTYET);
            timelineDTOs.add(startDay);
            timelineDTOs.add(inOutlet);
            timelineDTOs.add(endDay);
        }

        if(timelineDTOs.size() > 0) {
            timelineDTOs.get(0).setHeader(true);
            timelineDTOs.get(timelineDTOs.size() - 1).setFooter(true);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        reloadData();
        adapter.notifyItemRangeChanged(0, adapter.getItemCount());
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
    }
}
