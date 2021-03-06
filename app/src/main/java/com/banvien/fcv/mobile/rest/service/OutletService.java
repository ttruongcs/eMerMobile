package com.banvien.fcv.mobile.rest.service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Linh Nguyen on 6/25/2016.
 */
public interface OutletService {
    @POST("/mobile/route/searchOutlet.html")
    Call<Map<String, Object>> searchOutlet(@Query("keyword") String keyword,
                                   @Query("merchandiserId") Long merchandiserId,
                                   @Query("routeScheduleId")Long routeScheduleId,
                                   @Query("regionId") Long regionId,
                                   @Query("distributorId") Long distributorId,
                                   @Query("dateCreated") Timestamp createdDate);

    @POST("/mobile/route/addRoute.html")
    Call<Map<String, Object>> addRoute(@Query("scheduleDetailId")Long scheduleDetailId,
                                       @Query("deleteCurrentTask")Integer deleteCurrentTask);

    @POST("/mobile/working/updateLocation.html")
    Call<Integer> updateLocation(@Query("outletId")Long outletId,
                                  @Query("lat")BigDecimal lat,
                                  @Query("lng")BigDecimal lng);
}
