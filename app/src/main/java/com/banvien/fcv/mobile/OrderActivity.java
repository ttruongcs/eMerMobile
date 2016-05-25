package com.banvien.fcv.mobile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.DisplayMetrics;
import android.widget.ExpandableListView;

import com.banvien.fcv.mobile.adapter.MyExpandableAdapter;
import com.banvien.fcv.mobile.beanutil.OutletMerUtil;
import com.banvien.fcv.mobile.db.Repo;
import com.banvien.fcv.mobile.dto.OutletMerDTO;
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
    private static Long outletId;

    @Bind(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    @Bind(R.id.expandListProduct)
    ExpandableListView expandableListView;

    private Repo repo;
    private MyExpandableAdapter adapter;
    private List<ProductgroupDTO> sections;
    private Map<String, List<ProductDTO>> products;
    private Map<String, String> orderInfos;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        repo = new Repo(this);
        sections = new ArrayList<>();
        products = new HashMap<>();
        orderInfos = new HashMap<>();
        outletId = this.getIntent().getLongExtra(ScreenContants.KEY_OUTLET_ID, 0l);

        initOrderData();
        onFreshList();

        fixSizeExpandableList();
        adapter = new MyExpandableAdapter(this, sections, products, orderInfos, outletId);
        expandableListView.setAdapter(adapter);
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
        initOrderData();
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

    private void initOrderData() {
        try {
            sections = this.repo.getProductGroupDAO().findAll(); //Get all name of product group

            for(ProductgroupDTO productgroupDTO : sections) {
                List<ProductDTO> productDTOs = this.repo.getProductDAO().findByProductGroupId(productgroupDTO.getProductGroupId());
                products.put(productgroupDTO.getName(), productDTOs);
            }

            /*Get all outlet mer result by outlet id and dataType*/
            List<OutletMerDTO> outletMerDTOs = this.repo.getOutletMerDAO().findOrderByOutletId(ScreenContants.ORDER, outletId);
            if(outletMerDTOs.size() > 0) {
                for(OutletMerDTO outletMerDTO : outletMerDTOs) {
                    orderInfos.put(outletMerDTO.getReferenceValue(), outletMerDTO.getActualValue());
                }
            }
            ELog.d("map", orderInfos.toString());
        } catch (SQLException e) {
            ELog.d("Không thể lấy dữ liệu", e);
        }
    }

    public void addOrder(OutletMerDTO outletMerDTO) {
        try {
            this.repo.getOutletMerDAO().addOutletMerEntity(OutletMerUtil.convertToEntity(outletMerDTO));
        } catch (SQLException e) {
            ELog.d(e.getMessage(), e);
        }
    }

    public void updateOrder(OutletMerDTO outletMerDTO) {
        try {
            this.repo.getOutletMerDAO().updateOutletMerEntity(OutletMerUtil.convertToEntity(outletMerDTO));
        } catch (SQLException e) {
            ELog.d(e.getMessage(), e);
        }
    }

    public boolean checkProductExist(OutletMerDTO outletMerDTO) {
        boolean isExist = false;
        try {
            isExist = this.repo.getOutletMerDAO().checkExistByReferenceValue(outletMerDTO.getReferenceValue(), outletMerDTO.getOutletId());
        } catch (SQLException e) {
            ELog.d(e.getMessage(), e);
        }

        return isExist;
    }
}
