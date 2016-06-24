package com.banvien.fcv.mobile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.github.clans.fab.FloatingActionMenu;

import butterknife.Bind;

/**
 * Created by Linh Nguyen on 6/24/2016.
 */
public class AddOutletActivity extends BaseDrawerActivity {

    @Bind(R.id.fabMenu)
    FloatingActionMenu fabMenu;

    @Bind(R.id.lnAddOutlet)
    CoordinatorLayout lnAddOutlet;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_outlet);

        bindViews();
        bindEvents();

    }

    private void bindEvents() {
        fabMenu.setOnMenuToggleListener(new FloatingActionMenu.OnMenuToggleListener() {
            @Override
            public void onMenuToggle(boolean opened) {
                if(opened) {
                    lnAddOutlet.setAlpha(0.3f);
                    lnAddOutlet.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
                } else {
                    lnAddOutlet.setAlpha(1f);
                    lnAddOutlet.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);
                }
            }
        });
    }
}
