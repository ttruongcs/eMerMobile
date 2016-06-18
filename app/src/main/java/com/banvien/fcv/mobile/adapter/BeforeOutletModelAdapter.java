package com.banvien.fcv.mobile.adapter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.banvien.fcv.mobile.BeforeDisplayActivity;
import com.banvien.fcv.mobile.R;
import com.banvien.fcv.mobile.ScreenContants;
import com.banvien.fcv.mobile.beanutil.OutletMerUtil;
import com.banvien.fcv.mobile.db.Repo;
import com.banvien.fcv.mobile.dto.BeforeDisplayDTO;
import com.banvien.fcv.mobile.dto.HotzoneDTO;
import com.banvien.fcv.mobile.dto.OutletMerDTO;
import com.banvien.fcv.mobile.dto.ProductDTO;
import com.banvien.fcv.mobile.utils.CustomLinearLayoutManager;
import com.banvien.fcv.mobile.utils.DividerItemDecoration;
import com.banvien.fcv.mobile.utils.ELog;
import com.google.android.gms.analytics.ecommerce.Product;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Linh Nguyen on 6/16/2016.
 */
public class BeforeOutletModelAdapter extends RecyclerView.Adapter {

    private List<BeforeDisplayDTO> mData;
    private BeforeDisplayActivity activity;
    private List<HotzoneDTO> hotzoneDTOs;
    private Long outletId;
    private Repo repo;

    public BeforeOutletModelAdapter(BeforeDisplayActivity activity, List<BeforeDisplayDTO> data
            , List<HotzoneDTO> hotzoneDTOs, Repo repo, Long outletId) {
        this.mData = data;
        this.activity = activity;
        this.hotzoneDTOs = hotzoneDTOs;
        this.repo = repo;
        this.outletId = outletId;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_before_display_item, parent, false);
        ItemHolder itemHolder = new ItemHolder(v);

        return itemHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemHolder itemHolder =(ItemHolder) holder;
        itemHolder.bindView(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.spinner)
        Spinner spinner;

        @Bind(R.id.rcvBeforeDisplay)
        RecyclerView recyclerView;

        @Bind(R.id.edFacing)
        EditText edFacing;

        @Bind(R.id.edEIE)
        EditText edEIE;

        @Bind(R.id.tvOutletModelName)
        TextView tvOutletModelName;

        RecyclerView.Adapter adapter;
        RecyclerView.LayoutManager layoutManager;

        @Bind(R.id.tvCountChecked)
        TextView tvCountChecked;

        @Bind(R.id.tvCountTotal)
        TextView tvCountTotal;


        public ItemHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindView(BeforeDisplayDTO beforeDisplayDTO) {
            tvOutletModelName.setText(beforeDisplayDTO.getOutletModelName());
            bindModelView();
            initSpinner();
            initRecyclerView(beforeDisplayDTO);
        }

        private void initRecyclerView(BeforeDisplayDTO dto) {
            List<ProductDTO> productDTOs = convertToMHS(dto);
            ProductDTO productDTO = new ProductDTO();
            productDTO.setProductGroupId(1l);
            productDTO.setProductId(5l);
            productDTO.set_id(3);
            productDTO.setCode("cdd");
            productDTO.setName("Sua test");
            productDTOs.add(productDTO);
            productDTOs.add(productDTO);
            productDTOs.add(productDTO);
            productDTOs.add(productDTO);
            productDTOs.add(productDTO);
            productDTOs.add(productDTO);
            productDTOs.add(productDTO);
            productDTOs.add(productDTO);
            productDTOs.add(productDTO);
            productDTOs.add(productDTO);

            tvCountTotal.setText(String.valueOf(productDTOs.size()));
            recyclerView.setHasFixedSize(true);
            recyclerView.addItemDecoration(new DividerItemDecoration(activity, null));
            layoutManager = new CustomLinearLayoutManager(itemView.getContext(), 1, false);
            recyclerView.setLayoutManager(layoutManager);
            ELog.d("so luong product", String.valueOf(productDTOs.size()));
            adapter = new BeforeDisplayAdapter(activity, productDTOs, repo);
            recyclerView.setAdapter(adapter);
        }

