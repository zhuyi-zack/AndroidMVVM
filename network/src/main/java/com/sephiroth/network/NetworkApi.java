package com.sephiroth.network;

import android.nfc.Tag;
import android.util.Log;

import com.google.gson.Gson;
import com.sephiroth.network.bean.GirlPageBean;
import com.sephiroth.network.interceptor.CommonRequestInterceptor;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkApi {
    private static final String TAG = NetworkApi.class.getSimpleName();
    private static final String BASE_URL = "https://gank.io/api/v2/";

    public static void getGirlList() {
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl(BASE_URL);
        builder.addConverterFactory(GsonConverterFactory.create());
        builder.addCallAdapterFactory(RxJava2CallAdapterFactory.create());
        builder.client(getOkHttpClient());
        Retrofit retrofit = builder.build();
        ApiService apiService = retrofit.create(ApiService.class);
        apiService.getGirl().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<GirlPageBean>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(GirlPageBean girlPageBean) {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    private static OkHttpClient getOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.addInterceptor(httpLoggingInterceptor);
        builder.addInterceptor(new CommonRequestInterceptor());
        OkHttpClient okHttpClient = builder.build();
        return okHttpClient;
    }

    public <T> ObservableTransformer<T, T> getObservableTransformer() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                upstream.subscribeOn(Schedulers.io());
                upstream.observeOn(AndroidSchedulers.mainThread());
                return upstream;
            }
        };
    }
}
