package com.banvien.fcv.mobile.utils;

import android.content.Context;

import com.banvien.fcv.mobile.ScreenContants;
import com.banvien.fcv.mobile.db.Repo;

import java.sql.SQLException;

/**
 * Created by Linh Nguyen on 6/27/2016.
 */
public class ChangeStatusTimeline {
    private Repo repo;
    private Long routeScheduleDetailId;

    public ChangeStatusTimeline(Context context) {
        repo = new Repo(context);
    }

    public ChangeStatusTimeline(Context context, Long detailId) {
        repo = new Repo(context);
        this.routeScheduleDetailId = detailId;
    }

    public boolean changeStatusToDoing(String timeline, boolean isTotalDone) {


        return false;
    }

    public boolean changeStatusToDone(String parent, String now, String[] next, String parentNext, boolean isTotalDone) {
        boolean isSuccess = false;
        try {
            switch (parent) {
                case ScreenContants.PREPARE_DATE_COLUMN:
                    if (!isTotalDone) {
                        boolean isFinished = repo.getStartDayDAO().updateStatus(now, next);
                        isSuccess = isFinished;
                    } else if (next == null && isTotalDone == true) {
                        boolean isStep1Success = repo.getStartDayDAO().updateStatus(now, next);
                        if (isStep1Success) {
                            boolean isStep2Success = repo.getStatusHomeDAO().updateStatus(parent, parentNext);
                            isSuccess = isStep2Success;
                        }
                    }
                    break;
                case  ScreenContants.IN_OUTLET:
                    if (!isTotalDone) {
                        boolean isFinished = repo.getStatusInOutletDAO().updateStatus(now, next, routeScheduleDetailId);
                        isSuccess = isFinished;
                    } else if (next == null && isTotalDone == true) {
                        boolean isStep1Success = repo.getStatusInOutletDAO().updateStatus(now, next, routeScheduleDetailId);
                        if (isStep1Success) {
                            boolean isStep2Success = repo.getStatusHomeDAO().updateStatus(parent, parentNext);
                            isSuccess = isStep2Success;
                        }
                    }
                    break;
                case ScreenContants.END_DATE_COLUMN:
                    if (!isTotalDone) {
                        boolean isFinished = repo.getStatusEndDayDAO().updateStatus(now, next);
                        isSuccess = isFinished;
                    } else if (next == null && isTotalDone == true) {
                        boolean isStep1Success = repo.getStatusEndDayDAO().updateStatus(now, next);
                        if (isStep1Success) {
                            boolean isStep2Success = repo.getStatusHomeDAO().updateStatus(parent, parentNext);
                            isSuccess = isStep2Success;
                        }
                    }
                    break;
            }

        } catch (SQLException e) {
            ELog.d(e.getMessage(), e);
        }

        return isSuccess;
    }

}
