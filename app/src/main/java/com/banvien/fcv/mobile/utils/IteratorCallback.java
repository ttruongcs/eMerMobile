package com.banvien.fcv.mobile.utils;

import com.banvien.fcv.mobile.rest.RestClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Hieu Le on 6/27/2016.
 */
public abstract class IteratorCallback<T> implements Callback<T>{
    protected List iteratedList;
    protected int currentIndex;
    protected boolean isStop;

    public IteratorCallback(List iteratedList, int currentIndex) {
        this.iteratedList = iteratedList;
        this.currentIndex = currentIndex;
    }

    public void onResponse(Call<T> call, Response<T> response) {
        onResponseArrive(call, response);
        if (!isStop && ++currentIndex < iteratedList.size()) {
            Object obj = iteratedList.get(currentIndex);
            getCall(obj).enqueue(this);
        }

    }

    public abstract void onResponseArrive(Call<T> call, Response<T> response);

    public abstract Call getCall(Object object);

}
