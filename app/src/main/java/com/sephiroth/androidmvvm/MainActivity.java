package com.sephiroth.androidmvvm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;

import com.sephiroth.androidmvvm.databinding.ActivityMainBinding;
import com.sephiroth.network.NetworkApi;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        mBinding.network.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == mBinding.network) {
            NetworkApi.getGirlList();
        }
    }
}