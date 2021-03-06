package com.banvien.fcv.mobile.fragments;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.banvien.fcv.mobile.OutletTabActivity;
import com.banvien.fcv.mobile.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hieu on 4/11/2016.
 */
public class HomeFragment extends BaseFragment {
    private static final String TAG = "HomeFragment";
    @BindView(R.id.step1_photo)
    ImageView initStep;

    @BindView(R.id.step2_photo)
    ImageView actionStep;

    @BindView(R.id.step3_photo)
    ImageView endStep;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main_home_fragment, container, false);
        ButterKnife.bind(this, view);
        bindViews();
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        parent.setToolbarTitle(getString(R.string.home));
    }

    protected void bindViews(){
        initStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "change to init activity");

                FragmentTransaction tx = parent.getSupportFragmentManager().beginTransaction();
                tx.replace(R.id.content_frame, new PrepareFragment());
                tx.addToBackStack(TAG);
                tx.commit();
            }
        });

        actionStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.d(TAG, "change to action activity");
//
//                FragmentTransaction tx = parent.getSupportFragmentManager().beginTransaction();
//                tx.replace(R.id.content_frame, new OutletTabActivity());
//                tx.addToBackStack(TAG);
//                tx.commit();
            }
        });

        endStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "change to sync activity");

                FragmentTransaction tx = parent.getSupportFragmentManager().beginTransaction();
                tx.replace(R.id.content_frame, new SyncEndFragment());
                tx.addToBackStack(TAG);
                tx.commit();
            }
        });
    }
}
