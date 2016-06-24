package com.banvien.fcv.mobile;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.banvien.fcv.mobile.adapter.AddOutletAdapter;
import com.banvien.fcv.mobile.db.Repo;
import com.banvien.fcv.mobile.db.entities.OutletEntity;
import com.banvien.fcv.mobile.utils.DividerItemDecoration;
import com.banvien.fcv.mobile.utils.ELog;
import com.banvien.fcv.mobile.utils.MySpeedScrollManager;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.sql.SQLException;
import java.util.List;

import butterknife.Bind;

/**
 * Created by Linh Nguyen on 6/24/2016.
 */
public class FindOutletSimpleActivity extends BaseDrawerActivity {
    @Bind(R.id.fabMenu)
    FloatingActionMenu fabMenu;

    @Bind(R.id.lnAddOutlet)
    CoordinatorLayout lnAddOutlet;

    @Bind(R.id.fabSearch)
    FloatingActionButton fabSearch;

    @Bind(R.id.fabAdvanceSearch)
    FloatingActionButton fabAdvanceSearch;

    @Bind(R.id.edOutletCode)
    EditText edOutletCode;

    @Bind(R.id.btnFindOutlet)
    Button btnFindOutlet;

    @Bind(R.id.switcher)
    ViewSwitcher viewSwitcher;

    @Bind(R.id.tvAddOutlet)
    TextView tvAddOutlet;

    @Bind(R.id.rcvAddOutlet)
    RecyclerView recyclerView;

    @Bind(R.id.srlAddOutlet)
    SwipeRefreshLayout swipeRefreshLayout;

    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    Repo repo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_outlet_simple);
        repo = new Repo(this);

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

        fabSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), getString(R.string.find_simple_warning), Toast.LENGTH_SHORT).show();
            }
        });

        fabAdvanceSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), FindOutletActivity.class);
                startActivity(intent);
            }
        });

        btnFindOutlet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String outletCode = edOutletCode.getText().toString().trim();
                if(!outletCode.equals("")) {
                    try {
                        List<OutletEntity> outletEntities = repo.getOutletDAO().findByCode(outletCode);

                        if(outletEntities.size() > 0) {
                            initRecycleview(outletEntities);

                            new AnimationUtils();
                            viewSwitcher.setAnimation(AnimationUtils.makeInAnimation(getBaseContext(), true));
                            viewSwitcher.showNext();
                        } else {
                            Toast.makeText(v.getContext(), getString(R.string.no_result_found), Toast.LENGTH_SHORT).show();
                        }
                    } catch (SQLException e) {
                        ELog.e(e.getMessage(), e);
                    }

                } else {
                    Toast.makeText(v.getContext(), getString(R.string.find_failed), Toast.LENGTH_SHORT).show();
                }
            }


        });
    }

    private void initRecycleview(List<OutletEntity> outletEntities) {
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(getBaseContext(), null));
        layoutManager = new MySpeedScrollManager(getBaseContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new AddOutletAdapter(this, outletEntities);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
