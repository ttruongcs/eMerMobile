package com.banvien.fcv.mobile.adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.banvien.fcv.mobile.AfterDisplayActivity;
import com.banvien.fcv.mobile.BeforeDisplayActivity;
import com.banvien.fcv.mobile.CaptureOverviewActivity;
import com.banvien.fcv.mobile.MapsActivity;
import com.banvien.fcv.mobile.OrderActivity;
import com.banvien.fcv.mobile.R;
import com.banvien.fcv.mobile.RegisterHistoryActivity;
import com.banvien.fcv.mobile.ScreenContants;
import com.banvien.fcv.mobile.StatusOutletActivity;
import com.banvien.fcv.mobile.SurveyActivity;
import com.banvien.fcv.mobile.db.Repo;
import com.banvien.fcv.mobile.db.entities.OutletEntity;
import com.banvien.fcv.mobile.db.entities.StatusInOutletEntity;
import com.banvien.fcv.mobile.dto.TimelineInOutletDTO;
import com.banvien.fcv.mobile.library.SyncOutletMerResultService;
import com.banvien.fcv.mobile.library.UpdateService;
import com.banvien.fcv.mobile.utils.ELog;
import com.banvien.fcv.mobile.utils.StringUtils;
import com.j256.ormlite.stmt.query.In;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Linh Nguyen on 6/14/2016.
 */
public class TimelineInOutletAdapter extends RecyclerView.Adapter {
    public static final double PIC_RATIO_VALUE = 4.0;
    private final int HEADER_ITEM = 0;
    private final int NORMAL_ITEM = 1;
    private final int FOOTER_ITEM = 2;

    Repo repo;
    List<TimelineInOutletDTO> mData;
    Activity activity;
    private static ProgressDialog progressDialog;
    private static UpdatingTask updateTask = null;

    public TimelineInOutletAdapter(List<TimelineInOutletDTO> data, Activity activity) {
        this.mData = data;
        this.activity = activity;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.timeline_item_inoutlet_header, parent, false);
            ItemHolder itemHolder = new ItemHolder(v);

