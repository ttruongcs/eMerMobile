package com.banvien.fcv.mobile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.banvien.fcv.mobile.adapter.TimelineAdapter;
import com.banvien.fcv.mobile.dto.TimelineDTO;

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

    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<TimelineDTO> timelineDTOs;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        timelineDTOs = new ArrayList<>();

        TimelineDTO syncData = new TimelineDTO("Đồng bộ dữ liệu"
                , "Thực hiên đồng bộ dữ liệu các cửa hàng cần audit trong ngày", "01", "normal", 2);
        TimelineDTO addOutlet = new TimelineDTO("Thêm cửa hàng nếu muốn"
                , "Thêm vào các cửa hàng chưa có trong danh sách cửa hàng", "02", "normal", 1);
        TimelineDTO captureFirstOutlet = new TimelineDTO("Chụp cửa hàng đầu tiên"
                , "Selfie 1 tấm cùng cửa hàng đầu tiên", "03", "normal", 0);
        TimelineDTO startWork = new TimelineDTO("Bắt đầu làm việc"
                , "Lao động là vinh quang", "04", "checked", 0);

        timelineDTOs.add(syncData);
        timelineDTOs.add(addOutlet);
        timelineDTOs.add(captureFirstOutlet);
        timelineDTOs.add(startWork);

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new TimelineAdapter(timelineDTOs, this);
        recyclerView.setAdapter(adapter);
    }
}
