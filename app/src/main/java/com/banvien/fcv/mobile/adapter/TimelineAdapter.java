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

import com.banvien.fcv.mobile.CaptureEndDayActivity;
import com.banvien.fcv.mobile.CaptureToolActivity;
import com.banvien.fcv.mobile.CaptureUniformActivity;
import com.banvien.fcv.mobile.ConfirmWorkingActivity;
import com.banvien.fcv.mobile.CoverageInfoActivity;
import com.banvien.fcv.mobile.EndDayActivity;
import com.banvien.fcv.mobile.FindOutletActivity;
import com.banvien.fcv.mobile.FindOutletSimpleActivity;
import com.banvien.fcv.mobile.OutletTabActivity;
import com.banvien.fcv.mobile.HomeActivity;
import com.banvien.fcv.mobile.PrepareActivity;
import com.banvien.fcv.mobile.R;
import com.banvien.fcv.mobile.ScreenContants;
import com.banvien.fcv.mobile.StartDayActivity;
import com.banvien.fcv.mobile.SyncEndActivity;
import com.banvien.fcv.mobile.db.Repo;
import com.banvien.fcv.mobile.db.entities.OutletEntity;
import com.banvien.fcv.mobile.dto.TimelineDTO;
import com.banvien.fcv.mobile.library.SyncService;
import com.banvien.fcv.mobile.utils.ChangeStatusTimeline;
import com.banvien.fcv.mobile.utils.ELog;
import com.banvien.fcv.mobile.utils.StringUtils;

import java.sql.SQLException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Linh Nguyen on 6/14/2016.
 */
public class TimelineAdapter extends RecyclerView.Adapter {
    public static final double PIC_RATIO_VALUE = 4.0;
    private final int HEADER_ITEM = 0;
    private final int NORMAL_ITEM = 1;
    private final int FOOTER_ITEM = 2;

    List<TimelineDTO> mData;
    Activity activity;
    private static ProgressDialog progressDialog;
    private static UpdatingTask updateTask = null;
    Repo repo;

