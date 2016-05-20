package com.banvien.fcv.mobile.fragments;

import android.content.Context;
import android.os.Handler;
import android.support.v4.app.Fragment;

import com.banvien.fcv.mobile.MainActivity;
import com.banvien.fcv.mobile.db.Repo;

/**
 * Created by hieu on 4/11/2016.
 */
public class BaseFragment extends Fragment {
    protected MainActivity parent;
    protected Repo repo;
    protected Handler mHandler = new Handler();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        parent = (MainActivity)getActivity();
        repo = new Repo(parent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (repo != null) {
            repo.release();
        }
    }

    public MainActivity getParentActivity() {
        return parent;
    }

}
