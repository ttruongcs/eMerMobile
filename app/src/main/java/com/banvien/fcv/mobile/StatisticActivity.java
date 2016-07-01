package com.banvien.fcv.mobile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.banvien.fcv.mobile.utils.DividerItemDecoration;
import com.banvien.fcv.mobile.utils.MySpeedScrollManager;

import butterknife.BindView;

/**
 * Created by Linh Nguyen on 6/25/2016.
 */
public class StatisticActivity extends BaseDrawerActivity {
    private static final String TAG = "StatisticActivity";

    @BindView(R.id.rcvStatic)
    RecyclerView recyclerView;

    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        bindViews();
        initRecyclerView();
    }

    private void initRecyclerView() {
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, null));
        layoutManager = new MySpeedScrollManager(this);
        recyclerView.setLayoutManager(layoutManager);
//        adapter = ? ;
//        recyclerView.setAdapter(adapter);
    }
}
