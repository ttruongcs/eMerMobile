package com.banvien.fcv.mobile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.banvien.fcv.mobile.adapter.TimelineAdapter;
import com.banvien.fcv.mobile.beanutil.StatusInOutletUtil;
import com.banvien.fcv.mobile.db.Repo;
import com.banvien.fcv.mobile.db.entities.StatusInOutletEntity;
import com.banvien.fcv.mobile.dto.StatusInOutletDTO;
import com.banvien.fcv.mobile.dto.TimelineDTO;
import com.banvien.fcv.mobile.utils.ELog;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by Linh Nguyen on 6/14/2016.
 */
public class InOutletHomeActivity extends BaseDrawerActivity {
    private static final String TAG = "InOutletHomeActivity";

    @Bind(R.id.rcvHomeAct)
    RecyclerView recyclerView;

    private Repo repo;

    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private StatusInOutletDTO statusInOutlet;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.startdays_activity);
        repo = new Repo(this);
        try {
            StatusInOutletEntity statusInOutletEntity = repo.getStatusInOutletDAO().getConfigStatusInOutletHome();
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
        adapter = new TimelineAdapter(buildTreeStep(), this);
        recyclerView.setAdapter(adapter);
    }

    private List<TimelineDTO> buildTreeStep() {
        List<TimelineDTO> timelineDTOs = new ArrayList<>();
        if(statusInOutlet != null){
            TimelineDTO step1 = new TimelineDTO(getString(R.string.checkin)
                    , getString(R.string.motacheckin), getString(R.string.stepcheckin)
                    , ScreenContants.HOME_STEP_INOUTLET_CHECKIN, statusInOutlet.getCheckIn());

            TimelineDTO step2 = new TimelineDTO(getString(R.string.chupanhoverview)
                    , getString(R.string.motachupanhoverview), getString(R.string.stepchupanhoverview)
                    , ScreenContants.HOME_STEP_INOUTLET_CHUPANHOVERVIEW, statusInOutlet.getChupAnhOverview());

            TimelineDTO step3 = new TimelineDTO(getString(R.string.xemthongtindangkyvalichsueie)
                    , getString(R.string.motaxemthongtindangkyvalichsueie), getString(R.string.stepxemthongtindangkyvalichsueie)
                    , ScreenContants.HOME_STEP_INOUTLET_XEMTHONGTINDANGKYVALICHSUEIE, statusInOutlet.getXemThongTinDangKyLichSuEIE());

            TimelineDTO step4 = new TimelineDTO(getString(R.string.khaosattrungbaytruoc)
                    , getString(R.string.motakhaosattrungbaytruoc), getString(R.string.stepmotakhaosattrungbaytruoc)
                    , ScreenContants.HOME_STEP_INOUTLET_KHAOSATTRUNGBAYTRUOC, statusInOutlet.getKhaoSatTrungBayTruoc());

            TimelineDTO step5 = new TimelineDTO(getString(R.string.khaosattrungbaysau)
                    , getString(R.string.motakhaosattrungbaysau), getString(R.string.stepkhaosattrungbaysau)
                    , ScreenContants.HOME_STEP_INOUTLET_KHAOSATTRUNGBAYSAU, statusInOutlet.getKhaoSatTrungBaySau());

            TimelineDTO step6 = new TimelineDTO(getString(R.string.huthangdathang)
                    , getString(R.string.motahuthangdathang), getString(R.string.stephuthangdathang)
                    , ScreenContants.HOME_STEP_INOUTLET_HUTHANGDATHANG, statusInOutlet.getHutHangDatHang());

            TimelineDTO step7 = new TimelineDTO(getString(R.string.khaosatdichvukhachhang)
                    , getString(R.string.motakhaosatdichvukhachhang), getString(R.string.stepkhaosatdichvukhachhang)
                    , ScreenContants.HOME_STEP_INOUTLET_KHAOSATDICHVUKHACHHANG, statusInOutlet.getKhaoSatDichVuKhachHang());

            TimelineDTO step8 = new TimelineDTO(getString(R.string.xacnhanlamviec)
                    , getString(R.string.motaxacnhanlamviec), getString(R.string.stepxacnhanlamviec)
                    , ScreenContants.HOME_STEP_INOUTLET_KHAOSATPOSM, statusInOutlet.getKhaoSatPOSM());

            TimelineDTO step9 = new TimelineDTO(getString(R.string.ghinhankhieunai)
                    , getString(R.string.motaghinhankhieunai), getString(R.string.stepghinhankhieunai)
                    , ScreenContants.HOME_STEP_INOUTLET_GHINHANKHIEUNAI, statusInOutlet.getGhiNhanKhieuNai());

            timelineDTOs.add(step1);
            timelineDTOs.add(step2);
            timelineDTOs.add(step4);
            timelineDTOs.add(step4);
            timelineDTOs.add(step5);
            timelineDTOs.add(step6);
            timelineDTOs.add(step7);
            timelineDTOs.add(step8);
            timelineDTOs.add(step9);
        } else {
            TimelineDTO step1 = new TimelineDTO(getString(R.string.checkin)
                    , getString(R.string.motacheckin), getString(R.string.stepcheckin)
                    , ScreenContants.HOME_STEP_INOUTLET_CHECKIN, ScreenContants.STATUS_STEP_INPROGRESS);

            TimelineDTO step2 = new TimelineDTO(getString(R.string.chupanhoverview)
                    , getString(R.string.motachupanhoverview), getString(R.string.stepchupanhoverview)
                    , ScreenContants.HOME_STEP_INOUTLET_CHUPANHOVERVIEW, ScreenContants.STATUS_STEP_NOTYET);

            TimelineDTO step3 = new TimelineDTO(getString(R.string.xemthongtindangkyvalichsueie)
                    , getString(R.string.motaxemthongtindangkyvalichsueie), getString(R.string.stepxemthongtindangkyvalichsueie)
                    , ScreenContants.HOME_STEP_INOUTLET_XEMTHONGTINDANGKYVALICHSUEIE, ScreenContants.STATUS_STEP_NOTYET);

            TimelineDTO step4 = new TimelineDTO(getString(R.string.khaosattrungbaytruoc)
                    , getString(R.string.motakhaosattrungbaytruoc), getString(R.string.stepmotakhaosattrungbaytruoc)
                    , ScreenContants.HOME_STEP_INOUTLET_KHAOSATTRUNGBAYTRUOC, ScreenContants.STATUS_STEP_NOTYET);

            TimelineDTO step5 = new TimelineDTO(getString(R.string.khaosattrungbaysau)
                    , getString(R.string.motakhaosattrungbaysau), getString(R.string.stepkhaosattrungbaysau)
                    , ScreenContants.HOME_STEP_INOUTLET_KHAOSATTRUNGBAYSAU, ScreenContants.STATUS_STEP_NOTYET);

            TimelineDTO step6 = new TimelineDTO(getString(R.string.huthangdathang)
                    , getString(R.string.motahuthangdathang), getString(R.string.stephuthangdathang)
                    , ScreenContants.HOME_STEP_INOUTLET_HUTHANGDATHANG, ScreenContants.STATUS_STEP_NOTYET);

            TimelineDTO step7 = new TimelineDTO(getString(R.string.khaosatdichvukhachhang)
                    , getString(R.string.motakhaosatdichvukhachhang), getString(R.string.stepkhaosatdichvukhachhang)
                    , ScreenContants.HOME_STEP_INOUTLET_KHAOSATDICHVUKHACHHANG, ScreenContants.STATUS_STEP_NOTYET);

            TimelineDTO step8 = new TimelineDTO(getString(R.string.xacnhanlamviec)
                    , getString(R.string.motaxacnhanlamviec), getString(R.string.stepxacnhanlamviec)
                    , ScreenContants.HOME_STEP_INOUTLET_KHAOSATPOSM, ScreenContants.STATUS_STEP_NOTYET);

            TimelineDTO step9 = new TimelineDTO(getString(R.string.ghinhankhieunai)
                    , getString(R.string.motaghinhankhieunai), getString(R.string.stepghinhankhieunai)
                    , ScreenContants.HOME_STEP_INOUTLET_GHINHANKHIEUNAI, ScreenContants.STATUS_STEP_NOTYET);

            timelineDTOs.add(step1);
            timelineDTOs.add(step2);
            timelineDTOs.add(step4);
            timelineDTOs.add(step4);
            timelineDTOs.add(step5);
            timelineDTOs.add(step6);
            timelineDTOs.add(step7);
            timelineDTOs.add(step8);
            timelineDTOs.add(step9);
        }

        return timelineDTOs;
    }
}
