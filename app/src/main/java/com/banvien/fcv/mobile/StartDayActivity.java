package com.banvien.fcv.mobile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.banvien.fcv.mobile.adapter.TimelineAdapter;
import com.banvien.fcv.mobile.beanutil.StatusHomeUtil;
import com.banvien.fcv.mobile.beanutil.StatusStartDayUtil;
import com.banvien.fcv.mobile.db.Repo;
import com.banvien.fcv.mobile.db.entities.StatusHomeEntity;
import com.banvien.fcv.mobile.db.entities.StatusStartDayEntity;
import com.banvien.fcv.mobile.dto.StatusHomeDTO;
import com.banvien.fcv.mobile.dto.StatusStartDayDTO;
import com.banvien.fcv.mobile.dto.TimelineDTO;
import com.banvien.fcv.mobile.utils.ELog;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by Linh Nguyen on 6/14/2016.
 */
public class StartDayActivity extends BaseDrawerActivity {
    private static final String TAG = "StartDayActivity";

    @Bind(R.id.rcvHomeAct)
    RecyclerView recyclerView;

    private Repo repo;

    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<TimelineDTO> timelineDTOs;
    private StatusStartDayDTO statusStartDay;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.startdays_activity);
        repo = new Repo(this);
        try {
            StatusStartDayEntity statusStartDayEntity = repo.getStartDayDAO().getConfigStartDayHome();
            if(statusStartDayEntity != null){
                statusStartDay = StatusStartDayUtil.convertToDTO(statusStartDayEntity);
            } else{
                statusStartDay = null;
            }
        } catch (SQLException e) {
            ELog.d("Error when get CONFIG");
        }
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new TimelineAdapter(buildTreeStep(), this);
        recyclerView.setAdapter(adapter);
    }

    private List<TimelineDTO> buildTreeStep() {
        List<TimelineDTO> timelineDTOs = new ArrayList<>();
        if(statusStartDay != null){
            TimelineDTO step1 = new TimelineDTO(getString(R.string.dongbodulieuphancong)
                    , getString(R.string.motadongbodulieuphancong), getString(R.string.stepdongbodulieuphancong)
                    , ScreenContants.HOME_STEP_STARTDAY_DONGBODULIEUPHANCONG, statusStartDay.getDongBoDuLieuPhanCong());

            TimelineDTO step2 = new TimelineDTO(getString(R.string.themcuahangneumuon)
                    , getString(R.string.motathemcuahangneumuon), getString(R.string.stepthemcuahangneumuon)
                    , ScreenContants.HOME_STEP_STARTDAY_THEMCUAHANGNEUMUON, statusStartDay.getThemCuaHangNeuMuon());

            TimelineDTO step3 = new TimelineDTO(getString(R.string.chuphinhdongphuc)
                    , getString(R.string.motachuphinhdongphuc), getString(R.string.stepchuphinhdongphuc)
                    , ScreenContants.HOME_STEP_STARTDAY_CHUPHINHDONGPHUC, statusStartDay.getChupHinhDongPhuc());

            TimelineDTO step4 = new TimelineDTO(getString(R.string.chuphinhcongcudungcu)
                    , getString(R.string.motachuphinhcongcudungcu), getString(R.string.stepchuphinhcongcudungcu)
                    , ScreenContants.HOME_STEP_STARTDAY_CHUPHINHCONGCUDUNGCU, statusStartDay.getChupHinhCongCuDungCu());

            TimelineDTO step5 = new TimelineDTO(getString(R.string.chuphinhcuahangdautien)
                    , getString(R.string.motachuphinhcuahangdautien), getString(R.string.stepchuphinhcuahangdautien)
                    , ScreenContants.HOME_STEP_STARTDAY_CHUPHINHCUAHANGDAUTIEN, statusStartDay.getChupHinhCuaHangDauTien());

            TimelineDTO step6 = new TimelineDTO(getString(R.string.xacnhanlamviec)
                    , getString(R.string.motaxacnhanlamviec), getString(R.string.stepxacnhanlamviec)
                    , ScreenContants.HOME_STEP_STARTDAY_XACNHANLAMVIEC, statusStartDay.getXacNhanLamViec());

            timelineDTOs.add(step1);
            timelineDTOs.add(step2);
            timelineDTOs.add(step3);
            timelineDTOs.add(step4);
            timelineDTOs.add(step5);
            timelineDTOs.add(step6);
        } else {
            TimelineDTO step1 = new TimelineDTO(getString(R.string.dongbodulieuphancong)
                    , getString(R.string.motadongbodulieuphancong), getString(R.string.stepdongbodulieuphancong)
                    , ScreenContants.HOME_STEP_STARTDAY_DONGBODULIEUPHANCONG, ScreenContants.STATUS_STEP_INPROGRESS);

            TimelineDTO step2 = new TimelineDTO(getString(R.string.themcuahangneumuon)
                    , getString(R.string.motathemcuahangneumuon), getString(R.string.stepthemcuahangneumuon)
                    , ScreenContants.HOME_STEP_STARTDAY_THEMCUAHANGNEUMUON, ScreenContants.STATUS_STEP_NOTYET);

            TimelineDTO step3 = new TimelineDTO(getString(R.string.chuphinhdongphuc)
                    , getString(R.string.motachuphinhdongphuc), getString(R.string.stepchuphinhdongphuc)
                    , ScreenContants.HOME_STEP_STARTDAY_CHUPHINHDONGPHUC, ScreenContants.STATUS_STEP_NOTYET);

            TimelineDTO step4 = new TimelineDTO(getString(R.string.chuphinhcongcudungcu)
                    , getString(R.string.motachuphinhcongcudungcu), getString(R.string.stepchuphinhcongcudungcu)
                    , ScreenContants.HOME_STEP_STARTDAY_CHUPHINHCONGCUDUNGCU, ScreenContants.STATUS_STEP_NOTYET);

            TimelineDTO step5 = new TimelineDTO(getString(R.string.chuphinhcuahangdautien)
                    , getString(R.string.motachuphinhcuahangdautien), getString(R.string.stepchuphinhcuahangdautien)
                    , ScreenContants.HOME_STEP_STARTDAY_CHUPHINHCUAHANGDAUTIEN, ScreenContants.STATUS_STEP_NOTYET);

            TimelineDTO step6 = new TimelineDTO(getString(R.string.xacnhanlamviec)
                    , getString(R.string.motaxacnhanlamviec), getString(R.string.stepxacnhanlamviec)
                    , ScreenContants.HOME_STEP_STARTDAY_XACNHANLAMVIEC, ScreenContants.STATUS_STEP_NOTYET);

            timelineDTOs.add(step1);
            timelineDTOs.add(step2);
            timelineDTOs.add(step3);
            timelineDTOs.add(step4);
            timelineDTOs.add(step5);
            timelineDTOs.add(step6);
        }

        return timelineDTOs;
    }
}
