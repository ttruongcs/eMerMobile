package com.banvien.fcv.mobile.rest.service;

import android.database.Observable;

import com.banvien.fcv.mobile.command.OutletMerResultCommand;
import com.banvien.fcv.mobile.dto.HotzoneDTO;
import com.banvien.fcv.mobile.dto.OutletDTO;
import com.banvien.fcv.mobile.dto.TypeFile;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
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

    @POST("/mobile/outletregister/sync.html")
    Call<OutletMerResultCommand> syncDataToServer(@Body OutletMerResultCommand command);

    @POST("/mobile/example/sync.html")
    Call<HotzoneDTO> example();

    @Multipart
    @POST ("/mobile/image/sync.html")
    Call<ResponseBody> upload(@Part("fileToUpload2\"; filename=\"image1.jpg\";") RequestBody file2);
}
