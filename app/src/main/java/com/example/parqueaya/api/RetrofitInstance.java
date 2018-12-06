package com.example.parqueaya.api;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {
    //    public final ParkingApi api;
    private static final String BASE_URL_LOCAL_CELL = "http://192.168.0.7:3000/";
    private static final String BASE_URL_LOCAL_EMULADOR = "http://10.0.2.2:3000/";
    private static final String BASE_URL_AZURE = "http://sistemaparqueo.azurewebsites.net/api/";

    //    public RetrofitInstance() {
    //        Retrofit retrofit = new Retrofit.Builder()
    //            .baseUrl(BASE_URL_AZURE)
    //            .addConverterFactory(GsonConverterFactory.create())
    //            .build();
    //
    //        api = retrofit.create(ParkingApi.class);
    //    }

    private static Retrofit.Builder builder =
        new Retrofit.Builder()
            .baseUrl(BASE_URL_AZURE)
            .addConverterFactory(GsonConverterFactory.create());

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    private static Retrofit retrofit = builder.client(httpClient.build()).build();

    public static <S> S createService(Class<S> serviceClass) {
        return retrofit.create(serviceClass);
    }
}
