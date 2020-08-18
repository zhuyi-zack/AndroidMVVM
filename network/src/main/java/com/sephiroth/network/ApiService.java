package com.sephiroth.network;

import com.sephiroth.network.bean.GirlPageBean;

import retrofit2.Call;
import retrofit2.http.GET;

interface ApiService {

    @GET
    Call<GirlPageBean> getGirl();
}
