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

    public ChangeStatusTimeline(Repo repo) {
        this.repo = repo;
    }

    public ChangeStatusTimeline(Repo repo, Long detailId) {
        this.repo = repo;
        this.routeScheduleDetailId = detailId;
    }

    public boolean changeStatusToDoing(String timeline, boolean isTotalDone) {


        return false;
    }

    /*
    * parent: Tên cột trong db của node cha chứa node này
    *         now: node hiện tại cần đổi trạng thái
    *         next: các node sẽ được active trạng thái khi node này done, có thể mở nhiều node 1 lần
    *         parentNext: node cha tiếp theo
    *         isTotalDone: Nếu tất cả node con đã xong, muốn kích hoạt node cha tiếp theo, set trạng thái isTotalDone thành true
    * */
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

    public boolean changeStatusToDone(String parent, String now, String[] next, String parentNext, boolean isTotalDone, boolean hiddenMiddle) {
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
                        boolean isFinished = repo.getStatusInOutletDAO().updateStatus(now, next, routeScheduleDetailId, hiddenMiddle);
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
