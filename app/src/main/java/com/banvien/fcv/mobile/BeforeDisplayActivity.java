package com.banvien.fcv.mobile;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.banvien.fcv.mobile.adapter.BeforeOutletModelAdapter;
import com.banvien.fcv.mobile.beanutil.HotzoneUtil;
import com.banvien.fcv.mobile.db.Repo;
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

import butterknife.Bind;

/**
 * Created by Linh Nguyen on 5/27/2016.
 */
public class BeforeDisplayActivity extends BaseDrawerActivity {
    private static final String TAG = "BeforeDisplayActivity";
    private static Long outletId;
    private static Long routeScheduleDetailId;

    @Bind(R.id.rvOutletModel)
    RecyclerView recyclerView;

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
        repo = new Repo(this);
        outletId = this.getIntent().getLongExtra(ScreenContants.KEY_OUTLET_ID, 0l);
        routeScheduleDetailId = this.getIntent().getLongExtra(ScreenContants.KEY_ROUTESCHEDULE_DETAIL, 0l);
        beforeDisplayDTOs = new ArrayList<>();
        hotzoneDTOs = new ArrayList<>();
        sharedPreferences = getSharedPreferences(ScreenContants.BeforePREFERENCES, MODE_PRIVATE);

        bindDatas();
        initRecyclerView();
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

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(this.getString(R.string.before_display_title));
        builder.setMessage(this.getString(R.string.before_display_content));

        String positiveText = this.getString(R.string.accept);
        builder.setPositiveButton(positiveText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ChangeStatusTimeline changeStatusTimeline = new ChangeStatusTimeline(getBaseContext(), routeScheduleDetailId);
                String[] next = {ScreenContants.AFTER_DISPLAY_COLUMN};
                changeStatusTimeline.changeStatusToDone(ScreenContants.IN_OUTLET
                        , ScreenContants.BEFORE_DISPLAY_COLUMN, next, ScreenContants.END_DATE_COLUMN, false);
                finish();

            }
        });

        String negativeText = this.getString(R.string.cancel);
        builder.setNegativeButton(negativeText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

    }


}
