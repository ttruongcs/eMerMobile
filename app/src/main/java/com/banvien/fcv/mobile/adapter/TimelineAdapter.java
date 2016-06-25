package com.banvien.fcv.mobile.adapter;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.banvien.fcv.mobile.ConfirmWorkingActivity;
import com.banvien.fcv.mobile.FindOutletActivity;
import com.banvien.fcv.mobile.CaptureEndDayActivity;
import com.banvien.fcv.mobile.CaptureFirstOutletActivity;
import com.banvien.fcv.mobile.CaptureToolActivity;
import com.banvien.fcv.mobile.CaptureUniformActivity;
import com.banvien.fcv.mobile.CoverageInfoActivity;
import com.banvien.fcv.mobile.EndDayActivity;
import com.banvien.fcv.mobile.FindOutletSimpleActivity;
import com.banvien.fcv.mobile.PrepareActivity;
import com.banvien.fcv.mobile.R;
import com.banvien.fcv.mobile.ScreenContants;
import com.banvien.fcv.mobile.StartDayActivity;
import com.banvien.fcv.mobile.db.Repo;
import com.banvien.fcv.mobile.db.entities.StatusEndDayEntity;
import com.banvien.fcv.mobile.db.entities.StatusHomeEntity;
import com.banvien.fcv.mobile.db.entities.StatusStartDayEntity;
import com.banvien.fcv.mobile.dto.OutletDTO;
import com.banvien.fcv.mobile.dto.TimelineDTO;
import com.banvien.fcv.mobile.utils.ELog;

import java.sql.SQLException;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Linh Nguyen on 6/14/2016.
 */
public class TimelineAdapter extends RecyclerView.Adapter {
    public static final double PIC_RATIO_VALUE = 4.0;
    private final int HEADER_ITEM = 0;
    private final int NORMAL_ITEM = 1;
    private final int FOOTER_ITEM = 2;

    Repo repo;
    List<TimelineDTO> mData;
    Activity activity;

    public TimelineAdapter(List<TimelineDTO> data, Activity activity) {
        this.mData = data;
        this.activity = activity;

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
        itemHolder.linearLayout.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height));
        itemHolder.tvTimeline.setText(mData.get(position).getTitle());
        itemHolder.tvDetail.setText(mData.get(position).getDetail());
        itemHolder.tvOrder.setText(mData.get(position).getOrder());
        itemHolder.stepCode.setText(mData.get(position).getType());

        ELog.d("position", String.valueOf(position));

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

        @Bind(R.id.tvTimeline)
        TextView tvTimeline;

        @Bind(R.id.tvOrder)
        TextView tvOrder;

        @Bind(R.id.rlv2)
        RelativeLayout arrow;

        @Nullable
        @Bind(R.id.view2)
        View viewTop;

        @Nullable
        @Bind(R.id.view)
        View viewBottom;

        @Bind(R.id.lnTimeline)
        LinearLayout linearLayout;

        @Bind(R.id.tvTimelineDetail)
        TextView tvDetail;

        @Bind(R.id.card_view)
        CardView cardView;

        @Bind(R.id.stepCode)
        TextView stepCode;

        public ItemHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    repo = new Repo(v.getContext());
                    try {
                        StatusHomeEntity statusHome = repo.getStatusHomeDAO().getConfigStatusHome();
                        StatusStartDayEntity statusStartDay = repo.getStartDayDAO().getConfigStartDayHome();
                        StatusEndDayEntity statusEndDay = repo.getStatusEndDayDAO().getConfigStatusEndDayHome();

                        switch (stepCode.getText().toString()) {
                            // HOME
                            case ScreenContants.HOME_STEP_STARTDAY :
                                Intent startDayIntent = new Intent(v.getContext(), StartDayActivity.class);
                                v.getContext().startActivity(startDayIntent);
                                break;
                            case ScreenContants.HOME_STEP_INOUTLET :
                                Intent coverageActivity = new Intent(v.getContext(), CoverageInfoActivity.class);
                                v.getContext().startActivity(coverageActivity);
                                break;
                            case ScreenContants.HOME_STEP_ENDDAY:
                                Intent endDayIntent = new Intent(v.getContext(), EndDayActivity.class);
                                v.getContext().startActivity(endDayIntent);
                                break;

                            // START DAY
                            case ScreenContants.HOME_STEP_STARTDAY_CHUPHINHCONGCUDUNGCU :
                                Intent toolIntent = new Intent(v.getContext(), CaptureToolActivity.class);
                                v.getContext().startActivity(toolIntent);
                                break;
                            case ScreenContants.HOME_STEP_STARTDAY_CHUPHINHCUAHANGDAUTIEN :
                                try {
                                    showAlertBox(v);
                                } catch (SQLException e) {
                                    ELog.d("Error when go to choice first outlet");
                                }
                                break;
                            case ScreenContants.HOME_STEP_STARTDAY_CHUPHINHDONGPHUC:
                                Intent uniformIntent = new Intent(v.getContext(), CaptureUniformActivity.class);
                                v.getContext().startActivity(uniformIntent);
                                break;
                            case ScreenContants.HOME_STEP_STARTDAY_THEMCUAHANGNEUMUON:
                                Intent addOutletIntent = new Intent(v.getContext(), FindOutletSimpleActivity.class);
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
                                v.getContext().startActivity(captureEndDay);
                                break;
                            case ScreenContants.HOME_STEP_ENDDAY_DONGBOKETQUA :
                                // todo
                                break;

                            default:
                                // todo
                                break;
                        }
                    } catch (SQLException e) {
                        ELog.d("Message log :can find config home activity");
                    }
                }
            });
        }

        private void showAlertBox(final View v) throws SQLException {
            android.support.v7.app.AlertDialog.Builder builderSingle = new android.support.v7.app.AlertDialog.Builder(v.getContext());
            builderSingle.setTitle("Chọn một cửa hàng : ");
            List<OutletDTO> outletList = repo.getOutletDAO().findAll();
            final ArrayAdapter<OutletDTO> arrayAdapter = new ArrayAdapter<OutletDTO>(
                    v.getContext(),
                    android.R.layout.select_dialog_singlechoice);
            arrayAdapter.addAll(outletList);

            builderSingle.setNegativeButton(
                    "cancel",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

            builderSingle.setAdapter(
                    arrayAdapter,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            final OutletDTO outletDTO = arrayAdapter.getItem(which);
                            android.support.v7.app.AlertDialog.Builder builderInner = new android.support.v7.app.AlertDialog.Builder(
                                    v.getContext());
                            builderInner.setMessage(outletDTO.getName());
                            builderInner.setTitle("Bạn đã chọn cửa hàng đầu tiên là :");
                            builderInner.setPositiveButton(
                                    "Ok",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(
                                                DialogInterface dialog,
                                                int which) {
                                            Intent firstOutletIntent = new Intent(v.getContext(), CaptureFirstOutletActivity.class);
                                            firstOutletIntent.putExtra(ScreenContants.KEY_OUTLET_ID, outletDTO.getOutletId());
                                            v.getContext().startActivity(firstOutletIntent);
                                            dialog.dismiss();
                                        }
                                    });
                            builderInner.show();
                        }
                    });
            builderSingle.show();
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
                    itemView.getContext().startActivity(intent);
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

    }
}
