package com.banvien.fcv.mobile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.banvien.fcv.mobile.adapter.TimelineAdapter;
import com.banvien.fcv.mobile.adapter.TimelineInOutletAdapter;
import com.banvien.fcv.mobile.beanutil.StatusInOutletUtil;
import com.banvien.fcv.mobile.db.Repo;
import com.banvien.fcv.mobile.db.entities.StatusInOutletEntity;
import com.banvien.fcv.mobile.dto.StatusInOutletDTO;
import com.banvien.fcv.mobile.dto.TimelineInOutletDTO;
import com.banvien.fcv.mobile.utils.ELog;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Linh Nguyen on 6/14/2016.
 */
public class InOutletHomeActivity extends BaseDrawerActivity {
    private static final String TAG = "InOutletHomeActivity";

    @BindView(R.id.rcvHomeAct)
    RecyclerView recyclerView;

    private Repo repo;
    private Long outletId;
    private Long routeScheduleDetailId;
    private Long loadAgain;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private StatusInOutletDTO statusInOutlet;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.startdays_activity);
        repo = new Repo(this);
        getSupportActionBar().setTitle(R.string.chamdientrungbaytaicuahang);
        outletId = this.getIntent().getLongExtra(ScreenContants.KEY_OUTLET_ID, 0l);
        routeScheduleDetailId = this.getIntent().getLongExtra(ScreenContants.KEY_ROUTESCHEDULE_DETAIL, 0l);
        try {
            StatusInOutletEntity statusInOutletEntity = repo.getStatusInOutletDAO().getConfigStatusInOutletHome(routeScheduleDetailId);
            if(statusInOutletEntity != null){
                statusInOutlet = StatusInOutletUtil.convertToDTO(statusInOutletEntity);
            } else{
                statusInOutlet = null;
            }
        } catch (SQLException e) {
            ELog.d("Error when get CONFIG");
        }
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new TimelineInOutletAdapter(buildTreeStep(), this, repo);
        recyclerView.setAdapter(adapter);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (repo != null) {
            repo.release();
        }
    }

    private List<TimelineInOutletDTO> buildTreeStep() {
        List<TimelineInOutletDTO> timelineDTOs = new ArrayList<>();
        if(statusInOutlet != null){
            TimelineInOutletDTO step0 = new TimelineInOutletDTO(getString(R.string.xemthongtindangkyvalichsueie)
                    , getString(R.string.motaxemthongtindangkyvalichsueie), getString(R.string.stepxemthongtindangkyvalichsueie)
                    , ScreenContants.HOME_STEP_INOUTLET_XEMTHONGTINDANGKYVALICHSUEIE, statusInOutlet.getXemThongTinDangKyLichSuEIE(), outletId, routeScheduleDetailId);

            TimelineInOutletDTO step1 = new TimelineInOutletDTO(getString(R.string.checkin)
                    , getString(R.string.motacheckin), getString(R.string.stepcheckin)
                    , ScreenContants.HOME_STEP_INOUTLET_CHECKIN, statusInOutlet.getCheckIn(), outletId, routeScheduleDetailId);

            TimelineInOutletDTO step2 = new TimelineInOutletDTO(getString(R.string.chupanhoverview)
                    , getString(R.string.motachupanhoverview), getString(R.string.stepchupanhoverview)
                    , ScreenContants.HOME_STEP_INOUTLET_CHUPANHOVERVIEW, statusInOutlet.getChupAnhOverview(), outletId, routeScheduleDetailId);

            TimelineInOutletDTO step3 = new TimelineInOutletDTO(getString(R.string.tinhtrangcuahang)
                    , getString(R.string.motatinhtrangcuahang), getString(R.string.steptinhtrangcuahang)
                    , ScreenContants.HOME_STEP_INOUTLET_TINHTRANGCUAHANG, statusInOutlet.getTinhTrangCuaHang(), outletId, routeScheduleDetailId);

            TimelineInOutletDTO step4 = new TimelineInOutletDTO(getString(R.string.khaosattrungbaytruoc)
                    , getString(R.string.motakhaosattrungbaytruoc), getString(R.string.stepmotakhaosattrungbaytruoc)
                    , ScreenContants.HOME_STEP_INOUTLET_KHAOSATTRUNGBAYTRUOC, statusInOutlet.getKhaoSatTrungBayTruoc(), outletId, routeScheduleDetailId);

            TimelineInOutletDTO step5 = new TimelineInOutletDTO(getString(R.string.khaosattrungbaysau)
                    , getString(R.string.motakhaosattrungbaysau), getString(R.string.stepkhaosattrungbaysau)
                    , ScreenContants.HOME_STEP_INOUTLET_KHAOSATTRUNGBAYSAU, statusInOutlet.getKhaoSatTrungBaySau(), outletId, routeScheduleDetailId);

            TimelineInOutletDTO step6 = new TimelineInOutletDTO(getString(R.string.huthangdathang)
                    , getString(R.string.motahuthangdathang), getString(R.string.stephuthangdathang)
                    , ScreenContants.HOME_STEP_INOUTLET_HUTHANGDATHANG, statusInOutlet.getHutHangDatHang(), outletId, routeScheduleDetailId);

            TimelineInOutletDTO step7 = new TimelineInOutletDTO(getString(R.string.khaosatdichvukhachhang)
                    , getString(R.string.motakhaosatdichvukhachhang), getString(R.string.stepkhaosatdichvukhachhang)
                    , ScreenContants.HOME_STEP_INOUTLET_KHAOSATDICHVUKHACHHANG, statusInOutlet.getKhaoSat(), outletId, routeScheduleDetailId);

            TimelineInOutletDTO step8 = new TimelineInOutletDTO(getString(R.string.dongbocuahang)
                    , getString(R.string.motadongbocuahang), getString(R.string.stepdongbocuahang)
                    , ScreenContants.HOME_STEP_INOUTLET_DONGBOCUAHANG, statusInOutlet.getDongBoCuaHang(), outletId, routeScheduleDetailId);

            timelineDTOs.add(step0);
            timelineDTOs.add(step1);
            timelineDTOs.add(step2);
            timelineDTOs.add(step3);
            timelineDTOs.add(step4);
            timelineDTOs.add(step5);
            timelineDTOs.add(step6);
            timelineDTOs.add(step7);
            timelineDTOs.add(step8);

        } else {
            TimelineInOutletDTO step0 = new TimelineInOutletDTO(getString(R.string.xemthongtindangkyvalichsueie)
                    , getString(R.string.motaxemthongtindangkyvalichsueie), getString(R.string.stepxemthongtindangkyvalichsueie)
                    , ScreenContants.HOME_STEP_INOUTLET_XEMTHONGTINDANGKYVALICHSUEIE, ScreenContants.STATUS_STEP_NOTYET, outletId, routeScheduleDetailId);

            TimelineInOutletDTO step1 = new TimelineInOutletDTO(getString(R.string.checkin)
                    , getString(R.string.motacheckin), getString(R.string.stepcheckin)
                    , ScreenContants.HOME_STEP_INOUTLET_CHECKIN, ScreenContants.STATUS_STEP_INPROGRESS, outletId, routeScheduleDetailId);

            TimelineInOutletDTO step2 = new TimelineInOutletDTO(getString(R.string.chupanhoverview)
                    , getString(R.string.motachupanhoverview), getString(R.string.stepchupanhoverview)
                    , ScreenContants.HOME_STEP_INOUTLET_CHUPANHOVERVIEW, ScreenContants.STATUS_STEP_NOTYET, outletId, routeScheduleDetailId);

            TimelineInOutletDTO step3 = new TimelineInOutletDTO(getString(R.string.tinhtrangcuahang)
                    , getString(R.string.motatinhtrangcuahang), getString(R.string.steptinhtrangcuahang)
                    , ScreenContants.HOME_STEP_INOUTLET_TINHTRANGCUAHANG, ScreenContants.STATUS_STEP_NOTYET, outletId, routeScheduleDetailId);

            TimelineInOutletDTO step4 = new TimelineInOutletDTO(getString(R.string.khaosattrungbaytruoc)
                    , getString(R.string.motakhaosattrungbaytruoc), getString(R.string.stepmotakhaosattrungbaytruoc)
                    , ScreenContants.HOME_STEP_INOUTLET_KHAOSATTRUNGBAYTRUOC, ScreenContants.STATUS_STEP_NOTYET, outletId, routeScheduleDetailId);

            TimelineInOutletDTO step5 = new TimelineInOutletDTO(getString(R.string.khaosattrungbaysau)
                    , getString(R.string.motakhaosattrungbaysau), getString(R.string.stepkhaosattrungbaysau)
                    , ScreenContants.HOME_STEP_INOUTLET_KHAOSATTRUNGBAYSAU, ScreenContants.STATUS_STEP_NOTYET, outletId, routeScheduleDetailId);

            TimelineInOutletDTO step6 = new TimelineInOutletDTO(getString(R.string.huthangdathang)
                    , getString(R.string.motahuthangdathang), getString(R.string.stephuthangdathang)
                    , ScreenContants.HOME_STEP_INOUTLET_HUTHANGDATHANG, ScreenContants.STATUS_STEP_NOTYET, outletId, routeScheduleDetailId);

            TimelineInOutletDTO step7 = new TimelineInOutletDTO(getString(R.string.khaosatdichvukhachhang)
                    , getString(R.string.motakhaosatdichvukhachhang), getString(R.string.stepkhaosatdichvukhachhang)
                    , ScreenContants.HOME_STEP_INOUTLET_KHAOSATDICHVUKHACHHANG, ScreenContants.STATUS_STEP_NOTYET, outletId, routeScheduleDetailId);

            TimelineInOutletDTO step8 = new TimelineInOutletDTO(getString(R.string.dongbocuahang)
                    , getString(R.string.motadongbocuahang), getString(R.string.stepdongbocuahang)
                    , ScreenContants.HOME_STEP_INOUTLET_DONGBOCUAHANG, ScreenContants.STATUS_STEP_NOTYET, outletId, routeScheduleDetailId);

            timelineDTOs.add(step0);
            timelineDTOs.add(step1);
            timelineDTOs.add(step2);
            timelineDTOs.add(step3);
            timelineDTOs.add(step4);
            timelineDTOs.add(step5);
            timelineDTOs.add(step6);
            timelineDTOs.add(step7);
            timelineDTOs.add(step8);
        }

        if(timelineDTOs.size() > 0) {
            timelineDTOs.get(0).setHeader(true);
            timelineDTOs.get(timelineDTOs.size() - 1).setFooter(true);
        }

        return timelineDTOs;
    }

}
