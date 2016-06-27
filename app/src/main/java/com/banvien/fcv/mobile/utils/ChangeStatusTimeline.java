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

    public ChangeStatusTimeline(Context context) {
        repo = new Repo(context);
    }

    public boolean changeStatusToDoing(String timeline, boolean isTotalDone) {


        return false;
    }

    public boolean changeStatusToDone(String parent, String now, String[] next, String parentNext, boolean isTotalDone) {
        boolean isSuccess = false;
        try {
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
        } catch (SQLException e) {
            ELog.d(e.getMessage(), e);
        }

        return isSuccess;
    }

}
