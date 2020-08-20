package com.sephiroth.androidmvvm.api;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface ApiService {

    @GET("data/category/Girl/type/Girl/page/1/count/10")
    Observable<GirlPageBean> getGirl();
}
