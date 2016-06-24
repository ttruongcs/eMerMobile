package com.banvien.fcv.mobile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;

import com.banvien.fcv.mobile.adapter.AfterDisplayAdapter;
import com.banvien.fcv.mobile.adapter.AfterOutletModelAdapter;
import com.banvien.fcv.mobile.beanutil.HotzoneUtil;
import com.banvien.fcv.mobile.db.Repo;
import com.banvien.fcv.mobile.db.entities.HotzoneEntity;
import com.banvien.fcv.mobile.dto.AfterDisplayDTO;
import com.banvien.fcv.mobile.dto.HotzoneDTO;
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
public class AfterDisplayActivity extends BaseDrawerActivity {
    private static final String TAG = "BeforeDisplayActivity";
    private static Long outletId;

    @Bind(R.id.rvOutletModel)
    RecyclerView recyclerView;

    private Repo repo;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<AfterDisplayDTO> afterDisplayDTOs;
    private List<HotzoneDTO> hotzoneDTOs;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_outletmodel);
        repo = new Repo(this);
        outletId = this.getIntent().getLongExtra(ScreenContants.KEY_OUTLET_ID, 0l);
        afterDisplayDTOs = new ArrayList<>();
        hotzoneDTOs = new ArrayList<>();

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
        adapter = new AfterOutletModelAdapter(this, afterDisplayDTOs, hotzoneDTOs, repo, outletId);
        recyclerView.setAdapter(adapter);

    }

    private void bindDatas() {
        try {
            afterDisplayDTOs = repo.getOutletMerDAO().findOutletModelAfterByOutletId(outletId);

            List<HotzoneEntity> hotzoneEntities = repo.getHotZoneDAO().queryForAll();
            for(HotzoneEntity entity : hotzoneEntities) {
                hotzoneDTOs.add(HotzoneUtil.convertToDTO(entity));
            }
        } catch (SQLException e) {
            ELog.d(e.getMessage(), e);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(repo != null) {
            repo.release();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
