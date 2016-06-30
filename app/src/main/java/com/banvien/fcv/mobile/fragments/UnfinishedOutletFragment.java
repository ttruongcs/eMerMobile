package com.banvien.fcv.mobile.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.banvien.fcv.mobile.OutletTabActivity;
import com.banvien.fcv.mobile.R;
import com.banvien.fcv.mobile.ScreenContants;
import com.banvien.fcv.mobile.adapter.OutletListAdapter;
import com.banvien.fcv.mobile.db.Repo;
import com.banvien.fcv.mobile.dto.OutletDTO;
import com.banvien.fcv.mobile.utils.DividerItemDecoration;
import com.banvien.fcv.mobile.utils.ELog;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Linh Nguyen on 5/20/2016.
 */
public class UnfinishedOutletFragment extends Fragment {
    @Nullable
    @Bind(R.id.rvOutletList)
    RecyclerView recyclerView;

    @Nullable
    @Bind(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    Button btnLetGo;

    private View rootView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<OutletDTO> mData;
    private Repo repo;

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
        repo = new Repo(rootView.getContext());

        if(!checkExistData()) {
            rootView = inflater.inflate(R.layout.data_no_exist_item, container, false);
            btnLetGo = (Button) rootView.findViewById(R.id.btnLetGo);
        }

        return rootView;
    }

    private boolean checkExistData() {
        boolean isExist = false;
        try {
            List<OutletDTO> outletDTOs = repo.getOutletDAO().getOutletsWithCircumstance(ScreenContants.OUTLET_STATUS_UNFINISHED);
            if(outletDTOs.size() > 0) {
                isExist = true;
            }
        } catch (SQLException e) {
            ELog.d(e.getMessage(), e);
        }

        return isExist;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mData = new ArrayList<>();

        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), null));
        layoutManager = new LinearLayoutManager(rootView.getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new OutletListAdapter(mData, UnfinishedOutletFragment.this, ScreenContants.UNFINISH, repo);
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
            List<OutletDTO> outletDTOs = repo.getOutletDAO().getOutletsWithCircumstance(ScreenContants.OUTLET_STATUS_UNFINISHED);
            if(outletDTOs.size() > 0) {
                mData.addAll(outletDTOs);
            }
            adapter.notifyDataSetChanged();
        } catch (SQLException e) {
            ELog.d(e.getMessage(), e);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(repo != null) {
            repo.release();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(!checkExistData()) {
            changeDataUnavailable();
        } else {
            reloadOutletList();
        }
    }

    private void changeDataUnavailable() {
        LayoutInflater inflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        rootView = inflater.inflate(R.layout.data_no_exist_item, null);
        btnLetGo = (Button) rootView.findViewById(R.id.btnLetGo);
        ViewGroup viewGroup = (ViewGroup)getView();
        viewGroup.removeAllViews();
        viewGroup.addView(rootView);

        btnLetGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TabLayout tabLayout = ((TabLayout)getActivity().findViewById(R.id.tabs));
                tabLayout.getTabAt(1).select();
            }
        });
    }
}
