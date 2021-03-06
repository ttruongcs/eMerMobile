package com.banvien.fcv.mobile;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.banvien.fcv.mobile.adapter.MyExpandableAdapter;
import com.banvien.fcv.mobile.beanutil.OutletMerUtil;
import com.banvien.fcv.mobile.beanutil.OutletUtil;
import com.banvien.fcv.mobile.beanutil.ShortageProductUtil;
import com.banvien.fcv.mobile.core.A;
import com.banvien.fcv.mobile.db.Repo;
import com.banvien.fcv.mobile.db.entities.OutletEntity;
import com.banvien.fcv.mobile.dto.OutletDTO;
import com.banvien.fcv.mobile.dto.OutletMerDTO;
import com.banvien.fcv.mobile.dto.ProductDTO;
import com.banvien.fcv.mobile.dto.ProductgroupDTO;
import com.banvien.fcv.mobile.dto.ShortageProductDTO;
import com.banvien.fcv.mobile.dto.UserPrincipal;
import com.banvien.fcv.mobile.dto.getfromserver.MProductDTO;
import com.banvien.fcv.mobile.utils.ELog;
import com.github.clans.fab.FloatingActionButton;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by Linh Nguyen on 5/24/2016.
 */
public class OrderActivity extends BaseDrawerActivity {
    private static final String TAG = "OrderActivity";
    private static Long outletId;
    private static Long routeScheduleDetailId;

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.expandListProduct)
    ExpandableListView expandableListView;

    @BindView(R.id.fabShare)
    FloatingActionButton btnShare;

    private Repo repo;
    private MyExpandableAdapter adapter;
    private List<ProductgroupDTO> sections;
    private Map<String, List<MProductDTO>> products;
    private Map<String, String> orderInfos;
    private List<String> shortageCodes;
    private SharedPreferences sharedPreferences;
    private UserPrincipal principal;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        getSupportActionBar().setTitle(R.string.huthangdathang);
        repo = new Repo(this);
        sections = new ArrayList<>();
        products = new HashMap<>();
        orderInfos = new HashMap<>();
        shortageCodes = new ArrayList<>();

        principal = A.getPrincipal();
        outletId = this.getIntent().getLongExtra(ScreenContants.KEY_OUTLET_ID, 0l);
        routeScheduleDetailId = this.getIntent().getLongExtra(ScreenContants.KEY_ROUTESCHEDULE_DETAIL, 0l);

        initOrderData();
        onFreshList();

        fixSizeExpandableList();
        adapter = new MyExpandableAdapter(this, sections, products, orderInfos, outletId, routeScheduleDetailId);
        expandableListView.setAdapter(adapter);

        bindEvents();
    }

    private void bindEvents() {
        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    ELog.d(principal.toString());
                    List<ShortageProductDTO> whatsappProducts = repo.getShortageProductDAO().
                            findByRouteScheduleId(routeScheduleDetailId);
                    OutletEntity outletEntity = repo.getOutletDAO().findById(outletId);
                    OutletDTO outletDTO = OutletUtil.convertToDTO(outletEntity);
                    StringBuilder textSend = new StringBuilder();
                    Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
                    whatsappIntent.setType("text/plain");
                    whatsappIntent.setPackage("com.whatsapp");

                    /*Create template whatsapp*/
                    if(principal.getFirstName() != null) {
                        textSend.append(principal.getFirstName()).append(" ");
                        if(principal.getLastName() == null) {
                            textSend.append("\n");
                        }
                    }
                    if(principal.getLastName() != null) {
                        textSend.append(principal.getLastName()).append("\n");
                    }

                    textSend.append("------------").append("\n");
                    if(outletDTO.getName() != null) {
                        textSend.append(getString(R.string.whatsapp_outlet_name) + ": " + outletDTO.getName()).append("\n");
                    }
                    String fullAddress = "";
                    StringBuilder address = new StringBuilder();
                    if(!(outletDTO.getLocationNo() == null && outletDTO.getStreet() == null && outletDTO.getWard() == null && outletDTO.getCityName() == null)) {
                        address.append("Địa chỉ: ");
                        if(outletDTO.getLocationNo() != null && !TextUtils.isEmpty(outletDTO.getLocationNo())) {
                            address.append(outletDTO.getLocationNo()).append(", ");
                        }
                        if(outletDTO.getStreet() != null && !TextUtils.isEmpty(outletDTO.getStreet())) {
                            address.append(outletDTO.getStreet()).append(", ");
                        }
                        if(outletDTO.getWard() != null && !TextUtils.isEmpty(outletDTO.getWard())) {
                            address.append(outletDTO.getWard()).append(", ");
                        }
                        if(outletDTO.getCityName() != null && !TextUtils.isEmpty(outletDTO.getCityName())) {
                            address.append(outletDTO.getCityName());
                        }
                        fullAddress = address.toString();

                        if(fullAddress.substring(fullAddress.length() - 1).trim().equals(",")) {
                            fullAddress = fullAddress.substring(0, fullAddress.length() - 1);
                        }
                    }
                    textSend.append(fullAddress).append("\n");


                    if(whatsappProducts.size() > 0) {
                        for(ShortageProductDTO product : whatsappProducts) {
                            textSend.append("~ " + product.getProductName()).append("\n");
                        }
                        whatsappIntent.putExtra(Intent.EXTRA_TEXT, textSend.toString());
                        v.getContext().startActivity(whatsappIntent);

                    } else {
                        Toast.makeText(v.getContext(), "Bạn chưa chọn sản phẩm", Toast.LENGTH_SHORT).show();
                    }


                } catch (android.content.ActivityNotFoundException ex) {
//                    Toast.makeText("Whatsapp have not been installed.");
                } catch (SQLException e) {
                    ELog.e(e.getMessage(), e);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        int groupCount = adapter.getGroupCount();

        reloadProductData();
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
        int i = 0;
        try {
            sharedPreferences = getSharedPreferences(ScreenContants.BeforePREFERENCES, MODE_PRIVATE);
            for(String key : sharedPreferences.getAll().keySet()) {
                if(sharedPreferences.getInt(key, -1) == 0) {
                    shortageCodes.add(key);
                }
                i++;
            }
            sections = this.repo.getProductGroupDAO().findAll(); //Get all name of product group

            for(ProductgroupDTO productgroupDTO : sections) {
                List<MProductDTO> productDTOs = this.repo.getProductDAO().findByProductGroupId(productgroupDTO.getProductGroupId(), shortageCodes);
                products.put(productgroupDTO.getName(), productDTOs);
            }

            /*Get all outlet mer result by outlet id and dataType*/
            List<ShortageProductDTO> shortageProductDTOs = this.repo.getShortageProductDAO().findByRouteScheduleId(routeScheduleDetailId);

            if(shortageProductDTOs.size() > 0) {
                for(ShortageProductDTO shortageProductDTO : shortageProductDTOs) {
                    ELog.d("shortage", shortageProductDTO.toString());
                    orderInfos.put(shortageProductDTO.getProductCode(), String.valueOf(shortageProductDTO.get_id()));
                }
            }
            ELog.d("map", orderInfos.toString());
        } catch (SQLException e) {
            ELog.d("Không thể lấy dữ liệu!", e);
        }
    }

    public ShortageProductDTO addOrder(ShortageProductDTO shortageProductDTO) {
        ShortageProductDTO result = new ShortageProductDTO();

        try {
            result = this.repo.getShortageProductDAO().addShortageProduct(ShortageProductUtil.convertToEntity(shortageProductDTO));
        } catch (SQLException e) {
            ELog.d(e.getMessage(), e);
        }

        return result;
    }

//    public void updateOrder(OutletMerDTO outletMerDTO) {
//        try {
//            this.repo.getOutletMerDAO().updateOrderMer(OutletMerUtil.convertToEntity(outletMerDTO));
//        } catch (SQLException e) {
//            ELog.d(e.getMessage(), e);
//        }
//    }
//
//    public boolean checkProductExist(OutletMerDTO outletMerDTO) {
//        boolean isExist = false;
//        try {
//            isExist = this.repo.getOutletMerDAO().checkExistByReferenceValue(ScreenContants.ORDER ,outletMerDTO.getReferenceValue(), outletMerDTO.getOutletId());
//        } catch (SQLException e) {
//            ELog.d(e.getMessage(), e);
//        }
//
//        return isExist;
//    }


    public void removeOrder(String id) {
        try {
            this.repo.getShortageProductDAO().deleteShortageProduct(id);
        } catch (SQLException e) {
            ELog.d(e.getMessage(), e);
        }
    }


}