            return itemHolder;
        } else if (viewType == 1) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.timeline_item_inoutlet, parent, false);
            ItemHolder itemHolder = new ItemHolder(v);

            return itemHolder;
        } else {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.timeline_item_inoutlet_footer, parent, false);
            ItemHolder itemHolder = new ItemHolder(v);

            return itemHolder;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemHolder itemHolder = (ItemHolder) holder;

        int height = this.containerHeight(activity);
        TableRow.LayoutParams params = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, height);
        itemHolder.linearLayout.setLayoutParams(params);
        itemHolder.tvTimeline.setText(mData.get(position).getTitle());
        itemHolder.tvDetail.setText(mData.get(position).getDetail());
        itemHolder.tvOrder.setText(mData.get(position).getOrder());
        itemHolder.stepCode.setText(mData.get(position).getType());
        itemHolder.outletId.setText(mData.get(position).getOutletId().toString());
        itemHolder.routeScheduleDetailId.setText(mData.get(position).getRouteScheduleDetailId().toString());


        if (mData.get(position).getIsDone() == 1 || mData.get(position).getIsDone() == 2) {
            itemHolder.cardView.setAlpha(1f);
            itemHolder.tvOrder.setBackgroundResource(R.drawable.bg_circle);
            switch (holder.getItemViewType()) {
                case HEADER_ITEM:
                    itemHolder.viewBottom.setBackgroundResource(R.color.color_blog);
                    break;
                case NORMAL_ITEM:
                    itemHolder.viewTop.setBackgroundResource(R.color.color_blog);
                    itemHolder.viewBottom.setBackgroundResource(R.color.color_blog);
                    break;
                case FOOTER_ITEM:
                    itemHolder.viewTop.setBackgroundResource(R.color.color_blog);
                    break;
            }
            itemHolder.arrow.setVisibility(View.VISIBLE);

        } else {
            itemHolder.cardView.setAlpha(0.3f); //Todo: Change to 0.3f
            itemHolder.tvOrder.setBackgroundResource(R.drawable.bg_circle_not_done);
            switch (holder.getItemViewType()) {
                case HEADER_ITEM:
                    itemHolder.viewBottom.setBackgroundResource(R.color.red_500);
                    break;
                case NORMAL_ITEM:
                    itemHolder.viewTop.setBackgroundResource(R.color.red_500);
                    itemHolder.viewBottom.setBackgroundResource(R.color.red_500);
                    break;
                case FOOTER_ITEM:
                    itemHolder.viewTop.setBackgroundResource(R.color.red_500);
                    break;
            }
            itemHolder.arrow.setVisibility(View.GONE);
        }


    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public int containerHeight(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);

        //get predefined value
        double ratio = PIC_RATIO_VALUE;

        return (int) (dm.heightPixels / ratio);
    }

    @Override
    public int getItemViewType(int position) {
        return (mData.get(position).isHeader() ? 0 : (mData.get(position).isFooter() ? 2 : 1 ));
    }

    public class ItemHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvTimeline)
        TextView tvTimeline;

        @BindView(R.id.tvOrder)
        TextView tvOrder;

        @BindView(R.id.rlv2)
        RelativeLayout arrow;

        @Nullable
        @BindView(R.id.view2)
        View viewTop;

        @Nullable
        @BindView(R.id.view)
        View viewBottom;

        @BindView(R.id.lnTimeline)
        LinearLayout linearLayout;

        @BindView(R.id.tvTimelineDetail)
        TextView tvDetail;

        @BindView(R.id.card_view)
        CardView cardView;

        @BindView(R.id.stepCode)
        TextView stepCode;

        @BindView(R.id.outletId)
        TextView outletId;

        @BindView(R.id.routeScheduleDetailId)
        TextView routeScheduleDetailId;

        public ItemHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(cardView.getAlpha() == 0.3f) {
                        Toast.makeText(v.getContext(), v.getContext().getString(R.string.notyet_done), Toast.LENGTH_SHORT).show();

                    } else if(cardView.getAlpha() == 1f) {
                        repo = new Repo(v.getContext());
                        try {
                            StatusInOutletEntity statusInOutlet = repo.getStatusInOutletDAO().getConfigStatusInOutletHome(
                                    Long.valueOf(routeScheduleDetailId.getText().toString()));

                            switch (stepCode.getText().toString()) {
                                // IN OUTLET
                                case ScreenContants.HOME_STEP_INOUTLET_CHECKIN:
                                    OutletEntity outletEntity = repo.getOutletDAO().findById(Long.valueOf(outletId.getText().toString()));
                                    if(outletEntity.getLat() != null && outletEntity.getLg() != null) {
                                        Intent mapsIntent = new Intent(v.getContext(), MapsActivity.class);
                                        mapsIntent.putExtra(ScreenContants.KEY_OUTLET_ID, Long.valueOf(outletId.getText().toString()));
                                        mapsIntent.putExtra(ScreenContants.KEY_ROUTESCHEDULE_DETAIL
                                                , Long.valueOf(routeScheduleDetailId.getText().toString()));
                                        v.getContext().startActivity(mapsIntent);
                                    } else {
                                        showMapsDialog(v);
                                    }

                                    break;
                                case ScreenContants.HOME_STEP_INOUTLET_CHUPANHOVERVIEW:
                                    Intent overviewIntent = new Intent(v.getContext(), CaptureOverviewActivity.class);
                                    overviewIntent.putExtra(ScreenContants.KEY_OUTLET_ID, Long.valueOf(outletId.getText().toString()));
                                    overviewIntent.putExtra(ScreenContants.KEY_ROUTESCHEDULE_DETAIL
                                            , Long.valueOf(routeScheduleDetailId.getText().toString()));
                                    overviewIntent.putExtra(ScreenContants.KEY_TAKE_PICTURE_ACTION, Boolean.TRUE);
                                    v.getContext().startActivity(overviewIntent);
                                    break;
                                case ScreenContants.HOME_STEP_INOUTLET_GHINHANKHIEUNAI:
                                    // todo
                                    break;
                                case ScreenContants.HOME_STEP_INOUTLET_HUTHANGDATHANG:
                                    Intent orderIntent = new Intent(v.getContext(), OrderActivity.class);
                                    orderIntent.putExtra(ScreenContants.KEY_OUTLET_ID, Long.valueOf(outletId.getText().toString()));
                                    orderIntent.putExtra(ScreenContants.KEY_ROUTESCHEDULE_DETAIL
                                            , Long.valueOf(routeScheduleDetailId.getText().toString()));
                                    v.getContext().startActivity(orderIntent);
                                    break;
                                case ScreenContants.HOME_STEP_INOUTLET_KHAOSATDICHVUKHACHHANG:
                                    Intent intent = new Intent(v.getContext(), SurveyActivity.class);
                                    intent.putExtra(ScreenContants.KEY_OUTLET_ID, Long.valueOf(outletId.getText().toString()));
                                    intent.putExtra(ScreenContants.KEY_ROUTESCHEDULE_DETAIL
                                            , Long.valueOf(routeScheduleDetailId.getText().toString()));
                                    v.getContext().startActivity(intent);
                                    break;
                                case ScreenContants.HOME_STEP_INOUTLET_KHAOSATPOSM:
                                    // todo
                                    break;
                                case ScreenContants.HOME_STEP_INOUTLET_KHAOSATTRUNGBAYSAU:
                                    Intent afterIntent = new Intent(v.getContext(), AfterDisplayActivity.class);
                                    afterIntent.putExtra(ScreenContants.KEY_OUTLET_ID, Long.valueOf(outletId.getText().toString()));
                                    afterIntent.putExtra(ScreenContants.KEY_ROUTESCHEDULE_DETAIL
                                            , Long.valueOf(routeScheduleDetailId.getText().toString()));
                                    v.getContext().startActivity(afterIntent);
                                    break;
                                case ScreenContants.HOME_STEP_INOUTLET_KHAOSATTRUNGBAYTRUOC:
                                    Intent beforeIntent = new Intent(v.getContext(), BeforeDisplayActivity.class);
                                    beforeIntent.putExtra(ScreenContants.KEY_ROUTESCHEDULE_DETAIL
                                            , Long.valueOf(routeScheduleDetailId.getText().toString()));
                                    beforeIntent.putExtra(ScreenContants.KEY_OUTLET_ID, Long.valueOf(outletId.getText().toString()));
                                    v.getContext().startActivity(beforeIntent);
                                    break;
                                case ScreenContants.HOME_STEP_INOUTLET_XEMTHONGTINDANGKYVALICHSUEIE:
                                    Intent historyIntent = new Intent(v.getContext(), RegisterHistoryActivity.class);
                                    historyIntent.putExtra(ScreenContants.KEY_OUTLET_ID, Long.valueOf(outletId.getText().toString()));
                                    v.getContext().startActivity(historyIntent);
                                    break;
                                case ScreenContants.HOME_STEP_INOUTLET_DONGBOCUAHANG:
                                    progressDialog = new ProgressDialog(itemView.getContext());
                                    startUpdate(itemView, Long.valueOf(outletId.getText().toString()));
                                    break;
                                case ScreenContants.HOME_STEP_INOUTLET_TINHTRANGCUAHANG:
                                    Intent statusIntent = new Intent(v.getContext(), StatusOutletActivity.class);
                                    statusIntent.putExtra(ScreenContants.KEY_ROUTESCHEDULE_DETAIL
                                            , Long.valueOf(routeScheduleDetailId.getText().toString()));
                                    statusIntent.putExtra(ScreenContants.KEY_OUTLET_ID, Long.valueOf(outletId.getText().toString()));
                                    v.getContext().startActivity(statusIntent);
                                    break;

                                default:
                                    // todo
                                    break;
                            }
                        } catch (SQLException e) {
                            ELog.d("Message log :can find config In Outlet activity");
                        }
                    }

                }
            });
        }

        private void showMapsDialog(View v) {
            AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());

            builder.setMessage(v.getContext().getString(R.string.dialog_map_content));

            String positiveText = v.getContext().getString(R.string.update);
            builder.setPositiveButton(positiveText, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent mapsIntent = new Intent(itemView.getContext(), MapsActivity.class);
                    mapsIntent.putExtra(ScreenContants.KEY_OUTLET_ID, Long.valueOf(outletId.getText().toString()));
                    mapsIntent.putExtra(ScreenContants.KEY_ROUTESCHEDULE_DETAIL
                            , Long.valueOf(routeScheduleDetailId.getText().toString()));
                    mapsIntent.putExtra(ScreenContants.KEY_GPS_UPDATE, 1l);
                    itemView.getContext().startActivity(mapsIntent);
                }
            });


            AlertDialog dialog = builder.create();
            dialog.show();

        }
    }

    private class UpdatingTask extends AsyncTask<String, Void, String> {
        private Context context;
        private Long outletId;
        private String errorMessage = null;

        public UpdatingTask(Context context, Long outletId) {
            this.context = context;
            this.outletId = outletId;
        }

        protected void onPreExecute() {
            progressDialog.setMessage(context.getText(R.string.updating));
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(final String result) {
            if (result != null && StringUtils.isNotBlank(result)) {
                dismissProgressDialog();
                Toast.makeText(context, context.getText(R.string.update_successful), Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(context, context.getText(R.string.update_failed), Toast.LENGTH_LONG).show();
            }

        }

        protected String doInBackground(final String... args) {
            String result = null;
            try {
                SyncOutletMerResultService syncOutletMerResultService = new SyncOutletMerResultService(context, outletId);
                Integer success = syncOutletMerResultService.syncOneOuletService(outletId);
                if(success == 1){
                    result = "Success";
                } else {
                    errorMessage = "Fail";
                }
            }catch (Exception e) {
                Log.e("InOutletActivity", e.getMessage(), e);
            }
            return result;
        }
    }

    private void dismissProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
    private void startUpdatingTask(View v, Long outletId) {
        updateTask = new UpdatingTask(v.getContext(), outletId);
        updateTask.execute();
    }

    public void startUpdate(View v, Long outletId) {
        startUpdatingTask(v, outletId);
    }
}
