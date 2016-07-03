package com.banvien.fcv.mobile;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.banvien.fcv.mobile.adapter.BeforeOutletModelAdapter;
import com.banvien.fcv.mobile.beanutil.HotzoneUtil;
import com.banvien.fcv.mobile.db.Repo;
import com.banvien.fcv.mobile.db.entities.CaptureBeforeEntity;
import com.banvien.fcv.mobile.db.entities.HotzoneEntity;
import com.banvien.fcv.mobile.dto.BeforeDisplayDTO;
import com.banvien.fcv.mobile.dto.getfromserver.HotZoneDTO;
import com.banvien.fcv.mobile.utils.ChangeStatusTimeline;
import com.banvien.fcv.mobile.utils.DividerItemDecoration;
import com.banvien.fcv.mobile.utils.ELog;
import com.banvien.fcv.mobile.utils.MySpeedScrollManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Linh Nguyen on 5/27/2016.
 */
public class BeforeDisplayActivity extends BaseDrawerActivity {
    private static final String TAG = "BeforeDisplayActivity";
    private static Long outletId;
    private static Long routeScheduleDetailId;

    @BindView(R.id.rvOutletModel)
    RecyclerView recyclerView;

    @BindView(R.id.fabTakeCapture)
    FloatingActionButton fabTakeCapture;

    private Repo repo;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<BeforeDisplayDTO> beforeDisplayDTOs;
    private List<HotZoneDTO> hotzoneDTOs;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_before_outletmodel);
        getSupportActionBar().setTitle(R.string.khaosattrungbaytruoc);
        repo = new Repo(this);
        outletId = this.getIntent().getLongExtra(ScreenContants.KEY_OUTLET_ID, 0l);
        routeScheduleDetailId = this.getIntent().getLongExtra(ScreenContants.KEY_ROUTESCHEDULE_DETAIL, 0l);
        beforeDisplayDTOs = new ArrayList<>();
        hotzoneDTOs = new ArrayList<>();
        sharedPreferences = getSharedPreferences(ScreenContants.BeforePREFERENCES, MODE_PRIVATE);
        bindDatas();
        initRecyclerView();
        bindEvents();
    }

    private void bindEvents() {
        fabTakeCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), CaptureBeforeActivity.class);
                intent.putExtra(ScreenContants.KEY_TAKE_PICTURE_ACTION, Boolean.TRUE);
                intent.putExtra(ScreenContants.KEY_OUTLET_ID, outletId);
                startActivity(intent);
            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView.requestFocus();
                onBackPressed();

            }
        });
    }

    private void initRecyclerView() {
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(ContextCompat.getDrawable(
                this.getApplicationContext(), R.drawable.bubble_blue), false, false));
        layoutManager = new MySpeedScrollManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setNestedScrollingEnabled(false);
        adapter = new BeforeOutletModelAdapter(this, beforeDisplayDTOs, hotzoneDTOs, repo, outletId);
        recyclerView.setAdapter(adapter);


    }

    private void bindDatas() {
        try {
            beforeDisplayDTOs = repo.getOutletMerDAO().findOutletModelBeforeByOutletId(outletId);

            List<HotzoneEntity> hotzoneEntities = repo.getHotZoneDAO().queryForAll();
            for (HotzoneEntity entity : hotzoneEntities) {
                hotzoneDTOs.add(HotzoneUtil.convertToDTO(entity));
            }
        } catch (SQLException e) {
            ELog.d(e.getMessage(), e);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (repo != null) {
            repo.release();
        }
    }

    @Override
    public void onBackPressed() {
        try {
            boolean isCaptured = repo.getCaptureBeforeDAO().checkCaptured(outletId);
            if (isCaptured == true) {
                changeStatus();
            } else {
                AlertDialog.Builder captureBuilder = new AlertDialog.Builder(BeforeDisplayActivity.this);
                captureBuilder.setMessage(getString(R.string.dialog_before_content));

                String positiveText = getString(R.string.capture);
                captureBuilder.setPositiveButton(positiveText, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getBaseContext(), CaptureBeforeActivity.class);
                        intent.putExtra(ScreenContants.KEY_TAKE_PICTURE_ACTION, Boolean.TRUE);
                        intent.putExtra(ScreenContants.KEY_OUTLET_ID, outletId);
                        startActivity(intent);
                    }
                });

                AlertDialog captureDialog = captureBuilder.create();
                captureDialog.show();

            }
        } catch (SQLException e) {
            ELog.d(e.getMessage(), e);

        }

    }

    private void changeStatus() {
        ChangeStatusTimeline changeStatusTimeline = new ChangeStatusTimeline(repo, routeScheduleDetailId);
        String[] next = {ScreenContants.AFTER_DISPLAY_COLUMN};
        changeStatusTimeline.changeStatusToDone(ScreenContants.IN_OUTLET
                , ScreenContants.BEFORE_DISPLAY_COLUMN, next, ScreenContants.END_DATE_COLUMN, false);
        Intent intent = new Intent(getBaseContext(), InOutletHomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(ScreenContants.KEY_OUTLET_ID, outletId);
        intent.putExtra(ScreenContants.KEY_ROUTESCHEDULE_DETAIL, routeScheduleDetailId);
        startActivity(intent);
        finish();
    }


}
