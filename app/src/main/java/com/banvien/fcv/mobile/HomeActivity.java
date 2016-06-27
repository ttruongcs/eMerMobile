package com.banvien.fcv.mobile;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.banvien.fcv.mobile.adapter.TimelineAdapter;
import com.banvien.fcv.mobile.beanutil.StatusHomeUtil;
import com.banvien.fcv.mobile.db.Repo;
import com.banvien.fcv.mobile.db.dao.StatusHomeDAO;
import com.banvien.fcv.mobile.db.entities.StatusHomeEntity;
import com.banvien.fcv.mobile.dto.StatusHomeDTO;
import com.banvien.fcv.mobile.dto.TimelineDTO;
import com.banvien.fcv.mobile.utils.ELog;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by Linh Nguyen on 6/14/2016.
 */
public class HomeActivity extends BaseDrawerActivity {
    private static final String TAG = "HomeActivity";

    @Bind(R.id.rcvHomeAct)
    RecyclerView recyclerView;

    private Repo repo;
    private StatusHomeDTO statusHome;

    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        repo = new Repo(this);
//        repo.getDatabaseHelper().get();
        repo.getDatabaseHelper().getWritableDatabase(); //Create database and table
        try {
            StatusHomeEntity statusHomeEntity = repo.getStatusHomeDAO().getConfigStatusHome();
            if(statusHomeEntity != null){
                statusHome = StatusHomeUtil.convertToDTO(statusHomeEntity);
            } else{
                statusHome = null;
            }
        } catch (SQLException e) {
            ELog.d("Error when get CONFIG!");
        }
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new TimelineAdapter(buildTreeStep(), this);
        recyclerView.setAdapter(adapter);
    }

    private List<TimelineDTO> buildTreeStep() {
        List<TimelineDTO> timelineDTOs = new ArrayList<>();
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

        return timelineDTOs;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
