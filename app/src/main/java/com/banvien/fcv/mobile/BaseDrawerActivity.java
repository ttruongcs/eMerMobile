package com.banvien.fcv.mobile;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;

import com.banvien.fcv.mobile.core.A;
import com.banvien.fcv.mobile.utils.K;

public class BaseDrawerActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView(int layoutResID) {
        setContentViewWithoutInject(R.layout.activity_drawer);
        if (layoutResID > 0) {
            ViewGroup viewGroup = (ViewGroup) findViewById(R.id.content_frame);
            LayoutInflater.from(this).inflate(layoutResID, viewGroup, true);
        }

        bindViews();
        setupToolbar();
    }


    @Override
    protected void setupToolbar() {
        super.setupToolbar();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_loggout) {
            A.delc(K.PRINCIPAL_JSON);

            if (TextUtils.isEmpty(A.gets(K.PRINCIPAL_JSON))) {
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }
        return true;
    }

    @Override
    protected void setToolbarTitle(String title) {
        super.setToolbarTitle(title);
    }

}
