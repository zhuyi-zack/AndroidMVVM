package com.sephiroth.network;

import com.sephiroth.network.bean.GirlPageBean;

import io.reactivex.Observable;
import retrofit2.http.GET;

interface ApiService {

    @GET("data/category/Girl/type/Girl/page/1/count/10")
    Observable<GirlPageBean> getGirl();
}
