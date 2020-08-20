package com.sephiroth.androidmvvm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.sephiroth.androidmvvm.api.ApiService;
import com.sephiroth.androidmvvm.api.GirlNetworkApi;
import com.sephiroth.androidmvvm.api.GirlPageBean;
import com.sephiroth.androidmvvm.databinding.ActivityMainBinding;
import com.sephiroth.network.observer.BaseObserver;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mBinding.network.setOnClickListener(this);
    }

    @SuppressLint("CheckResult")
    @Override
    public void onClick(View view) {
        if (view == mBinding.network) {
            GirlNetworkApi.getService(ApiService.class)
                    .getGirl()
                    .compose(GirlNetworkApi.getInstance().getObservableTransformer(new BaseObserver<GirlPageBean>() {

                        @Override
                        public void onSuccess(GirlPageBean girlPageBean) {
                            Log.d("服务器返回数据", new Gson().toJson(girlPageBean));
                        }

                        @Override
                        public void onFailure(Throwable e) {

                        }
                    }));
        }
    }
}