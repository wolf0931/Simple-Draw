package com.simplemobiletools.draw.pro.http;

import androidx.annotation.NonNull;

import com.simplemobiletools.draw.pro.models.UserOpen;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;


import io.reactivex.rxjava3.core.Observable;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * CREATE BY bytexero ON 2022/6/20
 */

public class RetrofitApi {

    private static Retrofit retrofit = new Retrofit
            .Builder()
            .client(new OkHttpClient.Builder()
                    .addInterceptor(new Interceptor() {
                        @NonNull
                        @Override
                        public okhttp3.Response intercept(@NonNull Chain chain) throws IOException {
                            Request original = chain.request();
                            Request.Builder requestBuilder = original.newBuilder()
                                    .addHeader("Content-Type", "application/json;charset=UTF-8");
                            Request request = requestBuilder.build();
                            return chain.proceed(request);
                        }
                    })
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .addInterceptor(new HttpLoggingInterceptor()
                            .setLevel(HttpLoggingInterceptor.Level.BODY))
                    .build())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.createSynchronous())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://manager.bytexero.com/api/")
            .build();

    public static ApiService init() {
        return retrofit.create(ApiService.class);
    }

    public interface ApiService {

        @POST("app/v1/user/open")
        Observable<UserOpen> userOpen(@Body Map<String, Object> map);



    }

}


