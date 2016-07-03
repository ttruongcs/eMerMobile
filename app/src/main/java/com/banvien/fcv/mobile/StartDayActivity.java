package com.banvien.fcv.mobile;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.banvien.fcv.mobile.adapter.TimelineAdapter;
import com.banvien.fcv.mobile.beanutil.StatusHomeUtil;
import com.banvien.fcv.mobile.beanutil.StatusStartDayUtil;
import com.banvien.fcv.mobile.core.A;
import com.banvien.fcv.mobile.db.Repo;
import com.banvien.fcv.mobile.db.dao.OutletDAO;
import com.banvien.fcv.mobile.db.entities.OutletEntity;
import com.banvien.fcv.mobile.db.entities.StatusHomeEntity;
import com.banvien.fcv.mobile.db.entities.StatusStartDayEntity;
import com.banvien.fcv.mobile.dto.OutletDTO;
import com.banvien.fcv.mobile.dto.StatusHomeDTO;
import com.banvien.fcv.mobile.dto.StatusStartDayDTO;
import com.banvien.fcv.mobile.dto.TimelineDTO;
import com.banvien.fcv.mobile.utils.ELog;
import com.banvien.fcv.mobile.utils.IDGenerator;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Linh Nguyen on 6/14/2016.
 */
public class StartDayActivity extends BaseDrawerActivity  {
    private static final String TAG = "StartDayActivity";

    @BindView(R.id.rcvHomeAct)
    RecyclerView recyclerView;

    private Repo repo;

    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<TimelineDTO> timelineDTOs;
    private ArrayAdapter<OutletEntity> arrayAdapter;
    private StatusStartDayDTO statusStartDay;
    private List<OutletEntity> outletsSelect;
    private final static int LOADER_ID = IDGenerator.newId();
    private ProgressDialog progressDialog;
    private AlertDialog.Builder builderSingle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.startdays_activity);
        repo = new Repo(this);
        getSupportActionBar().setTitle(R.string.chuanbidaungay);
        timelineDTOs = new ArrayList<>();

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new TimelineAdapter(timelineDTOs, this);
        recyclerView.setAdapter(adapter);

    }

    private void reloadData() {
        try {
            timelineDTOs.clear();
            StatusStartDayEntity statusStartDayEntity = repo.getStartDayDAO().getConfigStartDayHome();
            if(statusStartDayEntity != null){
                statusStartDay = StatusStartDayUtil.convertToDTO(statusStartDayEntity);
            } else{
                statusStartDay = null;
            }
            buildTreeStep();
        } catch (SQLException e) {
            ELog.d("Error when get CONFIG");
        }
    }

    private void buildTreeStep() {
        if(statusStartDay != null){
            TimelineDTO step1 = new TimelineDTO(getString(R.string.dongbodulieuphancong)
                    , getString(R.string.motadongbodulieuphancong), getString(R.string.stepdongbodulieuphancong)
                    , ScreenContants.HOME_STEP_STARTDAY_DONGBODULIEUPHANCONG, statusStartDay.getDongBoDuLieuPhanCong());

            TimelineDTO step2 = new TimelineDTO(getString(R.string.themcuahangneumuon)
                    , getString(R.string.motathemcuahangneumuon), getString(R.string.stepthemcuahangneumuon)
                    , ScreenContants.HOME_STEP_STARTDAY_THEMCUAHANGNEUMUON, statusStartDay.getThemCuaHangNeuMuon());

            TimelineDTO step3 = new TimelineDTO(getString(R.string.chuphinhdongphuc)
                    , getString(R.string.motachuphinhdongphuc), getString(R.string.stepchuphinhdongphuc)
                    , ScreenContants.HOME_STEP_STARTDAY_CHUPHINHDONGPHUC, statusStartDay.getChupHinhDongPhuc());

            TimelineDTO step4 = new TimelineDTO(getString(R.string.chuphinhcongcudungcu)
                    , getString(R.string.motachuphinhcongcudungcu), getString(R.string.stepchuphinhcongcudungcu)
                    , ScreenContants.HOME_STEP_STARTDAY_CHUPHINHCONGCUDUNGCU, statusStartDay.getChupHinhCongCuDungCu());

            TimelineDTO step5 = new TimelineDTO(getString(R.string.xacnhanlamviec)
                    , getString(R.string.motaxacnhanlamviec), getString(R.string.stepxacnhanlamviec)
                    , ScreenContants.HOME_STEP_STARTDAY_XACNHANLAMVIEC, statusStartDay.getXacNhanLamViec());

            timelineDTOs.add(step1);
            timelineDTOs.add(step2);
            timelineDTOs.add(step3);
            timelineDTOs.add(step4);
            timelineDTOs.add(step5);
        } else {
            TimelineDTO step1 = new TimelineDTO(getString(R.string.dongbodulieuphancong)
                    , getString(R.string.motadongbodulieuphancong), getString(R.string.stepdongbodulieuphancong)
                    , ScreenContants.HOME_STEP_STARTDAY_DONGBODULIEUPHANCONG, ScreenContants.STATUS_STEP_INPROGRESS);

            TimelineDTO step2 = new TimelineDTO(getString(R.string.themcuahangneumuon)
                    , getString(R.string.motathemcuahangneumuon), getString(R.string.stepthemcuahangneumuon)
                    , ScreenContants.HOME_STEP_STARTDAY_THEMCUAHANGNEUMUON, ScreenContants.STATUS_STEP_NOTYET);

            TimelineDTO step3 = new TimelineDTO(getString(R.string.chuphinhdongphuc)
                    , getString(R.string.motachuphinhdongphuc), getString(R.string.stepchuphinhdongphuc)
                    , ScreenContants.HOME_STEP_STARTDAY_CHUPHINHDONGPHUC, ScreenContants.STATUS_STEP_NOTYET);

            TimelineDTO step4 = new TimelineDTO(getString(R.string.chuphinhcongcudungcu)
                    , getString(R.string.motachuphinhcongcudungcu), getString(R.string.stepchuphinhcongcudungcu)
                    , ScreenContants.HOME_STEP_STARTDAY_CHUPHINHCONGCUDUNGCU, ScreenContants.STATUS_STEP_NOTYET);

            TimelineDTO step5 = new TimelineDTO(getString(R.string.xacnhanlamviec)
                    , getString(R.string.motaxacnhanlamviec), getString(R.string.stepxacnhanlamviec)
                    , ScreenContants.HOME_STEP_STARTDAY_XACNHANLAMVIEC, ScreenContants.STATUS_STEP_NOTYET);

            timelineDTOs.add(step1);
            timelineDTOs.add(step2);
            timelineDTOs.add(step3);
            timelineDTOs.add(step4);
            timelineDTOs.add(step5);
        }

        if(timelineDTOs.size() > 0) {
            timelineDTOs.get(0).setHeader(true);
            timelineDTOs.get(timelineDTOs.size() - 1).setFooter(true);
        }
    }


