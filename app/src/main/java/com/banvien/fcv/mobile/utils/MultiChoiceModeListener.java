package com.banvien.fcv.mobile.utils;

import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;

/**
 * Created by Linh Nguyen on 5/27/2016.
 */
public class MultiChoiceModeListener implements GridView.MultiChoiceModeListener {

    private GridView mGrid;

    public MultiChoiceModeListener(GridView gridView) {
        this.mGrid = gridView;
    }

    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        mode.setTitle("Select Items");
        mode.setSubtitle("One item selected");
        return true;
    }

    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return true;
    }

    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        return true;
    }

    public void onDestroyActionMode(ActionMode mode) {
    }

    public void onItemCheckedStateChanged(ActionMode mode, int position, long id,
                                          boolean checked) {
        int selectCount = mGrid.getCheckedItemCount();
        switch (selectCount) {
            case 1:
                mode.setSubtitle("One item selected");
                break;
            default:
                mode.setSubtitle("" + selectCount + " items selected");
                break;
        }
    }

}
