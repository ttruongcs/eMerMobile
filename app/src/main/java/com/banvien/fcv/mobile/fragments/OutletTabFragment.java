package com.banvien.fcv.mobile.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.banvien.fcv.mobile.R;
import com.banvien.fcv.mobile.adapter.ViewPagerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Linh Nguyen on 5/20/2016.
 */
public class OutletTabFragment extends BaseFragment {
    private final String TAG = "OutletTab";

    @Nullable
    @BindView(R.id.outletViewPager)
    ViewPager viewPager;

    @BindView(R.id.tabs)
    TabLayout tabLayout;

    public static OutletTabFragment newInstance() {
        OutletTabFragment outletTabFragment = new OutletTabFragment();
        Bundle args = new Bundle();

        outletTabFragment.setArguments(args);

        return outletTabFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_outlet_tab, container, false);

        ButterKnife.bind(this, view);

        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        Log.d(TAG, "Init tab");
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new FinishedOutletFragment(), getString(R.string.outlet_status_finished));
        adapter.addFragment(new DoingOutletFragment(), getString(R.string.outlet_status_doing));
        adapter.addFragment(new UnfinishedOutletFragment(), getString(R.string.outlet_status_unfinished));
        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(2);
        parent.getSupportActionBar().setTitle(getString(R.string.outlet_status_unfinished));

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position == 0) {
                    parent.getSupportActionBar().setTitle(getString(R.string.outlet_status_finished));
                } else if(position == 1) {
                    parent.getSupportActionBar().setTitle(getString(R.string.outlet_status_doing));
                } else {
                    parent.getSupportActionBar().setTitle(getString(R.string.outlet_status_unfinished));
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }


}