//    public void showAlertBox(final View v) throws SQLException {
//        builderSingle = new android.support.v7.app.AlertDialog.Builder(v.getContext());
//        builderSingle.setTitle("Chọn một cửa hàng: ");
//        outletsSelect = new ArrayList<>();
//        arrayAdapter = new ArrayAdapter<>(
//                v.getContext(),
//                android.R.layout.select_dialog_singlechoice);
//        arrayAdapter.addAll(outletsSelect);
//
//        builderSingle.setNegativeButton(
//                "cancel",
//                new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                    }
//                });
//
//        builderSingle.setAdapter(
//                arrayAdapter,
//                new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        final OutletEntity outletDTO = arrayAdapter.getItem(which);
//                        android.support.v7.app.AlertDialog.Builder builderInner = new android.support.v7.app.AlertDialog.Builder(
//                                v.getContext());
//                        StringBuilder stringBuilder = new StringBuilder(outletDTO.getName());
//                        builderInner.setMessage(stringBuilder.toString());
//                        builderInner.setTitle("Bạn đã chọn cửa hàng đầu tiên là :");
//                        builderInner.setPositiveButton(
//                                "Ok",
//                                new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(
//                                            DialogInterface dialog,
//                                            int which) {
//                                        Intent firstOutletIntent = new Intent(v.getContext(), CaptureFirstOutletActivity.class);
//                                        firstOutletIntent.putExtra(ScreenContants.KEY_OUTLET_ID, outletDTO.getOutletId());
//                                        firstOutletIntent.putExtra(ScreenContants.KEY_ROUTESCHEDULE_DETAIL, outletDTO.getRouteScheduleDetailId());
//                                        firstOutletIntent.putExtra(ScreenContants.KEY_TAKE_PICTURE_ACTION, Boolean.TRUE);
//                                        v.getContext().startActivity(firstOutletIntent);
//                                        dialog.dismiss();
//                                    }
//                                });
//                        builderInner.show();
//                    }
//                });
//        getSupportLoaderManager().initLoader(LOADER_ID, null, this);
//
//        progressDialog = new ProgressDialog(this);
//        progressDialog.setMessage(A.s(R.string.loading));
//        progressDialog.show();
//
//
//    }

    @Override
    protected void onResume() {
        reloadData();
        adapter.notifyItemRangeChanged(0, adapter.getItemCount());
        super.onResume();

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

//    @Override
//    public Loader<List<OutletEntity>> onCreateLoader(int id, Bundle args) {
//        Loader<List<OutletEntity>> loader = null;
//
//        try {
//            OutletDAO outletDAO = repo.getOutletDAO();
//            QueryBuilder<OutletEntity, String> queryBuilder = outletDAO.queryBuilder();
//
//            queryBuilder.orderBy("name", true);
//            loader = outletDAO.getResultSetLoader(this, queryBuilder.prepare());
//        } catch (SQLException e) {
//            ELog.d(e.getMessage(), e);
//        }
//
//        return loader;
//    }
//
//    @Override
//    public void onLoadFinished(Loader<List<OutletEntity>> loader, List<OutletEntity> data) {
//        if(loader != null && data != null) {
//            arrayAdapter.clear();
//            arrayAdapter.addAll(data);
//            arrayAdapter.notifyDataSetChanged();
//            builderSingle.show();
//        }
//        progressDialog.dismiss();
//    }
//
//    @Override
//    public void onLoaderReset(Loader<List<OutletEntity>> loader) {
//        outletsSelect.clear();
//    }
}
