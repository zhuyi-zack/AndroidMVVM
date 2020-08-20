package com.sephiroth.androidmvvm.api;

import com.sephiroth.network.NetworkApi;
import com.sephiroth.network.exception.ExceptionHandler;

import java.io.IOException;

import io.reactivex.functions.Function;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class GirlNetworkApi extends NetworkApi {
    public static GirlNetworkApi sInstance;

    private GirlNetworkApi() {
    }

    public static GirlNetworkApi getInstance() {
        if (sInstance == null) {
            synchronized (GirlNetworkApi.class) {
                if (sInstance == null) {
                    sInstance = new GirlNetworkApi();
                }
            }
        }
        return sInstance;
    }

    public static <T> T getService(Class<T> service) {
        return getInstance().getRetrofit(service).create(service);
    }

    @Override
    public Interceptor getInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request.Builder builder = chain.request().newBuilder();
                builder.addHeader("header", "header");
                return chain.proceed(builder.build());
            }
        };
    }

    @Override
    public <T> Function<T, T> getFunction() {
        return new Function<T, T>() {
            @Override
            public T apply(T response) throws Exception {
                if (response instanceof GirlPageBean && ((GirlPageBean) response).getStatus() != 100) {
                    ExceptionHandler.ServerException serverException = new ExceptionHandler.ServerException();
                    serverException.code = ((GirlPageBean) response).getStatus();
                    serverException.message = "服务器返回数据异常";
                    throw serverException;
                }
                return response;
            }
        };
    }
}
