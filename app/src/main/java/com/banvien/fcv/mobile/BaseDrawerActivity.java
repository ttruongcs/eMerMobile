package com.banvien.fcv.mobile;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;

import com.banvien.fcv.mobile.core.A;
import com.banvien.fcv.mobile.dto.UserPrincipal;
import com.banvien.fcv.mobile.utils.K;
import com.banvien.fcv.mobile.utils.UserUtils;

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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if(id == R.id.action_loggout) {
            UserUtils.logOut(this);

            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }

        if(id == R.id.user_profile) {
            Intent intent = new Intent(this, UserprofileActivity.class);
            startActivity(intent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }


}
