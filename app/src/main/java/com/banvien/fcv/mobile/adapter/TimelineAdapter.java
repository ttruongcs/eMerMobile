package com.banvien.fcv.mobile.adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.banvien.fcv.mobile.CaptureToolActivity;
import com.banvien.fcv.mobile.CaptureUniformActivity;
import com.banvien.fcv.mobile.CoverageInfoActivity;
import com.banvien.fcv.mobile.EndDayActivity;
import com.banvien.fcv.mobile.HomeActivity;
import com.banvien.fcv.mobile.InOutletHomeActivity;
import com.banvien.fcv.mobile.PrepareActivity;
import com.banvien.fcv.mobile.R;
import com.banvien.fcv.mobile.ScreenContants;
import com.banvien.fcv.mobile.StartDayActivity;
import com.banvien.fcv.mobile.db.entities.CaptureUniformEntity;
import com.banvien.fcv.mobile.dto.TimelineDTO;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Linh Nguyen on 6/14/2016.
 */
public class TimelineAdapter extends RecyclerView.Adapter {
    public static final double PIC_RATIO_VALUE = 4.0;

    List<TimelineDTO> mData;
    Activity activity;

    public TimelineAdapter(List<TimelineDTO> data, Activity activity) {
        this.mData = data;
        this.activity = activity;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.timeline_item, parent, false);
        ItemHolder itemHolder = new ItemHolder(v);

        return itemHolder;
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

        if(position == 0) {
            itemHolder.viewTop.getLayoutParams().width = 0;
        } else if(position > 0) {
            if(mData.get(position).getIsDone() == 1 || mData.get(position).getIsDone() == 2 ) {
                itemHolder.cardView.setAlpha(1f);
                itemHolder.tvOrder.setBackgroundResource(R.drawable.bg_circle);
                itemHolder.viewTop.setBackgroundResource(R.color.color_blog);
                itemHolder.viewBottom.setBackgroundResource(R.color.color_blog);
                itemHolder.arrow.setVisibility(View.VISIBLE);

            } else {
                itemHolder.cardView.setAlpha(0.3f);
                itemHolder.tvOrder.setBackgroundResource(R.drawable.bg_circle_not_done);
                itemHolder.viewTop.setBackgroundResource(R.color.red_500);
                itemHolder.viewBottom.setBackgroundResource(R.color.red_500);
                itemHolder.arrow.setVisibility(View.GONE);
            }
            if(position == mData.size() - 1) {
                itemHolder.viewBottom.getLayoutParams().width = 0;
            }
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

    public class ItemHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.tvTimeline)
        TextView tvTimeline;

        @Bind(R.id.tvOrder)
        TextView tvOrder;

        @Bind(R.id.rlv2)
        RelativeLayout arrow;

        @Bind(R.id.view2)
        View viewTop;

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
                            // todo
                            break;
                        case ScreenContants.HOME_STEP_STARTDAY_CHUPHINHDONGPHUC:
                            Intent uniformIntent = new Intent(v.getContext(), CaptureUniformActivity.class);
                            v.getContext().startActivity(uniformIntent);
                            break;
                        case ScreenContants.HOME_STEP_STARTDAY_THEMCUAHANGNEUMUON:
                            // todo
                            break;
                        case ScreenContants.HOME_STEP_STARTDAY_DONGBODULIEUPHANCONG :
                            Intent prepareIntent = new Intent(v.getContext(), PrepareActivity.class);
                            v.getContext().startActivity(prepareIntent);
                            break;
                        case ScreenContants.HOME_STEP_STARTDAY_XACNHANLAMVIEC:
                            // todo
                            break;


                        // IN OUTLET
                        case ScreenContants.HOME_STEP_INOUTLET_CHECKIN:
                            // todo
                            break;
                        case ScreenContants.HOME_STEP_INOUTLET_CHUPANHOVERVIEW :
                            // todo
                            break;
                        case ScreenContants.HOME_STEP_INOUTLET_GHINHANKHIEUNAI:
                            // todo
                            break;
                        case ScreenContants.HOME_STEP_INOUTLET_HUTHANGDATHANG:
                            // todo
                            break;
                        case ScreenContants.HOME_STEP_INOUTLET_KHAOSATDICHVUKHACHHANG :
                            // todo
                            break;
                        case ScreenContants.HOME_STEP_INOUTLET_KHAOSATPOSM:
                            // todo
                            break;
                        case ScreenContants.HOME_STEP_INOUTLET_KHAOSATTRUNGBAYSAU:
                            // todo
                            break;
                        case ScreenContants.HOME_STEP_INOUTLET_KHAOSATTRUNGBAYTRUOC:
                            // todo
                            break;
                        case ScreenContants.HOME_STEP_INOUTLET_XEMTHONGTINDANGKYVALICHSUEIE:
                            // todo
                            break;



                        // END DAY
                        case ScreenContants.HOME_STEP_ENDDAY_CHUPHINHCUOINGAY :
                            // todo
                            break;
                        case ScreenContants.HOME_STEP_ENDDAY_DONGBOKETQUA :
                            // todo
                            break;

                        default:
                            // todo
                            break;
                    }
                }
            });
        }
    }
}
