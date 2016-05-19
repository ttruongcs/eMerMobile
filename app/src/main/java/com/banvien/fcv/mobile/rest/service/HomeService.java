package com.banvien.fcv.mobile.rest.service;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by hieu on 3/10/2016.
 */
public interface HomeService {
    @POST("/mobile/metadata/getdata.html")
    Call<Map<String, Object>> getRoute(@Query("userId") Long smId,
                                       @Query("day") Integer day,
                                       @Query("month") Integer month,
                                       @Query("year") Integer year);
}
