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
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.banvien.fcv.mobile.adapter.AfterOutletModelAdapter;
import com.banvien.fcv.mobile.beanutil.HotzoneUtil;
import com.banvien.fcv.mobile.db.Repo;
import com.banvien.fcv.mobile.db.entities.CaptureAfterEntity;
import com.banvien.fcv.mobile.db.entities.HotzoneEntity;
import com.banvien.fcv.mobile.dto.AfterDisplayDTO;
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
public class AfterDisplayActivity extends BaseDrawerActivity {
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
    private List<AfterDisplayDTO> afterDisplayDTOs;
    private List<HotZoneDTO> hotzoneDTOs;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_outletmodel);
        getSupportActionBar().setTitle(R.string.khaosattrungbaysau);
        repo = new Repo(this);
        outletId = this.getIntent().getLongExtra(ScreenContants.KEY_OUTLET_ID, 0l);
        routeScheduleDetailId = this.getIntent().getLongExtra(ScreenContants.KEY_ROUTESCHEDULE_DETAIL, 0l);
        afterDisplayDTOs = new ArrayList<>();
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
                Intent intent = new Intent(view.getContext(), CaptureAfterActivity.class);
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
        adapter = new AfterOutletModelAdapter(this, afterDisplayDTOs, hotzoneDTOs, repo, outletId, sharedPreferences);
        recyclerView.setAdapter(adapter);

    }

    private void bindDatas() {
        try {
            afterDisplayDTOs = repo.getOutletMerDAO().findOutletModelAfterByOutletId(outletId);

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

    private void changeStatus() {
        ChangeStatusTimeline changeStatusTimeline = new ChangeStatusTimeline(getBaseContext(), routeScheduleDetailId);
        String[] next = {ScreenContants.SHORTAGE_PRODUCT_COLUMN, ScreenContants.SURVEY_COLUMN};
        changeStatusTimeline.changeStatusToDone(ScreenContants.IN_OUTLET
                , ScreenContants.AFTER_DISPLAY_COLUMN, next, ScreenContants.END_DATE_COLUMN, false);
        Intent intent = new Intent(getBaseContext(), InOutletHomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(ScreenContants.KEY_OUTLET_ID, outletId);
        intent.putExtra(ScreenContants.KEY_ROUTESCHEDULE_DETAIL, routeScheduleDetailId);
        startActivity(intent);
        finish();
    }


    @Override
    public void onBackPressed() {

        try {
            boolean isCaptured = repo.getCaptureAfterDAO().checkCaptured(outletId);
            if (isCaptured == true) {
                changeStatus();
            } else {
                AlertDialog.Builder captureBuilder = new AlertDialog.Builder(AfterDisplayActivity.this);
                captureBuilder.setMessage(getString(R.string.dialog_after_content));

                String positiveText = getString(R.string.capture);
                captureBuilder.setPositiveButton(positiveText, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getBaseContext(), CaptureAfterActivity.class);
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

}
