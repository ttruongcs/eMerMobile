package com.banvien.fcv.mobile;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

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
}
