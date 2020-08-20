package com.sephiroth.network;

import android.annotation.SuppressLint;

import com.sephiroth.network.exception.ExceptionHandlerFunction;
import com.sephiroth.network.interceptor.CommonRequestInterceptor;

import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public abstract class NetworkApi {
    private String mBaseUrl = "https://gank.io/api/v2/";
    private HashMap<String, Retrofit> mRetrofitHashMap = new HashMap<>();

    public <T> Retrofit getRetrofit(Class<T> service) {
        if (mRetrofitHashMap.get(mBaseUrl + service.getName()) != null) {
            return mRetrofitHashMap.get(mBaseUrl + service.getName());
        }
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl(mBaseUrl);
        builder.addConverterFactory(GsonConverterFactory.create());
        builder.addCallAdapterFactory(RxJava2CallAdapterFactory.create());
        builder.client(getOkHttpClient());
        Retrofit retrofit = builder.build();
        mRetrofitHashMap.put(mBaseUrl + service.getName(), retrofit);
        return retrofit;
    }

    private OkHttpClient getOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (getInterceptor() != null) {
            builder.addInterceptor(getInterceptor());
        }
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.addInterceptor(httpLoggingInterceptor);
        builder.addInterceptor(new CommonRequestInterceptor());
        OkHttpClient okHttpClient = builder.build();
        return okHttpClient;
    }

    public <T> ObservableTransformer<T, T> getObservableTransformer(final Observer<T> observer) {
        return new ObservableTransformer<T, T>() {
            @SuppressLint("CheckResult")
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                Observable<T> observable = upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
                observable.map(getFunction());
                observable.subscribe(observer);
                observable.onErrorResumeNext(new ExceptionHandlerFunction());
                return observable;
            }
        };
    }

    public abstract Interceptor getInterceptor();

    public abstract <T> Function<T, T> getFunction();
}
