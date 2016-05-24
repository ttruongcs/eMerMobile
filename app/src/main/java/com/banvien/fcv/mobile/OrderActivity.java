package com.banvien.fcv.mobile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.DisplayMetrics;
import android.widget.ExpandableListView;

import com.banvien.fcv.mobile.adapter.MyExpandableAdapter;
import com.banvien.fcv.mobile.db.Repo;
import com.banvien.fcv.mobile.dto.ProductDTO;
import com.banvien.fcv.mobile.dto.ProductgroupDTO;
import com.banvien.fcv.mobile.utils.ELog;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;

/**
 * Created by Linh Nguyen on 5/24/2016.
 */
public class OrderActivity extends BaseDrawerActivity {
    private static final String TAG = "OrderActivity";

    @Bind(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    @Bind(R.id.expandListProduct)
    ExpandableListView expandableListView;

    private Repo repo;
    private MyExpandableAdapter adapter;
    private List<ProductgroupDTO> sections;
    private Map<String, List<ProductDTO>> products;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        repo = new Repo(this);
        sections = new ArrayList<>();
        products = new HashMap<>();

        initProductData();
        onFreshList();

        fixSizeExpandableList();
        adapter = new MyExpandableAdapter(this, sections, products);
        expandableListView.setAdapter(adapter);
    }

    private void onFreshList() {
        swipeRefreshLayout.setColorSchemeColors(R.color.wallet_holo_blue_light);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                reloadProductData();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void reloadProductData() {
        initProductData();
        adapter.notifyDataSetChanged();
    }

    private void fixSizeExpandableList() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int width = metrics.widthPixels;

        expandableListView.setIndicatorBounds(width - getPixelFromDips(50), width - getPixelFromDips(10));


    }

    private int getPixelFromDips(float pixels) {
        // Get the screen's density scale
        final float scale = getResources().getDisplayMetrics().density;
        // Convert the dps to pixels, based on density scale
        return (int) (pixels * scale + 0.5f);
    }

    private void initProductData() {
        try {
            sections = this.repo.getProductGroupDAO().findAll(); //Get all name of product group

            for(ProductgroupDTO productgroupDTO : sections) {
                List<ProductDTO> productDTOs = this.repo.getProductDAO().findByProductGroupId(productgroupDTO.getProductGroupId());
                products.put(productgroupDTO.getName(), productDTOs);
            }
            ELog.d("result", products.toString());
        } catch (SQLException e) {
            ELog.d("Không thể lấy dữ liệu", e);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        int groupCount = adapter.getGroupCount();
        for (int i= 0; i< groupCount; i++) {
            expandableListView.expandGroup(i);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(repo != null) {
            repo.release();
        }
    }
}
