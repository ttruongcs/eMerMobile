package com.banvien.fcv.mobile;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.banvien.fcv.mobile.utils.ELog;

import butterknife.Bind;

/**
 * Created by Linh Nguyen on 6/22/2016.
 */
public class CoverageInfoActivity extends BaseDrawerActivity {
    private final String TAG = "CoverageInfoActivity";

    @Bind(R.id.btnFind)
    ImageView btnFind;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coverage);
        bindViews();
//        setToolbarTitle(getString(R.string.outlet_list));
        bindEvent();
    }

    private void bindEvent() {
        btnFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), OutletTabActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }
}
