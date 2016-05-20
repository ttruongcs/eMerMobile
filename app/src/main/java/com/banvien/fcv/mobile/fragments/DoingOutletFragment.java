package com.banvien.fcv.mobile.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.banvien.fcv.mobile.R;
import com.banvien.fcv.mobile.ScreenContants;
import com.banvien.fcv.mobile.adapter.OutletListAdapter;
import com.banvien.fcv.mobile.dto.OutletDTO;
import com.banvien.fcv.mobile.utils.ELog;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Linh Nguyen on 5/20/2016.
 */
public class DoingOutletFragment extends BaseFragment {
    @Bind(R.id.rvOutletList)
    RecyclerView recyclerView;

    @Bind(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    private View rootView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<OutletDTO> mData;

    public static FinishedOutletFragment newInstance() {
        FinishedOutletFragment finishedOutletFragment = new FinishedOutletFragment();

        Bundle args = new Bundle();
        finishedOutletFragment.setArguments(args);

        return finishedOutletFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_outlet_list_finished, container, false);
        ButterKnife.bind(this, rootView);

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mData = new ArrayList<>();

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(rootView.getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new OutletListAdapter(mData, DoingOutletFragment.this);
        recyclerView.setAdapter(adapter);

        onFreshList();
        reloadOutletList();
    }

    private void onFreshList() {
        swipeRefreshLayout.setColorSchemeColors(R.color.wallet_holo_blue_light);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                reloadOutletList();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

    }

    private void reloadOutletList() {
        mData.clear();
        try {
            List<OutletDTO> outletDTOs = repo.getOutletDAO().getOutletsWithCircumstance(ScreenContants.OUTLET_STATUS_DOING);
            if(outletDTOs.size() > 0) {
                mData.addAll(outletDTOs);
            }
            adapter.notifyDataSetChanged();
        } catch (SQLException e) {
            ELog.d(e.getMessage(), e);
        }
    }
}
