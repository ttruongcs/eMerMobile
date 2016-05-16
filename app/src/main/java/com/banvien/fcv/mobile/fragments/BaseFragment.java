package com.banvien.fcv.mobile.fragments;

import android.content.Context;
import android.os.Handler;
import android.support.v4.app.Fragment;

import com.banvien.fcv.mobile.MainActivity;

/**
 * Created by hieu on 4/11/2016.
 */
public class BaseFragment extends Fragment {
    protected MainActivity parent;
    protected Handler mHandler = new Handler();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        parent = (MainActivity)getActivity();
    }

    public MainActivity getParentActivity() {
        return parent;
    }

}
