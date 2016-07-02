package com.banvien.fcv.mobile.rest;

import com.banvien.fcv.mobile.rest.service.HomeService;
import com.banvien.fcv.mobile.rest.service.OutletService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;


/**
 * Created by hieu on 3/8/2016.
 */
public final class RestClient {
    public static final String API_BASE_URL = "http://192.168.2.24:8080/";
    private static RestClient instance = new RestClient();

    private Retrofit retrofit;
    private HomeService homeService;
    private OkHttpClient httpClient;
    private String authToken, portalId;
    private OutletService outletService;


    private RestClient() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);

        OkHttpClient.Builder httpBuilder = new OkHttpClient.Builder();
        httpBuilder.addInterceptor(logging);
        Interceptor requestInterceptor = new Interceptor() {
            @Override
            public Response intercept(Interceptor.Chain chain) throws IOException {
                Request original = chain.request();

                // Request customization: add request headers
                Request.Builder requestBuilder = original.newBuilder();
                if (authToken != null) {
                    requestBuilder.header("Authorization", authToken);
                }
                String contentType = original.header("Content-Type");
                if (contentType == null) {
                    requestBuilder.header("Content-Type", "application/json")
                            .method(original.method(), original.body());
                }

                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        };
        httpBuilder.addInterceptor(requestInterceptor);

        httpClient = httpBuilder.build();

        retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(JacksonConverterFactory.create(createObjectMapper()))
                .client(httpClient)
                .build();
    }

    public static RestClient getInstance() {
        return instance;
    }

    public OkHttpClient getHttpClient() {
        return httpClient;
    }

    public String getPortalId() {
        return portalId;
    }

    public void setPortalId(String portalId) {
        this.portalId = portalId;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public static String getApiBaseUrl() {
        return API_BASE_URL;
    }

    public <S> S createService(Class<S> serviceClass) {
        return retrofit.create(serviceClass);
    }

    private ObjectMapper createObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        objectMapper.enable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        return objectMapper;
    }

    public static void setInstance(RestClient instance) {
        RestClient.instance = instance;
    }

    public HomeService getHomeService() {
        if (homeService == null) {
            homeService = createService(HomeService.class);
        }
        return homeService;
    }

    public OutletService getOutletService() {
        if (outletService == null) {
            outletService = createService(OutletService.class);
        }
        return outletService;
    }

    public void setHomeService(HomeService homeService) {
        this.homeService = homeService;
    }
}
