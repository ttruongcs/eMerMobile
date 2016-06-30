package com.banvien.fcv.mobile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.banvien.fcv.mobile.adapter.ViewPagerAdapter;
import com.banvien.fcv.mobile.core.A;
import com.banvien.fcv.mobile.fragments.DoingOutletFragment;
import com.banvien.fcv.mobile.fragments.FinishedOutletFragment;
import com.banvien.fcv.mobile.fragments.UnfinishedOutletFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Linh Nguyen on 5/20/2016.
 */
public class OutletTabActivity extends BaseDrawerActivity {
    private final String TAG = "OutletTab";

    @Nullable
    @Bind(R.id.outletViewPager)
    ViewPager viewPager;

    @Bind(R.id.tabs)
    TabLayout tabLayout;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_outlet_tab);
        ButterKnife.bind(this);

        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);


    }

    @Override
    protected void onStart() {
        super.onStart();
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        Log.d(TAG, "Init tab");
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new FinishedOutletFragment(), getString(R.string.outlet_status_finished));
        adapter.addFragment(new DoingOutletFragment(), getString(R.string.outlet_status_doing));
        adapter.addFragment(new UnfinishedOutletFragment(), getString(R.string.outlet_status_unfinished));
        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(2);
//        parent.getSupportActionBar().setTitle(getString(R.string.outlet_status_unfinished));

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position == 0) {
//                    parent.getSupportActionBar().setTitle(getString(R.string.outlet_status_finished));
                } else if(position == 1) {
//                    parent.getSupportActionBar().setTitle(getString(R.string.outlet_status_doing));
                } else {
//                    parent.getSupportActionBar().setTitle(getString(R.string.outlet_status_unfinished));
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
//        if(A.is("doingTab")) {
//            viewPager.setCurrentItem(1);
//        } else {
//            viewPager.setCurrentItem(2);
//        }
        tabLayout.getTabAt(2).select();


    }
}
