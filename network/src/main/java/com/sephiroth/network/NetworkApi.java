package com.sephiroth.network;

import android.nfc.Tag;
import android.util.Log;

import com.sephiroth.network.bean.GirlPageBean;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkApi {
    private static final String TAG = NetworkApi.class.getSimpleName();
    private static final String BASE_URL = "https://gank.io/api/v2/";
    private String url = "data/category/Girl/type/Girl/page/1/count/10";
    public static void getGirlList() {
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl(BASE_URL);
        builder.addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        ApiService apiService = retrofit.create(ApiService.class);
        Call<GirlPageBean> girl = apiService.getGirl();
        girl.enqueue(new Callback<GirlPageBean>() {
            @Override
            public void onResponse(Call<GirlPageBean> call, Response<GirlPageBean> response) {
                Log.d(TAG, response.body().toString());
            }

            @Override
            public void onFailure(Call<GirlPageBean> call, Throwable t) {

            }
        });
    }
}
