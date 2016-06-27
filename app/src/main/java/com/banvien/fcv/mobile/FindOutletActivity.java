package com.banvien.fcv.mobile;

import android.app.Dialog;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.banvien.fcv.mobile.adapter.AddOutletAdapter;
import com.banvien.fcv.mobile.db.Repo;
import com.banvien.fcv.mobile.db.entities.RouteScheduleEntity;
import com.banvien.fcv.mobile.dto.routeschedule.MRouteScheduleDetailDTO;
import com.banvien.fcv.mobile.rest.RestClient;
import com.banvien.fcv.mobile.utils.DataBinder;
import com.banvien.fcv.mobile.utils.DateDialog;
import com.banvien.fcv.mobile.utils.DividerItemDecoration;
import com.banvien.fcv.mobile.utils.ELog;
import com.banvien.fcv.mobile.utils.MySpeedScrollManager;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Linh Nguyen on 6/24/2016.
 */
public class FindOutletActivity extends BaseDrawerActivity {
    private static final int DILOG_ID = 0;

    @Bind(R.id.fabMenu)
    FloatingActionMenu fabMenu;

    @Bind(R.id.lnAddOutlet)
    CoordinatorLayout lnAddOutlet;

    @Bind(R.id.fabSearch)
    FloatingActionButton fabSearch;

    @Bind(R.id.fabAdvanceSearch)
    FloatingActionButton fabAdvanceSearch;

    @Bind(R.id.btnFindOutlet)
    Button btnFindOutlet;

    @Bind(R.id.rcvAddOutlet)
    RecyclerView recyclerView;

    @Bind(R.id.switcher)
    ViewSwitcher viewSwitcher;

    @Bind(R.id.edKeyword)
    EditText edKeyword;

    @Bind(R.id.edDate)
    EditText edDate;

    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private Repo repo;
    private Long routeScheduleId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_outlet);
        repo = new Repo(this);
        routeScheduleId = findRouteSchedule();

        bindViews();
        bindEvents();

    }

    private Long findRouteSchedule() {
        Long routeScheduleId = null;

        try {
            RouteScheduleEntity route = repo.getRouteScheduleDAO().findRoute();

            if(route != null) {
                routeScheduleId = route.getRouteScheduleId();
            }
        } catch (SQLException e) {
            ELog.d(e.getMessage(), e);
        }

        return routeScheduleId;
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
                Intent intent = new Intent(v.getContext(), FindOutletSimpleActivity.class);
                startActivity(intent);
            }
        });

        fabAdvanceSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), getString(R.string.find_warning), Toast.LENGTH_SHORT).show();
            }
        });

        edDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnFindOutlet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String keyword = edKeyword.getText().toString().trim();
                String createdDate = edDate.getText().toString().trim();
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                Timestamp createdDateTs = null;
                try {
                    Date parsedDate = dateFormat.parse(createdDate);
                    createdDateTs = new Timestamp(parsedDate.getTime());

                } catch (ParseException e) {
                    ELog.d(e.getMessage(), e);
                }

                if(createdDateTs != null) {
                    try {
                        Call<Map<String, Object>> call = RestClient.getInstance().getOutletService()
                                .searchOutlet(keyword, null, routeScheduleId, null, null, createdDateTs); //Todo Revise this
                        call.enqueue(new Callback<Map<String, Object>>() {
                            @Override
                            public void onResponse(Call<Map<String, Object>> call, Response<Map<String, Object>> response) {
                                Map<String, Object> result = response.body();
                                List<MRouteScheduleDetailDTO> routeScheduleDetailDTOs = DataBinder.readRouteScheduleDetail(result.get("listRouteOutlet"));
                                initRecycleview(routeScheduleDetailDTOs);
                            }

                            @Override
                            public void onFailure(Call<Map<String, Object>> call, Throwable t) {
                                ELog.d("Find outlet from API failed");
                            }
                        });


                    } catch (Exception e) {
                        ELog.e(e.getMessage(), e);
                    }

                } else {
                    Toast.makeText(v.getContext(), getString(R.string.find_failed), Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void initRecycleview(List<MRouteScheduleDetailDTO> routeScheduleDetailDTOs) {
        routeScheduleDetailDTOs = checkRouteExist(routeScheduleDetailDTOs);
        if(routeScheduleDetailDTOs.size() > 0) {
            recyclerView.setHasFixedSize(true);
            recyclerView.addItemDecoration(new DividerItemDecoration(getBaseContext(), null));
            layoutManager = new MySpeedScrollManager(getBaseContext());
            recyclerView.setLayoutManager(layoutManager);
            adapter = new AddOutletAdapter(this, routeScheduleDetailDTOs, repo);
            recyclerView.setAdapter(adapter);

            new AnimationUtils();
            viewSwitcher.setAnimation(AnimationUtils.makeInAnimation(getBaseContext(), true));
            viewSwitcher.showNext();
        } else {
            Toast.makeText(getBaseContext(), getString(R.string.no_result_found), Toast.LENGTH_SHORT).show();
        }

    }

    private List<MRouteScheduleDetailDTO> checkRouteExist(List<MRouteScheduleDetailDTO> routeScheduleDetailDTOs) {
        List<MRouteScheduleDetailDTO> results = new ArrayList<>(routeScheduleDetailDTOs);
        for(MRouteScheduleDetailDTO dto : routeScheduleDetailDTOs) {
            try {
                long countOf = repo.getOutletDAO().checkExist(dto.getOutlet().getOutletId());
                if(countOf > 0) {
                    results.remove(dto);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return results;
    }

    @Override
    protected void onStart() {
        super.onStart();
        edDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    DateDialog dialog = DateDialog.newInstance(v);
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    dialog.show(ft, "DatePicker");
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(repo != null) {
            repo.release();
        }
    }
}
