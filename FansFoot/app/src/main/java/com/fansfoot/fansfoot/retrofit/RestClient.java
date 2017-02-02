package com.fansfoot.fansfoot.retrofit;

import com.fansfoot.fansfoot.API.ConstServer;
import com.squareup.okhttp.OkHttpClient;

import java.util.concurrent.TimeUnit;

import retrofit.RestAdapter;
import retrofit.client.OkClient;

/**
 * Created by xamarin on 11/01/17.
 */

public class RestClient {
    private static ApiService apiService = null;
    public static ApiService getApiService() {
        if (apiService == null) {
            OkHttpClient mOkHttpClient = new OkHttpClient();
            mOkHttpClient.setConnectTimeout(20, TimeUnit.SECONDS);
            mOkHttpClient.setReadTimeout(20, TimeUnit.SECONDS);
            // For object response which is default

            //------
            RestAdapter restAdapter = null;
            try {
                restAdapter = new RestAdapter.Builder()
                        .setEndpoint(ConstServer._MainbaseUrls)
                        .setClient(new OkClient(mOkHttpClient))
                        .setLogLevel(RestAdapter.LogLevel.FULL)
                        .build();
            }catch (Exception e){
                e.printStackTrace();
            }

            apiService = restAdapter.create(ApiService.class);
        }
        return apiService;
    }
}
