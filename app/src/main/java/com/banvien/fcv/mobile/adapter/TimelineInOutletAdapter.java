package com.banvien.fcv.mobile.adapter;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.banvien.fcv.mobile.CaptureFirstOutletActivity;
import com.banvien.fcv.mobile.CaptureToolActivity;
import com.banvien.fcv.mobile.CaptureUniformActivity;
import com.banvien.fcv.mobile.CoverageInfoActivity;
import com.banvien.fcv.mobile.EndDayActivity;
import com.banvien.fcv.mobile.MapsActivity;
import com.banvien.fcv.mobile.PrepareActivity;
import com.banvien.fcv.mobile.R;
import com.banvien.fcv.mobile.ScreenContants;
import com.banvien.fcv.mobile.StartDayActivity;
import com.banvien.fcv.mobile.db.Repo;
import com.banvien.fcv.mobile.db.entities.StatusEndDayEntity;
import com.banvien.fcv.mobile.db.entities.StatusHomeEntity;
import com.banvien.fcv.mobile.db.entities.StatusInOutletEntity;
import com.banvien.fcv.mobile.db.entities.StatusStartDayEntity;
import com.banvien.fcv.mobile.dto.OutletDTO;
import com.banvien.fcv.mobile.dto.TimelineDTO;
import com.banvien.fcv.mobile.dto.TimelineInOutletDTO;
import com.banvien.fcv.mobile.utils.ELog;

import java.sql.SQLException;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Linh Nguyen on 6/14/2016.
 */
public class TimelineInOutletAdapter extends RecyclerView.Adapter {
    public static final double PIC_RATIO_VALUE = 4.0;

    Repo repo;
    List<TimelineInOutletDTO> mData;
    Activity activity;

    public TimelineInOutletAdapter(List<TimelineInOutletDTO> data, Activity activity) {
        this.mData = data;
        this.activity = activity;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.timeline_item_inoutlet, parent, false);
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
        itemHolder.outletId.setText(mData.get(position).getOutletId().toString());

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

        @Bind(R.id.outletId)
        TextView outletId;

        public ItemHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    repo = new Repo(v.getContext());
                    try {
                        StatusInOutletEntity statusInOutlet = repo.getStatusInOutletDAO().getConfigStatusInOutletHome();

                        switch (stepCode.getText().toString()) {
                            // IN OUTLET
                            case ScreenContants.HOME_STEP_INOUTLET_CHECKIN:
                                Intent mapsIntent = new Intent(v.getContext(), MapsActivity.class);
                                mapsIntent.putExtra(ScreenContants.KEY_OUTLET_ID, Long.valueOf(outletId.getText().toString()));
                                v.getContext().startActivity(mapsIntent);
                                break;
                            case ScreenContants.HOME_STEP_INOUTLET_CHUPANHOVERVIEW:
                                // todo
                                break;
                            case ScreenContants.HOME_STEP_INOUTLET_GHINHANKHIEUNAI:
                                // todo
                                break;
                            case ScreenContants.HOME_STEP_INOUTLET_HUTHANGDATHANG:
                                // todo
                                break;
                            case ScreenContants.HOME_STEP_INOUTLET_KHAOSATDICHVUKHACHHANG:
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

                            default:
                                // todo
                                break;
                        }
                    } catch(SQLException e){
                        ELog.d("Message log :can find config In Outlet activity");
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
    }
}
