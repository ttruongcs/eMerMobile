package com.banvien.fcv.mobile.adapter;

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

import com.banvien.fcv.mobile.HomeActivity;
import com.banvien.fcv.mobile.R;
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
    HomeActivity activity;

    public TimelineAdapter(List<TimelineDTO> data, HomeActivity homeActivity) {
        this.mData = data;
        this.activity = homeActivity;
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

    public int containerHeight(HomeActivity activity) {
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

        public ItemHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
