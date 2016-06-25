package com.banvien.fcv.mobile.rest.service;

import com.banvien.fcv.mobile.command.OutletMerResultCommand;

import java.sql.Timestamp;
import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
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


    @POST("/mobile/route/getRouteByMerchandisers.html")
    Call<Map<String, Object>> getDataInNewDays(@Query("merchandiserId") Long merchandiserId,
                                       @Query("date") Timestamp date);

    @POST("/mobile/outletregister/sync.html")
    Call<OutletMerResultCommand> syncDataToServer(@Body OutletMerResultCommand command);


    @Multipart
    @POST ("/mobile/image/sync.html")
    Call<ResponseBody> upload(@Part("fileToUpload2\"; filename=\"image1.jpg\";") RequestBody file2);
}
