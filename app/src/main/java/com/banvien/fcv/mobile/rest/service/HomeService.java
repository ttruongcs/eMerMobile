package com.banvien.fcv.mobile.rest.service;

import com.banvien.fcv.mobile.command.OutletMerResultCommand;
import com.banvien.fcv.mobile.dto.getfromserver.MConfirmWorkingImageCommand;
import com.banvien.fcv.mobile.dto.getfromserver.MOutletMerResultImageDTO;
import com.banvien.fcv.mobile.dto.getfromserver.MSurveyDTO;
import com.banvien.fcv.mobile.dto.getfromserver.MSurveyResultDTO;
import com.banvien.fcv.mobile.dto.syncdto.MOutletMerResultDTO;

import java.sql.Timestamp;
import java.util.List;
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

    @POST("/mobile/working/submitFirstOutletImages.html")
    Call<Integer> submitOutletResultToServerA(@Query("merchandiserId") Long merchandiserId,
                                             @Query("outletId") Long outletId,
                                             @Query("images") String[] images);

    @POST("/mobile/working/submitFirstOutletResult.html")
    Call<Integer> submitFirstOutletResult(@Body MOutletMerResultDTO outletMerResult);

    @POST("/mobile/working/submitMerResultImage.html")
    Call<Integer> submitOutletResultImageToServer(@Body List<MOutletMerResultImageDTO> mOutletMerResultImages);

    @Multipart
    @POST ("/mobile/image/sync.html")
    Call<ResponseBody> upload(@Part("fileToUpload\"; filename=\"image.jpg\";") RequestBody file2);


    @Multipart
    @POST ("/mobile/working/syncImage.html")
    Call<ResponseBody> uploadBeginImageDay(@Query("imageName") String imageName, @Query("path") String path
            , @Part( "fileToUpload\"; filename=\"image.jpg\";") RequestBody file2);

    @POST ("/mobile/working/confirmBeginningTheDay.html")
    Call<Long> uploadBeginDay(@Query("routeScheduleId") Long routeScheduleId,
                                      @Query("startTime") Timestamp startTime,
                                      @Query("confirmWoringId") Long confirmWoringId,
                                      @Query("endTime") Timestamp endTime,
                                      @Body MConfirmWorkingImageCommand file2);
}