        private List<ProductDTO> convertToMHS(BeforeDisplayDTO dto) {
            List<ProductDTO> results = new ArrayList<>();

            if(dto.getMhs() != null) {
                String[] mhsCode = dto.getMhs().getRegisterValue().split(",");
                try {
                    results = repo.getProductDAO().findByCodes(mhsCode);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            return results;
        }

        private void initSpinner() {
            final List<String> spinnerName = new ArrayList<>();
            final List<Long> spinnerId = new ArrayList<>();
            Map<String, String> mapForSearch = new HashMap<>();
            // int positionSelected = 0;
            try {
                spinnerName.add(itemView.getContext().getString(R.string.select_one));
                spinnerId.add(-1l);

                for (HotzoneDTO hotzoneDTO : hotzoneDTOs) {
                    spinnerName.add(hotzoneDTO.getCode());
                    spinnerId.add(hotzoneDTO.getHotZoneId());
                   // mapForSearch.put(hotzoneDTO.getCode(), hotzoneDTO.getCode());
                }

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(activity, R.layout.simple_spinner_item, spinnerName);
                arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                spinner.setAdapter(arrayAdapter);

//                positionSelected = findHotzoneSelected(arrayAdapter, mapForSearch);
//                spinner.setSelection(positionSelected);

                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if(position != 0) {
                            //addBeforeHotzone(hotzoneList.get(position - 1), spinnerId.get(position));
                        }
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            } catch (Exception e) {
                ELog.d(e.getMessage(), e);
            }
        }

        private void bindModelView() {
            try {
                String facingValue = repo.getOutletMerDAO().findActualValueByDataType(ScreenContants.FACING_BEFORE, outletId);
                String eieValue = repo.getOutletMerDAO().findActualValueByDataType(ScreenContants.EIE_BEFORE, outletId);;

                if(facingValue != null) {
                    edFacing.setText(facingValue);
                }
                if(eieValue != null) {
                    edEIE.setText(eieValue);
                }

                edFacing.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        if(actionId == EditorInfo.IME_ACTION_DONE) {
                            OutletMerDTO outletMerDTO = new OutletMerDTO();
                            outletMerDTO.setDataType(ScreenContants.FACING_BEFORE);
                            outletMerDTO.setActualValue(v.getText().toString());
                            outletMerDTO.setOutletId(outletId);
                            insertOrUpdateData(outletMerDTO);
                            return true;
                        }
                        return false;
                    }
                });

                edFacing.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        EditText numberInput;

                        if(!hasFocus) {
                            numberInput = (EditText) v;
                            if(!numberInput.getText().toString().equals("")) {
                                OutletMerDTO outletMerDTO = new OutletMerDTO();
                                outletMerDTO.setDataType(ScreenContants.FACING_BEFORE);
                                outletMerDTO.setActualValue(numberInput.getText().toString());
                                outletMerDTO.setOutletId(outletId);
                                insertOrUpdateData(outletMerDTO);
                            }
                        }
                    }
                });

                edEIE.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        if(actionId == EditorInfo.IME_ACTION_DONE) {
                            OutletMerDTO outletMerDTO = new OutletMerDTO();
                            outletMerDTO.setDataType(ScreenContants.EIE_BEFORE);
                            outletMerDTO.setActualValue(v.getText().toString());
                            outletMerDTO.setOutletId(outletId);
                            insertOrUpdateData(outletMerDTO);
                            return true;
                        }
                        return false;
                    }
                });

                edEIE.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        EditText numberInput;

                        if(!hasFocus) {
                            numberInput = (EditText) v;
                            if(!numberInput.getText().toString().equals("")) {
                                OutletMerDTO outletMerDTO = new OutletMerDTO();
                                outletMerDTO.setDataType(ScreenContants.EIE_BEFORE);
                                outletMerDTO.setActualValue(numberInput.getText().toString());
                                outletMerDTO.setOutletId(outletId);
                                insertOrUpdateData(outletMerDTO);
                            }
                        }
                    }
                });

            } catch (Exception e) {
                ELog.d(e.getMessage(), e);
            }
        }

        private void insertOrUpdateData(OutletMerDTO outletMerDTO) {
            boolean isExist = checkDisplayExist(outletMerDTO);
            try {
                if(!isExist) {
                    repo.getOutletMerDAO().addOutletMerEntity(OutletMerUtil.convertToEntity(outletMerDTO));
                } else {
                    repo.getOutletMerDAO().updateFacingOrEIE(outletMerDTO.getDataType(),
                            OutletMerUtil.convertToEntity(outletMerDTO));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        private boolean checkDisplayExist(OutletMerDTO outletMerDTO) { //Include Facing and EIE
            boolean isExist = false;
            try {
                List<OutletMerDTO> outletMerDTOs = repo.getOutletMerDAO()
                        .findByDataTypeAndOutlet(outletMerDTO.getDataType() , outletMerDTO.getOutletId());

                if(outletMerDTOs.size() > 0) {
                    isExist = true;
                } else {
                    isExist = false;
                }
            } catch (SQLException e) {
                ELog.d(e.getMessage(), e);
            }

            return isExist;
        }
    }
}