    public TimelineAdapter(List<TimelineDTO> data, Activity activity, Repo repo) {
        this.mData = data;
        this.activity = activity;
        this.repo = repo;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == 0) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.timeline_layout_header_item, parent, false);
            ItemHolder itemHolder = new ItemHolder(v);

            return itemHolder;
        } else if(viewType == 1) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.timeline_item, parent, false);
            ItemHolder itemHolder = new ItemHolder(v);

            return itemHolder;
        } else  {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.timeline_layout_footer_item, parent, false);
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

        ELog.d("position", String.valueOf(position));

        if (mData.get(position).getIsDone() == 1 || mData.get(position).getIsDone() == 2) {
            if(mData.get(position).getIsDone() == 2) {
                itemHolder.arrow.setVisibility(View.INVISIBLE);
            } else if(mData.get(position).getIsDone() == 1) {
                itemHolder.arrow.setVisibility(View.VISIBLE);
            }
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


        } else {
            itemHolder.cardView.setAlpha(0.3f);
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

    @Override
    public int getItemViewType(int position) {
        return (mData.get(position).isHeader() ? 0 : (mData.get(position).isFooter() ? 2 : 1 ));
    }

    public int containerHeight(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);

        //get predefined value
        double ratio = PIC_RATIO_VALUE;

        return (int) (dm.heightPixels / ratio);
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

        public ItemHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(cardView.getAlpha() == 0.3f) {
                        Toast.makeText(v.getContext(), v.getContext().getString(R.string.notyet_done), Toast.LENGTH_SHORT).show();

                    } else if(cardView.getAlpha() == 1f) {
                        try {

                            switch (stepCode.getText().toString()) {
                                // HOME
                                case ScreenContants.HOME_STEP_STARTDAY :
                                    Intent startDayIntent = new Intent(v.getContext(), StartDayActivity.class);
                                    v.getContext().startActivity(startDayIntent);
                                    break;
                                case ScreenContants.HOME_STEP_INOUTLET :
                                    Intent outletTabActivity = new Intent(v.getContext(), OutletTabActivity.class);
                                    v.getContext().startActivity(outletTabActivity);
                                    break;
                                case ScreenContants.HOME_STEP_ENDDAY:
                                    Intent endDayIntent = new Intent(v.getContext(), EndDayActivity.class);
                                    v.getContext().startActivity(endDayIntent);
                                    break;

                                // START DAY
                                case ScreenContants.HOME_STEP_STARTDAY_CHUPHINHCONGCUDUNGCU :
                                    Intent toolIntent = new Intent(v.getContext(), CaptureToolActivity.class);
                                    toolIntent.putExtra(ScreenContants.KEY_TAKE_PICTURE_ACTION, Boolean.TRUE);
                                    v.getContext().startActivity(toolIntent);
                                    break;
                                case ScreenContants.HOME_STEP_STARTDAY_CHUPHINHCUAHANGDAUTIEN :
//                                    try {
//                                        ((StartDayActivity)activity).showAlertBox(v);
//                                    } catch (SQLException e) {
//                                        ELog.d("Error when go to choice first outlet");
//                                    }
                                    break;
                                case ScreenContants.HOME_STEP_STARTDAY_CHUPHINHDONGPHUC:
                                    Intent uniformIntent = new Intent(v.getContext(), CaptureUniformActivity.class);
                                    uniformIntent.putExtra(ScreenContants.KEY_TAKE_PICTURE_ACTION, Boolean.TRUE);
                                    v.getContext().startActivity(uniformIntent);
                                    break;
                                case ScreenContants.HOME_STEP_STARTDAY_THEMCUAHANGNEUMUON:
                                    Intent addOutletIntent = new Intent(v.getContext(), FindOutletActivity.class);
                                    v.getContext().startActivity(addOutletIntent);
                                    break;
                                case ScreenContants.HOME_STEP_STARTDAY_DONGBODULIEUPHANCONG :
                                    Intent prepareIntent = new Intent(v.getContext(), PrepareActivity.class);
                                    v.getContext().startActivity(prepareIntent);
                                    break;
                                case ScreenContants.HOME_STEP_STARTDAY_XACNHANLAMVIEC:
                                    showConfirmDialog();
                                    break;


                                // END DAY
                                case ScreenContants.HOME_STEP_ENDDAY_CHUPHINHCUOINGAY :
                                    Intent captureEndDay = new Intent(v.getContext(), CaptureEndDayActivity.class);
                                    captureEndDay.putExtra(ScreenContants.KEY_TAKE_PICTURE_ACTION, Boolean.TRUE);
                                    v.getContext().startActivity(captureEndDay);
                                    break;
                                case ScreenContants.HOME_STEP_ENDDAY_DONGBOKETQUA :
                                    Intent syncEndDay = new Intent(v.getContext(), SyncEndActivity.class);
                                    v.getContext().startActivity(syncEndDay);
                                    break;

                                case ScreenContants.HOME_STEP_ENĐAY_KETTHUCCUOINGAY:
                                    showConfirmEndDialog();
                                    break;

                                default:
                                    // todo
                                    break;
                            }
                        } catch (Exception e) {
                            ELog.e("Message log :can find config home activity", e);
                        }
                    }

                }
            });
        }

        private void showConfirmEndDialog() {
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);

            builder.setTitle(activity.getString(R.string.dialog_confirm_end_title));
            builder.setMessage(activity.getString(R.string.dialog_confirm_end_content));

            String positiveText = activity.getString(R.string.accept);
            builder.setPositiveButton(positiveText, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                        progressDialog = new ProgressDialog(itemView.getContext());
                        updateTask = new UpdatingTask(itemView.getContext());
                        updateTask.execute();
                        ChangeStatusTimeline changeStatusTimeline = new ChangeStatusTimeline(repo);
                        changeStatusTimeline.changeStatusToDone(ScreenContants.END_DATE_COLUMN
                                , ScreenContants.CONFIRM_END_COLUMN, null, ScreenContants.END_DATE_COLUMN, true);
                }
            });

            String negativeText = activity.getString(R.string.cancel);
            builder.setNegativeButton(negativeText, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();
        }


        private void showConfirmDialog() {
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);

            builder.setTitle(activity.getString(R.string.dialog_confirm_working_title));
            builder.setMessage(activity.getString(R.string.dialog_confirm_working));

            String positiveText = activity.getString(R.string.accept);
            builder.setPositiveButton(positiveText, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(itemView.getContext(), ConfirmWorkingActivity.class);
                    intent.putExtra(ScreenContants.KEY_TAKE_PICTURE_ACTION, Boolean.TRUE);
                    itemView.getContext().startActivity(intent);
                }
            });

            String negativeText = activity.getString(R.string.xacnhankhongchupanh);
            builder.setNegativeButton(negativeText, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    try {
                        try {
                            SyncService syncService = new SyncService(activity, repo);
                            syncService.synConfirmNewDayInformationDontHaveImage();
                        } catch (SQLException e) {
                            ELog.d("Error when Sync Comfirm Working");
                        }
                        SyncService syncService = new SyncService(activity, repo);
                        syncService.synConfirmNewDayInformationDontHaveImage();
                        ChangeStatusTimeline changeStatusTimeline = new ChangeStatusTimeline(repo);
                        String[] parentNext = {ScreenContants.IN_OUTLET, ScreenContants.END_DATE_COLUMN};
                        changeStatusTimeline.changeStatusToDone(ScreenContants.PREPARE_DATE_COLUMN
                                , ScreenContants.CONFIRM_WORKING_COLUMN, null, parentNext, true);
                        Intent intent = new Intent(itemView.getContext(), HomeActivity.class);
                        itemView.getContext().startActivity(intent);
                        //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        activity.startActivity(intent);
                        //activity.finish();

                    } catch (SQLException e) {
                        Log.e("TimelineAdapter", "Sync no image Error");
                    }
                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();

        }
    }

    private class UpdatingTask extends AsyncTask<String, Void, String> {
        private Context context;
        private String errorMessage = null;

        public UpdatingTask(Context context) {
            this.context = context;
        }

        protected void onPreExecute() {
            progressDialog.setMessage(context.getText(R.string.updating));
            progressDialog.setCancelable(false);
            progressDialog.show();
//            new android.os.Handler().postDelayed(
//                    new Runnable() {
//                        public void run() {
//                            dismissProgressDialog();
//                            Log.i("tag", "This'll run 3000 milliseconds later");
//                        }
//                    },
//                    3000);
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
                SyncService syncService = new SyncService(context, repo);
                Long success = syncService.synConfirmEndDayInformation();
                if(success != null){
                    result = "Success";
                } else {
                    errorMessage = "Fail";
                }
            }catch (Exception e) {
                Log.e("EndDayActivity", e.getMessage(), e);
            }
            return result;
        }

        private void dismissProgressDialog() {
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        }
    }
}
